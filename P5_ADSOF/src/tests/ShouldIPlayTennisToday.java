package tests;

import dataset.*;

/**
 * Esta clase representa un etiquetador de Weather
 */
public class ShouldIPlayTennisToday implements LabelProvider<Weather, Boolean>{
	
	/**
	 * Cosntructor de este etiquetador
	 */
	public ShouldIPlayTennisToday() {
		
	}

	@Override
	public Boolean asignarEtiqueta(Weather w) {
		return (w.getCondition() == WeatherCondition.SUNNY || w.getCondition() == WeatherCondition.CLOUDY 
				|| w.getCondition() == WeatherCondition.WINDY) && 
				(w.getTemperature() == Temperature.COOL || w.getTemperature() == Temperature.MILD ||
				 w.getTemperature() == Temperature.WARM);
	}

}
