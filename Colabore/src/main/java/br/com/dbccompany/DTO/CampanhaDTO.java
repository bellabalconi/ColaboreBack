package br.com.dbccompany.DTO;

import br.com.dbccompany.Entity.Contribuicao;
import br.com.dbccompany.Entity.Tag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CampanhaDTO {
    private long id;
    private UsuarioDTO usuario;
    private List<Contribuicao> contribuicoes= new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    private String titulo;
    private String descricao;
    private String imagem;
    private double metaArrecadacao;
    private String metaArrecadacaoString;
    private double valorArrecadado;
    private String valorArrecadadoString;
    private LocalDate expectativaData;
    private String expectativaDataString;
    private boolean conclusaoAutomatica;
    private boolean isConcluida = false;
    private LocalDate ultimaModificacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public double getMetaArrecadacao() {
        return metaArrecadacao;
    }

    public void setMetaArrecadacao(double metaArrecadacao) {
        this.metaArrecadacao = metaArrecadacao;
    }

    public String getMetaArrecadacaoString() {
        return metaArrecadacaoString;
    }

    public void setMetaArrecadacaoString(String metaArrecadacaoString) {
        this.metaArrecadacaoString = metaArrecadacaoString;
    }

    public double getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(double valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public String getValorArrecadadoString() {
        return valorArrecadadoString;
    }

    public void setValorArrecadadoString(String valorArrecadadoString) {
        this.valorArrecadadoString = valorArrecadadoString;
    }

    public LocalDate getExpectativaData() {
        return expectativaData;
    }

    public void setExpectativaData(LocalDate expectativaData) {
        this.expectativaData = expectativaData;
    }

    public String getExpectativaDataString() {
        return expectativaDataString;
    }

    public void setExpectativaDataString(String expectativaDataString) {
        this.expectativaDataString = expectativaDataString;
    }

    public boolean getIsConclusaoAutomatica() {
        return conclusaoAutomatica;
    }

    public void setIConclusaoAutomatica(boolean conclusaoAutomatica) {
        this.conclusaoAutomatica = conclusaoAutomatica;
    }

    public boolean getIsConcluida() {
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
}
