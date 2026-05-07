package tests;

import java.util.function.Predicate;

import decision_tree.DecisionTree;
import exceptions.CicloArbol;
import exceptions.NotExistingNode;

/**
 * Esta clase es un test de prueba para el apartado 3
 */
class Apartado3Test {

	/**
	 * Programa ejecutable
	 * @param args Argumentos de entrada
	 */
	public static void main(String[] args) {

		DecisionTree<Person> dt = buildDecisionTree();

		System.out.println("=== Coger predicado de nodo que existe: ===");
		Predicate<Person> isOldMale;
		try {
			isOldMale = dt.getPredicate("old male");
			System.out.println(isOldMale.test(new Person("Miguel", 86, 72, 165, true)));
			System.out.println(isOldMale.test(new Person("Clara", 42, 59, 162, false)));
			System.out.println(isOldMale.test(new Person("Juan", 42, 80, 175, true)));
			System.out.println(isOldMale.test(new Person("Ana", 30, 55, 160, false)));

		} catch (NotExistingNode e) {
			e.printStackTrace();
		}

		System.out.println("\n=== Intentar coger predicado de nodo que existe: ===");
		try {
			dt.getPredicate("noExiste");
		} catch (NotExistingNode e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construye un árbol de decisión
	 * @return El árbol de decisión
	 */
	private static DecisionTree<Person> buildDecisionTree() {
		DecisionTree<Person> dt = new DecisionTree<>();
		try {
			dt.node("root").withCondition("male", p -> p.isMale()).otherwise("female");
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		try {
			dt.node("male").withCondition("old male", p -> p.getAge() > 65)
					.withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34).otherwise("young male");
		} catch (CicloArbol | NotExistingNode e) {
			e.printStackTrace();
		}
		return dt;
	}
}
