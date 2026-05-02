package visualizers;

import decision_tree.DecisionTree;

public interface DecisionTreeVisitor<T> {
    void visit(DecisionTree<T> node);
}