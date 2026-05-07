package tests;

import dataset.*;
import decision_tree.*;
import exceptions.CicloArbol;
import exceptions.NotExistingNode;
import exceptions.ObjetoSinSalida;

/**
 * Esta clase es un test de prueba para el apartado 2
 */
class Apartado2Test {
	
	/**
	 * Programa ejecutable
	 * @param args Argumentos de entrada
	 */
	public static void main(String[] args){
		Dataset<Person> dataSet = buildDataSet();
		DecisionTree<Person> dt = buildDecisionTree();
		
		System.out.println("=== Árbol de decisión construido: ===");
		System.out.println(dt + "\n");
		
		System.out.println("=== Predict de valores: ===");
		try {
			System.out.println(dt.predict(dataSet));
			System.out.println(dt.predict(new Person("Miguel", 86, 72, 165, true), new Person("Clara", 42, 59, 162, false)) + "\n");
		} catch (ObjetoSinSalida e) {
			e.printStackTrace();
		}
		
		System.out.println("=== Intentar construir un árbol con ciclos: ===");
		buildDecisionTreeCiclos();
		
		System.out.println("\n=== Intentar buscar un nodo que no existe: ===");
		try {
			dt.node("noExiste");
		} catch (NotExistingNode e) {
			e.printStackTrace();
		}
		
		System.out.println("\n=== Evaluar un objeto sin nodo correspondiente: ===");
		DecisionTree<Person> dtSinSalida = buildDecisionTreeSinSalida();
		try {
			System.out.println(dtSinSalida.predict(new Person("Clara", 42, 59, 162, false)) + "\n");
		} catch (ObjetoSinSalida e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Construye un dataset de objetos Person
	 * @return El dataset de personas
	 */
	private static Dataset<Person> buildDataSet() {
		Person people[] = { new Person("Pedro", 66, 75, 180, true), new Person("Ana", 47, 54, 158, false),
				new Person("Luis", 34, 75, 176, true), new Person("Rosa", 47, 54, 158, false) };

		Dataset<Person> dataset = new Dataset<>(new PersonFeaturizer());
		dataset.addAll(people);
		return dataset;
	}
	
	/**
	 * Construye un árbol de decisión parametrizado con Person
	 * @return El árbol de decisión construido
	 */
	private static DecisionTree<Person> buildDecisionTree(){
		DecisionTree<Person> dt = new DecisionTree<>();
		try {
			dt.node("root")
			.withCondition("male", p -> p.isMale())
			.otherwise("female");
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		
		try {
			dt.node("male")
			.withCondition("old male", p -> p.getAge() > 65)
			.withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34)
			.otherwise("young male");
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * Intenta construir un árbol que tiene ciclos
	 * @return El árbol si se construyera
	 */
	private static DecisionTree<Person> buildDecisionTreeCiclos(){
		DecisionTree<Person> dt = new DecisionTree<>();
		try {
			dt.node("root")
			.withCondition("male", p -> p.isMale())
			.otherwise("female");
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		
		try {
			dt.node("male")
			.withCondition("old male", p -> p.getAge() > 65)
			.withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34)
			.otherwise("root");
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * Construye un árbol para que se queden objetos sin salida
	 * @return El árbol construido
	 */
	private static DecisionTree<Person> buildDecisionTreeSinSalida(){
		DecisionTree<Person> dt = new DecisionTree<>();
		try {
			dt.node("root")
			.withCondition("male", p -> p.isMale());
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		return dt;
	}
}
