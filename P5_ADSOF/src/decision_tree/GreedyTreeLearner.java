package decision_tree;

import java.util.*;

import dataset.*;

public class GreedyTreeLearner<T, S> {

	public GreedyTreeLearner() {
	}

	public DecisionTree<T> learn(LabeledDataSet<T, S> dataset) {
		Set<String> allFeatures = new LinkedHashSet<>(dataset.getFeatureNames());
		DecisionTree<T> tree = new DecisionTree<>();
		buildTree(tree, dataset, allFeatures);
		return tree;

	}

	private void buildTree(DecisionTree<T> tree, LabeledDataSet<T, S> dataset, Set<String> availableFeatures) {
		// Caso base: Todos los objetos tienen la misma label
		if (dataset.allSameLabel())
			return;

		// Caso base: Sin fetaures disponibles
		if (availableFeatures.isEmpty())
			return;

		//Coger bestFeature y borrar de remaining features
		String bestFeature = chooseBestFeature(dataset, availableFeatures);
		Set<String> remainingFeatures = new LinkedHashSet<>(availableFeatures);
		remainingFeatures.remove(bestFeature);

		//Split del dataset en funcion de bestFeature
		Map<Object, LabeledDataSet<T, S>> subsets = dataset.split(bestFeature);
		Map<Object, DecisionTree<T>> branches = new LinkedHashMap<>();
		int featureIndex = dataset.getFeaturizer().getFeatureNames().indexOf(bestFeature);

		for (Map.Entry<Object, LabeledDataSet<T, S>> entry : subsets.entrySet()) {
			Object value = entry.getKey();
			LabeledDataSet<T, S> subset = entry.getValue();

			//FALTA AÑADIR LOS NODOS AL DECISION TREE
		}
	}

	private String chooseBestFeature(LabeledDataSet<T, S> data, Set<String> availableFeatures) {
		// Selección aleatoria
		List<String> featureList = new ArrayList<>(availableFeatures);
		return featureList.get(new Random().nextInt(featureList.size()));
	}

}
