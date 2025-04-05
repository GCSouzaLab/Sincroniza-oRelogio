import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        int numeroClientes = getNumeroDeClientes();

        System.out.println("[Servidor] Conectando clientes.");
        while (servidor.getNumeroClientes() < numeroClientes) {
            servidor.aceitarClientes();
        }

        servidor.sincronizarRelogio();
    }

    private static int getNumeroDeClientes() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o número de clientes deste servidor!");
        return scanner.nextInt();
    }

}


