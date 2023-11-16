package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Funcionario {
    private String nome;
    private String cargo;
    private LocalDate dataAdmissao;
    private int saldoFerias;

    public Funcionario(String nome, String cargo, LocalDate dataAdmissao) {
        this.nome = nome;
        this.cargo = cargo;
        this.dataAdmissao = dataAdmissao;
        this.saldoFerias = calcularSaldoFerias();
    }

    private int calcularSaldoFerias() {
        if (tempoTrabalhoSuficiente()) {
            int anosTrabalhados = calcularAnosTrabalhados();
            int mesesAquisitivos = anosTrabalhados * 12; // Meses completos
            LocalDate dataAquisicao = dataAdmissao.plusMonths(mesesAquisitivos - 1); // Ajuste para considerar o mês de admissão

            // Verifica se já passou um ano desde a data de aquisição do direito
            if (LocalDate.now().isAfter(dataAquisicao)) {
                int periodosAquisitivosCompletos = (int) ChronoUnit.YEARS.between(dataAdmissao, LocalDate.now());
                saldoFerias = 30 + (periodosAquisitivosCompletos - 1); // 30 dias iniciais + 1/12 por ano adicional
            } else {
                saldoFerias = 0;
            }
        } else {
            saldoFerias = 0;
        }
        return saldoFerias;
    }


    public boolean verificarDireitoFerias() {
        return tempoTrabalhoSuficiente() && saldoFerias > 0;
    }

    private boolean tempoTrabalhoSuficiente() {
        LocalDate hoje = LocalDate.now();
        LocalDate umAnoAtras = dataAdmissao.plusYears(1);
        return !hoje.isBefore(umAnoAtras);
    }

    private int calcularAnosTrabalhados() {
        LocalDate hoje = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(dataAdmissao, hoje);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public int getSaldoFerias() {
        return saldoFerias;
    }

    public void setSaldoFerias(int saldoFerias) {
        this.saldoFerias = saldoFerias;
    }

}

