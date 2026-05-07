package tests;

import dataset.LabelProvider;

/**
 * Esta clase representa un etiquetador de personas
 */
public class FitnessLevelLabeler implements LabelProvider<Person, String> {
	
	/**
	 * Constructor del etiquetador
	 */
	public FitnessLevelLabeler() {
		
	}

    @Override
    public String asignarEtiqueta(Person p) {
        if (p.isMale()) {
            if (p.getAge() < 35) {
                return p.getWeight() < 80 ? "en forma" : "mejorable";
            } else {
                return p.getWeight() < 85 ? "activo"   : "sedentario";
            }
        } else {
            if (p.getAge() < 35) {
                return p.getWeight() < 65 ? "en forma" : "mejorable";
            } else {
                return p.getWeight() < 70 ? "activa"   : "sedentaria";
            }
        }
    }
}
