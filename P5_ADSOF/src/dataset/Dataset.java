package dataset;

import java.util.*;

import exceptions.NotExistingFeature;

/**
 * Esta clase representa un conjunto de datos
 * @param <T> Tipo de los objetos que guarda el dataset
 */
public class Dataset<T> {

    private final Map<String, Feature<?>> features;
    protected final Featurizer<T> featurizer;
    protected List<T> objects = new ArrayList<>();

    /**
     * Constructor de un dataset
     * @param featurizer Featurizer que va a tener el dataset
     */
    public Dataset(Featurizer<T> featurizer) {
        this.featurizer = featurizer;
        this.features = new LinkedHashMap<>();
        for (String name : featurizer.getFeatureNames()) {
            features.put(name, new Feature<>(name));
        }
    }

    /**
     * Devuelve el featurizer del dataset
     * @return El featurizer
     */
    public Featurizer<T> getFeaturizer() {
        return featurizer;
    }

    /**
     * Devuelve el feature correspondiente a un nombre
     * @param <V> Tipo del feature que devuelve
     * @param name Nombre del feature
     * @return Feature correspondiente al nombre
     * @throws NotExistingFeature se lanza si no existe un feature con ese nombre en el dataset
     */
    @SuppressWarnings("unchecked")
    public <V extends Comparable<V>> Feature<V> getFeature(String name) throws NotExistingFeature {
        Feature<?> f = features.get(name);
        if (f == null)
            throw new NotExistingFeature(name);
        return (Feature<V>) f;
    }

    /**
     * Borra los objetos con features duplicados iterando por features y considerando solo los objetos que no pueden estar duplicados
     */
    public void removeDuplicates() {
        Set<T> possiblyDuplicated = new HashSet<>(objects);

        for (String feat : featurizer.getFeatureNames()) {
            possiblyDuplicated = findDuplicatesForFeature(possiblyDuplicated, feat);
            if (possiblyDuplicated.isEmpty()) break;
        }

        if (!possiblyDuplicated.isEmpty()) {
            List<T> toRemove = getDefiniteDuplicates(possiblyDuplicated);

            List<Integer> indicesToRemove = new ArrayList<>();
            for (T obj : toRemove) {
                int idx = objects.indexOf(obj);
                if (idx != -1) indicesToRemove.add(idx);
            }
            indicesToRemove.sort(Collections.reverseOrder());

            for (String feat : featurizer.getFeatureNames()) {
                Feature<?> feature = this.features.get(feat);
                for (int idx : indicesToRemove) {
                    feature.remove(idx);
                }
            }
            objects.removeAll(toRemove);
        }
    }

    /**
     * Encuentra los duplicados entre los que se dan para un feature en concreto
     * @param <V> Tipo del valor correspondiente para el feature
     * @param candidates Objetos que están posiblemente duplicados
     * @param feat Nombre de la feature que se está considerando para esta iteración
     * @return Set con los objetos posiblemente duplicados una vez se han retirado los que no lo están
     */
    private <V> Set<T> findDuplicatesForFeature(Set<T> candidates, String feat) {
        Map<V, T> seenValueToObj = new HashMap<>();
        Set<T> confirmed = new HashSet<>();

        for (T obj : candidates) {
            V value = featurizer.getFeatureValue(obj, feat);
            if (!seenValueToObj.containsKey(value)) {
                seenValueToObj.put(value, obj);
            } else {
                confirmed.add(seenValueToObj.get(value));
                confirmed.add(obj);
            }
        }
        return confirmed;
    }

    /**
     * Devuelve la lista de objetos una vez se retiran los representantes de cada grupo
     * @param possiblyDuplicated Objetos posiblemente duplicados después de iterar por todos los features
     * @return Lista con los objetos que se deben retirar del dataset
     */
    private List<T> getDefiniteDuplicates(Set<T> possiblyDuplicated) {
        List<T> sorted = new ArrayList<>(possiblyDuplicated);
        sorted.sort(Comparator.comparingInt(obj -> objects.indexOf(obj)));

        Map<String, T> groupRepresentative = new HashMap<>();
        List<T> toRemove = new ArrayList<>();

        for (T obj : sorted) {
            StringBuilder keyBuilder = new StringBuilder();
            for (String feat : featurizer.getFeatureNames()) {
                keyBuilder.append(featurizer.getFeatureValue(obj, feat).toString());
                keyBuilder.append("|");
            }
            String compositeKey = keyBuilder.toString();

            if (!groupRepresentative.containsKey(compositeKey)) {
                groupRepresentative.put(compositeKey, obj);
            } else {
                toRemove.add(obj);
            }
        }
        return toRemove;
    }

    /**
     * Devuelve el nombre de las features del featurizer
     * @return Lista de las features
     */
    public List<String> getFeatureNames() {
        return featurizer.getFeatureNames();
    }

    /**
     * Devuelve el tamaño del dataset
     * @return tamaño del dataset
     */
    public int size() {
        return objects.size();
    }

    /**
     * Devuelve los objetos del dataset
     * @return Los objetos del dataset
     */
    public List<T> getObjects() {
        return objects;
    }

    /**
     * Añade un valor a la feature correspondiente
     * @param <V> Tipo de los valores en el dataset
     * @param feature Feature a la que se añade
     * @param value Valor que se añade
     */
    @SuppressWarnings("unchecked")
    private <V extends Comparable<? super V>> void addToFeature(Feature<V> feature, Object value) {
        feature.add((V) value);
    }

    /**
     * Añade un conjunto de objetos al dataset
     * @param items Conjunto de objetos que se añaden
     */
    @SuppressWarnings("unchecked")
    public void addAll(T... items) {
        for (T item : items) {
            this.objects.add(item);
            for (String f : features.keySet()) {
                addToFeature(features.get(f), featurizer.getFeatureValue(item, f));
            }
        }
    }

    /**
     * Quita una feature del dataset
     * @param feature Nombre de la feature
     */
    protected void removeFeature(String feature) {
        features.remove(feature);
    }

    /**
     * Devuelve las features del dataset
     * @return Las features
     */
    public Map<String, Feature<?>> getFeatures() {
        return features;
    }

    /**
     * Devuelve una lista de features del dataset
     * @return Lista con las features
     */
    public List<Feature<?>> getListFeatures() {
        return this.features.values().stream().toList();
    }

    @Override
    public String toString() {
        return features.toString();
    }
}