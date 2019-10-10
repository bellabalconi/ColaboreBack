package br.com.dbccompany.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Usuario.class)
public class Usuario {

    @Id
    @SequenceGenerator(allocationSize = 1, name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ")
    @GeneratedValue(generator = "USUARIO_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column( nullable = false )
    private String nome;

    @Column( nullable = false, unique = true)
    private String email;

    @Column( nullable = false )
    private String senha;

    @Column
    private String imagem;

    @Column(name = "is_Criador")
    private boolean isCriador;

    @OneToMany( mappedBy = "usuario")
    private List<Campanha> campanhas = new ArrayList<>();

    @OneToMany( mappedBy = "usuario" )
    private List<Contribuicao> contribuicoes = new ArrayList<>();

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
    

    public List<Campanha> getCampanhas() {
        return campanhas;
    }

    public void setCampanhas(List<Campanha> campanhas) {
        this.campanhas = campanhas;
    }

    public List<Contribuicao> getContribuicoes() {
        return contribuicoes;
    }

    public void setContribuicoes(List<Contribuicao> contribuicoes) {
        this.contribuicoes = contribuicoes;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
