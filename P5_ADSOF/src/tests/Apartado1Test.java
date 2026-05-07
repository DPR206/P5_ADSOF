package tests;

import java.util.Collections;

import dataset.*;
import exceptions.NotExistingFeature;

public class Apartado1Test {

	public static void main(String[] args) {
		Dataset<Person> dataset = buildDataSet();
		System.out.println("=== Dataset creado: ===");
		System.out.println(dataset + "\n");

		dataset.removeDuplicates();
		System.out.println("=== Dataset sin duplicados: ===");
		System.out.println(dataset + "\n");

		Feature<Integer> ages;
		try {
			ages = dataset.getFeature("age");
			System.out.println("=== Ages: " + ages + "\n");
			Collections.sort(ages);
			System.out.println("=== Ages sorted: " + ages + "\n");
			System.out.println("=== Min ages: " + ages.getMin() + "\n");
			System.out.println("=== Gender distribution: " + dataset.getFeature("gender").getDistribution() + "\n");
		} catch (NotExistingFeature e) {
			e.printStackTrace();
		}
		
		System.out.println("=== Coger una feature que no existe: ===");
		try {
			dataset.getFeature("noExiste");
		} catch (NotExistingFeature e) {
			e.printStackTrace();
		}
	}

	private static Dataset<Person> buildDataSet() {
		Person people[] = { new Person("Pedro", 66, 75, 180, true), new Person("Ana", 47, 54, 158, false),
				new Person("Luis", 66, 75, 180, true), new Person("Rosa", 47, 54, 158, false), new Person ("Maria", 47, 54, 158, false)};

		Dataset<Person> dataset = new Dataset<>(new PersonFeaturizer());
		dataset.addAll(people);
		return dataset;
	}

}
