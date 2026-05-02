package decision_tree;

import java.util.*;
import java.util.function.Predicate;

import dataset.*;
import exceptions.*;
import strategies.RandomStrategy;
import strategies.Strategy;

public class GreedyTreeLearner<T, S> {
	
	private final Strategy<T, S> strategy;
	
	public GreedyTreeLearner() {
		this.strategy = new RandomStrategy<T, S>();
	}

	public GreedyTreeLearner(Strategy<T, S> strategy) {
		this.strategy = strategy;
	}

	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset) {
		Set<String> allFeatures = new LinkedHashSet<>(dataset.getFeatureNames());
		DecisionTree<T> tree = new DecisionTree<>();
		buildTree(tree, dataset, allFeatures);
		return tree;

	}

	public DecisionTree<T> learn(T[] objects, Featurizer<T> featurizer, LabelProvider<T, S> labeler) {
		LabeledDataSet<T, S> dataset = new LabeledDataSet<T, S>(featurizer, labeler);
		dataset.addAll(objects);

		Set<String> allFeatures = new LinkedHashSet<>(dataset.getFeatureNames());
		DecisionTree<T> tree = new DecisionTree<>();
		buildTree(tree, dataset, allFeatures);
		return tree;
	}

	private void buildTree(DecisionTree<T> tree, LabeledDataSet<T, S> dataset, Set<String> availableFeatures) {
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
		Map<Object, LabeledDataSet<T, S>> subsets = dataset.split(bestFeature);

		for (Map.Entry<Object, LabeledDataSet<T, S>> entry : subsets.entrySet()) {
			Object featureValue = entry.getKey();
			LabeledDataSet<T, S> subset = entry.getValue();

			// Nombre del nodo hijo "nombre del padre + feature"
			String childName = (tree.getName().equals("root")? "" : tree.getName() + "_") + featureValue;

			// Condición del nodo: featureValue == bestFeatureValue
			Featurizer<T> featurizer = dataset.getFeaturizer();
			Predicate<T> localCondition = obj -> featurizer.getFeatureValue(obj, bestFeature).equals(featureValue);

			try {
				tree.withCondition(childName, localCondition);
				DecisionTree<T> subtree = tree.node(childName);

				buildTree(subtree, subset, remainingFeatures);

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
