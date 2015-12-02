package principal.herramientas;

public class CambiarResolucion {

	static{
		System.loadLibrary("ChangeDisplaySettings");
	}
	
	  public native void addNumber(int n);
}
