package br.com.ufsergipe.atividadep2p;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Benchmark {

    private static final String HOST = "localhost";
    private static final int BUFFER = 8192;

    public static void main(String[] args) throws InterruptedException {

        int porta = 5000;
        int clientes = 1;
        long tamanhoMB = 5;

        if (args.length > 0) {
            porta = Integer.parseInt(args[0]);
        }

        if (args.length > 1) {
            clientes = Integer.parseInt(args[1]);
        }

        if (args.length > 2) {
            tamanhoMB = Long.parseLong(args[2]);
        }

        System.out.println("=================================");
        System.out.println("       BENCHMARK DE TRANSFERÊNCIA");
        System.out.println("=================================");
        System.out.println("Porta: " + porta);
        System.out.println("Clientes: " + clientes);
        System.out.println("Tamanho: " + tamanhoMB + " MB");
        System.out.println();

        // Variável final para poder ser usada pelas threads
        final int portaFinal = porta;

        List<Double> tempos = Collections.synchronizedList(
                new ArrayList<>()
        );

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= clientes; i++) {

            final int numeroCliente = i;

            Thread thread = new Thread(() -> {

                try {

                    long inicio = System.nanoTime();

                    long totalRecebido =
                            receberArquivo(portaFinal);

                    long fim = System.nanoTime();

                    double tempoMs =
                            (fim - inicio) / 1_000_000.0;

                    tempos.add(tempoMs);

                    System.out.printf(
                            "Cliente %d: %d bytes - %.2f ms%n",
                            numeroCliente,
                            totalRecebido,
                            tempoMs
                    );

                } catch (IOException e) {

                    System.err.println(
                            "Cliente " + numeroCliente
                                    + " falhou: "
                                    + e.getMessage()
                    );
                }
            });

            threads.add(thread);
        }

        long inicioBenchmark = System.nanoTime();

        // Inicia todos os clientes
        for (Thread thread : threads) {
            thread.start();
        }

        // Aguarda todos terminarem
        for (Thread thread : threads) {
            thread.join();
        }

        long fimBenchmark = System.nanoTime();

        if (!tempos.isEmpty()) {

            double minimo = Collections.min(tempos);

            double maximo = Collections.max(tempos);

            double media = tempos.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            double tempoTotal =
                    (fimBenchmark - inicioBenchmark)
                            / 1_000_000.0;

            System.out.println();
            System.out.println("----------- RESULTADOS -----------");

            System.out.printf(
                    "Mínimo: %.2f ms%n",
                    minimo
            );

            System.out.printf(
                    "Média:  %.2f ms%n",
                    media
            );

            System.out.printf(
                    "Máximo: %.2f ms%n",
                    maximo
            );

            System.out.printf(
                    "Tempo total: %.2f ms%n",
                    tempoTotal
            );
        }
    }

    private static long receberArquivo(int porta)
            throws IOException {

        long totalRecebido = 0;

        try (Socket socket = new Socket(HOST, porta);
             InputStream entrada = socket.getInputStream()) {

            byte[] buffer = new byte[BUFFER];

            int bytesLidos;

            while ((bytesLidos = entrada.read(buffer)) != -1) {

                totalRecebido += bytesLidos;
            }
        }

        return totalRecebido;
    }
}