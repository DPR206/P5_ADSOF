package tests;

import dataset.LabeledDataSet;
import decision_tree.DecisionTree;
import decision_tree.GreedyTreeLearner;
import visualizers.GraphvizVisualizer;
import visualizers.TextVisualizer;

/**
 * Esta clase es un test de prueba para el apartado 6
 */
class Apartado6Test {

	/**
	 * Programa ejecutable
	 * @param args Argumentos de entrada
	 */
	public static void main(String[] args) {
		LabeledDataSet<Weather, Boolean> dataset = buildDataSetWeather();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataset);

		System.out.println("=== Texto plano para Weather: ===");
		TextVisualizer<Weather> textViz = new TextVisualizer<>();
		tree.accept(textViz);
		System.out.println(textViz);

		System.out.println("=== Graphviz para Weather: ===");
		GraphvizVisualizer<Weather> dotViz = new GraphvizVisualizer<>();
		tree.accept(dotViz);
		System.out.println(dotViz);
		
		LabeledDataSet<Person, String> datasetPerson = buildDataSetPerson();
		GreedyTreeLearner<Person, String> learnerPerson = new GreedyTreeLearner<>();
		DecisionTree<Person> treePerson = learnerPerson.learn(datasetPerson);

		System.out.println("=== Texto plano para Person: ===");
		TextVisualizer<Person> textVizPerson = new TextVisualizer<>();
		treePerson.accept(textVizPerson);
		System.out.println(textVizPerson);

		System.out.println("=== Graphviz para Person: ===");
		GraphvizVisualizer<Person> dotVizPerson = new GraphvizVisualizer<>();
		treePerson.accept(dotVizPerson);
		System.out.println(dotVizPerson);
		
	}

	/**
	 * Construye un árbol de decisión parametrizado para Weather
	 * @return El árbol construido
	 */
	public static DecisionTree<Weather> learnTreeWeather() {
		LabeledDataSet<Weather, Boolean> dataSet = buildDataSetWeather();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataSet);
		return tree;
	}
	
	/**
	 * Construye un árbol de decisión parametrizado para Person
	 * @return El árbol construido
	 */
	public static DecisionTree<Person> learnTreePerson() {
		LabeledDataSet<Person, String> dataSet = buildDataSetPerson();
		GreedyTreeLearner<Person, String> learner = new GreedyTreeLearner<>();
		DecisionTree<Person> tree = learner.learn(dataSet);
		return tree;
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
				new Weather(WeatherCondition.RAINY, Temperature.WARM) };

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
