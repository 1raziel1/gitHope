package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla izquierda = new Tecla();
	public Tecla derecha = new Tecla();
	public Tecla espacio = new Tecla();
	public Tecla f3 = new Tecla();
	public Tecla shift = new Tecla();
	public Tecla p = new Tecla();

	public Tecla j = new Tecla();

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			arriba.teclaPulsada();
			break;
		case KeyEvent.VK_S:
			abajo.teclaPulsada();
			break;
		case KeyEvent.VK_A:
			izquierda.teclaPulsada();
			break;
		case KeyEvent.VK_D:
			derecha.teclaPulsada();
			break;
		case KeyEvent.VK_SPACE:
			espacio.teclaPulsada();
			break;
		case KeyEvent.VK_J:
			j.teclaPulsada();
			break;
		case KeyEvent.VK_SHIFT:
			shift.teclaPulsada();
			break;
		case KeyEvent.VK_F3:
			f3.teclaPulsada();
			break;
		case KeyEvent.VK_P:
			p.teclaPulsada();
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			arriba.teclaLiberada();
			break;
		case KeyEvent.VK_S:
			abajo.teclaLiberada();
			break;
		case KeyEvent.VK_A:
			izquierda.teclaLiberada();
			break;
		case KeyEvent.VK_D:
			derecha.teclaLiberada();
			break;
		case KeyEvent.VK_SPACE:
			espacio.teclaLiberada();
			break;
		case KeyEvent.VK_J:
			j.teclaLiberada();
			break;
		case KeyEvent.VK_SHIFT:
			shift.teclaLiberada();
			break;
		case KeyEvent.VK_F3:
			f3.teclaLiberada();
			break;
		case KeyEvent.VK_P:
			p.teclaLiberada();
			break;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
