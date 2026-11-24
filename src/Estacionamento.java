import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Estacionamento {
    static final int VAGAS_CARRO = 10;
    static final int VAGAS_MOTO = 5;
    static final double VALOR_PRIMEIRA_HORA = 10.00;
    static final double VALOR_HORA_ADICIONAL = 5.00;

    static class Veiculo {
        String tipo;
        LocalDateTime entrada;

        Veiculo(String tipo, LocalDateTime entrada) {
            this.tipo = tipo;
            this.entrada = entrada;
        }
    }

    private final Map<String, Veiculo> patio = new HashMap<>();
    private double caixa = 0;

    public void executar(Scanner sc) {
        int op;
        do {
            System.out.println("\n--- ESTACIONAMENTO ---");
            System.out.println("1 - Entrada");
            System.out.println("2 - Saída");
            System.out.println("0 - Voltar");
            op = Main.lerInt(sc, "Opção: ");
            switch (op) {
                case 1 -> entrada(sc);
                case 2 -> saida(sc);
                case 0 -> System.out.println("Processo encerrado.");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private long ocupadas(String tipo) {
        return patio.values().stream().filter(v -> v.tipo.equals(tipo)).count();
    }

    private void entrada(Scanner sc) {
        System.out.print("Placa: ");
        String placa = sc.nextLine().trim().toUpperCase();
        int t = Main.lerInt(sc, "Tipo (1 - Carro, 2 - Moto): ");
        String tipo = t == 2 ? "MOTO" : "CARRO";
        int capacidade = t == 2 ? VAGAS_MOTO : VAGAS_CARRO;

        if (patio.containsKey(placa)) {
            System.out.println("Veículo já está no estacionamento.");
            return;
        }
        if (ocupadas(tipo) >= capacidade) {
            System.out.println("Estacionamento lotado.");
            return;
        }

        patio.put(placa, new Veiculo(tipo, LocalDateTime.now()));
        System.out.println("Entrada registrada: " + placa + " (" + tipo + ").");
    }

    private void saida(Scanner sc) {
        System.out.print("Placa: ");
        String placa = sc.nextLine().trim().toUpperCase();
        Veiculo v = patio.get(placa);
        if (v == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        System.out.print("Minutos simulados (Enter para tempo real): ");
        String sim = sc.nextLine().trim();
        long minutos = sim.isEmpty()
                ? Duration.between(v.entrada, LocalDateTime.now()).toMinutes()
                : Long.parseLong(sim);

        long horas = Math.max(1, (long) Math.ceil(minutos / 60.0));
        double valor = VALOR_PRIMEIRA_HORA + (horas - 1) * VALOR_HORA_ADICIONAL;
        System.out.printf("Tempo: %d min | Valor a pagar: R$ %.2f%n", minutos, valor);

        System.out.print("Forma de pagamento: ");
        String pagamento = sc.nextLine().trim();
        System.out.print("Pagamento aprovado? (s/n): ");
        boolean aprovado = sc.nextLine().trim().equalsIgnoreCase("s");

        if (!aprovado) {
            System.out.println("Pagamento não autorizado.");
            return;
        }

        patio.remove(placa);
        caixa += valor;
        System.out.println("Pagamento registrado (" + pagamento + ").");
        System.out.println("Saída liberada.");
    }
}
