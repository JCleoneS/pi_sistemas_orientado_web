package br.com.projetointegrador.wine.context.controller;

import br.com.projetointegrador.wine.context.dto.RequisicaoEditarUsuarioDTO;
import br.com.projetointegrador.wine.context.dto.RequisicaoNovoUsuarioDTO;
import br.com.projetointegrador.wine.context.model.Grupo;
import br.com.projetointegrador.wine.context.model.Situacao;
import br.com.projetointegrador.wine.context.model.Usuario;
import br.com.projetointegrador.wine.context.repository.UsuarioRepository;
import br.com.projetointegrador.wine.context.utils.CriptografiaUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    public ModelAndView index() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    @GetMapping("/buscar")
    public ModelAndView buscarUsuarioPorNome(@RequestParam("nome") String nome) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if(nome != null && !nome.equals("")) {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        }
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newUsuario(RequisicaoNovoUsuarioDTO requisicaoNovoUsuarioDTO) {
        ModelAndView mv = new ModelAndView("admin/new");
        mv.addObject("situacoes", Situacao.values());
        mv.addObject("grupos", Grupo.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid RequisicaoNovoUsuarioDTO requisicao, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("admin/new");
            mv.addObject("situacoes", Situacao.values());
            mv.addObject("grupos", Grupo.values());
            return mv;
        }
        Usuario usuario = requisicao.toUsuario();
        String senhaCriptada = CriptografiaUtils.criptografar(usuario.getSenha());
        usuario.setSenha(senhaCriptada);
        usuario.setSituacao(Situacao.ATIVO);
        this.usuarioRepository.save(usuario);
        return new ModelAndView("redirect:/usuarios");
    }

    @GetMapping("/{id}/inativar")
    public ModelAndView inativar(Usuario requisicao) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        boolean contemAdminAtivo = false;
        for (Usuario usuario : usuarios) {
            if(!requisicao.getId().equals(usuario.getId()) && usuario.getGrupo().equals(Grupo.ADMIN) && usuario.getSituacao().equals(Situacao.ATIVO)) {
                contemAdminAtivo = true;
                break;
            }
        }
        if (contemAdminAtivo) {
            Optional<Usuario> optional = usuarioRepository.findById(requisicao.getId());
            if (optional.isPresent()) {
                Usuario usuario = optional.get();
                usuario.setSituacao(Situacao.INATIVO);
                usuarioRepository.save(usuario);
            }
        }
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("usuarios", usuarioRepository.findAll());
        return mv;
    }

    @GetMapping("/{id}/ativar")
    public ModelAndView ativar(Usuario requisicao) {
        Optional<Usuario> optional = usuarioRepository.findById(requisicao.getId());
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.setSituacao(Situacao.ATIVO);
            usuarioRepository.save(usuario);
        }
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("usuarios", usuarioRepository.findAll());
        return mv;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView edit(@PathVariable Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            ModelAndView mv = new ModelAndView("admin/edit");
            String senhaDescriptografrada = CriptografiaUtils.descriptografar(usuario.getSenha());
            usuario.setSenha(senhaDescriptografrada);
            mv.addObject("usuario", usuario);
            mv.addObject("grupos", Grupo.values());
            mv.addObject("situacoes", Situacao.values());
            return mv;
        } else {
            return new ModelAndView("redirect:/usuarios");
        }
    }

    @PostMapping("/editar")
    public ModelAndView editar(@Valid RequisicaoEditarUsuarioDTO requisicao) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(requisicao.getId());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setId(requisicao.getId());
            usuario.setNome(requisicao.getNome());
            usuario.setCpf(requisicao.getCpf());
            usuario.setEmail(requisicao.getEmail());
            usuario.setGrupo(requisicao.getGrupo());
            String senhaCriptada = CriptografiaUtils.criptografar(usuario.getSenha());
            usuario.setSenha(senhaCriptada);
            usuario.setSituacao(requisicao.getSituacao());
            usuarioRepository.save(usuario);
            return new ModelAndView("redirect:/usuarios");
        }
        return new ModelAndView("redirect:/usuarios");
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoNovoUsuarioDTO requisicao, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("admin/edit");
            mv.addObject("situacoes", Situacao.values());
            mv.addObject("grupos", Grupo.values());
            return mv;
        } else {
            Optional<Usuario> optional = this.usuarioRepository.findById(id);

            if (optional.isPresent()) {
                Usuario usuario = requisicao.toUsuario(optional.get());
                String senhaCriptada = CriptografiaUtils.criptografar(usuario.getSenha());
                usuario.setSenha(senhaCriptada);
                usuario.setSituacao(Situacao.ATIVO);
                this.usuarioRepository.save(usuario);

                return new ModelAndView("redirect:/usuarios");
                //não achou um registro na tabela usuario com o id informado
            } else {
                System.out.println("Nao Achaou o Usuario de ID: " + id);
                return new ModelAndView("redirect:/usuarios");

            }
        }
    }
}