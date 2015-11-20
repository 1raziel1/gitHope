package principal.control;

public class Tecla {

	private boolean pulsada = false;
	private long ultimaPulsacion;
	private long primeraPulsacion;
	private int i=0;

	public void teclaPulsada() {
		pulsada = true;
	}

	public void teclaLiberada() {
		pulsada = false;
	}

	public boolean estaPulsada() {
		return pulsada;
	}

	public long obtenerUltimaPulsacion() {
		return ultimaPulsacion;
	}
	public long obtenerPrimeraPulsacion(){
		return primeraPulsacion;
	}
}
