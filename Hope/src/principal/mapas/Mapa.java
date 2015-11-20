package principal.mapas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {

	private String partes[];

	private final int ancho;
	private final int alto;

	private final Point posicionInicial;
	private final Point puntoSalida;
	private final Point puntoEntrada;

	private Rectangle zonaSalida;

	private String siguienteMapa;

	private final Sprite[] paleta;

	private final boolean[] colisiones;
	private final boolean[] agua;
	private final int[] enemigos;
	private final String[] plataformas;
	private final String[] puertas;


	public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();
	public ArrayList<Rectangle> areasAgua = new ArrayList<Rectangle>();

	private final int[] sprites;

	private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2
			- Constantes.LADO_SPRITE / 2;
	private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2
			- Constantes.LADO_SPRITE / 2;

	public Mapa(final String ruta) {
		String contenido = CargadorRecursos.leerArchivoTexto(ruta);

		partes = contenido.split("\\*");

		ancho = Integer.parseInt(partes[0]);
		alto = Integer.parseInt(partes[1]);

		// hojas utilizadas
		String hojasUtilizadas = partes[2];
		String[] hojasSeparadas = hojasUtilizadas.split(",");

		// paleta
		String paletaEntera = partes[3];
		String[] partesPaleta = paletaEntera.split("#");

		paleta = asignarSprites(partesPaleta, hojasSeparadas);

		// colisiones
		String colisionesEnteras = partes[4];
		String[] partesColisiones = colisionesEnteras.split(",");

		colisiones = extraerColisiones(partesColisiones);
		agua = extraerAgua(partesColisiones);
		enemigos = extraerEnemigos(partesColisiones);
		plataformas=extraerPlataformas(partesColisiones);
		puertas=extraerPuertas(partesColisiones);

		// orden de los sprites
		String spritesEnteros = partes[5];
		String[] cadenasSprites = spritesEnteros.split(",");

		sprites = extraerSprites(cadenasSprites);

		// posicion jugador , salidas y entradas
		String posicion = partes[6];
		String[] posiciones = posicion.split(",");

		posicionInicial = new Point();
		posicionInicial.x = Integer.parseInt(posiciones[0])
				* Constantes.LADO_SPRITE;
		posicionInicial.y = Integer.parseInt(posiciones[1])
				* Constantes.LADO_SPRITE;

		String salida = partes[7];
		String[] datosSalida = salida.split(",");

		puntoSalida = new Point();
		puntoSalida.x = Integer.parseInt(datosSalida[0]);
		puntoSalida.y = Integer.parseInt(datosSalida[1]);
		siguienteMapa = datosSalida[2];
		puntoEntrada=new Point();
		puntoEntrada.x= Integer.parseInt(datosSalida[3])* Constantes.LADO_SPRITE;;
		puntoEntrada.y=Integer.parseInt(datosSalida[4])* Constantes.LADO_SPRITE;;

		zonaSalida = new Rectangle();

	}
	public Mapa(final String ruta,final Point entrada) {
		String contenido = CargadorRecursos.leerArchivoTexto(ruta);

		partes = contenido.split("\\*");

		ancho = Integer.parseInt(partes[0]);
		alto = Integer.parseInt(partes[1]);

		// hojas utilizadas
		String hojasUtilizadas = partes[2];
		String[] hojasSeparadas = hojasUtilizadas.split(",");

		// paleta
		String paletaEntera = partes[3];
		String[] partesPaleta = paletaEntera.split("#");

		paleta = asignarSprites(partesPaleta, hojasSeparadas);

		// colisiones
		String colisionesEnteras = partes[4];
		String[] partesColisiones = colisionesEnteras.split(",");

		colisiones = extraerColisiones(partesColisiones);
		agua = extraerAgua(partesColisiones);
		enemigos = extraerEnemigos(partesColisiones);
		plataformas=extraerPlataformas(partesColisiones);
		puertas=extraerPuertas(partesColisiones);


		// orden de los sprites
		String spritesEnteros = partes[5];
		String[] cadenasSprites = spritesEnteros.split(",");

		sprites = extraerSprites(cadenasSprites);

		// posicion jugador , salidas y entradas
		String posicion = partes[6];
		String[] posiciones = posicion.split(",");

//		posicionInicial = new Point();
//		posicionInicial.x = Integer.parseInt(posiciones[0])
//				* Constantes.LADO_SPRITE;
//		posicionInicial.y = Integer.parseInt(posiciones[1])
//				* Constantes.LADO_SPRITE;
		
		posicionInicial=entrada;

		String salida = partes[7];
		String[] datosSalida = salida.split(",");

		puntoSalida = new Point();
		puntoSalida.x = Integer.parseInt(datosSalida[0]);
		puntoSalida.y = Integer.parseInt(datosSalida[1]);
		siguienteMapa = datosSalida[2];
		puntoEntrada=new Point();
		puntoEntrada.x= Integer.parseInt(datosSalida[3]);
		puntoEntrada.y=Integer.parseInt(datosSalida[4]);

		zonaSalida = new Rectangle();

	}

	
	private Sprite[] asignarSprites(final String[] partesPaleta,
			final String[] hojasSeparadas) {
		Sprite[] paleta = new Sprite[partesPaleta.length];

		HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/"
				+ hojasSeparadas[0] + ".png", 32, false);

		for (int i = 0; i < partesPaleta.length; i++) {
			String spriteTemporal = partesPaleta[i];
			String[] partesSprite = spriteTemporal.split("-");

			int indicePaleta = Integer.parseInt(partesSprite[0]);
			int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);

			paleta[indicePaleta] = hoja.obtenerSprite(indiceSpriteHoja);

		}

		return paleta;

	}

	private int[] extraerEnemigos(final String[] cadenaColisiones) {
		int[] enemigos = new int[cadenaColisiones.length];
		for (int i = 0; i < cadenaColisiones.length; i++) {
			if(cadenaColisiones[i].length()!=1){
				enemigos[i] = 0;
			}else{
				enemigos[i] = Integer.parseInt(cadenaColisiones[i]);
			}
			
		}

		return enemigos;
	}
	private String[] extraerPuertas(final String[] cadenaColisiones){
		String [] plataformas=new String[cadenaColisiones.length];
		for (int i = 0; i < cadenaColisiones.length; i++) {
			if(cadenaColisiones[i].length()==2){
				if(cadenaColisiones[i].charAt(0)=='b'){
					plataformas[i] = cadenaColisiones[i];
				}else{
					plataformas[i]="0";
				}
			}else{
				plataformas[i]="0";
			}
		}
		return plataformas;
	}
	
	private String[] extraerPlataformas(final String[] cadenaColisiones){
		String [] plataformas=new String[cadenaColisiones.length];
		for (int i = 0; i < cadenaColisiones.length; i++) {
			if(cadenaColisiones[i].length()==2){
				if(cadenaColisiones[i].charAt(0)=='a'){
					plataformas[i] = cadenaColisiones[i];
				}else{
					plataformas[i]="0";
				}
			}else{
				plataformas[i]="0";
			}
		}
		return plataformas;
	}

	private boolean[] extraerAgua(final String[] cadenaColisiones) {
		boolean[] agua = new boolean[cadenaColisiones.length];

		for (int i = 0; i < cadenaColisiones.length; i++) {
			if (cadenaColisiones[i].equals("2")) {
				agua[i] = true;
			} else {
				agua[i] = false;
			}
		}

		return agua;
	}

	private boolean[] extraerColisiones(final String[] cadenaColisiones) {
		boolean[] colisiones = new boolean[cadenaColisiones.length];

		for (int i = 0; i < cadenaColisiones.length; i++) {
			if (cadenaColisiones[i].equals("1")) {
				colisiones[i] = true;
			} else {
				colisiones[i] = false;
			}
		}

		return colisiones;
	}

	private int[] extraerSprites(final String[] cadenaSprites) {
		ArrayList<Integer> sprites = new ArrayList<Integer>();

		for (int i = 0; i < cadenaSprites.length; i++) {

			sprites.add(Integer.parseInt(cadenaSprites[i]) - 1);

		}

		int[] vectorSprites = new int[sprites.size()];

		for (int i = 0; i < sprites.size(); i++) {
			vectorSprites[i] = sprites.get(i);
		}

		return vectorSprites;
	}

	public void actualizar(final int posicionX, final int posicionY) {
		actualizarAreasColision(posicionX, posicionY);
		actualizarZonaSalida(posicionX, posicionY);
		actualizarAgua(posicionX, posicionY);
	}

	private void actualizarAreasColision(final int posicionX,
			final int posicionY) {
		if (!areasColision.isEmpty()) {
			areasColision.clear();
		}
		for (int y = 0; y < this.alto; y++) {
			for (int x = 0; x < this.ancho; x++) {
				int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

				if (colisiones[x + y * this.ancho]) {
					final Rectangle r = new Rectangle(puntoX, puntoY,
							Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
					areasColision.add(r);
				}
			}
		}
	}

	private void actualizarAgua(final int posicionX, final int posicionY) {
		if (!areasAgua.isEmpty()) {
			areasAgua.clear();
		}
		for (int y = 0; y < this.alto; y++) {
			for (int x = 0; x < this.ancho; x++) {
				int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

				if (agua[x + y * this.ancho]) {
					final Rectangle r = new Rectangle(puntoX, puntoY,
							Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
					areasAgua.add(r);
				}
			}
		}
	}

	private void actualizarZonaSalida(final int posicionX, final int posicionY) {
		int puntoX = puntoSalida.x * Constantes.LADO_SPRITE - posicionX
				+ MARGEN_X;
		int puntoY = puntoSalida.y * Constantes.LADO_SPRITE - posicionY
				+ MARGEN_Y;

		zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE,
				Constantes.LADO_SPRITE);
	}

	public void dibujar(Graphics g, int posicionX, int posicionY) {

		for (int y = 0; y < this.alto; y++) {
			for (int x = 0; x < this.ancho; x++) {
				BufferedImage imagen = paleta[sprites[(x + y * this.ancho)]]
						.obtenerImagen();

				int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

				g.drawImage(imagen, puntoX, puntoY, null);

				// g.setColor(Color.GREEN);
				//
				// for (int r = 0; r < areasAgua.size(); r++) {
				// Rectangle area = areasAgua.get(r);
				// g.drawRect(area.x, area.y, area.width, area.height);
				// }
			}
		}
	}

	public Rectangle obtenerBordes(final int posicionX, final int posicionY,
			final int altoJugador, final int anchoJugador) {
		int x = MARGEN_X - posicionX + anchoJugador;
		int y = MARGEN_Y - posicionY + altoJugador;
		int ancho = this.ancho * Constantes.LADO_SPRITE - anchoJugador * 2;
		int alto = this.alto * Constantes.LADO_SPRITE - altoJugador * 2;

		return new Rectangle(x, y, ancho, alto);
	}

	public Point obtenerPosicionInicial() {
		return posicionInicial;
	}

	public Point obtenerPuntoSalida() {
		return puntoSalida;
	}

	public String obtenerSiguienteMapa() {
		return siguienteMapa;
	}

	public Rectangle obtenerZonaSalida() {
		return zonaSalida;
	}

	public int[] obtenerEnemigos() {
		return enemigos;
	}
	public String[] obtenerPlataformas() {
		return plataformas;
	}
	public Point obtenerEntrada(){
		return puntoEntrada;
	}

	public int obtenerAlto() {
		return alto;
	}

	public int obtenerAncho() {
		return ancho;
	}
	public String[] obtenerPuertas() {
		return puertas;
	}
}
