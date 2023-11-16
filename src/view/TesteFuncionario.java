package view;

import controller.RHController;
import model.Funcionario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TesteFuncionario {
    private static final List<Funcionario> listaDeFuncionarios = new ArrayList<>();

    public static void main(String[] args) {
        listaDeFuncionarios.add(new Funcionario("Enio", "Analista de Sistemas", LocalDate.parse("2022-11-06")));
        listaDeFuncionarios.add(new Funcionario("Francisco", "Reitor do Campus", LocalDate.parse("2022-03-01")));

        Scanner scanner = new Scanner(System.in);
        RHController rhController = new RHController();

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    inserirFuncionario(scanner);
                    break;
                case 2:
                    exibirFuncionarios();
                    break;
                case 3:
                    calcularFerias(scanner, rhController);
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("----- Menu -----");
        System.out.println("1. Inserir novo funcionário");
        System.out.println("2. Exibir funcionários cadastrados");
        System.out.println("3. Calcular férias");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void inserirFuncionario(Scanner scanner) {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o cargo do funcionário: ");
        String cargo = scanner.nextLine();

        System.out.print("Digite a data de admissão do funcionário (AAAA-MM-DD): ");
        String dataAdmissaoStr = scanner.nextLine();
        LocalDate dataAdmissao = LocalDate.parse(dataAdmissaoStr);

        Funcionario funcionario = new Funcionario(nome, cargo, dataAdmissao);
        listaDeFuncionarios.add(funcionario);

        System.out.println("Funcionário cadastrado com sucesso!");
        System.out.println("-------------------------------");
    }

    private static void exibirFuncionarios() {
        System.out.println("----- Lista de Funcionários -----");
        for (Funcionario funcionario : listaDeFuncionarios) {
            System.out.println("Nome: " + funcionario.getNome() + ", Cargo: " + funcionario.getCargo() +
                    ", Data de Admissão: " + funcionario.getDataAdmissao());
        }
        System.out.println("-------------------------------");
    }

    private static void calcularFerias(Scanner scanner, RHController rhController) {
        System.out.println("----- Calcular Férias -----");
        System.out.print("Digite o nome do funcionário: ");
        String nomeFuncionario = scanner.nextLine();

        Funcionario funcionario = rhController.procurarFuncionarioPorNome(nomeFuncionario, listaDeFuncionarios);

        if (funcionario != null) {
            System.out.println("Iniciando cálculo de férias para " + funcionario.getNome());
            System.out.println("Data de admissão: " + funcionario.getDataAdmissao());

            System.out.print("Digite a data de início das férias (AAAA-MM-DD): ");
            String dataInicioStr = scanner.nextLine();
            LocalDate dataInicio = LocalDate.parse(dataInicioStr);

            System.out.print("Digite a duração das férias em dias: ");
            int duracao = scanner.nextInt();
            scanner.nextLine();

            rhController.calcularFerias(funcionario, dataInicio, duracao);
        } else {
            System.out.println("Funcionário não encontrado. Verifique o nome e tente novamente.");
        }

        System.out.println("-------------------------------");
    }
}
