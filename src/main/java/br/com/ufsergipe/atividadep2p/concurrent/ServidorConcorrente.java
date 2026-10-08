package br.com.ufsergipe.atividadep2p.concurrent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorConcorrente {

    private static final int PORTA = 5001;
    private static final int BUFFER = 8192;

    public static void main(String[] args) {

        long tamanhoMB = 5;

        if (args.length > 0) {
            tamanhoMB = Long.parseLong(args[0]);
        }

        long tamanhoArquivo = tamanhoMB * 1024 * 1024;

        System.out.println("Servidor concorrente iniciado!");
        System.out.println("Porta: " + PORTA);
        System.out.println("Tamanho da transferência: " + tamanhoMB + " MB");

        try (ServerSocket servidor = new ServerSocket(PORTA)) {

            while (true) {

                Socket cliente = servidor.accept();

                System.out.println(
                        "Cliente conectado: " + cliente.getInetAddress()
                );

                Thread thread = new Thread(() -> {

                    try (cliente) {

                        enviarArquivo(cliente, tamanhoArquivo);

                        System.out.println(
                                "Transferência finalizada para "
                                        + cliente.getInetAddress()
                        );

                    } catch (IOException e) {

                        System.err.println(
                                "Erro no atendimento: " + e.getMessage()
                        );
                    }
                });

                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void enviarArquivo(
            Socket cliente,
            long tamanhoArquivo
    ) throws IOException {

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