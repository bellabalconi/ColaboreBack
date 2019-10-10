package br.com.dbccompany.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Campanha {
    @Id
    @SequenceGenerator(allocationSize = 1, name = "campanha_seq", sequenceName = "campanha_seq")
    @GeneratedValue(generator = "campanha_seq", strategy = GenerationType.SEQUENCE)

    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_criador")
    private Usuario usuario;

    @OneToMany(mappedBy = "campanha")
    private List<Contribuicao> contribuicoes= new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "campanha_tag",
            joinColumns = {
                    @JoinColumn(name = "id_campanha")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_tag")})
    private List<Tag> tags = new ArrayList<>();

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column
    private String imagem;

    @Column(nullable = false, name = "meta_arrecadacao")
    private double metaArrecadacao;

    @Column(name = "meta_arrecadacao_string")
    private String metaArrecadacaoString;

    @Column(nullable = false, name = "valor_arrecadado")
    private double valorArrecadado;

    @Column( name = "valor_arrecadado_string" )
    private String valorArrecadadoString;

    @Column(nullable = false, name = "expectativa_data")
    private LocalDate expectativaData;

    @Column(nullable = false, name= "expectativa_data_string")
    private String expectativaDataString;

    @Column(nullable = false, name = "conclusao_automatica")
    private boolean conclusaoAutomatica;

    @Column(nullable = false, name = "is_Concluida")
    private boolean isConcluida = false;

    @Column(nullable = false, name = "ultima_modificacao")
    private LocalDate ultimaModificacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Contribuicao> getContribuicoes() {
        return contribuicoes;
    }

    public void setContribuicoes(List<Contribuicao> contribuicoes) {
        this.contribuicoes = contribuicoes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMetaArrecadacao() {
        return metaArrecadacao;
    }

    public void setMetaArrecadacao(double metaArrecadacao) {
        this.metaArrecadacao = metaArrecadacao;
    }

    public double getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(double valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public LocalDate getExpectativaData() {
        return expectativaData;
    }

    public void setExpectativaData(LocalDate expectativaData) {
        this.expectativaData = expectativaData;
    }

    public boolean getisConclusaoAutomatica() {
        return conclusaoAutomatica;
    }

    public void setIsConclusaoAutomatica(boolean conclusaoAutomatica) {
        this.conclusaoAutomatica = conclusaoAutomatica;
    }

    public boolean getisConcluida() {
        return isConcluida;
    }

    public void setIsConcluida(boolean concluida) {
        isConcluida = concluida;
    }

    public LocalDate getUltimaModificacao() {
        return ultimaModificacao;
    }

    public void setUltimaModificacao(LocalDate ultimaModificacao) {
        this.ultimaModificacao = ultimaModificacao;
    }

    public String getExpectativaDataString() {
        return expectativaDataString;
    }

    public void setExpectativaDataString(String expectativaDataString) {
        this.expectativaDataString = expectativaDataString;
    }

    public String getMetaArrecadacaoString() {
        return metaArrecadacaoString;
    }

    public void setMetaArrecadacaoString(String metaArrecadacaoString) {
        this.metaArrecadacaoString = metaArrecadacaoString;
    }

    public String getValorArrecadadoString() {
        return valorArrecadadoString;
    }

    public void setValorArrecadadoString(String valorArrecadadoString) {
        this.valorArrecadadoString = valorArrecadadoString;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}