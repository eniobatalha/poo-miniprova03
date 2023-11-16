package controller;

import model.Funcionario;
import model.PeriodoFerias;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

            // Confirmar o registro de férias apenas se o cálculo for bem-sucedido
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

    public boolean verificarCriteriosFerias(Funcionario funcionario, LocalDate dataInicio, int duracao) {
        System.out.println("Data de admissão: " + funcionario.getDataAdmissao());

        LocalDate dataAquisitivaInicio = funcionario.getDataAdmissao().plusYears(1);
        LocalDate dataAquisitivaLimite = dataAquisitivaInicio.plusYears(1).minusDays(1);

        System.out.println("Data aquisitiva início: " + dataAquisitivaInicio);
        System.out.println("Data aquisitiva limite: " + dataAquisitivaLimite);
        System.out.println("Data de início das férias: " + dataInicio);

        int duracaoMinima = 14;
        int duracaoMaxima = 30;

        if (duracao < duracaoMinima || duracao > duracaoMaxima) {
            System.out.println("O período de férias deve ser no mínimo " + duracaoMinima + " dias e no máximo "
                    + duracaoMaxima + " dias corridos de acordo com as regras da CLT.");
            return false;
        }

        if (dataInicio.isAfter(dataAquisitivaInicio) || dataInicio.isEqual(dataAquisitivaInicio)
                && dataInicio.isBefore(dataAquisitivaLimite)) {
            return true;
        } else {
            System.out.println("A data de início das férias deve ser após o período aquisitivo do funcionário.");
            return false;
        }


    }

    private PeriodoFerias calcularPeriodoFerias(Funcionario funcionario, LocalDate dataInicio, int duracao) {
        if (dataInicio.isBefore(funcionario.getDataAdmissao().plusMonths(12))) {
            System.out.println("A data de início das férias deve ser após o período aquisitivo do funcionário.");
            return null;
        }

        LocalDate dataTermino = dataInicio.plusDays(duracao - 1);

        // Verificar se o período ultrapassa 30 dias corridos
        long diasAteLimite = ChronoUnit.DAYS.between(dataInicio, dataTermino) + 1;

        if (diasAteLimite < 14 || diasAteLimite > 30) {
            System.out.println("O período de férias deve ser no mínimo 14 dias e no máximo 30 dias corridos de acordo com as regras da CLT.");
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
