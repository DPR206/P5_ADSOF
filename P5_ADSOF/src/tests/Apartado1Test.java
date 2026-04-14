package tests;

import java.util.Collections;

import dataset.*;

public class Apartado1Test {

	public static void main(String[] args) {
		Dataset<Person> dataset = buildDataSet();
		System.out.println("dataset: " + dataset);

		dataset.removeDuplicates();
		System.out.println("dataset w/0 duplicates: " + dataset);

		Feature<Integer> ages = dataset.getFeature("age");
		System.out.println("Ages: " + ages);
		Collections.sort(ages);
		System.out.println("Ages sorted: " + ages);
		System.out.println("Min ages: " + ages.getMin());
		System.out.println("Gender distribution: " + dataset.getFeature("gender").getDistribution());

	}

	private static Dataset<Person> buildDataSet() {
		Person people[] = { new Person("Pedro", 66, 75, 180, true), new Person("Ana", 47, 54, 158, false),
				new Person("Luis", 34, 75, 176, true), new Person("Rosa", 47, 54, 158, false) };

		Dataset<Person> dataset = new Dataset<>(new PersonFeaturizer());
		dataset.addAll(people);
		return dataset;
	}

}
