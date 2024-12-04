import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProcessadorArquivos {
    public static void main(String[] args) {
        try {
            List<String> palavrasChave = Files.readAllLines(Paths.get("palavras-chave.txt"));
            List<String> textoLinhas = Files.readAllLines(Paths.get("texto.txt"));
            
            StringBuilder entradaSimulada = new StringBuilder();
            
            for (String palavra : palavrasChave) {
                entradaSimulada.append(palavra).append("\n");
            }
            entradaSimulada.append("fim\n");
            
            for (String linha : textoLinhas) {
                entradaSimulada.append(linha).append("\n");
            }
            entradaSimulada.append("fim\n");
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(entradaSimulada.toString().getBytes());
            System.setIn(inputStream);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream oldOut = System.out;
            System.setOut(printStream);
            
            IndiceRemissivo.main(args);
            
            System.setOut(oldOut);
            
            String saida = outputStream.toString();
            String[] linhas = saida.split("\n");
            boolean indiceIniciado = false;
            List<String> linhasIndice = new ArrayList<>();
            
            for (String linha : linhas) {
                if (linha.contains("=== Índice Remissivo Gerado ===")) {
                    indiceIniciado = true;
                    continue;
                }
                if (linha.contains("-------------------------------")) {
                    continue;
                }
                if (indiceIniciado && !linha.trim().isEmpty()) {
                    linhasIndice.add(linha.trim());
                }
            }
            
            try (PrintWriter writer = new PrintWriter("indice.txt")) {
                for (int i = 0; i < linhasIndice.size(); i++) {
                    writer.print(linhasIndice.get(i));
                    if (i < linhasIndice.size() - 1) {
                        writer.println();
                    }
                }
            }
            
            System.out.println("Índice remissivo gerado com sucesso em 'indice.txt'");
            
        } catch (IOException e) {
            System.err.println("Erro ao processar os arquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 