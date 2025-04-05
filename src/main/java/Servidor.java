import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Servidor {
    ServerSocket servidor = new ServerSocket(9999);
    LocalTime horaServidor = LocalTime.of(10, 30);

    private List<Socket> clientes = new ArrayList<>();

    public Servidor() throws IOException {
    }

    public Servidor(List<Socket> clientes) throws IOException {
        this.clientes = clientes;
    }

    public int getNumeroClientes() {
        return clientes.size();
    }

    public void aceitarClientes() throws IOException {
        Socket cliente = servidor.accept();
        clientes.add(cliente);
        System.out.println("[Servidor] Cliente conectado: " + cliente.getInetAddress().getHostAddress());
    }

    public void sincronizarRelogio() throws IOException {
        List<Pair<Socket, Long>> diferencas = capturarDiferencaEntreServidorEClientes();

        List<Long> diferencasRelogio = diferencas.stream().map(Pair::getRight).collect(Collectors.toList());
        long media = calcularMediaDosRelogios(diferencasRelogio);
        ajustarHoraServidor(media);

        System.out.println("[Servidor] Media: " + media);
        enviarAjustesParaOsClientes(diferencas, media);

        for (Socket cliente : clientes) cliente.close();
        System.out.println("[Servidor] The end!");
        servidor.close();
    }

    private void ajustarHoraServidor(long media) {
        System.out.println("[Servidor] Servidor ajustando hora de servidor com a media: " + media);
        this.horaServidor = media >= 0 ? horaServidor.plusSeconds(media) : horaServidor.minusSeconds(-media);
        System.out.println("[Servidor] Hora servidor ajustada: " + horaServidor);
    }

    private static void enviarAjustesParaOsClientes(List<Pair<Socket, Long>> diferencas, long media) throws IOException {
        System.out.println("[Servidor] Enviando ajustes para os clientes...");
        for (Pair<Socket, Long> pair : diferencas) {
            Socket cliente = pair.getLeft();
            long diff = pair.getRight();

            PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
            long ajuste = Math.negateExact(diff - media);
            out.println(ajuste);
        }
    }

    private long calcularMediaDosRelogios(List<Long> diferencas) {
        System.out.println("[Servidor] Calculando a média...");
        long soma = diferencas.stream().reduce(0L, Long::sum);
        // + 1 pois precisamos considerar o servidor
        return soma / (diferencas.size() + 1);
    }

    private List<Pair<Socket, Long>> capturarDiferencaEntreServidorEClientes() {
        List<Pair<Socket, Long>> diferencas = new ArrayList<>();
        for (Socket cliente : clientes) {
            try {
                // out -> Envia para o cliente no println()
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                // Ler o retorno do cliente.
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                out.println(horaServidor);
                long diferenca = Long.parseLong(in.readLine());
                System.out.println("Recebendo diferenca do cliente: " + diferenca);
                diferencas.add(Pair.of(cliente, diferenca));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return diferencas;
    }
}
