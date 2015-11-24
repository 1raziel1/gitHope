package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Puerta {
	// bloque que se pueda empujar

	private Point posicion;


	private final int ANCHO = 30;
	private final int ALTO = 30;

	private boolean abierta=false;
	private HojaSprites hs;

	private BufferedImage imagenActual;
	private int tiempoAbrir;
	private long tiempo;


	private int estadoAnimacion = 0;
	public int animacion = 0;

	private ArrayList<Rectangle> colisionPuerta = new ArrayList<Rectangle>();



	public Puerta(Jugador jugador, Point posicion, Mapa mapa,int pos) {
		this.posicion = posicion;
		hs = new HojaSprites("/imagenes/hojasPersonajes/objetos.png", Constantes.LADO_SPRITE, false);
		imagenActual = hs.obtenerSprite(3,0).obtenerImagen();
	}

	public void actualizar(int posicionX, int posicionY) {
		if(abierta){
			if (!colisionPuerta.isEmpty()) {
				colisionPuerta.clear();
			}
		}else{
			actualizarColisiones(posicionX, posicionY);
		}
		animar();
	
		

	}

	private void animar() {
		int frecuenciaAnimacion = 15; 
		int limite = 4;
		if(abierta){
			if((tiempoAbrir*700)+tiempo<=System.currentTimeMillis())
			if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0 && animacion<3) {
	
				animacion++;	
				if (animacion >= limite) {
					animacion = 3;
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
						estadoAnimacion = 6;
						break;
			
					}
			}
		}else{
			estadoAnimacion=0;
		}

	

		imagenActual = hs.obtenerSprite(3,estadoAnimacion).obtenerImagen();

	}

	private void actualizarColisiones(final int posicionX, final int posicionY) {
		if (!colisionPuerta.isEmpty()) {
			colisionPuerta.clear();
		}
		
			Rectangle colision = new Rectangle(posicion.x - posicionX, posicion.y - posicionY, ANCHO, ALTO);

			colisionPuerta.add(colision);
	}
	

	public void dibujar(Graphics g, int posicionX, int posicionY) {

		g.drawImage(imagenActual, posicion.x - posicionX, posicion.y - posicionY, null);
//		g.setColor(Color.red);
//		if(!colisionPuerta.isEmpty()){
//			for(int i=0;i<colisionPuerta.size();i++){
//				g.drawRect(colisionPuerta.get(i).x, colisionPuerta.get(i).y, colisionPuerta.get(i).width, colisionPuerta.get(i).height);
//			}
//		}
		
	}

	public ArrayList<Rectangle> obtenerColisiones() {
		return colisionPuerta;
	}
	public void abrirPuerta(boolean abrir,int id){
		abierta=abrir;
		tiempoAbrir=id;
		tiempo=System.currentTimeMillis();
		
	}

}
