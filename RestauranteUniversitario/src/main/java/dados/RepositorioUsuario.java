package dados;

import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuario implements IRepositorioUsuario {

    private List<Usuario> usuarios;
    private static IRepositorioUsuario instance;

    public RepositorioUsuario() {
        Usuario admin = new Usuario("123","admin","admin@","admin", 123L, true, "Gerente");
        Usuario avulso = new Usuario("000","avulso","avulso@","avulso", 000L, false, "Cliente");
        Usuario online = new Usuario("123", "online", "online@", "online", 001L, false, "Vendedor");
        usuarios = new ArrayList<>();
        usuarios.add(admin);
        usuarios.add(avulso);
        usuarios.add(online);
    }

    public static IRepositorioUsuario getInstance() {
        if (instance == null) {
            instance = new RepositorioUsuario();
        }

        return instance;
    }

    @Override
    public void cadastrarUsuario(Usuario u) {
        if (u != null) {
            if (getUsuarioPorCPF(u.getCpf()) == null) {
                usuarios.add(u);
            }
            else {
                //throw exceção usuario com mesmo cpf ja existe
            }
        }
    }

    @Override
    public void removerUsuario(Usuario u) {
        if (u != null) {
            if (getUsuarioPorCPF(u.getCpf()) != null) {
                usuarios.remove(u);
            }
            else {
                //throw usuario nao cadastrado
            }
        }
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public Usuario getUsuarioPorCPF(long cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf() == cpf) {
                return usuario;
            }
        }

        return null;
    }

    @Override
    public Usuario getUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email))
                return usuario;
        }

        return null;
    }

    @Override
    public List<Usuario> getUsuariosPorNome(String nome) {
        if (nome != null) {
            List<Usuario> usuariosList = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                if (usuario.getNome().contains(nome)) {
                    usuariosList.add(usuario);
                }
            }

            return usuariosList;
        }

        return null;
    }

    @Override
    public List<Usuario> getUsuariosPorDataNascimento(LocalDate dataNascimento) {
        List<Usuario> usuariosList = new ArrayList<>();

        return usuariosList;
    }

    @Override
    public Usuario getUsuarioPorLogin(String login) {
        if (login != null) {
            for (Usuario usuario : usuarios) {
                if (login.equals(usuario.getLogin())) {
                    return usuario;
                }
                //atirar exceçao nao existe usuario
            }
        }

        return null;
    }

    @Override
    public List<Usuario> getUsuariosPorAtivacao(boolean ativo) {
        List<Usuario> listaUsuarios = new ArrayList<>();

        if (ativo) {
            for (Usuario usuario : usuarios) {
                if (usuario.isAtivado()) {
                    listaUsuarios.add(usuario);
                }
            }
        }
        else {
            for (Usuario usuario : usuarios) {
                if (!usuario.isAtivado()) {
                    listaUsuarios.add(usuario);
                }
            }
        }

        return listaUsuarios;
    }

    public List<Usuario> getClientes() {
        List<Usuario> clientes = new ArrayList<>();

        for (Usuario u : usuarios) {
            if (u instanceof Cliente)
                clientes.add(u);
        }

        return clientes;
    }

    @Override
    public List<Usuario> getUsuariosComPerfil(String perfil) {
        List<Usuario> usuariosComPerfil = new ArrayList<>();

        for (Usuario u : usuarios) {
            if (u.getPerfil().equals(perfil))
                usuariosComPerfil.add(u);
        }

        return usuariosComPerfil;
    }

    @Override
    public void alterarUsuario(Usuario user, Usuario editado) {
        if (getUsuarioPorLogin(editado.getLogin()) != null &&
                getUsuarioPorLogin(editado.getLogin()) != user) {
            //atirar exceção login ja cadastrado
        }
        else if (getUsuarioPorCPF(editado.getCpf()) != null &&
            getUsuarioPorCPF(editado.getCpf()) != user) {
            //atirar exceção cpf ja cadastrado
        }
        else if (getUsuarioPorEmail(editado.getEmail()) != null
                && getUsuarioPorEmail(editado.getEmail()) != user) {
            //atirar execção email cadastrado
        }
        else {
            for (Usuario usuario : usuarios) {
                if (user.getLogin().equals(usuario.getLogin())) {
                    usuario.setNome(editado.getNome());
                    usuario.setAtivado(editado.isAtivado());
                    usuario.setPerfil(editado.getPerfil());
                    usuario.setLogin(editado.getLogin());
                    usuario.setEmail(editado.getEmail());
                    usuario.setCpf(editado.getCpf());
                    usuario.setSenha(editado.getSenha());
                }
            }
        }
    }

    public Usuario obterUsuarioDeCredenciais(String login, String senha) {
        for (Usuario usuario : usuarios) {
            if (login.equals(usuario.getLogin())) {
                if (senha.equals(usuario.getSenha()))
                    return usuario;
            }
        }

        return null;
    }

    @Override
    public List<Usuario> obterUsuariosComInformacoesContidasEm(Usuario modelo) {
        List<Usuario> usuariosFiltrados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (compararUsuarioAoModelo(usuario, modelo))
                usuariosFiltrados.add(usuario);
        }

        return usuariosFiltrados;
    }

    private boolean compararUsuarioAoModelo(Usuario u, Usuario modelo) {
        return u.getNome().contains(modelo.getNome()) && u.getPerfil().contains(modelo.getPerfil())
                && u.getLogin().contains(modelo.getLogin()) && u.getEmail().contains(modelo.getEmail()) && u.isAtivado() == modelo.isAtivado();
    }
}
