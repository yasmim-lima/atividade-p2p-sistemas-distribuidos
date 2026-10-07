package br.com.ufsergipe.atividadep2p.sequential;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSequencial {

    private static final int PORTA = 5000;
    private static final int BUFFER = 8192;

    public static void main(String[] args) {

        long tamanhoMB = 5;

        if (args.length > 0) {
            tamanhoMB = Long.parseLong(args[0]);
        }

        long tamanhoArquivo = tamanhoMB * 1024 * 1024;

        System.out.println("Servidor sequencial iniciado!");

        try (ServerSocket servidor = new ServerSocket(PORTA)) {

            System.out.println("Aguardando clientes na porta " + PORTA);
            System.out.println("Tamanho da transferência: " + tamanhoMB + " MB");

            while (true) {

                try (Socket cliente = servidor.accept()) {

                    System.out.println("Cliente conectado!");

                    enviarArquivo(cliente, tamanhoArquivo);

                    System.out.println("Transferência finalizada!");

                } catch (IOException e) {
                    System.err.println("Erro no atendimento: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enviarArquivo(Socket cliente, long tamanhoArquivo)
            throws IOException {

        OutputStream saida = cliente.getOutputStream();

        byte[] buffer = new byte[BUFFER];

        long enviados = 0;

        while (enviados < tamanhoArquivo) {

            int quantidade = (int) Math.min(
                    buffer.length,
                    tamanhoArquivo - enviados
            );

            saida.write(buffer, 0, quantidade);

            enviados += quantidade;
        }

        saida.flush();
    }
}