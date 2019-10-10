package br.com.dbccompany;

import br.com.dbccompany.Entity.Campanha;
import br.com.dbccompany.Entity.Tag;
import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Repository.TagRepository;
import br.com.dbccompany.Service.CampanhaService;
import br.com.dbccompany.Service.TagService;
import br.com.dbccompany.Service.UsuarioService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TagTest extends ColaboreApplicationTests {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CampanhaService campanhaService;

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    ApplicationContext context;

//    @Before
//    public void setUp() {
//        Tag tag = new Tag();
//        tag.setNome("tag1");
//
//
//        Mockito.when(tagRepository.findByNome(tag.getNome())).thenReturn(tag);
//    }

    @Test
    public void buscarPorNome(){
        Usuario usuario = new Usuario();
        usuario.setEmail("dani@dani");
        usuario.setNome("Daniela");
        usuario.setSenha("dani");
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        Campanha campanha = new Campanha();
        campanha.setUsuario(usuarioSalvo);
        campanha.setTitulo("Animais");
        campanha.setDescricao("Arrecadacao para animais");
        campanha.setMetaArrecadacao(100.2);
        campanha.setIsConclusaoAutomatica(true);

        Tag tag = new Tag();
        tag.setNome("tag1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        List<Tag> tagSalva = tagService.salvar(tags);

        List<Tag> tagsDaCampanha = campanha.getTags();
        tagsDaCampanha.add(tagSalva.get(0));
        campanha.setTags(tagsDaCampanha);
        Campanha campanhaSalva = campanhaService.salvar(campanha);

        assertThat(campanhaSalva.getTags().size()).isEqualTo(1);

        Tag found = tagRepository.findById(tagSalva.get(0).getId()).get();
        List<Campanha> campanhasTag = found.getCampanhas();

        assertThat(campanhasTag.size()).isEqualTo(1);
    }



}
