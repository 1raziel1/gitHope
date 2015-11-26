package principal.entes.enemigos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.entes.Jugador;
import principal.entes.Puerta;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Jefe {
	

	
		private boolean estaVivo = true;
		private int salud = 10;
		private int saludMaxima = salud;
		private boolean objeto = false;
		private double velocidad = 1;

		private int estadoAnimacion = 0;
		public int animacion = 0;
		private int muriendo = 0;

		private int direccion;

		private ArrayList<Rectangle> colisionEnemigo = new ArrayList<Rectangle>();

		private boolean enMovimiento;
		public boolean golpeado = false;

		private HojaSprites hs;

		private BufferedImage imagenActual;
		private BufferedImage imagenActual2;
	
		private Mapa mapa;
		private int limitador = 0;
		Jugador jugador;

		private final int ANCHO = 30;
		private final int ALTO = 32*2;
		private int velocidadX = 1;
		private int velocidadY = 1;
		private Point posicionInicial;
		private boolean colisionJugador = false;
		private  ArrayList<Puerta[]> puertas;
		Point posicion=new Point(0,0);


		public Jefe(Mapa mapa, Point posicionInicial,ArrayList<Puerta[]> puertas) {

			this.posicion = posicionInicial;
			this.mapa = mapa;
			this.puertas=puertas;
			hs = new HojaSprites("/imagenes/hojasPersonajes/boss.png", Constantes.LADO_SPRITE, false);
			salud=1;

		}

		public void actualizar(int posicionX, int posicionY) {
			
			estaVivo();
			actualizarColisiones(posicionX, posicionY);
			movimiento(posicion);
			if (estaVivo) {
				
				
			} else {

				if (GestorPrincipal.obtenerAps() % 10 == 0) {
					muriendo++;
				}

			}
			animar();

		}

		private void movimiento(Point posicion) {
			double x = posicion.x;
			double y = posicion.y;
			velocidad=1;
			if (colisionAbajo(velocidadY)) {
				velocidadY = 0;
			} else {
				velocidadY = 1;
			}
			if (velocidadY >= 1 && !colisionAbajo(velocidadY)) {
				y += (double) velocidadY * velocidad;
			}
			if (colisionDerecha(velocidadX) ||  enColisionDerechaPuerta(velocidadX)) {
				velocidadX = -1;
			}if (colisionIzquierda(velocidadX) || enColisionIzquierdaPuerta(velocidadX)) {
				velocidadX = 1;
			}
			
			if(salud>=8){
				velocidad=1;
			}else if(salud>=5){
				velocidad=1;
			}else if(salud>=2){
				velocidad=2;
			}else{
				velocidad=3;
			}
			
			x += (double) velocidadX * velocidad;
		
			
			posicion.setLocation(x, y);
		}

		private void actualizarColisiones(final int posicionX, final int posicionY) {
			if (!colisionEnemigo.isEmpty()) {
				colisionEnemigo.clear();
			}
			if (estaVivo) {
				Rectangle arriba = new Rectangle(posicion.x - posicionX - 2, posicion.y - posicionY,
						ANCHO + 5, 2);

				Rectangle abajo = new Rectangle(posicion.x - posicionX, posicion.y - posicionY + ALTO - 2,
						ANCHO, 2);

				Rectangle izquierda = new Rectangle(posicion.x - posicionX, posicion.y - posicionY, 2,
						ALTO);

				Rectangle derecha = new Rectangle(posicion.x - posicionX + ANCHO - 2, posicion.y - posicionY
						, 2, ALTO);

				colisionEnemigo.add(arriba);
				colisionEnemigo.add(abajo);
				colisionEnemigo.add(izquierda);
				colisionEnemigo.add(derecha);
			}

		}

		private boolean colisionAbajo(final int velocidadY) {
			for (int r = 0; r < mapa.areasColision.size(); r++) {
				final Rectangle area = mapa.areasColision.get(r);

				int origenX = area.x;
				int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;

				final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

				if (colisionEnemigo.get(1).intersects(areaFutura)) {
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

				if (colisionEnemigo.get(2).intersects(areaFutura)) {
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

				if (colisionEnemigo.get(3).intersects(areaFutura)) {
					return true;
				}
			}
			return false;
		}
		private boolean enColisionIzquierdaPuerta(final int velocidadX) {
			for (int r = 0; r < puertas.size(); r++) {
				for(int i=0;i<puertas.get(r).length;i++){
					if(!puertas.get(r)[i].obtenerColisiones().isEmpty()){
						final Rectangle area = puertas.get(r)[i].obtenerColisiones().get(0);

						int origenX = area.x + velocidadX * (int) velocidad +3 * (int) velocidad;
						int origenY = area.y;

						final Rectangle areaFutura = new Rectangle(origenX, origenY, 
								puertas.get(r)[i].obtenerColisiones().get(0).width,
								puertas.get(r)[i].obtenerColisiones().get(0).height);
						if (colisionEnemigo.get(2).intersects(areaFutura)) {
							return true;
						}
					}
					
				}
				
			
			}

			return false;
		}
		private boolean enColisionDerechaPuerta(final int velocidadX) {
			for (int r = 0; r < puertas.size(); r++) {
				for(int i=0;i<puertas.get(r).length;i++){
					if(!puertas.get(r)[i].obtenerColisiones().isEmpty()){
						final Rectangle area = puertas.get(r)[i].obtenerColisiones().get(0);

						int origenX = area.x + velocidadX * (int) velocidad -3 * (int) velocidad;
						int origenY = area.y;

							final Rectangle areaFutura = new Rectangle(origenX, origenY, 
								puertas.get(r)[i].obtenerColisiones().get(0).width,
								puertas.get(r)[i].obtenerColisiones().get(0).height);
							if (colisionEnemigo.get(3).intersects(areaFutura)) {
								return true;
							}
					}
				
				}
				
			
			}

			return false;
		}

		private void animar() {
			int frecuenciaAnimacion = 10;
			int limite = 4;

			if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0) {

				animacion++;
				if (animacion >= limite) {
					animacion = 0;
				}
				switch (animacion) {
				case 0:
					estadoAnimacion = 1;
					break;
				case 1:
					estadoAnimacion = 3;
					break;
				case 2:
					estadoAnimacion = 1;
					break;
				case 3:
					estadoAnimacion = 5;
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
			imagenActual = hs.obtenerSprite(direccion, estadoAnimacion).obtenerImagen();
			imagenActual2 = hs.obtenerSprite(direccion, 0).obtenerImagen();
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
	

//			g.drawString("" + saludAnterior + " salud:" + salud, 10, 180);

			g.drawImage(imagenActual, posicion.x - posicionX, posicion.y - posicionY+32, null);
			g.drawImage(imagenActual2, posicion.x - posicionX, posicion.y - posicionY, null);
		
//			 g.setColor(Color.GREEN);
//			 g.drawRect( posicion.x -posicionX, posicionY,10,10);
//			 
//			 for (int i = 0; i < colisionEnemigo.size(); i++)
//			 if (!colisionEnemigo.isEmpty())
//			 g.drawRect(colisionEnemigo.get(i).x, colisionEnemigo.get(i).y,
//			 colisionEnemigo.get(i).width,
//			 colisionEnemigo.get(i).height);

		}

		public Point obtenerPosicion() {

			int x = posicionInicial.x;
			int y = posicionInicial.y + 5;

			return new Point(x, y);

		}

		public ArrayList<Rectangle> obtenerColisiones() {
			return colisionEnemigo;
		}

		public boolean estaVivo() {
			if (salud <= 0) {
				estaVivo = false;
			}
			return estaVivo;
		}

		public void recivirDano(int dano) {
			this.salud -= dano;
			golpeado = true;
			animacion = 0;
		}

		public int obtenerMuriendo() {
			return muriendo;
		}

		public boolean obtenerColisionEnemiga() {
			return colisionJugador;
		}

		public boolean obtenerObjeto() {

			return objeto;
		}

		public void cambiarObjeto(boolean o) {
			objeto = o;
		}

	

}
