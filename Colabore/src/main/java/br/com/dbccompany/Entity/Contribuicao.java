package br.com.dbccompany.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Contribuicao.class)
public class Contribuicao {
    @Id
    @SequenceGenerator(allocationSize = 1, name = "contribuicao_seq", sequenceName = "contribuicao_seq")
    @GeneratedValue(generator = "contribuicao_seq", strategy = GenerationType.SEQUENCE)

    private long id;

    @Column(nullable = false)
    private double valor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contribuinte")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    private Campanha campanha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }
}
