package tests;

/**
 * Esta clase representa una condición meteorológica
 */
public class Weather {
	/** Condicion del tiempo */
	private WeatherCondition condition;
	/** Temperatura */
	private Temperature temperature;
	
	/**
	 * Constructor de la clase Weather
	 * @param condition Condición del tiempo
	 * @param temperature Temperatura
	 */
	public Weather(WeatherCondition condition, Temperature temperature) {
		this.condition = condition;
		this.temperature = temperature;
	}

	/**
	 * Devuelve la condición del tiempo
	 * @return Condición del tiempo
	 */
	public WeatherCondition getCondition() {
		return condition;
	}
	/**
	 * Devuelve la temperatura
	 * @return the temperature
	 */
	public Temperature getTemperature() {
		return temperature;
	}
	
	@Override
	public String toString() {
		return "(Condition: " + condition + ", Temperature: " + temperature + ")";
	}
	
}
