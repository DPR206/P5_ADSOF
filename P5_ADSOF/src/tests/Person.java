package tests;

/**
 * Esta clase representa una persona
 */
public class Person {
	/** Nombre de la persona */
	private String name;
	/** Edad de la persona */
	private int age;
	/** Peso de la persona */
	private int weight;
	/** Altura de la persona */
	private int height;
	/** Género de la persona */
	private boolean gender;

	/**
	 * Constructor de la clase Person
	 * @param name Nombre de la persona
	 * @param age Edad de la persona
	 * @param weight Peso de la persona
	 * @param height Altura de la persona
	 * @param gender Género de la persona
	 */
	public Person(String name, int age, int weight, int height, boolean gender) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
	}
	
	/**
	 * Devuelve el nombre de la persona
	 * @return Nombre de la persona
	 */
	public String getName() {
		return name;
	}

	/**
	 * Devuelve la edad de la persona
	 * @return Edad de la persona
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Devuelve el peso de la persona
	 * @return Peso de la persona
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Devuelve la altura de la persona
	 * @return Altura de la persona
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Devuelve si es hombre
	 * @return True si es hombre, false en caso contrario
	 */
	public boolean isMale() {
		return gender;
	}

	@Override
	public String toString() {
		return name + "(age: " + age + " " + (gender==true ? "male":"female") + ")";
	}
	
	
}
