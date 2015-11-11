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

	private double velocidad = 1;

	private int estadoAnimacion = 0;
	public int animacion = 0;

	private int direccion;

	private ArrayList<Rectangle> colisionPlataforma = new ArrayList<Rectangle>();

	private boolean enMovimiento;
	public boolean golpeado = false;

	private HojaSprites hs;

	private BufferedImage imagenActual;

	private Mapa mapa;
	private int limitador = 0;
	Jugador jugador;

	private final int ANCHO = 32;
	private final int ALTO = 2;
	private int velocidadX = 1;
	private int velocidadY = 1;
	private Point posicionInicial;
	private Point posicionFinal;
	private Point posicionPlataforma;
	private boolean colisionJugador = false;


	public Plataforma(Mapa mapa, Point posicionInicial, Point posicionFinal) {

		this.posicionInicial = posicionInicial;
		this.posicionFinal=posicionFinal;
		this.mapa = mapa;
		this.posicionPlataforma=new Point(posicionInicial.x,posicionInicial.y);

		hs = new HojaSprites("/imagenes/hojasPersonajes/objetos.png",
				Constantes.LADO_SPRITE, false);

	}

	public void actualizar(int posicionX, int posicionY) {

		actualizarColisiones(posicionX, posicionY);

		movimiento(posicionPlataforma);

		animar();

	}

	private void movimiento(Point posicionPlataforma) {
		double x = posicionPlataforma.x;
		double y = posicionPlataforma.y;
		//cuando llegue al final , se cambiara el punto final por el inicial para que cambie la direccion

	
		velocidadY=0;
		if(posicionPlataforma.x==posicionFinal.x){
			
			int temp=posicionInicial.x;
			posicionInicial.x=posicionFinal.x;
			posicionFinal.x=temp;
			
			posicionPlataforma.x=posicionInicial.x;
			
		}else{
			if(posicionPlataforma.x>posicionFinal.x){
				velocidadX = -1;
			}else if(posicionPlataforma.x<posicionFinal.x){
				velocidadX = 1;
			}
			
		}
		
		//limitador  de movimiento.
//		if (limitador >= 2) {
//			limitador = 0;
//			x += (double) velocidadX * velocidad;
//			y += (double) velocidadY * velocidad;
//		} else {
//			limitador++;
//		}
		x += (double) velocidadX * velocidad;
		y += (double) velocidadY * velocidad;
		posicionPlataforma.setLocation(x, y);
	}

	private void actualizarColisiones(final int posicionX, final int posicionY) {
		if (!colisionPlataforma.isEmpty()) {
			colisionPlataforma.clear();
		}

		Rectangle arriba = new Rectangle(posicionPlataforma.x - posicionX - 8,
				posicionPlataforma.y - posicionY+1, ANCHO , ALTO);

		colisionPlataforma.add(arriba);

	}

	public void colision(boolean colision) {
		colisionJugador=colision;
	}

	private void animar() {
	
		if(colisionJugador){
			estadoAnimacion=1;
		}else{
			estadoAnimacion=0;
		}
		
			
		

		determinarMovimiento();

		imagenActual = hs.obtenerSprite(1, estadoAnimacion)
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

		g.drawImage(imagenActual, posicionPlataforma.x - posicionX - 10,
				posicionPlataforma.y - posicionY - 20, null);

		// g.setColor(Color.GREEN);
		//
		// for (int i = 0; i < colisionEnemigo.size(); i++)
		
//		 if (!colisionPlataforma.isEmpty()){
//			 g.drawRect(colisionPlataforma.get(0).x, colisionPlataforma.get(0).y,
//					 colisionPlataforma.get(0).width,
//					 colisionPlataforma.get(0).height);
//		 }
		
	}

	public ArrayList<Rectangle> obtenerColisiones() {
		return colisionPlataforma;
	}

	public boolean obtenerColision() {
		return colisionJugador;
	}
	public int obtenerVelocidadX(){
		return velocidadX;
	}

}
