package br.com.dbccompany.Service;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Tag;
import br.com.dbccompany.Repository.CampanhaRepository;
import br.com.dbccompany.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public CampanhaRepository campanhaRepository;

    @Transactional(rollbackFor = Exception.class)
    public List<Tag> salvar(List<Tag> tags) {
        List<Tag> tagsSalvas = new ArrayList<>();
        for(Tag tag: tags){
            if(this.buscarPorNome(tag.getNome()) != null){
                Tag anterior = this.buscarPorNome(tag.getNome());
                List<Campanha> campanhas = new ArrayList<>();
                campanhas.addAll(anterior.getCampanhas());
                campanhas.addAll(tag.getCampanhas());
                anterior.setCampanhas(campanhas);
                tagsSalvas.add(tagRepository.save(anterior));
            }
            else if(tag.getCampanhas().size() > 0){
                List<Campanha> campanhasDaTag = new ArrayList<>();
                for(Campanha campanha : tag.getCampanhas()){
                    campanhasDaTag.add(campanhaRepository.findById(campanha.getId()).get());
                }
                tag.setCampanhas(campanhasDaTag);
                tagsSalvas.add(tagRepository.save(tag));
            }
            else { tagsSalvas.add(tagRepository.save(tag)); }
        }
        return tagsSalvas;
    }

    public List<Tag> buscarTodos() {
        return (List<Tag>) tagRepository.findAll();
    }

    public Tag buscarPorNome(String nome){ return tagRepository.findByNome(nome);}


}