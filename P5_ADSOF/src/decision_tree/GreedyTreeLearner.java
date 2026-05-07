package decision_tree;

import java.util.*;
import java.util.function.Predicate;

import dataset.*;
import exceptions.*;
import strategies.RandomStrategy;
import strategies.Strategy;

/**
 * Esta clase representa un generador de árboles de decisión a partir de un
 * dataset
 * 
 * @param <T> Tipo de los objetos que guarda el dataset
 * @param <S> Tipo de las etiquetas generadas por el etiquetador
 */
public class GreedyTreeLearner<T, S> {

	/** Estrategia utilizada para seleccionar la mejor feature */
	private final Strategy<T, S> strategy;

	/** Constructor del generador de árboles */
	public GreedyTreeLearner() {
		this.strategy = new RandomStrategy<T, S>();
	}

	/**
	 * Constructor del generador de árboles especificando la estrategia
	 * 
	 * @param strategy Estrategia que se usa para seleccionar la mejor feature
	 */
	public GreedyTreeLearner(Strategy<T, S> strategy) {
		this.strategy = strategy;
	}

	/**
	 * Genera un árbol de decisión a partir de un dataset
	 * 
	 * @param dataset Dataset que se proporciona para generar el árbol
	 * @return El árbol generado
	 */
	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset) {
		Set<String> allFeatures = new LinkedHashSet<>(dataset.getFeatureNames());
		DecisionTree<T> tree = new DecisionTree<>();
		buildTree(tree, dataset, allFeatures, new LinkedList<String>());
		return tree;

	}

	/**
	 * Genera un árbol de decisión a partir de un conjunto de objetos, un featurizer
	 * y un labeler
	 * 
	 * @param objects    Conjunto de objetos
	 * @param featurizer Featurizer proporcionado
	 * @param labeler    LabelProvider proporcionado
	 * @return Árbol de decisión creado
	 */
	public DecisionTree<T> learn(T[] objects, Featurizer<T> featurizer, LabelProvider<T, S> labeler) {
		LabeledDataSet<T, S> dataset = new LabeledDataSet<T, S>(featurizer, labeler);
		dataset.addAll(objects);

		Set<String> allFeatures = new LinkedHashSet<>(dataset.getFeatureNames());
		DecisionTree<T> tree = new DecisionTree<>();
		buildTree(tree, dataset, allFeatures, new LinkedList<String>());
		return tree;
	}

	/**
	 * Crea el árbol de manera recursiva por nivel de profundidad
	 * 
	 * @param tree              Árbol de decisión creado hasta ese momento
	 * @param dataset           Dataset a partir del cual se crea el árbol
	 * @param availableFeatures Features que quedan por evaluar
	 * @param removedFeatures   Features que ya han sido evaluadas
	 */
	private void buildTree(DecisionTree<T> tree, LabeledDataSet<T, S> dataset, Set<String> availableFeatures,
			List<String> removedFeatures) {
		// Caso base: Todos los objetos tienen la misma label
		if (dataset.allSameLabel())
			return;
		// Caso base2: Sin features disponibles
		if (availableFeatures.isEmpty())
			return;

		dataset.removeDuplicates();

		// Coger bestFeature y borrar de remaining features
		String bestFeature = strategy.chooseBestFeature(dataset, availableFeatures);
		Set<String> remainingFeatures = new LinkedHashSet<>(availableFeatures);
		remainingFeatures.remove(bestFeature);

		// Split del dataset en función de bestFeature
		Map<?, LabeledDataSet<T, S>> subsets = dataset.split(bestFeature, removedFeatures);
		removedFeatures.add(bestFeature);

		for (Map.Entry<?, LabeledDataSet<T, S>> entry : subsets.entrySet()) {
			Object featureValue = entry.getKey();
			LabeledDataSet<T, S> subset = entry.getValue();

			// Nombre del nodo hijo "nombre del padre + feature"
			String childName = (tree.getName().equals("root") ? "" : tree.getName() + "_") + featureValue;

			// Condición del nodo: featureValue == bestFeatureValue
			Featurizer<T> featurizer = dataset.getFeaturizer();
			Predicate<T> localCondition = obj -> featurizer.getFeatureValue(obj, bestFeature).equals(featureValue);

			try {
				tree.withCondition(childName, localCondition);
				DecisionTree<T> subtree = tree.node(childName);

				buildTree(subtree, subset, remainingFeatures, removedFeatures);

			} catch (CicloArbol | NotExistingNode e) {
				throw new RuntimeException("Error construyendo el árbol en nodo: " + childName, e);
			}
		}

		String otherwiseName = tree.getName() + "_otherwise";
		try {
			tree.otherwise(otherwiseName);
		} catch (CicloArbol e) {
			throw new RuntimeException("Error construyendo el árbol en nodo: " + otherwiseName, e);
		}
	}

}
