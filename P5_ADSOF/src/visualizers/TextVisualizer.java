package visualizers;

import decision_tree.DecisionTree;

public class TextVisualizer<T> implements DecisionTreeVisitor<T> {

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
