package decision_tree;

import java.util.*;
import java.util.function.Predicate;

public class DecisionTree2<T> {
    private Nodo<T> raiz = null; // Guardamos la verdadera raíz
    private Nodo<T> nodoActual = null; // El puntero para la construcción (fluent API)
    private Map<Nodo<T>, List<DecisionTree2<T>>> estructura = new LinkedHashMap<>();
    private Map<String, T> classification = new LinkedHashMap<>();

    public DecisionTree2() {}

    public DecisionTree2<T> node(String name) {
        // Si es el primer nodo, es la raíz
        if (this.raiz == null) {
            this.raiz = new Nodo<>(name);
            this.nodoActual = raiz;
            this.estructura.put(raiz, new ArrayList<>());
        } else {
            // Buscamos el nodo por nombre en toda la estructura para mover el puntero de construcción
            this.nodoActual = buscarNodo(this.raiz, name);
        }
        return this;
    }

    private Nodo<T> buscarNodo(Nodo<T> actual, String name) {
        if (actual.getEtiqueta().equals(name)) return actual;
        List<DecisionTree2<T>> hijos = estructura.get(actual);
        if (hijos != null) {
            for (DecisionTree2<T> hijo : hijos) {
                Nodo<T> encontrado = buscarNodo(hijo.getNodo(), name);
                if (encontrado != null) return encontrado;
            }
        }
        return null;
    }

    public DecisionTree2<T> withCondition(String etiqueta, Predicate<? super T> condition) {
        DecisionTree2<T> hijoTree = new DecisionTree2<>();
        hijoTree.setNodo(new Nodo<>(etiqueta));
        
        this.estructura.computeIfAbsent(this.nodoActual, k -> new ArrayList<>()).add(hijoTree);
        this.nodoActual.addCondition(condition);
        return this;
    }

    public DecisionTree2<T> otherwise(String etiqueta) {
        DecisionTree2<T> hijoTree = new DecisionTree2<>();
        hijoTree.setNodo(new Nodo<>(etiqueta));
        this.estructura.computeIfAbsent(this.nodoActual, k -> new ArrayList<>()).add(hijoTree);
        return this;
    }

    public Map<String, T> predict(T... elements) {
        this.classification.clear(); // Limpiamos para cada llamada a predict
        for (T element : elements) {
            // Iniciamos la recursión desde la raíz
            predecirRecursivo(this.raiz, element);
        }
        return this.classification;
    }

    private void predecirRecursivo(Nodo<T> nodo, T element) {
        List<DecisionTree2<T>> hijos = estaEnEstructura(nodo);
        List<Predicate<? super T>> condiciones = nodo.getConditions();

        // Si no tiene hijos, es una hoja: clasificamos
        if (hijos == null || hijos.isEmpty()) {
            this.classification.put(nodo.getEtiqueta(), element);
            return;
        }

        boolean cumplio = false;
        for (int i = 0; i < condiciones.size(); i++) {
            if (condiciones.get(i).test(element)) {
                predecirRecursivo(hijos.get(i).getNodo(), element);
                cumplio = true;
                break;
            }
        }

        // Si no cumplió ninguna y hay un hijo extra (el otherwise)
        if (!cumplio && hijos.size() > condiciones.size()) {
            predecirRecursivo(hijos.get(hijos.size() - 1).getNodo(), element);
        }
    }

    // Método auxiliar para buscar en el mapa de estructura global
    private List<DecisionTree2<T>> estaEnEstructura(Nodo<T> n) {
        return this.estructura.get(n);
    }

    public Nodo<T> getNodo() { return nodoActual; }
    public void setNodo(Nodo<T> nodo) { this.nodoActual = nodo; }
}
