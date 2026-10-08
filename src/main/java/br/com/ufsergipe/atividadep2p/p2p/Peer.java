package br.com.ufsergipe.atividadep2p.p2p;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Peer {

    private static final int PORTA = 6000;
    private static final int BUFFER = 8192;

    public static void main(String[] args) {

        int porta = PORTA;
        boolean servidor = false;
        long tamanhoMB = 5;

        if (args.length > 0) {
            porta = Integer.parseInt(args[0]);
        }

        if (args.length > 1 && args[1].equalsIgnoreCase("servidor")) {
            servidor = true;
        }

        if (args.length > 2) {
            tamanhoMB = Long.parseLong(args[2]);
        }

        if (servidor) {
            iniciarServidor(porta, tamanhoMB);
        } else {
            iniciarCliente(porta);
        }
    }

    private static void iniciarServidor(int porta, long tamanhoMB) {

        System.out.println("Peer servidor iniciado!");
        System.out.println("Porta: " + porta);
        System.out.println("Tamanho da transferência: " + tamanhoMB + " MB");

        try (ServerSocket servidor = new ServerSocket(porta)) {

            while (true) {

                Socket cliente = servidor.accept();

                System.out.println(
                        "Peer conectado: " + cliente.getInetAddress()
                );

                Thread thread = new Thread(() -> {

                    try (cliente) {

                        enviarDados(cliente, tamanhoMB);

                        System.out.println(
                                "Transferência P2P finalizada!"
                        );

                    } catch (IOException e) {

                        System.err.println(
                                "Erro na transferência: "
                                        + e.getMessage()
                        );
                    }
                });

                thread.start();
            }

        } catch (IOException e) {

            System.err.println(
                    "Erro no peer: " + e.getMessage()
            );
        }
    }

    private static void iniciarCliente(int porta) {

        long totalRecebido = 0;

        long inicio = System.nanoTime();

        try (Socket socket = new Socket("localhost", porta);
             InputStream entrada = socket.getInputStream()) {

            byte[] buffer = new byte[BUFFER];

            int bytesLidos;

            while ((bytesLidos = entrada.read(buffer)) != -1) {
                totalRecebido += bytesLidos;
            }

            long fim = System.nanoTime();

            double tempoMs =
                    (fim - inicio) / 1_000_000.0;

            System.out.println(
                    "Transferência P2P concluída!"
            );

            System.out.println(
                    "Bytes recebidos: " + totalRecebido
            );

            System.out.printf(
                    "Tempo: %.2f ms%n",
                    tempoMs
            );

        } catch (IOException e) {

            System.err.println(
                    "Erro na conexão P2P: "
                            + e.getMessage()
            );
        }
    }

    private static void enviarDados(
            Socket cliente,
            long tamanhoMB
    ) throws IOException {

        OutputStream saida = cliente.getOutputStream();

        long tamanhoArquivo =
                tamanhoMB * 1024 * 1024;

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