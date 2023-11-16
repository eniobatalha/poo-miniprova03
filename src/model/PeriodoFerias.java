package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PeriodoFerias {
    private LocalDate dataInicio;
    private LocalDate dataTermino;

    public PeriodoFerias(LocalDate dataInicio, LocalDate dataTermino) {
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
    }

    public int getDuracao() {
        return (int)ChronoUnit.DAYS.between(dataInicio, dataTermino) + 1; // Adicionando 1 para incluir a data de início
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

}
