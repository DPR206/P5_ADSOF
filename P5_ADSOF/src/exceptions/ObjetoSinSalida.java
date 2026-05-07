package exceptions;

public class ObjetoSinSalida extends DecisionTreeDatasetException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de la excepción
	 * @param objeto El objeto que no encuentra salida en el decisionTree
	 */
	public ObjetoSinSalida(Object objeto) {
		super("El objeto " + objeto.toString() + " no puede evaluarse porque no existe ninguna hoja a la que pueda ir");
	}	
	
}
