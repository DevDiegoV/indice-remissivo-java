public class Palavra {
    private String palavra;
    private ListaEncadeada ocorrencias;
    
    public Palavra(String palavra) {
        this.palavra = palavra;
        this.ocorrencias = new ListaEncadeada();
    }
    
    public String getPalavra() {
        return palavra;
    }
    
    public void adicionarOcorrencia(int linha) {
        ocorrencias.adicionar(linha);
    }
    
    public ListaEncadeada getOcorrencias() {
        return ocorrencias;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Palavra) {
            return this.palavra.equals(((Palavra) obj).getPalavra());
        }
        return false;
    }
} 