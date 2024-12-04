import java.io.*;
import java.util.*;

public class IndiceRemissivo {
    private TabelaHash tabelaHash;
    private Set<String> palavrasChave;
    private Scanner scanner;
    
    public IndiceRemissivo() {
        this.tabelaHash = new TabelaHash();
        this.palavrasChave = new HashSet<>();
        this.scanner = new Scanner(System.in);
    }
    
    public void executar() {
        try {
            lerPalavrasChave();
            lerTexto();
            gerarEExibirIndice();
        } catch (IOException e) {
            System.err.println("Erro ao processar o índice: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    private void lerPalavrasChave() {
        System.out.println("=== Entrada de Palavras-Chave ===");
        System.out.println("Digite as palavras-chave (uma por linha).");
        System.out.println("Digite 'fim' para terminar a entrada.");
        System.out.println("----------------------------------");
        
        while (true) {
            System.out.print("Palavra-chave: ");
            String palavra = scanner.nextLine().trim().toLowerCase();
            if (palavra.equals("fim")) {
                break;
            }
            if (!palavra.isEmpty()) {
                palavrasChave.add(palavra);
            }
        }
    }
    
    private void lerTexto() {
        System.out.println("\n=== Entrada do Texto ===");
        System.out.println("Digite o texto (uma linha por vez).");
        System.out.println("Digite 'fim' para terminar a entrada.");
        System.out.println("-------------------------");
        
        int numeroLinha = 1;
        while (true) {
            System.out.print("Linha " + numeroLinha + ": ");
            String linha = scanner.nextLine();
            if (linha.equals("fim")) {
                break;
            }
            processarLinha(linha, numeroLinha);
            numeroLinha++;
        }
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
    
    private void gerarEExibirIndice() throws IOException {
        List<Palavra> palavrasOrdenadas = new ArrayList<>();
        
        for (char c = 'a'; c <= 'z'; c++) {
            ArvoreBinaria arvore = tabelaHash.getArvore(c - 'a');
            if (arvore != null) {
                arvore.percorrerEmOrdem(palavrasOrdenadas);
            }
        }
        
        palavrasOrdenadas.sort((p1, p2) -> p1.getPalavra().compareTo(p2.getPalavra()));
        
        System.out.println("\n=== Índice Remissivo Gerado ===");
        System.out.println("-------------------------------");
        for (Palavra palavra : palavrasOrdenadas) {
            System.out.println(palavra.getPalavra() + " " + palavra.getOcorrencias().toString());
        }
    }
    
    public static void main(String[] args) {
        IndiceRemissivo indice = new IndiceRemissivo();
        indice.executar();
    }
} 