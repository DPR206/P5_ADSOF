package tests;

import dataset.*;
import decision_tree.*;
import exceptions.ObjetoSinSalida;

/**
 * Esta clase es un test de prueba para el apartado 4
 */
class Apartado4Test {

	/**
	 * Programa ejecutable
	 * @param args Argumentos de entrada
	 */
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
		
		LabeledDataSet<Person, String> datasetPerson = buildDataSetPerson();
		GreedyTreeLearner<Person, String> learnerPerson = new GreedyTreeLearner<>();
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

	/**
	 * Construye un LabeledDataSet parametrizado para Weather
	 * @return El labeleDataset construido
	 */
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
	
	/**
	 * Construye un LabeledDataSet parametrizado para Person
	 * @return El labeleDataset construido
	 */
	private static LabeledDataSet<Person, String> buildDataSetPerson() {
		Person people[] = {
		        new Person("Luis",    22, 65,  175, true),
		        new Person("Carlos",  25, 70,  180, true),
		        new Person("Jorge",   28, 68,  177, true),
		        new Person("Marcos",  24, 95,  182, true),
		        new Person("Diego",   27, 100, 185, true),
		        new Person("Pedro",   66, 72,  176, true),
		        new Person("Antonio", 70, 68,  170, true),
		        new Person("Manuel",  65, 75,  173, true),
		        new Person("Roberto", 68, 98,  178, true),
		        new Person("Felipe",  72, 105, 174, true),
		        new Person("Ana",     23, 52,  162, false),
		        new Person("Laura",   26, 55,  165, false),
		        new Person("Sofia",   29, 50,  160, false),
		        new Person("Carmen",  25, 85,  168, false),
		        new Person("Lucia",   28, 90,  170, false),
		        new Person("Rosa",    64, 58,  158, false),
		        new Person("Maria",   67, 54,  155, false),
		        new Person("Teresa",  70, 60,  157, false),
		        new Person("Pilar",   66, 88,  162, false),
		        new Person("Amparo",  69, 92,  160, false),
		    };
		
		LabeledDataSet<Person, String> ds = new LabeledDataSet<>(new PersonFeaturizer(), new FitnessLevelLabeler());
		ds.addAll(people);
		return ds;
	}
}
