import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Servidor servidor = new Servidor();
        System.out.println("Você quer automatizar os clientes com threads ou subir manualmente os clientes para conectar ao servidor? [0 = Não, 1 = Sim]");
        int automatico = scanner.nextInt();
        int numeroClientes = askNumeroDeClientes(scanner);

        System.out.println("[Servidor] Conectando clientes.");
        while (servidor.getNumeroClientes() < numeroClientes) {
            if (automatico == 1) {
                new Thread(new Cliente()).start();
            }
            servidor.aceitarClientes();
        }

        List<Socket> clientes = servidor.sincronizarRelogio();
        System.out.println("[Servidor] Hora final servidor: " + servidor.getHoraServidor());
        for (Socket cliente : clientes) {
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            System.out.println("[Servidor] Hora final do cliente: " + in.readLine());
            cliente.close();
        }
        servidor.getServerSocket().close();
    }

    private static int askNumeroDeClientes(Scanner scanner) {
        System.out.println("Informe o número de clientes deste servidor!");
        return scanner.nextInt();
    }
}


