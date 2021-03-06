package principal;

import principal.graficos.SuperficieDibujo;
import principal.graficos.Ventana;
import principal.maquinaDeEstado.GestorEstados;

public class GestorPrincipal {
	
	

	private boolean enFuncionamiento = false;
	private String titulo;
	private int ancho;
	private int alto;

	private SuperficieDibujo sd;
	private Ventana ventana;
	private GestorEstados ge;

	private static int aps = 0;
	private static int fps = 0;

	private GestorPrincipal(final String titulo, final int ancho, final int alto) {
		this.titulo = titulo;
		this.alto = alto;
		this.ancho = ancho;
	}


	public static void main(String[] args) {
		GestorPrincipal gp = new GestorPrincipal("Hope",
				Constantes.ANCHO_PANTALLA_COMPLETA,
				Constantes.ALTO_PANTALLA_COMPLETA);

		Constantes.ANCHO_JUEGO = 640;
		Constantes.ALTO_JUEGO = 360;

		gp.iniciarJuego();
		gp.iniciarBuclePrincipal();
	}

	private void iniciarJuego() {
		enFuncionamiento = true;
		inicializar();

	}

	private void inicializar() {

		sd = new SuperficieDibujo(ancho, alto);
		ventana = new Ventana(titulo, sd);
		ge = new GestorEstados(sd);

	}

	private void iniciarBuclePrincipal() {
		int actualizacionesAcumuladas = 0;
		int framesAcumulados = 0;

		final int NS_POR_SEGUNDO = 1000000000;
		final int APS_OBJETIVO = 60;
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0;

		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				actualizar();
				delta--;
				actualizacionesAcumuladas++;
				aps = actualizacionesAcumuladas;

			}

			dibujar();
			framesAcumulados++;
			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {

				aps = actualizacionesAcumuladas;
				fps = framesAcumulados;
				actualizacionesAcumuladas = 0;
				framesAcumulados = 0;
				referenciaContador = System.nanoTime();
			}
		}
	}

	private void actualizar() {

		ge.actualizar();
		sd.actualizar();
	}

	private void dibujar() {
		sd.dibujar(ge);
	}

	public static int obtenerFps() {
		return fps;
	}

	public static int obtenerAps() {
		return aps;
	}

}
