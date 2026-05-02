package tests;

import dataset.LabeledDataSet;
import decision_tree.DecisionTree;
import decision_tree.GreedyTreeLearner;
import strategies.MisclassificationStrategy;
import strategies.RandomStrategy;

public class Apartado5Test {

	public static void main(String[] args) {
		LabeledDataSet<Weather, Boolean> dataset = buildDataSet();

		System.out.println("=== Con estrategia \"Misclassification\": ===\n");
		GreedyTreeLearner<Weather, Boolean> learnerMisclassification = new GreedyTreeLearner<>(new MisclassificationStrategy<Weather, Boolean>());
		DecisionTree<Weather> treeMisclassification = learnerMisclassification.learn(dataset);

		System.out.println("=== Árbol de decisión: ===");
		System.out.println(treeMisclassification + "\n");
		
		System.out.println("=== Predict del dataset: ===");
		System.out.println(treeMisclassification.predict(dataset) + "\n");

		System.out.println("=== Con estrategia \"Random\": ===\n");
		GreedyTreeLearner<Weather, Boolean> learnerRandom = new GreedyTreeLearner<>(new RandomStrategy<Weather, Boolean>());
		DecisionTree<Weather> treeRandom = learnerRandom.learn(dataset);

		System.out.println("=== Árbol de decisión: ===");
		System.out.println(treeRandom + "\n");
		
		System.out.println("=== Predict del dataset: ===");
		System.out.println(treeRandom.predict(dataset) + "\n");
	}
	
	public static DecisionTree<Weather> learnTree() {
		LabeledDataSet<Weather, Boolean> dataSet = buildDataSet();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataSet);
		return tree;
	}

	private static LabeledDataSet<Weather, Boolean> buildDataSet() {
		Weather conditions[] = { new Weather(WeatherCondition.RAINY, Temperature.COLD),
				new Weather(WeatherCondition.RAINY, Temperature.HOT),
				new Weather(WeatherCondition.SUNNY, Temperature.MILD),
				new Weather(WeatherCondition.CLOUDY, Temperature.COOL),
				new Weather(WeatherCondition.WINDY, Temperature.WARM),
				new Weather(WeatherCondition.SUNNY, Temperature.SCORCHING),
				new Weather(WeatherCondition.SNOWY, Temperature.FREEZING),
				new Weather(WeatherCondition.SANDSTORM, Temperature.HOT),
				new Weather(WeatherCondition.RAINY, Temperature.COLD),
				new Weather(WeatherCondition.RAINY, Temperature.WARM) };

		LabeledDataSet<Weather, Boolean> ds = new LabeledDataSet<>(new WeatherFeaturizer(),
				new ShouldIPlayTennisToday());
		ds.addAll(conditions);
		return ds;

	}

}
