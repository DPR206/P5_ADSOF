package dataset;

public class LabeledDataSet<T, S> extends Dataset<T>{
	
	private LabelProvider<T, S> etiquetador; 

	public LabeledDataSet(Featurizer<T> featurizer, LabelProvider<T, S> etiquetador) {
		super(featurizer);
		this.etiquetador = etiquetador;
	}

	/**
	 * @return the etiquetador
	 */
	public LabelProvider<T, S> getEtiquetador() {
		return etiquetador;
	}

	/**
	 * @param etiquetador the etiquetador to set
	 */
	public void setEtiquetador(LabelProvider<T, S> etiquetador) {
		this.etiquetador = etiquetador;
	}
	
	

}
