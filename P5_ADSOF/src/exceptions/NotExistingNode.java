package exceptions;

public class NotExistingNode extends Exception{
	private static final long serialVersionUID = 1L;
	
	public NotExistingNode(String etiqueta) {
		super("No se encuentra el nodo con etiqueta " + etiqueta);
	}
	
}
