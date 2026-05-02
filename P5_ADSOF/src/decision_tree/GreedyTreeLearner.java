package decision_tree;

import java.util.*;
import java.util.function.Predicate;

import dataset.*;
import exceptions.*;

public class GreedyTreeLearner<T, S> {

	public GreedyTreeLearner() {
	}

	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset) {
		Set<String> allFeatures = new LinkedHashSet<>(dataset.getFeatureNames());
		DecisionTree<T> tree = new DecisionTree<>("root");
		buildTree(tree, dataset, allFeatures);
		return tree;

	}

	private void buildTree(DecisionTree<T> tree, LabeledDataSet<T, S> dataset, Set<String> availableFeatures) {
		// Caso base: Todos los objetos tienen la misma label
		if (dataset.allSameLabel())
			return;
		// Caso base: Sin features disponibles
		if (availableFeatures.isEmpty())
			return;

		dataset.removeDuplicates();

		// Coger bestFeature y borrar de remaining features
		String bestFeature = chooseBestFeature(dataset, availableFeatures);
		Set<String> remainingFeatures = new LinkedHashSet<>(availableFeatures);
		remainingFeatures.remove(bestFeature);

		// Split del dataset en función de bestFeature
		Map<Object, LabeledDataSet<T, S>> subsets = dataset.split(bestFeature);

		for (Map.Entry<Object, LabeledDataSet<T, S>> entry : subsets.entrySet()) {
			Object featureValue = entry.getKey();
			LabeledDataSet<T, S> subset = entry.getValue();

			// Nombre del nodo hijo: "feature=valor"
			String childName = featureValue.toString();

			// El predicado es feature == bestFeature
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

	private String chooseBestFeature(LabeledDataSet<T, S> data, Set<String> availableFeatures) {
		// Selección aleatoria
		List<String> featureList = new ArrayList<>(availableFeatures);
		return featureList.get(new Random().nextInt(featureList.size()));
	}

}
