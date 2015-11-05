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

public class Plataforma {
	private boolean estaVivo = true;
	private int salud = 2;
	private int saludAnterior = salud;

	private double velocidad = 1;

	private int estadoAnimacion = 0;
	public int animacion = 0;
	private int muriendo = 0;

	private int direccion;

	private ArrayList<Rectangle> colisionPlataforma = new ArrayList<Rectangle>();

	private boolean enMovimiento;
	public boolean golpeado = false;

	private HojaSprites hs;

	private BufferedImage imagenActual;

	private Mapa mapa;
	private int limitador = 0;
	Jugador jugador;

	private final int ANCHO = 11;
	private final int ALTO = 11;
	private int velocidadX = 1;
	private int velocidadY = 1;
	private Point posicionInicial = new Point(600, 450);
	private Point posicionFinal = new Point(600, 600);
	private boolean colisionJugador = false;

	public Plataforma(Mapa mapa, Point posicionInicial, Point posicionFinal) {

		this.posicionInicial = posicionInicial;
		this.mapa = mapa;

		hs = new HojaSprites("/imagenes/hojasPersonajes/ojo.png",
				Constantes.LADO_SPRITE, false);

	}

	public void actualizar(int posicionX, int posicionY) {

		actualizarColisiones(posicionX, posicionY);

		movimiento(posicionInicial);

		animar();

	}

	private void movimiento(Point posicionPlataforma) {
		double x = posicionPlataforma.x;
		double y = posicionPlataforma.y;
		//cuando llegue al final , se cambiara el punto final por el inicial para que cambie la direccion
		if(posicionInicial.x==posicionFinal.x){
			int temp=posicionInicial.x;
			posicionInicial.y=posicionFinal.x;
			posicionFinal.x=temp;
		}else if(posicionInicial.x>=posicionFinal.x){
			velocidadX = 1;
		}else if(posicionInicial.x<posicionFinal.x){
			velocidadX = -1;
		}
		
		
		if(posicionInicial.y==posicionFinal.y){
			int temp=posicionInicial.y;
			posicionInicial.y=posicionFinal.y;
			posicionFinal.y=temp;
		}else if(posicionInicial.y>=posicionFinal.y){
			velocidadY = 1;
		}else if(posicionInicial.y<posicionFinal.y){
			velocidadY = -1;
		}
		//limitador  de movimiento.
		if (limitador >= 2) {
			limitador = 0;
			x += (double) velocidadX * velocidad;
			y += (double) velocidadY * velocidad;
		} else {
			limitador++;
		}

		posicionPlataforma.setLocation(x, y);
	}

	private void actualizarColisiones(final int posicionX, final int posicionY) {
		if (!colisionPlataforma.isEmpty()) {
			colisionPlataforma.clear();
		}

		Rectangle arriba = new Rectangle(posicionInicial.x - posicionX - 2,
				posicionInicial.y - posicionY, ANCHO + 5, 2);

		colisionPlataforma.add(arriba);

	}

	private boolean colision(final int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); r++) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 11
					* (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY,
					Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (colisionPlataforma.get(0).intersects(areaFutura)) {
				colisionJugador = true;
				return true;
			}
		}
		colisionJugador = false;
		return false;
	}

	private void animar() {
		int frecuenciaAnimacion = 10;
		int limite = 6;

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
				estadoAnimacion = 1;
				break;
			case 2:
				estadoAnimacion = 2;
				break;
			case 3:
				estadoAnimacion = 2;
				break;
			case 4:
				estadoAnimacion = 1;
				break;
			case 5:
				estadoAnimacion = 0;
				break;
			}
			if (golpeado) {
				estadoAnimacion = 3;
				if (animacion >= 2) {
					golpeado = false;
				}
			}
		}

		determinarMovimiento();

		imagenActual = hs.obtenerSprite(direccion, estadoAnimacion)
				.obtenerImagen();
	}

	private void determinarMovimiento() {
		if (velocidadX == 1) {
			direccion = 1;
		}
		if (velocidadX == -1) {
			direccion = 0;
		}
		if (velocidadX == 0 && velocidadY == 0) {
			enMovimiento = false;
		} else {
			enMovimiento = true;
		}
		if (!enMovimiento) {
			estadoAnimacion = 0;
		}
	}

	public void dibujar(Graphics g, int posicionX, int posicionY) {

		g.drawImage(imagenActual, posicionInicial.x - posicionX - 10,
				posicionInicial.y - posicionY - 20, null);

		// g.setColor(Color.GREEN);
		//
		// for (int i = 0; i < colisionEnemigo.size(); i++)
		// if (!colisionEnemigo.isEmpty())
		// g.drawRect(colisionEnemigo.get(i).x, colisionEnemigo.get(i).y,
		// colisionEnemigo.get(i).width,
		// colisionEnemigo.get(i).height);

	}

	public ArrayList<Rectangle> obtenerColisiones() {
		return colisionPlataforma;
	}

	public boolean obtenerColision() {
		return colisionJugador;
	}

}
