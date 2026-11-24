import java.util.Scanner;

public class Main {
    static int lerInt(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        Loja loja = new Loja();
        int op;
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Biblioteca Escolar");
            System.out.println("2 - Compra Online");
            System.out.println("0 - Sair");
            op = lerInt(sc, "Opção: ");
            switch (op) {
                case 1 -> biblioteca.executar(sc);
                case 2 -> loja.executar(sc);
                case 0 -> System.out.println("Sistema encerrado.");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
        sc.close();
    }
}
