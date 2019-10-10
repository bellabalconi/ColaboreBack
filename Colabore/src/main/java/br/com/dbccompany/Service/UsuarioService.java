package br.com.dbccompany.Service;

import br.com.dbccompany.Entity.Usuario;
import br.com.dbccompany.Properties.StorageProperties;
import br.com.dbccompany.Repository.UsuarioRepository;
import br.com.dbccompany.Security.MD5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Transactional( rollbackFor = Exception.class )
    public Usuario salvar(Usuario usu, MultipartFile imagem) {
        String senhaDescrip= usu.getSenha();
        usu.setSenha(new MD5Crypt().encode(senhaDescrip));
        if(imagem != null){
            Usuario usuarioSalvo = usuarioRepository.save(usu);
            usu.setImagem(this.store(imagem));
            return usuarioRepository.save(usuarioSalvo);
        }
        else{
            return usuarioRepository.save(usu);
        }
    }

    @Transactional( rollbackFor = Exception.class )
    public Usuario salvar(Usuario usu) {
        String senhaDescrip= usu.getSenha();
        usu.setSenha(new MD5Crypt().encode(senhaDescrip));
            return usuarioRepository.save(usu);

    }

    public Optional<Usuario> buscarPorId( long id ) {
        return usuarioRepository.findById( id );
    }

    @Transactional( rollbackFor = Exception.class )
    public Usuario editarUsuario( long id, Usuario usuario ) {
        usuario.setId( id );
        return usuarioRepository.save( usuario );
    }

    @Transactional( rollbackFor = Exception.class )
    public Usuario editarValorCriadorDoUsuario( long id, boolean status ) {
        Usuario usuarioDoBanco = usuarioRepository.findById(id).get();
        usuarioDoBanco.setIsCriador( status );
        return usuarioRepository.save( usuarioDoBanco );
    }

    public Usuario buscarPorEmail( String email ) {
        return usuarioRepository.findByEmail( email );
    }

    //IMAGEM
    private final Path rootLocation;
    @Autowired
    public UsuarioService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e) { }
        return this.rootLocation.toAbsolutePath().toString().replace("./", "") + "/" + file.getOriginalFilename();
    }

//    public Path load(String filename) {
//        return rootLocation.resolve(filename);
//    }

    public Resource loadAsResource(String url) {
        try {
            Path file = Paths.get(url);
            Resource resource = new UrlResource(file.toUri());
                return resource;
        }
        catch (MalformedURLException e) {
        }
        return null;
    }



}