package tests;

import dataset.*;
import decision_tree.*;

public class Apartado2Test {
	
	public static void main(String[] args) {
		Dataset<Person> dataSet = buildDataSet();
		DecisionTree<Person> dt = buildDecisionTree();
		
		System.out.println(dt.predict(dataSet));
		System.out.println(dt.predict(new Person("Miguel", 86, 72, 165, true), new Person("Clara", 42, 59, 162, false)));
		
	}
	
	private static Dataset<Person> buildDataSet() {
		Person people[] = { new Person("Pedro", 66, 75, 180, true), new Person("Ana", 47, 54, 158, false),
				new Person("Luis", 34, 75, 176, true), new Person("Rosa", 47, 54, 158, false) };

		Dataset<Person> dataset = new Dataset<>(new PersonFeaturizer());
		dataset.addAll(people);
		return dataset;
	}
	
	private static DecisionTree<Person> buildDecisionTree(){
		DecisionTree<Person> dt = new DecisionTree<>();
		dt.node("root")
			.withCondition("male", p -> p.isMale())
			.otherwise("female");
		dt.node("male")
			.withCondition("old male", p -> p.getAge() > 65)
			.withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34)
			.otherwise("young male");
		return dt;
	}
}
