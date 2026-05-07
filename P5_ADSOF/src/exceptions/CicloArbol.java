package exceptions;

/**
 * Esta excepción se lanza en caso de producirse un ciclo en el árbol
 */
public class CicloArbol extends DecisionTreeDatasetException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la excepción
	 * @param nodoAntiguo Nodo anterior
	 * @param nodoNuevo Nodo nuevo que se está intentando crear
	 */
	public CicloArbol(String nodoAntiguo, String nodoNuevo) {
		super("La unión entre los nodos " + nodoNuevo + " y " + nodoAntiguo + " genera un ciclo");
	}

}
