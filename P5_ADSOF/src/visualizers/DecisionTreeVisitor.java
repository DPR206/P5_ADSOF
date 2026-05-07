package visualizers;

import decision_tree.DecisionTree;

/**
 * Esta interfaz representa un objeto que visita a un decision tree, de acuerdo al patrón de diseño Visitor
 * @param <T> Tipo del objeto con el que se parametriza el árbol de decisión
 */
public interface DecisionTreeVisitor<T> {
	/**
	 * Visita un nodo de un árbol de decisión
	 * @param node Nodo que se está visitando
	 */
    void visit(DecisionTree<T> node);
}