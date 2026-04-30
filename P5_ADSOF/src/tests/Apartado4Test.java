package tests;

import dataset.*;
import decision_tree.*;
import exceptions.ObjetoSinSalida;

public class Apartado4Test {

	public static void main(String[] args) {
		LabeledDataSet<Weather, Boolean> dataset = buildDataSet();
		System.out.println("Dataset : " + dataset);

		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataset);

		System.out.println("=== Árbol de decisión: ===");
		System.out.println(tree + "\n");

		/*Weather[] tests = { new Weather(WeatherCondition.SUNNY, Temperature.HOT),
				new Weather(WeatherCondition.RAINY, Temperature.COLD),
				new Weather(WeatherCondition.SANDSTORM, Temperature.MILD),
				new Weather(WeatherCondition.SUNNY, Temperature.COLD), };

		System.out.println("=== Clasificación de nuevos objetos ===");
		for (Weather w : tests) {
			try {
				System.out.println(w + " → ¿Jugar al tenis? " + tree.predict(w));
			} catch (ObjetoSinSalida e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

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
