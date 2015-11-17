package principal.maquinaDeEstado.estados.juego;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import principal.Constantes;
import principal.entes.Bloque;
import principal.entes.Enemigo;
import principal.entes.Jugador;
import principal.entes.Objeto;
import principal.entes.Plataforma;
import principal.mapas.Mapa;
import principal.maquinaDeEstado.EstadoJuego;
import principal.maquinaDeEstado.GestorEstados;

public class GestorJuego implements EstadoJuego {

	private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
	private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
	private final GestorEstados ge;
	Mapa mapa;
	// File audio = new File("recursos/audio/temaPrincipalHope.wav");
	Jugador jugador;

	ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
	ArrayList<Plataforma> plataformas = new ArrayList<Plataforma>();
	ArrayList<Bloque> bloques = new ArrayList<Bloque>();
	ArrayList<Objeto> corazon = new ArrayList<Objeto>();

	public GestorJuego(GestorEstados ge) {
		this.ge = ge;

		// System.out.println(audio.getAbsolutePath());
		mapa = new Mapa("/mapas/mapa0");

		iniciarJugador();

		generadorEnemigos();

		generadorPlataformas();

		generadorBloques();
		// try {
		// Clip sonido = AudioSystem.getClip();
		// try {
		// sonido.open(AudioSystem.getAudioInputStream(audio));
		// sonido.start();
		// } catch (IOException | UnsupportedAudioFileException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// } catch (LineUnavailableException e) {
		// e.printStackTrace();
		// }
	}

	public GestorJuego(GestorEstados ge, String url, Point puntoEntrada) {
		this.ge = ge;
		mapa = new Mapa(url, puntoEntrada);

		iniciarJugador();
		generadorEnemigos();
		generadorPlataformas();
		generadorBloques();
	}

	private ArrayList<Bloque> generadorBloques() {

		bloques.clear();

		for (int y = 0; y < this.mapa.obtenerAlto(); y++) {
			for (int x = 0; x < this.mapa.obtenerAncho(); x++) {
				int puntoX = x * Constantes.LADO_SPRITE  + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE  + MARGEN_Y;

				if (4 == mapa.obtenerEnemigos()[x + y * this.mapa.obtenerAncho()]) {

					final Point punto = new Point(puntoX, puntoY);
					final Bloque e = new Bloque(jugador, punto, mapa);

					bloques.add(e);
				}
			}
		}

		return bloques;
	}

	private ArrayList<Enemigo> generadorEnemigos() {

		enemigos.clear();

		for (int y = 0; y < this.mapa.obtenerAlto(); y++) {
			for (int x = 0; x < this.mapa.obtenerAncho(); x++) {
				int puntoX = x * Constantes.LADO_SPRITE + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE + MARGEN_Y;

				if (3 == mapa.obtenerEnemigos()[x + y * this.mapa.obtenerAncho()]) {

					final Point punto = new Point(puntoX, puntoY);
					final Enemigo e = new Enemigo(mapa, punto);

					enemigos.add(e);
				}
			}
		}

		return enemigos;
	}

	private ArrayList<Plataforma> generadorPlataformas() {

		plataformas.clear();
		String temp = "";
		Point puntoTemp = new Point(0, 0);
		// cada dos puntos se creara una plataforma que se desplaza de punto a
		// punto
		for (int y = 0; y < this.mapa.obtenerAlto(); y++) {
			for (int x = 0; x < this.mapa.obtenerAncho(); x++) {
				int puntoX = x * Constantes.LADO_SPRITE  + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE  + MARGEN_Y;

				if (mapa.obtenerPlataformas()[x + y * this.mapa.obtenerAncho()].length() == 2) {

					if (mapa.obtenerPlataformas()[x + y * this.mapa.obtenerAncho()].equals(temp)) {

						final Point punto = new Point(puntoX, puntoY);
						final Plataforma p = new Plataforma(mapa, puntoTemp, punto);

						plataformas.add(p);

					}
					puntoTemp = new Point(puntoX, puntoY);
					temp = mapa.obtenerPlataformas()[x + y * this.mapa.obtenerAncho()];

				}
			}
		}

		return plataformas;
	}

	private void recargarJuego() {
		final String ruta = "/mapas/" + mapa.obtenerSiguienteMapa();

		iniciarMapa(ruta, mapa.obtenerEntrada());
		jugador.establecerPosicionX(mapa.obtenerPosicionInicial().x);
		jugador.establecerPosicionY(mapa.obtenerPosicionInicial().y);
		iniciarJugador();

		generadorEnemigos();
		generadorPlataformas();
		generadorBloques();

		// jugador.establecerPosicionX(mapa.obtenerPosicionInicial().x);
		// jugador.establecerPosicionY(mapa.obtenerPosicionInicial().y);
	}

	private void iniciarJugador() {
		jugador = new Jugador(mapa, enemigos, plataformas, bloques);

	}

	private void iniciarMapa(final String ruta, final Point entrada) {
		mapa = new Mapa(ruta, entrada);
	}

	public void actualizar() {
		if (jugador.obtenerLIMITE_DERECHA().intersects(mapa.obtenerZonaSalida())) {
			recargarJuego();
		}
		jugador.actualizar();
		mapa.actualizar((int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());

		if (!enemigos.isEmpty()) {
			for (int i = 0; i < enemigos.size(); i++) {
				enemigos.get(i).actualizar((int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
			}
		}
		if (!bloques.isEmpty()) {
			for (int i = 0; i < bloques.size(); i++) {
				bloques.get(i).actualizar((int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
			}
		}
		if (!plataformas.isEmpty()) {
			for (int i = 0; i < plataformas.size(); i++) {
				plataformas.get(i).actualizar((int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
			}
		}

		if (!corazon.isEmpty()) {
			for (int i = 0; i < corazon.size(); i++) {
				corazon.get(i).actualizar((int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
				if (jugador.obtenerLIMITE_DERECHA().intersects(corazon.get(i).obtenerColision())
						|| jugador.obtenerLIMITE_IZQUIERDA().intersects(corazon.get(i).obtenerColision())) {
					corazon.remove(i);// cojer corazon
				}
			}
		}

	}

	public void dibujar(Graphics g) {

		mapa.dibujar(g, (int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());

		// if (enemigo.estaVivo() || enemigo.obtenerMuriendo() < 5) {
		for (int i = 0; i < enemigos.size(); i++) {
			if (enemigos.get(i).estaVivo()) {
				enemigos.get(i).dibujar(g, (int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
			} else {
				Point p = new Point(enemigos.get(i).obtenerPosicion());
				corazon.add(new Objeto(mapa, 0, p));
				enemigos.remove(i);
				i--;
			}

		}
		for (int i = 0; i < corazon.size(); i++) {
			corazon.get(i).dibujar(g, (int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());

		}

		for (int i = 0; i < plataformas.size(); i++) {
			plataformas.get(i).dibujar(g, (int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
		}

		for (int i = 0; i < bloques.size(); i++) {
			bloques.get(i).dibujar(g, (int) jugador.obtenerPosicionX(), (int) jugador.obtenerPosicionY());
		}

		jugador.dibujar(g);

		//
		// } else {
		// corazon.dibujar(g, (int) jugador.obtenerPosicionX(),
		// (int) jugador.obtenerPosicionY());
		//
		// }

		// g.setColor(Color.RED);
		// g.drawString("X = " + jugador.obtenerPosicionX(), 20, 20);
		// g.drawString("Y = " + jugador.obtenerPosicionY(), 20, 30);
		// g.drawString("Siguiente mapa: " + mapa.obtenerSiguienteMapa(), 20,
		// 100);
		// g.drawString("Cordenadas Salida X: " +
		// mapa.obtenerPuntoSalida().getX(), 20, 110);
		// g.drawString("Cordenadas Salida Y: " +
		// mapa.obtenerPuntoSalida().getY(), 20, 120);
		//
		// g.fillRect(mapa.obtenerZonaSalida().x, mapa.obtenerZonaSalida().y,
		// mapa.obtenerZonaSalida().width,
		// mapa.obtenerZonaSalida().height);
	}
}
