package decision_tree;

import java.util.*;
import java.util.function.Predicate;

import dataset.*;
import exceptions.*;
import visualizers.DecisionTreeVisitor;

/**
 * Esta clase representa un árbol de decisión
 * 
 * @param <T> Tipo parametrizado del árbol
 */
public class DecisionTree<T> {

	/** Nombre del nodo raíz */
	private String name;
	/** Condidión del nodo raíz */
	private Predicate<T> predicate;
	/** Nodo padre */
	private DecisionTree<T> parent;
	/** Lista de nodos hijos */
	private List<DecisionTree<T>> children = new ArrayList<>();

	/**
	 * Constructor de un árbol de decisión nuevo
	 */
	public DecisionTree() {
		this.name = "root";
		this.predicate = p -> true;
	}

	/**
	 * Constructor de un árbol de decisión que va a ser hijo
	 * 
	 * @param name Nombre del nodo
	 * @param p    Condición asociada a este nodo
	 */
	private DecisionTree(String name, Predicate<T> p) {
		this.name = name;
		this.predicate = p;
	}

	/**
	 * Encuentra un nodo dentro del árbol a partir de su nombre
	 * 
	 * @param label Nombre del nodo
	 * @return Árbol que representa el nodo y sus hijos
	 * @throws NotExistingNode, se lanza en caso de que no exista un nodo con ese
	 *                          nombre
	 */
	private DecisionTree<T> encontrarNodo(String label) throws NotExistingNode {
		if (this.name != null && this.name.equals(label))
			return this;

		for (DecisionTree<T> child : children) {
			try {
				return child.encontrarNodo(label);
			} catch (NotExistingNode ignored) {
				// Eso es porque no existe en esa rama, pero no significa que no exista
			}
		}

		throw new NotExistingNode(label);
	}

	/**
	 * Devuelve el nodo raíz de cualquier árbol
	 * 
	 * @return El árbol donde el nodo raíz es la raíz del árbol general
	 */
	private DecisionTree<T> root() {
		DecisionTree<T> current = this;
		while (current.parent != null)
			current = current.parent;
		return current;
	}

	/**
	 * Devuelve el árbol correspondiente un nodo a partir de su nombre
	 * 
	 * @param name Nombre del nodo
	 * @return Árbol asociado a dicho nodo
	 * @throws NotExistingNode, se lanza en caso de que no exista un nodo con ese
	 *                          nombre
	 */
	public DecisionTree<T> node(String name) throws NotExistingNode {
		if (this.name == null) {
			this.name = name;
			return this;
		}
		return root().encontrarNodo(name);
	}

	/**
	 * Crea un nodo hijo cuya condición es la especificada
	 * 
	 * @param label     Nombre del nodo hijo
	 * @param condition Condición de dicho nodo
	 * @return El árbol sobre el que está operando
	 * @throws CicloArbol, se lanza si se produce un ciclo en la creación del nuevo
	 *                     nodo
	 */
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

	/**
	 * Crea un nodo cuya condición es la negación de todos los otros nodos en su
	 * nivel
	 * 
	 * @param label Nombre del nodo
	 * @return El árbol sobre el que está operando
	 * @throws CicloArbol, se lanza si se produce un ciclo en la creación del nuevo
	 *                     nodo
	 */
	public DecisionTree<T> otherwise(String label) throws CicloArbol {

		try {
			root().encontrarNodo(label);
			throw new CicloArbol(label, this.name);
		} catch (NotExistingNode e) {
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

	/**
	 * Predice las hojas del árbol en las que debe caer cada objeto del dataset
	 * 
	 * @param dataset Dataset con los objetos que se van a evaluar
	 * @return Mapa que corresponde cada nombre de nodo con los objetos que caerían
	 *         en él
	 * @throws ObjetoSinSalida, se lanza si un objeto no es capaz de caer en ninguna
	 *                          hoja
	 */
	public Map<String, List<T>> predict(Dataset<T> dataset) throws ObjetoSinSalida {
		return predict(dataset.getObjects());
	}

	/**
	 * Predice las hojas del árbol en las que debe caer cada objeto
	 * 
	 * @param values Diferentes objetos que se van a evaluar
	 * @return Mapa que corresponde cada nombre de nodo con los objetos que caerían
	 *         en él
	 * @throws ObjetoSinSalida, se lanza si un objeto no es capaz de caer en ninguna
	 *                          hoja
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List<T>> predict(T... values) throws ObjetoSinSalida {
		return predict(List.of(values));
	}

	/**
	 * Método auxiliar que predice sobre una lista de objetos para unificar métodos
	 * predict
	 * 
	 * @param values Lista de objetos que se van a evaluar
	 * @return Mapa que corresponde cada nombre de nodo con los objetos que caerían
	 *         en él
	 * @throws ObjetoSinSalida, se lanza si un objeto no es capaz de caer en ninguna
	 *                          hoja
	 */
	private Map<String, List<T>> predict(List<T> values) throws ObjetoSinSalida {
		Map<String, List<T>> results = new HashMap<>();

		for (T value : values) {
			List<String> leaves = evaluate(value);
			if (leaves.isEmpty())
				throw new ObjetoSinSalida(value);
			results.computeIfAbsent(leaves.get(0), k -> new ArrayList<>()).add(value);
		}

		return results;
	}

	/**
	 * Método privado que evalua de manera recursiva sobre un nodo y después sobre
	 * sus hijos, hasta encontrar la que le corresponde
	 * 
	 * @param input   Objeto que se está evaluando
	 * @param results Nombre del nodo que le corresponde
	 * @return Devuelve true en el caso de que haya encontrado su hoja, false si no
	 */
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

	/**
	 * Evalua un objeto haciendo uso de un método privado recursivo
	 * 
	 * @param input Objeto que está evaluando
	 * @return Devuelve el nombre de la hoja en la que cae
	 */
	private List<String> evaluate(T input) {
		List<String> results = new ArrayList<>();
		evaluateRecursive(input, results);
		return results;
	}

	/**
	 * Devuelve la condición asociada a un nodo a partir del nombre
	 * 
	 * @param label Nombre del nodo
	 * @return El predicado con la condición
	 * @throws NotExistingNode, se lanza si no existe ningún nodo con ese nombre
	 */
	public Predicate<T> getPredicate(String label) throws NotExistingNode {
		List<Predicate<T>> path = new ArrayList<>();
		if (!findPredicatePath(root(), label, path))
			throw new NotExistingNode(label);

		return path.stream().reduce(p -> true, Predicate::and);
	}

	/**
	 * Método privado para ir acumulando las condiciones que llevan hasta el nodo
	 * concreto
	 * 
	 * @param node  Árbol sobre el que se está operando
	 * @param label Nombre del nodo al que se quiere llegar
	 * @param path  Lista de predicados acumulados hasta llegar a ese nodo
	 * @return True si ha encontrado el nodo, false en caso contrario
	 */
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

	/**
	 * Setter del padre de un árbol
	 * 
	 * @param parent Padre del árbol
	 */
	private void setParent(DecisionTree<T> parent) {
		this.parent = parent;
	}

	/**
	 * Devuelve el nombre del nodo raíz del árbol
	 * 
	 * @return El nombre
	 */
	public String getName() {
		return name;
	}

	/**
	 * Los hijos del árbol
	 * 
	 * @return Los hijos
	 */
	public List<DecisionTree<T>> getChildren() {
		return children;
	}

	/**
	 * Devuelve si el árbol es una hoja
	 * 
	 * @return True si es una hoja, false en caso contrario
	 */
	public boolean isLeaf() {
		return children.isEmpty();
	}

	/**
	 * Acepta un visitor
	 * 
	 * @param visitor El visitor
	 */
	public void accept(DecisionTreeVisitor<T> visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return name + " " + children;
	}

}
