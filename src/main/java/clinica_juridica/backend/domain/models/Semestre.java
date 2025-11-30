package clinica_juridica.backend.domain.models;

import java.time.LocalDate;

public class Semestre {
    private Integer id;
    private String termino;
    private String periodo;
    private LocalDate fechaIni;
    private LocalDate fechaFin;

    public Semestre() {
    }

    public Semestre(Integer id, String termino, String periodo, LocalDate fechaIni, LocalDate fechaFin) {
        this.id = id;
        this.termino = termino;
        this.periodo = periodo;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}

