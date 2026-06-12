package model;

import exception.DadosInvalidosException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Subclasse de Usuario que representa um ex-fumante.
 * Demonstra Herança, Encapsulamento e Polimorfismo.
 */
public class Fumante extends Usuario {

    private int cigarrosPorDia;
    private double precoPorMaco;        // preço de um maço
    private int cigarrosPorMaco;        // quantidade de cigarros por maço
    private LocalDate dataParada;       // dia em que parou de fumar

    public Fumante(String nome, LocalDate dataNascimento,
                   int cigarrosPorDia, double precoPorMaco,
                   int cigarrosPorMaco, LocalDate dataParada) {
        super(nome, dataNascimento);
        setCigarrosPorDia(cigarrosPorDia);
        setPrecoPorMaco(precoPorMaco);
        setCigarrosPorMaco(cigarrosPorMaco);
        setDataParada(dataParada);
    }

    // --- Getters e Setters com validação ---

    public int getCigarrosPorDia() {
        return cigarrosPorDia;
    }

    public void setCigarrosPorDia(int cigarrosPorDia) {
        if (cigarrosPorDia <= 0) {
            throw new DadosInvalidosException("cigarrosPorDia",
                    "A quantidade de cigarros por dia deve ser maior que zero.");
        }
        this.cigarrosPorDia = cigarrosPorDia;
    }

    public double getPrecoPorMaco() {
        return precoPorMaco;
    }

    public void setPrecoPorMaco(double precoPorMaco) {
        if (precoPorMaco <= 0) {
            throw new DadosInvalidosException("precoPorMaco",
                    "O preço do maço deve ser maior que zero.");
        }
        this.precoPorMaco = precoPorMaco;
    }

    public int getCigarrosPorMaco() {
        return cigarrosPorMaco;
    }

    public void setCigarrosPorMaco(int cigarrosPorMaco) {
        if (cigarrosPorMaco <= 0) {
            throw new DadosInvalidosException("cigarrosPorMaco",
                    "A quantidade de cigarros por maço deve ser maior que zero.");
        }
        this.cigarrosPorMaco = cigarrosPorMaco;
    }

    public LocalDate getDataParada() {
        return dataParada;
    }

    public void setDataParada(LocalDate dataParada) {
        if (dataParada == null) {
            throw new DadosInvalidosException("dataParada",
                    "A data de parada não pode ser nula.");
        }
        if (dataParada.isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("dataParada",
                    "A data de parada não pode ser no futuro.");
        }
        this.dataParada = dataParada;
    }

    // --- Lógica de negócio ---

    /**
     * Retorna quantos dias sem fumar (de dataParada até hoje).
     */
    public long getDiasSemFumar() {
        return ChronoUnit.DAYS.between(dataParada, LocalDate.now());
    }

    /**
     * Retorna quantos cigarros não foram fumados desde a parada.
     */
    public long getCigarrosNaoFumados() {
        return getDiasSemFumar() * cigarrosPorDia;
    }

    /**
     * Retorna o dinheiro economizado com base nos cigarros não fumados.
     * Cálculo: (cigarros não fumados / cigarros por maço) * preço por maço
     */
    public double getDinheiroEconomizado() {
        return ((double) getCigarrosNaoFumados() / cigarrosPorMaco) * precoPorMaco;
    }

    /**
     * Polimorfismo — sobrescreve getResumo() de Usuario.
     */
    @Override
    public String getResumo() {
        return String.format(
                "Ex-fumante: %s | Dias sem fumar: %d | Economizado: R$ %.2f",
                getNome(), getDiasSemFumar(), getDinheiroEconomizado()
        );
    }
}
