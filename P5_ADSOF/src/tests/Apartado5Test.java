package tests;

import dataset.LabeledDataSet;
import decision_tree.DecisionTree;
import decision_tree.GreedyTreeLearner;
import exceptions.ObjetoSinSalida;
import strategies.MisclassificationStrategy;
import strategies.RandomStrategy;

/**
 * Esta clase es un test de prueba para el apartado 5
 */
class Apartado5Test {

	/**
	 * Programa ejecutable
	 * @param args Argumentos de entrada
	 */
	public static void main(String[] args) {
		LabeledDataSet<Weather, Boolean> dataset = buildDataSet();

		System.out.println("=== Con estrategia \"Misclassification\": ===\n");
		GreedyTreeLearner<Weather, Boolean> learnerMisclassification = new GreedyTreeLearner<>(new MisclassificationStrategy<Weather, Boolean>());
		DecisionTree<Weather> treeMisclassification = learnerMisclassification.learn(dataset);

		System.out.println("=== Árbol de decisión: ===");
		System.out.println(treeMisclassification + "\n");
		
		System.out.println("=== Predict del dataset: ===");
		try {
			System.out.println(treeMisclassification.predict(dataset) + "\n");
		} catch (ObjetoSinSalida e) {
			e.printStackTrace();
		}

		System.out.println("=== Con estrategia \"Random\": ===\n");
		GreedyTreeLearner<Weather, Boolean> learnerRandom = new GreedyTreeLearner<>(new RandomStrategy<Weather, Boolean>());
		DecisionTree<Weather> treeRandom = learnerRandom.learn(dataset);

		System.out.println("=== Árbol de decisión: ===");
		System.out.println(treeRandom + "\n");
		
		System.out.println("=== Predict del dataset: ===");
		try {
			System.out.println(treeRandom.predict(dataset) + "\n");
		} catch (ObjetoSinSalida e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Construye un árbol de decisión parametrizado para Weather
	 * @return El árbol construido
	 */
	public static DecisionTree<Weather> learnTree() {
		LabeledDataSet<Weather, Boolean> dataSet = buildDataSet();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataSet);
		return tree;
	}

	/**
	 * Construye un LabeledDataSet parametrizado para Weather
	 * @return El labeleDataset construido
	 */
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
