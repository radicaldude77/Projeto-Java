package service;

import exception.DadosInvalidosException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Fumante;

/**
 * Classe de serviço responsável por criar e validar objetos Fumante.
 * Separa a lógica de negócio da interface gráfica.
 */
public class FumanteService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Cria um Fumante a partir de strings brutas vindas da UI.
     * Lança DadosInvalidosException (unchecked) se qualquer campo for inválido.
     *
     * @throws DadosInvalidosException se algum campo for inválido
     */
    public Fumante criarFumante(String nome,
                                String dataNascimentoStr,
                                String cigarrosPorDiaStr,
                                String precoPorMacoStr,
                                String cigarrosPorMacoStr,
                                String dataParadaStr) {

        // Validação e parse da data de nascimento
        LocalDate dataNascimento = parseData(dataNascimentoStr, "dataNascimento",
                "Data de nascimento inválida. Use o formato dd/MM/yyyy.");

        // Validação e parse dos inteiros
        int cigarrosPorDia = parseInt(cigarrosPorDiaStr, "cigarrosPorDia",
                "Cigarros por dia deve ser um número inteiro positivo.");

        double precoPorMaco = parseDouble(precoPorMacoStr, "precoPorMaco",
                "Preço do maço deve ser um número válido (ex: 12.50).");

        int cigarrosPorMaco = parseInt(cigarrosPorMacoStr, "cigarrosPorMaco",
                "Cigarros por maço deve ser um número inteiro positivo.");

        if (cigarrosPorDia <= 0) {
            throw new DadosInvalidosException("cigarrosPorDia",
                    "Cigarros por dia deve ser maior que zero.");
        }
        if (precoPorMaco <= 0) {
            throw new DadosInvalidosException("precoPorMaco",
                    "O preço do maço deve ser maior que zero.");
        }
        if (cigarrosPorMaco <= 0) {
            throw new DadosInvalidosException("cigarrosPorMaco",
                    "A quantidade de cigarros por maço deve ser maior que zero.");
        }

        // Validação e parse da data de parada
        LocalDate dataParada = parseData(dataParadaStr, "dataParada",
                "Data em que parou de fumar inválida. Use o formato dd/MM/yyyy.");

        if (dataParada.isBefore(dataNascimento)) {
            throw new DadosInvalidosException("dataParada",
                    "A data de parada não pode ser anterior à data de nascimento.");
        }

        return new Fumante(nome, dataNascimento, cigarrosPorDia,
                precoPorMaco, cigarrosPorMaco, dataParada);
    }

    // --- Helpers privados de parse/validação ---

    private LocalDate parseData(String valor, String campo, String mensagem) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                throw new DadosInvalidosException(campo, mensagem);
            }
            return LocalDate.parse(valor.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DadosInvalidosException(campo, mensagem);
        }
    }

    private int parseInt(String valor, String campo, String mensagem) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                throw new DadosInvalidosException(campo, mensagem);
            }
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new DadosInvalidosException(campo, mensagem);
        }
    }

    private double parseDouble(String valor, String campo, String mensagem) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                throw new DadosInvalidosException(campo, mensagem);
            }
            // Aceita vírgula como separador decimal
            return Double.parseDouble(valor.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new DadosInvalidosException(campo, mensagem);
        }
    }
}
