package dados;

import negocio.beans.Cliente;
import negocio.beans.RegistroCompra;

import java.time.LocalDateTime;
import java.util.List;

public interface IRepositorioRegistroCompra {

    void cadastrarRegistroCompra(RegistroCompra rc);

    void removerRegistroCompra(RegistroCompra rc);

    List<RegistroCompra> getListaRegistroCompra();

    List<RegistroCompra> obterRegistrosComInformacoesContidasEm(RegistroCompra modelo, int almoco, int janta);

}
