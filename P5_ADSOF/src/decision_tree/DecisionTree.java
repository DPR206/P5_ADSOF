/**
 * 
 */
package decision_tree;

import java.util.*;
import java.util.function.Predicate;

import dataset.*;
import exceptions.*;

/**
 * 
 */
public class DecisionTree<T> {

	private String name;
	private Predicate<T> predicate;
	private DecisionTree<T> parent;
	private List<DecisionTree<T>> children = new ArrayList<>();

	public DecisionTree() {
		this.predicate = p -> true;
	}

	public DecisionTree(String name) {
		this.name = name;
		this.predicate = p -> true;
	}

	public DecisionTree(String name, Predicate<T> p) {
		this.name = name;
		this.predicate = p;
	}

	public DecisionTree<T> encontrarNodo(String label) throws NotExistingNode {
		if (this.name != null && this.name.equals(label))
			return this;

		for (DecisionTree<T> child : children) {
			try {
				return child.encontrarNodo(label);
			} catch (NotExistingNode ignored) {
			}
		}

		throw new NotExistingNode(label);
	}

	private DecisionTree<T> root() {
		DecisionTree<T> current = this;
		while (current.parent != null)
			current = current.parent;
		return current;
	}

	public DecisionTree<T> node(String name) throws NotExistingNode {
		if (this.name == null) {
			this.name = name;
			return this;
		}
		return root().encontrarNodo(name);
	}

	@SuppressWarnings("unchecked")
	public DecisionTree<T> withCondition(String label, Predicate<? super T> condition) throws CicloArbol {

		try {
			root().encontrarNodo(label);
			throw new CicloArbol(label, this.name);
		} catch (NotExistingNode e) {
		}

		DecisionTree<T> child = new DecisionTree<>(label, (Predicate<T>) condition);
		child.setParent(this);
		this.children.add(child);

		return this;
	}


	public DecisionTree<T> otherwise(String label) throws CicloArbol {

		try {
			root().encontrarNodo(label);
			throw new CicloArbol(label, this.name);
		} catch (NotExistingNode e) {
			// Nombre nuevo, seguimos
		}

		// Predicado = negación de TODOS los hijos actuales
		Predicate<T> otherwisePredicate = p -> true;
		for (DecisionTree<T> child : children) {
			otherwisePredicate = otherwisePredicate.and(child.predicate.negate());
		}

		DecisionTree<T> otherwiseNode = new DecisionTree<>(label, otherwisePredicate);
		otherwiseNode.setParent(this);
		this.children.add(otherwiseNode);

		return this;
	}

	public Map<String, List<T>> predict(Dataset<T> dataset) {
		return predict(dataset.getObjects());
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<T>> predict(T... values) {
		return predict(List.of(values));
	}

	private Map<String, List<T>> predict(List<T> values) {
		Map<String, List<T>> results = new HashMap<>();

		for (T value : values) {
			evaluate(value).stream().findFirst()
					.ifPresent(leaf -> results.computeIfAbsent(leaf, k -> new ArrayList<>()).add(value));
		}

		return results;
	}

	private boolean evaluateRecursive(T input, List<String> results) {
		if (!this.predicate.test(input))
			return false;

		if (children.isEmpty()) {
			results.add(this.name);
			return true;
		}

		for (DecisionTree<T> child : children) {
			if (child.evaluateRecursive(input, results))
				return true;
		}

		return false;
	}

	private List<String> evaluate(T input) {
		List<String> results = new ArrayList<>();
		evaluateRecursive(input, results);
		return results;
	}

	public Predicate<T> getPredicate(String label) throws NotExistingNode {
		List<Predicate<T>> path = new ArrayList<>();
		if (!findPredicatePath(root(), label, path))
			throw new NotExistingNode(label);

		return path.stream().reduce(p -> true, Predicate::and);
	}

	private boolean findPredicatePath(DecisionTree<T> node, String label, List<Predicate<T>> path) {
		path.add(node.predicate);

		if (node.name != null && node.name.equals(label))
			return true;

		for (DecisionTree<T> child : node.children) {
			if (findPredicatePath(child, label, path))
				return true;
		}

		path.remove(path.size() - 1);
		return false;
	}

	private void setParent(DecisionTree<T> parent) {
		this.parent = parent;

	}

	@Override
	public String toString() {
		return name + " " + children;
	}

	public String getName() {
		return name;
	}

}
