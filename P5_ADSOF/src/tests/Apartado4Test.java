package tests;

import dataset.*;
import decision_tree.*;
import exceptions.ObjetoSinSalida;

public class Apartado4Test {

	public static void main(String[] args) {
		LabeledDataSet<Weather, Boolean> dataset = buildDataSetWeather();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataset);

		System.out.println("=== Árbol de decisión de Weather: ===");
		System.out.println(tree + "\n");
		
		System.out.println("=== Predict del dataset de Weather: ===");
		try {
			System.out.println(tree.predict(dataset) + "\n");
		} catch (ObjetoSinSalida e) {
			e.printStackTrace();
		}

		Weather[] tests = { new Weather(WeatherCondition.SUNNY, Temperature.HOT),
				new Weather(WeatherCondition.RAINY, Temperature.COLD),
				new Weather(WeatherCondition.SANDSTORM, Temperature.MILD),
				new Weather(WeatherCondition.SUNNY, Temperature.COLD), };

		System.out.println("=== Clasificación de nuevos objetos  en Weather ===");
		for (Weather w : tests) {
			try {
				System.out.println(w + " -> " + tree.predict(w));
			} catch (ObjetoSinSalida e) {
				e.printStackTrace();
			}
		}
		
		LabeledDataSet<Person, Boolean> datasetPerson = buildDataSetPerson();
		GreedyTreeLearner<Person, Boolean> learnerPerson = new GreedyTreeLearner<>();
		DecisionTree<Person> treePerson = learnerPerson.learn(datasetPerson);

		System.out.println("\n=== Árbol de decisión de Person: ===");
		System.out.println(treePerson + "\n");
		
		System.out.println("=== Predict del dataset de Person: ===");
		try {
			System.out.println(treePerson.predict(datasetPerson) + "\n");
		} catch (ObjetoSinSalida e) {
			e.printStackTrace();
		}

	}

	private static LabeledDataSet<Weather, Boolean> buildDataSetWeather() {
		Weather conditions[] = { new Weather(WeatherCondition.RAINY, Temperature.COLD),
				new Weather(WeatherCondition.RAINY, Temperature.HOT),
				new Weather(WeatherCondition.SUNNY, Temperature.MILD),
				new Weather(WeatherCondition.CLOUDY, Temperature.COOL),
				new Weather(WeatherCondition.WINDY, Temperature.WARM),
				new Weather(WeatherCondition.SUNNY, Temperature.SCORCHING),
				new Weather(WeatherCondition.SNOWY, Temperature.FREEZING),
				new Weather(WeatherCondition.SANDSTORM, Temperature.HOT),
				new Weather(WeatherCondition.RAINY, Temperature.COLD),
				new Weather(WeatherCondition.RAINY, Temperature.WARM)};

		LabeledDataSet<Weather, Boolean> ds = new LabeledDataSet<>(new WeatherFeaturizer(),
				new ShouldIPlayTennisToday());
		ds.addAll(conditions);
		return ds;

	}
	
	private static LabeledDataSet<Person, Boolean> buildDataSetPerson() {
		Person people[] = { new Person("Pedro", 66, 75, 176, true), new Person("Ana", 47, 34, 158, false),
				new Person("Luis", 66, 74, 180, true), new Person("Rosa", 47, 54, 189, false), new Person ("Maria", 45, 54, 158, false)};
		
		LabeledDataSet<Person, Boolean> ds = new LabeledDataSet<>(new PersonFeaturizer(), new IsOldMale());
		ds.addAll(people);
		return ds;
	}
}
