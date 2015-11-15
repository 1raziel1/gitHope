package principal.entes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Bloque {
	// bloque que se pueda empujar
	private Mapa mapa;
	private Point posicion;
	private Jugador jugador;

	private final int ANCHO = 30;
	private final int ALTO = 30;
	private int velocidadX = 0;
	private int velocidadY = 1;
	private boolean enMovimiento;
	private HojaSprites hs;
	private int limitador = 0;

	private BufferedImage imagenActual;
	private double velocidad = 1;

	private int estadoAnimacion = 0;
	public int animacion = 0;
	private Rectangle[] limitesJugador;
	private int direccion;
	private ArrayList<Rectangle> colisionBloque = new ArrayList<Rectangle>();

	private boolean colisionJugadorIzquierda = false;
	private boolean colisionJugadorDerecha = false;

	public Bloque(Jugador jugador, Point posicion, Mapa mapa) {
		this.jugador = jugador;
		this.posicion = posicion;
		this.mapa = mapa;
		limitesJugador = jugador.obtenerLimitesJugador();

		hs = new HojaSprites("/imagenes/hojasPersonajes/objetos.png", Constantes.LADO_SPRITE, false);
	}

	public void actualizar(int posicionX, int posicionY) {

		actualizarColisiones(posicionX, posicionY);

		movimiento(posicion);

		animar();

	}

	private void movimiento(Point posicion) {
		double x = posicion.x;
		double y = posicion.y;
		velocidadX = 0;
		if (colisionAbajo(velocidadY)) {
			velocidadY = 0;
		} else {
			velocidadY = 1;
		}
		if (velocidadY >= 1 && !colisionAbajo(velocidadY)) {
			y += (double) velocidadY * velocidad;
		}

		if (colisionDerecha(velocidadX)) {
			colisionJugadorDerecha = false;
			colisionJugadorIzquierda = false;
			velocidadX = 0;
		} else if (colisionJugadorIzquierda) {

			velocidadX = -1;
		}
		if (colisionIzquierda(velocidadX)) {
			colisionJugadorDerecha = false;
			colisionJugadorIzquierda = false;
			velocidadX = 0;
		} else if (colisionJugadorDerecha) {
			velocidadX = 1;
		}

		x += (double) velocidadX * velocidad;

		posicion.setLocation(x, y);

		if (jugador.velocidadX != velocidadX || jugador.velocidadY != velocidadY) {
			colisionJugadorDerecha = false;
			colisionJugadorIzquierda = false;
		}

	}

	private void animar() {
		int frecuenciaAnimacion = 30;
		int limite = 6;

		if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0) {

			animacion++;
			if (animacion >= limite) {
				animacion = 0;
			}
			if (velocidadX == -1) {
				estadoAnimacion = 2;
			} else if (velocidadX == 1) {
				estadoAnimacion = 0;
			} else {
				switch (animacion) {
				case 0:
					estadoAnimacion = 1;
					break;
				case 1:
					estadoAnimacion = 1;
					break;
				case 2:
					estadoAnimacion = 1;
					break;
				case 3:
					estadoAnimacion = 1;
					break;
				case 4:
					estadoAnimacion = 1;
					break;
				case 5:
					estadoAnimacion = 3;
					break;
				}

			}
			if (velocidadY > 0) {
				estadoAnimacion = 3;
			}

		}

		imagenActual = hs.obtenerSprite(2, estadoAnimacion).obtenerImagen();

	}

	private void actualizarColisiones(final int posicionX, final int posicionY) {
		if (!colisionBloque.isEmpty()) {
			colisionBloque.clear();
		}

		Rectangle arriba = new Rectangle(posicion.x - posicionX, posicion.y - posicionY, ANCHO, 2);

		Rectangle abajo = new Rectangle(posicion.x - posicionX, posicion.y - posicionY + ALTO, ANCHO, 2);

		Rectangle izquierda = new Rectangle(posicion.x - posicionX, posicion.y - posicionY + 2, 2, ALTO);

		Rectangle derecha = new Rectangle(posicion.x - posicionX + ANCHO - 2, posicion.y - posicionY + 2, 2, ALTO);

		colisionBloque.add(arriba);
		colisionBloque.add(abajo);
		colisionBloque.add(izquierda);
		colisionBloque.add(derecha);

	}

	private boolean colisionAbajo(final int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 2 * (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (colisionBloque.get(1).intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean colisionIzquierda(final int velocidadX) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (colisionBloque.get(2).intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean colisionDerecha(final int velocidadX) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
			;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (colisionBloque.get(3).intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	public void colisionJugadorIzquierda(boolean colision) {

		colisionJugadorIzquierda = colision;

	}

	public void colisionJugadorDerecha(boolean colision) {

		colisionJugadorDerecha = colision;

	}

	public void dibujar(Graphics g, int posicionX, int posicionY) {

		g.drawImage(imagenActual, posicion.x - posicionX, posicion.y - posicionY, null);

	}

	public ArrayList<Rectangle> obtenerColisiones() {
		return colisionBloque;
	}

}
