package tests;

public class Person {
	private String name;
	private int age;
	private int weight;
	private int height;
	private boolean gender;

	public Person(String name, int age, int weight, int height, boolean gender) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getWeight() {
		return weight;
	}

	public int getHeight() {
		return height;
	}

	public boolean isMale() {
		return gender;
	}

	@Override
	public String toString() {
		return name + "(age: " + age + " " + (gender==true ? "male":"female") + ")";
	}
	
	
}
