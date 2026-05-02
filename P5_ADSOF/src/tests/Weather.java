package tests;

public class Weather {
	
	private WeatherCondition condition;
	private Temperature temperature;
	
	/**
	 * @param condition
	 * @param temperature
	 */
	public Weather(WeatherCondition condition, Temperature temperature) {
		this.condition = condition;
		this.temperature = temperature;
	}

	/**
	 * @return the condition
	 */
	public WeatherCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(WeatherCondition condition) {
		this.condition = condition;
	}

	/**
	 * @return the temperature
	 */
	public Temperature getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}
	
	@Override
	public String toString() {
		return "(Condition: " + condition + ", Temperature: " + temperature + ")";
	}
	
}
