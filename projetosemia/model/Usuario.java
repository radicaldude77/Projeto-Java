package model;

import exception.DadosInvalidosException;

import java.time.LocalDate;

/**
 * Classe base que representa um Usuário genérico do sistema.
 * Utiliza encapsulamento com getters/setters e validações.
 */
public class Usuario {

    private String nome;
    private LocalDate dataNascimento;

    public Usuario(String nome, LocalDate dataNascimento) {
        setNome(nome);
        setDataNascimento(dataNascimento);
    }

    // --- Getters e Setters com validação (Encapsulamento) ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new DadosInvalidosException("nome", "O nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new DadosInvalidosException("dataNascimento", "A data de nascimento não pode ser nula.");
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DadosInvalidosException("dataNascimento", "A data de nascimento não pode ser no futuro.");
        }
        this.dataNascimento = dataNascimento;
    }

    /**
     * Método polimórfico — será sobrescrito nas subclasses.
     */
    public String getResumo() {
        return "Usuário: " + nome;
    }

    @Override
    public String toString() {
        return getResumo();
    }
}
