package principal.control;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;

public class Raton extends MouseAdapter implements MouseListener{
    

	private final Cursor cursor;
	private Point posicion;
	private static boolean click=false;
	
	
	public Raton(final SuperficieDibujo sd) {
		Toolkit configuracion = Toolkit.getDefaultToolkit();

		BufferedImage icono = CargadorRecursos
				.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoCursor.png");

		Point punta = new Point(0, 0);
		this.cursor = configuracion.createCustomCursor(icono, punta,
				"Cursor por defecto");
		
		posicion=new Point();
		actualizarPosicion(sd);
		
	}
	public void mouseClicked(MouseEvent e) {
    	click=true;
    	
    }
	public void mouseReleased(MouseEvent e) {
    	click=false;
    	
    }
	

	
	public void actualizar(final SuperficieDibujo sd){
		click=false;
		actualizarPosicion(sd);
	}
	public void dibujar(Graphics g){
		g.setColor(Color.red);
		g.drawString("raton x: "+posicion.getX(), 10, 200);
		g.drawString("raton y: "+posicion.getY(), 10, 210);
	}
	
	private void actualizarPosicion(final SuperficieDibujo sd){
		final Point posicionInicial= MouseInfo.getPointerInfo().getLocation();
		
		SwingUtilities.convertPointFromScreen(posicionInicial, sd);
		
		posicion.setLocation(posicionInicial.getX(),posicionInicial.getY());
	}

	public Cursor ObtenerCursor() {
		return this.cursor;
	}
	public Point obtenerPos(){
		return posicion;
	}
	public boolean obtenerClick(){
		return click;
	}
}
