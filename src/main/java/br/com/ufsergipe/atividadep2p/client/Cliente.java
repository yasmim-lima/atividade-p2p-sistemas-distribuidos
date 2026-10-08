package br.com.ufsergipe.atividadep2p.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PORTA_PADRAO = 5000;
    private static final int BUFFER = 8192;

    public static void main(String[] args) {

        byte[] buffer = new byte[BUFFER];
        long totalRecebido = 0;

        long inicio = System.nanoTime();

        int porta = PORTA_PADRAO;

        if (args.length > 0) {
            porta = Integer.parseInt(args[0]);
        }

        try (Socket socket = new Socket(HOST, porta);
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