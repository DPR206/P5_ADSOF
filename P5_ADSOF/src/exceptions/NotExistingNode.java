package exceptions;

/**
 * Esta excepción se lanza en caso de que el nodo que se está buscando no exista en el dataset
 */
public class NotExistingNode extends DecisionTreeDatasetException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la excepción
	 * @param etiqueta Nombre del nodo que no se encuentra
	 */
	public NotExistingNode(String etiqueta) {
		super("No se encuentra el nodo con etiqueta " + etiqueta);
	}
	
}
