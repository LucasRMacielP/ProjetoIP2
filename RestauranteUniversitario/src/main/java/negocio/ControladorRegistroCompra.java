package negocio;

import dados.IRepositorioRegistroCompra;
import dados.RepositorioRegistroCompra;
import negocio.beans.RegistroCompra;

import java.util.List;

public class ControladorRegistroCompra{

    private IRepositorioRegistroCompra repositorioRegistroCompra;

    private static ControladorRegistroCompra instance;

    private ControladorRegistroCompra() {
        this.repositorioRegistroCompra = RepositorioRegistroCompra.getInstance();
    }

    public static ControladorRegistroCompra getInstance() {
        if (instance == null) {
            instance = new ControladorRegistroCompra();
        }

        return instance;
    }

    public void cadastrarRegistroCompra(RegistroCompra rc) {
        if (rc != null) {
            repositorioRegistroCompra.cadastrarRegistroCompra(rc);
        }
    }

    public void removerRegistroCompra(RegistroCompra rc) {
        if (rc != null) {
            repositorioRegistroCompra.removerRegistroCompra(rc);
        }
    }

    public List<RegistroCompra> getListaRegistroCompra() {
        return repositorioRegistroCompra.getListaRegistroCompra();
    }

    public List<RegistroCompra> obterRegistrosContidosEm(RegistroCompra modelo, int almoco, int janta) {
        return this.repositorioRegistroCompra.obterRegistrosComInformacoesContidasEm(modelo, almoco, janta);
    }
}
