package com.example.sqlcrud.modelo;

import java.io.Serializable;

public class Pessoa implements Serializable { // Serializable = enviar objetos de classe para classe, outra opção é o Parcelable
    private int id, idade;
    private String nome, email, endereco;

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    //Para aparecer o nome correto na lista
    @Override
    public String toString() {
        return nome.toString();
    }
}
