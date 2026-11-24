import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Clinica {
    static class Horario {
        String hora;
        boolean livre = true;

        Horario(String hora) {
            this.hora = hora;
        }
    }

    private final Map<String, String> pacientes = new HashMap<>();
    private final Map<String, List<Horario>> agenda = new HashMap<>();
    private final List<String> consultas = new ArrayList<>();

    public Clinica() {
        pacientes.put("11111111111", "Maria Silva");
        pacientes.put("22222222222", "João Souza");

        agenda.put("cardiologia", horarios("08:00", "09:00", "10:00"));
        agenda.put("dermatologia", horarios("14:00", "15:00"));
        agenda.put("ortopedia", new ArrayList<>());
    }

    private List<Horario> horarios(String... horas) {
        List<Horario> lista = new ArrayList<>();
        for (String h : horas) lista.add(new Horario(h));
        return lista;
    }

    public void executar(Scanner sc) {
        System.out.println("\n--- AGENDAMENTO MÉDICO ---");
        System.out.print("CPF: ");
        String cpf = sc.nextLine().trim();
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Especialidade (cardiologia, dermatologia, ortopedia): ");
        String especialidade = sc.nextLine().trim().toLowerCase();

        if (!pacientes.containsKey(cpf)) {
            System.out.println("Paciente não cadastrado.");
            return;
        }

        List<Horario> livres = new ArrayList<>();
        for (Horario h : agenda.getOrDefault(especialidade, new ArrayList<>())) {
            if (h.livre) livres.add(h);
        }
        if (livres.isEmpty()) {
            System.out.println("Não há horários disponíveis.");
            return;
        }

        System.out.println("Horários livres:");
        for (int i = 0; i < livres.size(); i++) {
            System.out.println((i + 1) + " - " + livres.get(i).hora);
        }
        int escolha = Main.lerInt(sc, "Escolha o horário: ");
        if (escolha < 1 || escolha > livres.size()) {
            System.out.println("Horário indisponível.");
            return;
        }

        Horario escolhido = livres.get(escolha - 1);
        if (!escolhido.livre) {
            System.out.println("Horário indisponível.");
            return;
        }

        escolhido.livre = false;
        consultas.add(cpf + " | " + especialidade + " | " + escolhido.hora);
        System.out.println("Confirmação enviada para " + nome + ": " + especialidade + " às " + escolhido.hora + ".");
        System.out.println("Consulta agendada com sucesso.");
    }
}
