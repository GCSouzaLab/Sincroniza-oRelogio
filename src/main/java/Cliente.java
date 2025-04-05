import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Random;

public class Cliente extends Socket {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int porta = 9999;
        Socket socket = new Socket(host, porta);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String horaServidorStr = in.readLine();
        System.out.println(horaServidorStr);
        LocalTime horaServidor = LocalTime.parse(horaServidorStr);
        System.out.println("[Cliente] Hora servidor:" + horaServidor);

        // Simula hora do cliente (pode adicionar offset para teste)
        LocalTime horaCliente = gerarHoraLocal();

        enviarDiferencaParaOServer(horaServidor, horaCliente, out);

        String ajusteStr = in.readLine();
        long ajuste = Long.parseLong(ajusteStr);
        System.out.println("[Cliente] Ajuste recebido: " + ajuste + " segundos");

        // Aplica ajuste
        LocalTime novaHora = horaCliente.plusSeconds(ajuste);
        System.out.println("[Cliente] Nova hora ajustada: " + novaHora);

        socket.close();
    }

    private static void enviarDiferencaParaOServer(LocalTime horaServidor, LocalTime horaCliente, PrintWriter out) {
        // Calcula diferença (cliente - servidor) em segundos
        long diff = getDiffBetweenServerAndClient(horaServidor, horaCliente);
        System.out.println("[Cliente] Hora local: " + horaCliente + " | Diferença em segundos: " + diff);

        System.out.println("[Cliente] Enviando diferença para o servidor!");
        out.println(diff);
    }

    private static int getDiffBetweenServerAndClient(LocalTime horaServidor, LocalTime horaCliente) {
        return horaCliente.toSecondOfDay() - horaServidor.toSecondOfDay();
    }

    private static LocalTime gerarHoraLocal() {
        Random random = new Random();
        return LocalTime.of(random.nextInt(24), random.nextInt(59));
    }
}
