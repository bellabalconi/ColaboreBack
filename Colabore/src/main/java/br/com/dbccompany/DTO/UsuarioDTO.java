package br.com.dbccompany.DTO;

import java.util.List;

public class UsuarioDTO {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private boolean isCriador;
    private List<Long> campanhas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getIsCriador() {
        return isCriador;
    }

    public void setIsCriador(boolean criador) {
        isCriador = criador;
    }

    public List<Long> getCampanhas() {
        return campanhas;
    }

    public void setCampanhas(List<Long> campanhas) {
        this.campanhas = campanhas;
    }
}
