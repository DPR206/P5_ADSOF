package visualizers;

import decision_tree.DecisionTree;

/**
 * Esta clase representa un visualizador en texto indentado que visita un árbol de decisión
 * @param <T> Tipo del objeto con el que se parametriza el árbol de decisión
 */
public class TextVisualizer<T> implements DecisionTreeVisitor<T> {
	
	/**
	 * Constructor del visualizador de texto
	 */
	public TextVisualizer() {
		
	}

	private final StringBuilder sb = new StringBuilder();
	private int depth = 0;

	@Override
	public void visit(DecisionTree<T> node) {
		String indent = "  ".repeat(depth);
		String marker = node.isLeaf() ? "(LEAF) " : "(BRANCH) ";
		sb.append(indent).append(marker).append(node.getName()).append("\n");

		depth++;
		for (DecisionTree<T> child : node.getChildren()) {
			child.accept(this);
		}
		depth--;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
