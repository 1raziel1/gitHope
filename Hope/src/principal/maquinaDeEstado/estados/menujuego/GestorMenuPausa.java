package principal.maquinaDeEstado.estados.menujuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.Raton;
import principal.mapas.Mapa;
import principal.maquinaDeEstado.EstadoJuego;
import principal.maquinaDeEstado.GestorEstados;
import principal.sprites.HojaSprites;

public class GestorMenuPausa implements EstadoJuego{
	
	
	Mapa mapaFondo=new Mapa("/mapas/nivelPausa");
	private int posicionX=32*10,posicionY=33*10;
	
	private Raton raton;
	private Rectangle ratonRec;
	
	private boolean c=false;
	
	private HojaSprites hs;
	private BufferedImage mario;
	private GestorEstados ge;
	private Rectangle continuar;
	private int animacion=0,estadoAnimacion=0;
	
	public GestorMenuPausa(GestorEstados ge,Raton raton){
		hs=new HojaSprites("/imagenes/hojasPersonajes/1.png", Constantes.LADO_SPRITE, false);
		mario=hs.obtenerSprite(0, 1).obtenerImagen();
		this.raton=raton;
		continuar=new Rectangle(Constantes.CENTRO_VENTANA_X,Constantes.CENTRO_VENTANA_Y,32*3,32);
		
		this.ge=ge;
	}
	
	
	@Override
	public void actualizar() {
		animar();
		actualizarColisiones();
		click();
	}
	private void animar(){
		int frecuenciaAnimacion = 10;
		int limite = 4;

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
				estadoAnimacion = 0;
				break;
			case 3:
				estadoAnimacion = 2;
				break;
		
			}
			posicionX+=5;
			
			if(posicionX>450){
				posicionX=32*10;
			}
			
		}
		
		
		mario = hs.obtenerSprite(2, estadoAnimacion).obtenerImagen();
	}
	private void actualizarColisiones() {
		if(raton!=null){
			ratonRec = new Rectangle(raton.obtenerPos().x/2, raton.obtenerPos().y/2 ,5,5);
		}
		if(ratonRec.intersects(continuar)){
			c=true;
		}else{
			c=false;
		}
	}
	private void click(){
		if(c){
			if(raton.obtenerClick()){
				ge.cambiarEstadoActual(0);
			}
		}
	}
	@Override
	public void dibujar(Graphics g) {

		mapaFondo.dibujar(g, posicionX, posicionY);
		g.drawImage(mario, 100,100 ,150,150,null);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Constantes.ANCHO_PANTALLA_COMPLETA/2, Constantes.ALTO_PANTALLA_COMPLETA/9);
		g.fillRect(0, Constantes.ALTO_PANTALLA_COMPLETA/3+20, Constantes.ANCHO_PANTALLA_COMPLETA/2, Constantes.ALTO_PANTALLA_COMPLETA/7);
		g.fillRect(Constantes.ALTO_PANTALLA_COMPLETA/3+20, 0, Constantes.ANCHO_PANTALLA_COMPLETA/2, Constantes.ALTO_PANTALLA_COMPLETA);
		g.fillRect(0, 0,100, Constantes.ALTO_PANTALLA_COMPLETA);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		g.drawString("PAUSA", continuar.x-10, continuar.y-100);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawRect(continuar.x, continuar.y, continuar.width, continuar.height);
		g.drawString("Continuar", continuar.x+20, continuar.y+20);
		
	}

	@Override
	public void continuar() {
		
	}

}
