package principal.entes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Objeto {

	private int animacion = 0;
	private int estadoAnimacion = 0;

	private int id;

	private Rectangle colisionObjeto;
	private Point posicionObjeto;
	private HojaSprites hs;
	private BufferedImage imagenActual;
	private Mapa mapa;

	private int velocidadY = 1;
	private double velocidad = 1;

	public Objeto(Mapa mapa, int id, Point posicionObjeto) {
		this.mapa = mapa;
		this.id = id;
		this.posicionObjeto = posicionObjeto;

		hs = new HojaSprites("/imagenes/hojasPersonajes/objetos.png", Constantes.LADO_SPRITE, false);

	}

	public void actualizar(int posicionX, int posicionY) {
		animar();
		actualizarColisiones(posicionX, posicionY);
		movimiento(posicionObjeto);

	}

	private void actualizarColisiones(final int posicionX, final int posicionY) {

		colisionObjeto = new Rectangle(posicionObjeto.x - posicionX, posicionObjeto.y - posicionY - 10, 5, 5);

	}

	private boolean colisionAbajo(final int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 2 * (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (colisionObjeto.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private void movimiento(Point posicionObjeto) {
		double x = posicionObjeto.x;
		double y = posicionObjeto.y;

		if (colisionAbajo(velocidadY)) {
			velocidadY = 0;
		} else {
			velocidadY = 1;
		}
		if (velocidadY >= 1 && !colisionAbajo(velocidadY)) {
			y += (double) velocidadY * velocidad;
		}

		posicionObjeto.setLocation(x, y);
	}

	private void animar() {
		int frecuenciaAnimacion = 10;
		int limite = 3;

		if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0) {
			animacion++;
			if (animacion >= limite) {
				animacion = 0;
			}
			switch (animacion) {
			case 0:
				estadoAnimacion = 0;
				break;
			case 1:
				estadoAnimacion = 0;
				break;
			case 2:
				estadoAnimacion = 1;
				break;

			}
		}

		imagenActual = hs.obtenerSprite(id, estadoAnimacion).obtenerImagen();

	}

	public void dibujar(Graphics g, int posicionX, int posicionY) {
		g.drawImage(imagenActual, posicionObjeto.x - posicionX - 10, posicionObjeto.y - posicionY - 25, null);
		// if (colisionObjeto != null) {
		// g.drawRect(colisionObjeto.x, colisionObjeto.y, 5, 5);
		// }

	}

	public Rectangle obtenerColision() {
		return colisionObjeto;
	}
}
