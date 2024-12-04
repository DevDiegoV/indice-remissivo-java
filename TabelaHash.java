public class TabelaHash {
    private ArvoreBinaria[] tabela;
    private static final int TAMANHO = 26;
    
    public TabelaHash() {
        this.tabela = new ArvoreBinaria[TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            tabela[i] = new ArvoreBinaria();
        }
    }
    
    private int funcaoHash(String palavra) {
        if (palavra == null || palavra.isEmpty()) {
            return 0;
        }
        return palavra.toLowerCase().charAt(0) - 'a';
    }
    
    public void inserir(Palavra palavra) {
        int indice = funcaoHash(palavra.getPalavra());
        if (indice >= 0 && indice < TAMANHO) {
            tabela[indice].inserir(palavra);
        }
    }
    
    public Palavra buscar(String palavra) {
        int indice = funcaoHash(palavra);
        if (indice >= 0 && indice < TAMANHO) {
            return tabela[indice].buscar(palavra);
        }
        return null;
    }
    
    public ArvoreBinaria getArvore(int indice) {
        if (indice >= 0 && indice < TAMANHO) {
            return tabela[indice];
        }
        return null;
    }
} 