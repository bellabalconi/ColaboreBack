package br.com.dbccompany.Controller;

import br.com.dbccompany.DTO.TagDTO;
import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Tag;
import br.com.dbccompany.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping(value = "/novo")
    @ResponseBody
    public List<Tag> salvarNovo(@RequestBody List<Tag> tags) {
        return tagService.salvar(tags);
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<TagDTO> listarTodos() {
        List<TagDTO> tagsDTO = new ArrayList<>();
        List<Tag> tags = tagService.buscarTodos();
        for(Tag tag : tags){
            List<Long> idsCampanhas = new ArrayList<>();
            for(Campanha campanha : tag.getCampanhas()){
                idsCampanhas.add(campanha.getId());
            }
            TagDTO tagDTO = new TagDTO();
            tagDTO.setId(tag.getId()); tagDTO.setNome(tag.getNome()); tagDTO.setCampanhas(idsCampanhas);
            tagsDTO.add(tagDTO);
        }
        return tagsDTO;
    }
}