package principal.maquinaDeEstado.estados.menuinicial;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.Raton;
import principal.herramientas.CargadorRecursos;
import principal.mapas.Mapa;
import principal.maquinaDeEstado.EstadoJuego;
import principal.maquinaDeEstado.GestorEstados;
import principal.sprites.HojaSprites;

public class GestorMenuInicial implements EstadoJuego{
	GestorEstados ge;
	Mapa mapaFondo=new Mapa("/mapas/nivel1");
	
	private int posicionX=32*10, posicionY=32*10;
	
	private int posVentanaX=Constantes.ANCHO_PANTALLA_COMPLETA/7;
	private int posVentanaY=Constantes.ALTO_PANTALLA_COMPLETA/8;
	private int ventanaAlto=Constantes.ALTO_PANTALLA_COMPLETA/4;
	private int ventanaAncho=Constantes.ANCHO_PANTALLA_COMPLETA/5;
	
	private Raton raton;
	private Rectangle ratonRec;
	private Rectangle iniciar;
	private Rectangle opciones;
	private Rectangle salir;
	
	
	
	private BufferedImage img;
	private BufferedImage hope;
	private BufferedImage mario;
	private HojaSprites hs;
	
	private int animacion=0,estadoAnimacion=0,posicionM=0;
	private boolean i,o,s;
	
	public GestorMenuInicial(GestorEstados ge,Raton raton){
		this.ge=ge;
		this.raton=raton;
		iniciar=new Rectangle( posVentanaX+ventanaAncho/4+20, posVentanaY+32,32*3,32);
		opciones=new Rectangle(posVentanaX+ventanaAncho/4+20, posVentanaY+32*2+10,32*3,32);
		salir=new Rectangle(posVentanaX+ventanaAncho/4+20, posVentanaY+32*3+20,32*3,32);
		
		img=CargadorRecursos.cargarImagenCompatibleOpaca("/imagenes/hojasPersonajes/a.png");
		hope=CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoVentana.png");
		hs= new HojaSprites("/imagenes/hojasPersonajes/1.png", Constantes.LADO_SPRITE, false);
		actualizarColisiones();
	}

	@Override
	public void actualizar() {
		animarFondo();
		animar();
		actualizarColisiones();
		click();
	}
	private void animarFondo(){
		int frecuenciaActualizacion = 2;
	

		if (GestorPrincipal.obtenerAps() % frecuenciaActualizacion == 0) {
			posicionX++;
			if(posicionX>(110*32)){
				posicionX=32*10;
			}
			
		}
		
	}
	private void animar(){
		int frecuenciaAnimacion = 10;
		int limite = 6;

		if (GestorPrincipal.obtenerAps() % frecuenciaAnimacion == 0) {

			animacion++;
			if (animacion >= limite) {
				animacion = 0;
			}
			switch (animacion) {
			case 0:
				estadoAnimacion = 6;
				posicionM++;
				break;
			case 1:
				estadoAnimacion = 6;
				break;
			case 2:
				estadoAnimacion = 6;
				break;
			case 3:
				estadoAnimacion = 7;
				break;
			case 4:
				estadoAnimacion = 7;
				break;
			case 5:
				estadoAnimacion = 7;
				break;
			}
			
		}

		if((ventanaAncho-32+posVentanaX-posicionM-100)<=posVentanaX){
			posicionM=0;
		}
		mario = hs.obtenerSprite(3, estadoAnimacion).obtenerImagen();
	}

	@Override
	public void dibujar(Graphics g) {
		
		mapaFondo.dibujar(g, posicionX, posicionY);
		
		g.setColor(Color.BLACK);
		g.fillRect(posVentanaX,posVentanaY , ventanaAncho, ventanaAlto);
		g.setColor(Color.white);
		g.drawRect(posVentanaX,posVentanaY , ventanaAncho, ventanaAlto);
		

		//detalles
		g.setColor(Color.magenta);
		g.drawRect(posVentanaX+1,posVentanaY +1, ventanaAncho-2, ventanaAlto-2);
		g.drawRect(posVentanaX+5,posVentanaY +5, ventanaAncho-10, ventanaAlto-10);
		g.drawRect(posVentanaX+10,posVentanaY +10, ventanaAncho-20, ventanaAlto-20);
		g.drawRect(posVentanaX+15,posVentanaY +15, ventanaAncho-30, ventanaAlto-30);

		g.drawImage(hope,iniciar.x-120,iniciar.y-50,null);
		g.drawImage(mario,ventanaAncho-32+posVentanaX-posicionM,posVentanaY-32,null);
		
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawRect(iniciar.x, iniciar.y, iniciar.width, iniciar.height);
		g.drawString("Iniciar", iniciar.x+32, iniciar.y+20);
		
		g.drawRect(opciones.x, opciones.y, opciones.width, opciones.height);
		g.drawString("Opciones", opciones.x+21, opciones.y+20);
		
		g.drawRect(salir.x, salir.y, salir.width, salir.height);
		g.drawString("Salir", salir.x+35, salir.y+20);
		
		
		if(i){
			g.drawImage(img,iniciar.x+100, iniciar.y,null);
		}
		if(o){
			g.drawImage(img,opciones.x+100, opciones.y,null);
		}
		if(s){
			g.drawImage(img,salir.x+100, salir.y,null);
		}
		
		
	}

	@Override
	public void continuar() {
		// TODO Auto-generated method stub
		
	}
	private void actualizarColisiones() {
		if(raton!=null){
			ratonRec = new Rectangle(raton.obtenerPos().x/2, raton.obtenerPos().y/2 ,5,5);
		}
		if(ratonRec.intersects(iniciar)){
			i=true;
		}else{
			i=false;
		}
		if(ratonRec.intersects(opciones)){
			o=true;
		}else{
			o=false;
		}
		if(ratonRec.intersects(salir)){
			s=true;
		}else{
			s=false;
		}
	}
	private void click(){
		if(i){
			if(raton.obtenerClick()){
				ge.reiniciarJuego();
			}
			

		}else if(o){
			if(raton.obtenerClick()){
			
			}
		

		}else if(s){
			if(raton.obtenerClick()){
				System.exit(0);
			}
			
		}
	}

}
