package principal.entes.enemigos;

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

public class Enemigo {
	private boolean estaVivo = true;
	private int salud = 2;
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

	private BufferedImage imagenActual,imagenActual2;

	private Mapa mapa;
	private int limitador = 0;
	Jugador jugador;

	private final int ANCHO;
	private final int ALTO;
	private int velocidadX = 1;
	private int velocidadY = 1;
	private Point posicionInicial;
	private boolean colisionJugador = false;
	private  ArrayList<Puerta[]> puertas;
	private int id;
	private String spriteE="/imagenes/hojasPersonajes/ojo.png";
	private String spriteJ="/imagenes/hojasPersonajes/boss.png";
	

	public Enemigo(Mapa mapa, Point posicionInicial,ArrayList<Puerta[]> puertas,int id) {

		this.posicionInicial = posicionInicial;
		this.mapa = mapa;
		this.puertas=puertas;
		this.id=id;
		if(id==3){
			hs = new HojaSprites(spriteE, Constantes.LADO_SPRITE, false);
			ALTO=11;ANCHO=11;
		}else{
			hs = new HojaSprites(spriteJ, Constantes.LADO_SPRITE, false);
			ANCHO = 30; ALTO = 32*2;
			salud=10;
		}
		

	}

	public void actualizar(int posicionX, int posicionY) {

		estaVivo();
		actualizarColisiones(posicionX, posicionY);
		if (estaVivo) {
			movimiento(posicionInicial);

		} else {

			if (GestorPrincipal.obtenerAps() % 10 == 0) {
				muriendo++;
			}

		}
		if(id==3){
			animar();
		}else{
			animarJefe();
		}

	}

	private void movimiento(Point posicionEnemigo) {
		double x = posicionEnemigo.x;
		double y = posicionEnemigo.y;
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
		}
		if (colisionIzquierda(velocidadX) || enColisionIzquierdaPuerta(velocidadX)) {
			velocidadX = 1;
		}
		if(id==3){
			if (limitador >= 2) {
				limitador = 0;
				x += (double) velocidadX * velocidad;
			} else {
				limitador++;
			}
		}else{
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
		}
		

		posicionEnemigo.setLocation(x, y);
	}

	private void actualizarColisiones(final int posicionX, final int posicionY) {
		if (!colisionEnemigo.isEmpty()) {
			colisionEnemigo.clear();
		}
		if (estaVivo) {
			Rectangle arriba = new Rectangle(posicionInicial.x - posicionX - 2, posicionInicial.y - posicionY,
					ANCHO + 5, 3);

			Rectangle abajo = new Rectangle(posicionInicial.x - posicionX, posicionInicial.y - posicionY + ALTO - 3,
					ANCHO, 3);

			Rectangle izquierda = new Rectangle(posicionInicial.x - posicionX, posicionInicial.y - posicionY + 2, 3,
					ALTO - 2);

			Rectangle derecha = new Rectangle(posicionInicial.x - posicionX + ANCHO - 2, posicionInicial.y - posicionY
					+ 2, 3, ALTO - 2);

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

		imagenActual = hs.obtenerSprite(direccion, estadoAnimacion).obtenerImagen();
	}
	private void animarJefe() {
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

//		g.drawString("" + saludAnterior + " salud:" + salud, 10, 180);
		if(id==3){
			g.drawImage(imagenActual, posicionInicial.x - posicionX - 10, posicionInicial.y - posicionY - 20, null);
	
		}else{
			g.drawImage(imagenActual, posicionInicial.x - posicionX, posicionInicial.y - posicionY+32, null);
			g.drawImage(imagenActual2, posicionInicial.x - posicionX, posicionInicial.y - posicionY, null);
		
		}
		
	

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
