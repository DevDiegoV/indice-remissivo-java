public class ArvoreBinaria {
    class Nodo {
        public Palavra elemento;
        public Nodo esquerdo;
        public Nodo direito;

        public Nodo(Palavra elemento) {
            this.elemento = elemento;
            this.esquerdo = null;
            this.direito = null;
        }
    }

    private Nodo raiz;
    private int nElementos;

    public ArvoreBinaria() {
        this.raiz = null;
        this.nElementos = 0;
    }

    public void inserir(Palavra palavra) {
        raiz = inserirRec(raiz, palavra);
        nElementos++;
    }

    private Nodo inserirRec(Nodo nodo, Palavra palavra) {
        if (nodo == null) {
            return new Nodo(palavra);
        }
        
        int comparacao = palavra.getPalavra().compareTo(nodo.elemento.getPalavra());
        if (comparacao < 0) {
            nodo.esquerdo = inserirRec(nodo.esquerdo, palavra);
        } else if (comparacao > 0) {
            nodo.direito = inserirRec(nodo.direito, palavra);
        }
        
        return nodo;
    }

    public Palavra buscar(String palavra) {
        return buscarRec(raiz, palavra);
    }

    private Palavra buscarRec(Nodo nodo, String palavra) {
        if (nodo == null) {
            return null;
        }
        
        int comparacao = palavra.compareTo(nodo.elemento.getPalavra());
        if (comparacao == 0) {
            return nodo.elemento;
        }
        
        if (comparacao < 0) {
            return buscarRec(nodo.esquerdo, palavra);
        }
        
        return buscarRec(nodo.direito, palavra);
    }

    public void percorrerEmOrdem(java.util.List<Palavra> palavras) {
        percorrerEmOrdemRec(raiz, palavras);
    }

    private void percorrerEmOrdemRec(Nodo nodo, java.util.List<Palavra> palavras) {
        if (nodo != null) {
            percorrerEmOrdemRec(nodo.esquerdo, palavras);
            palavras.add(nodo.elemento);
            percorrerEmOrdemRec(nodo.direito, palavras);
        }
    }
} 