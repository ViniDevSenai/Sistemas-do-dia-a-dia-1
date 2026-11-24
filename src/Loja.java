import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Loja {
    static final double FRETE_GRATIS_ACIMA = 500.00;
    static final double TAXA_FRETE = 25.00;

    static class Produto {
        String nome;
        double preco;

        Produto(String nome, double preco) {
            this.nome = nome;
            this.preco = preco;
        }
    }

    private final Map<Integer, Produto> catalogo = new HashMap<>();
    private int proximoPedido = 1000;

    public Loja() {
        catalogo.put(1, new Produto("Mouse", 80.00));
        catalogo.put(2, new Produto("Teclado", 150.00));
        catalogo.put(3, new Produto("Monitor", 700.00));
        catalogo.put(4, new Produto("Cabo HDMI", 30.00));
    }

    public void executar(Scanner sc) {
        System.out.println("\n--- COMPRA ONLINE ---");
        catalogo.forEach((id, p) -> System.out.printf("%d - %s (R$ %.2f)%n", id, p.nome, p.preco));

        double total = 0;
        while (true) {
            int id = Main.lerInt(sc, "Código do produto (0 para finalizar): ");
            if (id == 0) break;
            Produto p = catalogo.get(id);
            if (p == null) {
                System.out.println("Produto inexistente.");
                continue;
            }
            int qtd = Main.lerInt(sc, "Quantidade: ");
            if (qtd <= 0) {
                System.out.println("Quantidade inválida.");
                continue;
            }
            total += p.preco * qtd;
            System.out.printf("Adicionado. Subtotal: R$ %.2f%n", total);
        }

        if (total == 0) {
            System.out.println("Carrinho vazio. Pedido encerrado.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("CPF: ");
        String cpf = sc.nextLine().trim();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine().trim();
        System.out.print("Forma de pagamento: ");
        String pagamento = sc.nextLine().trim();

        double frete = total > FRETE_GRATIS_ACIMA ? 0 : TAXA_FRETE;
        double totalFinal = total + frete;
        System.out.printf("Produtos: R$ %.2f | Frete: R$ %.2f | Total: R$ %.2f%n", total, frete, totalFinal);

        System.out.print("Cartão aprovado? (s/n): ");
        boolean aprovado = sc.nextLine().trim().equalsIgnoreCase("s");

        if (!aprovado) {
            System.out.println("Pagamento não autorizado.");
            return;
        }

        int pedido = ++proximoPedido;
        System.out.println("Pedido nº " + pedido + " registrado.");
        System.out.println("Confirmação enviada para " + nome + " (CPF " + cpf + ") - entrega em: " + endereco + " - pagamento: " + pagamento);
        System.out.println("Compra realizada com sucesso.");
    }
}
