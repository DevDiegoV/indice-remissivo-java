import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProcessadorArquivos {
    private TabelaHash tabelaHash;
    private Set<String> palavrasChave;
    
    public ProcessadorArquivos() {
        this.tabelaHash = new TabelaHash();
        this.palavrasChave = new HashSet<>();
    }
    
    public void processarArquivos() throws IOException {

        List<String> linhasPalavrasChave = Files.readAllLines(Paths.get("palavras-chave.txt"));
        for (String palavra : linhasPalavrasChave) {
            palavrasChave.add(palavra.trim().toLowerCase());
        }
        
        List<String> textoLinhas = Files.readAllLines(Paths.get("texto.txt"));
        for (int numeroLinha = 0; numeroLinha < textoLinhas.size(); numeroLinha++) {
            processarLinha(textoLinhas.get(numeroLinha), numeroLinha + 1);
        }
        
        gerarArquivoIndice();
    }
    
    private void processarLinha(String linha, int numeroLinha) {
        String[] palavras = linha.toLowerCase()
                                .replaceAll("[^a-záéíóúâêîôûãõç\\s-]", "")
                                .split("\\s+");
        
        for (String palavra : palavras) {
            if (palavrasChave.contains(palavra)) {
                Palavra palavraExistente = tabelaHash.buscar(palavra);
                if (palavraExistente == null) {
                    Palavra novaPalavra = new Palavra(palavra);
                    novaPalavra.adicionarOcorrencia(numeroLinha);
                    tabelaHash.inserir(novaPalavra);
                } else {
                    palavraExistente.adicionarOcorrencia(numeroLinha);
                }
            }
        }
    }
    
    private void gerarArquivoIndice() throws IOException {
        List<Palavra> palavrasOrdenadas = new ArrayList<>();
        
        for (char c = 'a'; c <= 'z'; c++) {
            ArvoreBinaria arvore = tabelaHash.getArvore(c - 'a');
            if (arvore != null) {
                arvore.percorrerEmOrdem(palavrasOrdenadas);
            }
        }
        
        palavrasOrdenadas.sort((p1, p2) -> p1.getPalavra().compareTo(p2.getPalavra()));
        
        try (PrintWriter writer = new PrintWriter("indice.txt")) {
            for (int i = 0; i < palavrasOrdenadas.size(); i++) {
                Palavra palavra = palavrasOrdenadas.get(i);
                writer.println(palavra.getPalavra() + " " + palavra.getOcorrencias().toString());
            }
        }
        
        System.out.println("Índice remissivo gerado com sucesso em 'indice.txt'");
    }
    
    public static void main(String[] args) {
        ProcessadorArquivos processador = new ProcessadorArquivos();
        try {
            processador.processarArquivos();
        } catch (IOException e) {
            System.err.println("Erro ao processar os arquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 