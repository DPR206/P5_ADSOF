package tests;

import dataset.LabeledDataSet;
import decision_tree.DecisionTree;
import decision_tree.GreedyTreeLearner;
import visualizers.GraphvizVisualizer;
import visualizers.TextVisualizer;

public class Apartado6Test {

	public static void main(String[] args) {
		LabeledDataSet<Weather, Boolean> dataset = buildDataSet();

		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>();
		DecisionTree<Weather> tree = learner.learn(dataset);

		System.out.println("=== Texto plano: ===");
		TextVisualizer<Weather> textViz = new TextVisualizer<>();
		tree.accept(textViz);
		System.out.println(textViz);

		System.out.println("=== Graphviz: ===");
		GraphvizVisualizer<Weather> dotViz = new GraphvizVisualizer<>();
		tree.accept(dotViz);
		System.out.println(dotViz.getDot());
		
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
