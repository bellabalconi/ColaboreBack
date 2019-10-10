package br.com.dbccompany.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "TAG")
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Tag.class)
public class Tag {
    @Id
    @SequenceGenerator(allocationSize = 1, name = "TAG_SEQ", sequenceName = "TAG_SEQ")
    @GeneratedValue(generator = "TAG_SEQ", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "campanha_tag",
            joinColumns = {
                    @JoinColumn(name = "id_tag")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_campanha")})
    private List<Campanha> campanhas = new ArrayList<>();

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

    public List<Campanha> getCampanhas() {
        return campanhas;
    }

    public void setCampanhas(List<Campanha> campanhas) {
        this.campanhas = campanhas;
    }
}
