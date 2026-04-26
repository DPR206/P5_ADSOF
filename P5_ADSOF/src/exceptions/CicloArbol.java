package exceptions;

public class CicloArbol extends Exception{

	private static final long serialVersionUID = 1L;
	private String nodoAntiguo;
	private String nodoNuevo;
	
	/**
	 * @param nodoAntiguo
	 * @param nodoNuevo
	 */
	public CicloArbol(String nodoAntiguo, String nodoNuevo) {
		this.nodoAntiguo = nodoAntiguo;
		this.nodoNuevo = nodoNuevo;
	}

	@Override
	public String toString() {
		return "La unión entre los nodos " + nodoNuevo + " y " + nodoAntiguo + " genera un ciclo\n";
	}

}
