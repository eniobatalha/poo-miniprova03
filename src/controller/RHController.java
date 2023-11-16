package controller;

import model.Funcionario;
import model.PeriodoFerias;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RHController {
    public void calcularFerias(Funcionario funcionario, LocalDate dataInicio, int duracao) {
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.println("Iniciando cálculo de férias para " + funcionario.getNome());
        System.out.println("Data de admissão: " + funcionario.getDataAdmissao());
        System.out.println("Data de início das férias: " + dataInicio);
        System.out.println("Duração das férias: " + duracao + " dias");

        if (verificarCriteriosFerias(funcionario, dataInicio, duracao)) {
            System.out.println("Cálculo de férias aprovado. Executando cálculo...");

            // Calcular o período de férias
            PeriodoFerias periodoFerias = calcularPeriodoFerias(funcionario, dataInicio, duracao);

            // Exibir informações
            exibirInformacoes(funcionario);
            exibirInformacoesFerias(periodoFerias);

            // Confirmar o registro de férias
            confirmarRegistroFerias(new Scanner(System.in));
        } else {
            System.out.println("Critérios de férias não atendidos. Encerrando o cálculo.");
        }

        System.out.println("Cálculo de férias concluído.");
    }

    public Funcionario procurarFuncionarioPorNome(String nome, List<Funcionario> listaFuncionarios) {
        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario.getNome().equalsIgnoreCase(nome)) {
                return funcionario;
            }
        }
        return null;
    }

    private boolean verificarCriteriosFerias(Funcionario funcionario, LocalDate dataInicio, int duracao) {
        if (!funcionario.verificarDireitoFerias()) {
            System.out.println("O funcionário não possui direito a férias no momento, de acordo com as regras da CLT.");
            return false;
        }

        // Atualizado para considerar o período aquisitivo de 12 meses
        LocalDate dataAquisitivaInicio = funcionario.getDataAdmissao().plusMonths(1);
        LocalDate dataAquisitivaLimite = funcionario.getDataAdmissao().plusMonths(12);

        System.out.println("Data de Início do Período Aquisitivo: " + dataAquisitivaInicio);
        System.out.println("Data Limite do Período Aquisitivo: " + dataAquisitivaLimite);
        System.out.println("Data de Início das Férias: " + dataInicio);

        if (dataInicio.isBefore(dataAquisitivaInicio) || dataInicio.isAfter(dataAquisitivaLimite)) {
            System.out.println("O período de férias deve estar dentro do período aquisitivo do funcionário.");
            return false;
        }

        if (duracao > 30) {
            System.out.println("O período de férias não pode ser superior a 30 dias corridos.");
            return false;
        }

        return true;
    }

    private PeriodoFerias calcularPeriodoFerias(Funcionario funcionario, LocalDate dataInicio, int duracao) {
        if (dataInicio.isBefore(funcionario.getDataAdmissao().plusMonths(12))) {
            System.out.println("A data de início das férias deve ser após o período aquisitivo do funcionário.");
            return null;
        }

        LocalDate dataTermino = dataInicio.plusDays(duracao - 1);
        if (dataTermino.isAfter(funcionario.getDataAdmissao().plusMonths(12).plusDays(30))) {
            System.out.println("O período de férias ultrapassa o limite permitido pela CLT (30 dias corridos).");
            return null;
        }

        return new PeriodoFerias(dataInicio, dataTermino);
    }

    private void exibirInformacoes(Funcionario funcionario) {
        System.out.println("Informações do Funcionário:");
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Cargo: " + funcionario.getCargo());
        System.out.println("Data de Admissão: " + funcionario.getDataAdmissao());
        System.out.println("Saldo de Férias: " + funcionario.getSaldoFerias() + " dias");
        System.out.println("-------------------------------");
    }

    private void exibirInformacoesFerias(PeriodoFerias periodo) {
        if (periodo != null) {
            System.out.println("Período de Férias Calculado:");
            System.out.println("Data de Início: " + periodo.getDataInicio());
            System.out.println("Data de Término: " + periodo.getDataTermino());
            System.out.println("Duração: " + periodo.getDuracao() + " dias");
            System.out.println("-------------------------------");
        } else {
            System.out.println("Não foi possível calcular o período de férias.");
            System.out.println("-------------------------------");
        }
    }

    private void confirmarRegistroFerias(Scanner scanner) {
        System.out.print("Deseja confirmar o registro de férias? (S para sim, N para não): ");
        String resposta = scanner.nextLine().toUpperCase();
        if ("S".equals(resposta)) {
            System.out.println("Férias confirmadas e registradas no banco de dados do nosso RH.");
        } else {
            System.out.println("Registro de férias cancelado.");
        }
    }
}
