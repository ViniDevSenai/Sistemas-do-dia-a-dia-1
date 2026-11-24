import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Biblioteca {
    static final int LIMITE = 3;

    static class Aluno {
        boolean multaPendente;
        int emprestimos;

        Aluno(boolean multaPendente, int emprestimos) {
            this.multaPendente = multaPendente;
            this.emprestimos = emprestimos;
        }
    }

    static class Livro {
        String titulo;
        int estoque;

        Livro(String titulo, int estoque) {
            this.titulo = titulo;
            this.estoque = estoque;
        }
    }

    private final Map<String, Aluno> alunos = new HashMap<>();
    private final Map<String, Livro> livros = new HashMap<>();

    public Biblioteca() {
        alunos.put("1001", new Aluno(false, 0));
        alunos.put("1002", new Aluno(true, 1));
        alunos.put("1003", new Aluno(false, 3));
        livros.put("L01", new Livro("Dom Casmurro", 2));
        livros.put("L02", new Livro("Algoritmos", 1));
        livros.put("L03", new Livro("Banco de Dados", 0));
    }

    public void executar(Scanner sc) {
        System.out.println("\n--- BIBLIOTECA ESCOLAR ---");
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine().trim();

        Aluno aluno = alunos.get(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        if (aluno.multaPendente) {
            System.out.println("Empréstimo bloqueado por multa.");
            return;
        }
        if (aluno.emprestimos >= LIMITE) {
            System.out.println("Limite de empréstimos atingido.");
            return;
        }

        System.out.print("Código do livro: ");
        String codigo = sc.nextLine().trim().toUpperCase();

        Livro livro = livros.get(codigo);
        if (livro == null || livro.estoque <= 0) {
            System.out.println("Livro indisponível.");
            return;
        }

        aluno.emprestimos++;
        livro.estoque--;
        System.out.println("Empréstimo realizado com sucesso.");
    }
}
