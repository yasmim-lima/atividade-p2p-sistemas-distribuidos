package br.com.ufsergipe.atividadep2p.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PORTA = 5000;
    private static final int BUFFER = 8192;

    public static void main(String[] args) {

        byte[] buffer = new byte[BUFFER];
        long totalRecebido = 0;

        long inicio = System.nanoTime();

        try (Socket socket = new Socket(HOST, PORTA);
             InputStream entrada = socket.getInputStream()) {

            int bytesLidos;

            while ((bytesLidos = entrada.read(buffer)) != -1) {
                totalRecebido += bytesLidos;
            }

            long fim = System.nanoTime();

            double tempoMs = (fim - inicio) / 1_000_000.0;

            System.out.println("Transferência concluída!");
            System.out.println("Bytes recebidos: " + totalRecebido);
            System.out.printf("Tempo: %.2f ms%n", tempoMs);

        } catch (IOException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
    }
}