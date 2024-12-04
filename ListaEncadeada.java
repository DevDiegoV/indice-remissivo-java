public class ListaEncadeada {
    private static class Nodo {
        public int elemento;
        public Nodo proximo;

        public Nodo(int elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    private Nodo primeiro;
    private Nodo ultimo;
    private int n_elementos;

    public ListaEncadeada() {
        this.primeiro = null;
        this.ultimo = null;
        this.n_elementos = 0;
    }

    public void adicionar(int elemento) {
        insereFinal(elemento);
    }

    public void insereFinal(int elemento) {
        Nodo novoNodo = new Nodo(elemento);
        if (ultimo == null) {
            primeiro = novoNodo;
            ultimo = novoNodo;
        } else {
            ultimo.proximo = novoNodo;
            ultimo = novoNodo;
        }
        n_elementos++;
    }

    public boolean contem(int valor) {
        Nodo atual = primeiro;
        while (atual != null) {
            if (atual.elemento == valor) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo atual = primeiro;
        while (atual != null) {
            sb.append(atual.elemento);
            if (atual.proximo != null) {
                sb.append(" ");
            }
            atual = atual.proximo;
        }
        return sb.toString();
    }
} 