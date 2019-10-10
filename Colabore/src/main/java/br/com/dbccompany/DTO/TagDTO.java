package br.com.dbccompany.DTO;

import java.util.List;

public class TagDTO {
    private long id;
    private String nome;
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

    public List<Long> getCampanhas() {
        return campanhas;
    }

    public void setCampanhas(List<Long> campanhas) {
        this.campanhas = campanhas;
    }

}
