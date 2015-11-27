package principal.maquinaDeEstado.estados.menujuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.Raton;
import principal.herramientas.CargadorRecursos;
import principal.maquinaDeEstado.EstadoJuego;
import principal.maquinaDeEstado.GestorEstados;


public class GestorMenu implements EstadoJuego{
	private int gameOver=0;
	private String go="";
	private final GestorEstados ge;
	private Rectangle continuar;
	private Rectangle reiniciar;
	private Rectangle salir;
	private Rectangle ratonRec;
	private Raton raton;
	private BufferedImage img;
	private boolean cont=false,re=false,sal=false;
	
	public GestorMenu(GestorEstados ge,Raton raton){
		this.ge=ge;
		continuar=new Rectangle(265, 180,32*3,32);
		reiniciar=new Rectangle(265, 220,32*3,32);
		salir=new Rectangle(265, 260,32*3,32);
		this.raton=raton;
		img=CargadorRecursos.cargarImagenCompatibleOpaca("/imagenes/hojasPersonajes/a.png");
		actualizarColisiones();
	}

	@Override
	public void actualizar() {
		gameOver=escribirGameOver(gameOver);
		actualizarColisiones();
		click();
		
	}
	private void click(){
		if(cont){
			if(raton.obtenerClick()){
				ge.continuarJuego();
			}
			

		}else if(re){
			if(raton.obtenerClick()){
				ge.reiniciarJuego();
			}
		

		}else if(sal){
			if(raton.obtenerClick()){
				System.exit(0);
			}
			
		}
	}
	private void actualizarColisiones() {
		if(raton!=null){
			ratonRec = new Rectangle(raton.obtenerPos().x/2, raton.obtenerPos().y/2 ,5,5);
		}
		if(ratonRec.intersects(continuar)){
			cont=true;
		}else{
			cont=false;
		}
		if(ratonRec.intersects(reiniciar)){
			re=true;
		}else{
			re=false;
		}
		if(ratonRec.intersects(salir)){
			sal=true;
		}else{
			sal=false;
		}
	}
	
	
	private int escribirGameOver(int gameOver){
		int frecuenciaActualizacion = 5;
		int limite = 14;

		if (GestorPrincipal.obtenerAps() % frecuenciaActualizacion == 0) {
			
//			System.out.println("over"+go+gameOver);
			
			if (gameOver < limite) {
				gameOver++;
			}
			switch (gameOver) {
			case 0:
				go = "";
				break;
			case 1:
				go = "G";
				break;
			case 2:
				go = "Ga";
				break;
			case 3:
				go = "Gam";
				break;
			case 4:
				go = "Game";
				break;
			case 5:
				go = "Game ";
				break;
			case 6:
				go = "Game o";
				break;
			case 7:
				go = "Game ov";
				break;
			case 8:
				go = "Game ove";
				break;
			case 9:
				go = "Game over";
				break;
				
			case 10:
				go = "Game over.";
				break;
			case 11:
				go = "Game over.";
				break;
			case 12:
				go = "Game over.";
				break;
			case 13:
				go = "Game over.";
				break;
			}
		}
		return gameOver;
	}

	@Override
	public void dibujar(Graphics g) {
	
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		g.drawString(go, 230, 130);
		
		if(gameOver>=11){
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.drawRect(continuar.x, continuar.y, continuar.width, continuar.height);
			g.drawString("Continuar", continuar.x+21, continuar.y+20);
		}if(gameOver>=12){
		
			g.drawRect(reiniciar.x, reiniciar.y, reiniciar.width, reiniciar.height);
			g.drawString("Reiniciar", reiniciar.x+23, reiniciar.y+20);
		}if(gameOver>=13){	
			g.drawRect(salir.x, salir.y, salir.width, salir.height);
			g.drawString("Salir", salir.x+35, salir.y+20);
			if(cont){
				g.drawImage(img,continuar.x+100, continuar.y,null);
			}
			if(re){
				g.drawImage(img,reiniciar.x+100, reiniciar.y,null);
			}
			if(sal){
				g.drawImage(img,salir.x+100, salir.y,null);
			}
			
		
		}
	
//		if(raton!=null){
//			g.drawRect(ratonRec.x, ratonRec.y, ratonRec.width, ratonRec.height);
//		}
		
	
		
	}

	@Override
	public void continuar() {
		// TODO Auto-generated method stub
		
	}
	

}
