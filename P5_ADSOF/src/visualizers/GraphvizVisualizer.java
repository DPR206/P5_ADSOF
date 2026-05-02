package visualizers;

import java.util.IdentityHashMap;
import java.util.Map;

import decision_tree.DecisionTree;

public class GraphvizVisualizer<T> implements DecisionTreeVisitor<T> {

    private final StringBuilder sb = new StringBuilder();
    private int nodeCounter = 0;
    private final Map<DecisionTree<T>, Integer> nodeIds = new IdentityHashMap<>();

    public GraphvizVisualizer() {
        sb.append("digraph DecisionTree {\n");
        sb.append("  node [shape=rectangle, style=filled, fillcolor=lightblue];\n");
    }

    @Override
    public void visit(DecisionTree<T> node) {
        int id = nodeIds.computeIfAbsent(node, n -> nodeCounter++);
        String label = node.getName() != null ? node.getName() : "?";

        if (node.isLeaf()) {
            sb.append("  ").append(id)
              .append(" [label=\"").append(label)
              .append("\", shape=ellipse, fillcolor=lightgreen];\n");
        } else {
            sb.append("  ").append(id)
              .append(" [label=\"").append(label).append("\"];\n");
        }

        for (DecisionTree<T> child : node.getChildren()) {
            int childId = nodeIds.computeIfAbsent(child, n -> nodeCounter++);
            sb.append("  ").append(id).append(" -> ").append(childId).append(";\n");
            child.accept(this);
        }
    }

    public String getDot() {
        return sb.toString() + "}";
    }

    @Override
    public String toString() { return getDot(); }
}
