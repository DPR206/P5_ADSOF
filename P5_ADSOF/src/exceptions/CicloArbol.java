package exceptions;

public class CicloArbol extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * @param nodoAntiguo
	 * @param nodoNuevo
	 */
	public CicloArbol(String nodoAntiguo, String nodoNuevo) {
		super("La unión entre los nodos " + nodoNuevo + " y " + nodoAntiguo + " genera un ciclo");
	}

}
