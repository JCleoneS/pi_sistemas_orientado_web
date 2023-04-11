package br.com.projetointegrador.wine.context.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    //    Eu quero, eu posso: Listar os usuários do sistema
//    Para que: Para poder selecionar um usuário para manutenção
//    Critérios de aceite:
//    Lista todos os usuários  usuários cadastrados no sistema na entrada da tela mostrando o Nome, email, status, Grupo - para administrador.
//    Ao clicar em altera usuário o sistema deverá enviar para Alteração
//    Ao clicar em inativar/reativar o sistema deverá troca (se ativo passa para inativo ou se inativo passa para ativo)
//    O listar usuários deve permitir filtrar (por nome de usuário) a lista de usuários do sistema.
    @GetMapping("")
    public ModelAndView index() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ModelAndView mv = new ModelAndView("admin/index");
        mv.addObject("usuarios", usuarios);
        return mv;
    }

    //    Eu quero, eu posso: Cadastrar um novo usuário
//    Para que: Para registrar um usuário no sistema
//    Critérios de aceite:
//    Cadastrar o nome do usuário, cpdf, email, senha, grupo (admin/estoquista) no banco de dados.
//    No cadastro, pedir a senha 2 vezes. Só permitir o cadastro quando as 2 senhas estiverem iguais.
//    A senha deve ser encriptada antes de enviar para o banco de dados.
//    O cadastro de usuário cadastra o registro como ativo (sempre)
//    Não é permitido cadastrar dois usuários com mesmo login (email)
//    O cpf deve ser validado antes da gravação.
    @GetMapping("/new")
    public ModelAndView newUsuario(RequisicaoNovoUsuarioDTO requisicaoNovoUsuarioDTO) {
        ModelAndView mv = new ModelAndView("admin/new");
        mv.addObject("situacoes", Situacao.values());
        mv.addObject("grupos", Grupo.values());
        return mv;
    }

    //    Eu quero, eu posso: Cadastrar um novo usuário
//    Para que: Para registrar um usuário no sistema
//    Critérios de aceite:
//    Cadastrar o nome do usuário, cpdf, email, senha, grupo (admin/estoquista) no banco de dados.
//    No cadastro, pedir a senha 2 vezes. Só permitir o cadastro quando as 2 senhas estiverem iguais.
//    A senha deve ser encriptada antes de enviar para o banco de dados.
//    O cadastro de usuário cadastra o registro como ativo (sempre)
//    Não é permitido cadastrar dois usuários com mesmo login (email)
//    O cpf deve ser validado antes da gravação.
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
    public ModelAndView edit(@PathVariable Long id, RequisicaoNovoUsuarioDTO requisicaoNovoUsuarioDTO) {
        Optional<Usuario> optional = this.usuarioRepository.findById(id);
        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            ModelAndView mv = new ModelAndView("admin/edit");
            mv.addObject("usuario", usuario);
            mv.addObject("usuarioId", usuario.getId());
            mv.addObject("situacoes", Situacao.values());
            mv.addObject("grupos", Grupo.values());
            return mv;
        } else {
            return new ModelAndView("redirect:/usuarios");
        }
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