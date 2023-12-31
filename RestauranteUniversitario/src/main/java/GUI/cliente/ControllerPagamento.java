package GUI.cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import negocio.Fachada;
import negocio.beans.Ficha;
import negocio.beans.RegistroCompra;
import view.ScreenManager;
import view.TelasEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControllerPagamento {

    @FXML
    private CheckBox cbBoleto;

    @FXML
    private CheckBox cbCartao;

    @FXML
    private CheckBox cbPix;

    @FXML
    private Label labelTCompra;

    @FXML
    private Label labelTFichasAlmoco;

    @FXML
    private Label labelTFichasJantar;

    @FXML
    void bttnComprarOn(ActionEvent event) {
        List<Ficha> fichasCompradas = new ArrayList<>();

        if (cbPix.isSelected() || cbCartao.isSelected() || cbBoleto.isSelected()) {
            for (int i = 0; i < ScreenManager.getInstance().getControllerCompraFichas().getContadorAlmoco(); i++) {
                Ficha f = new Ficha("Almoco", Fachada.getInstance().getUsuarioLogado());
                fichasCompradas.add(f);
                Fachada.getInstance().adicionarFicha(f);
            }

            for (int i = 0; i < ScreenManager.getInstance().getControllerCompraFichas().getContadorJantar(); i++) {
                Ficha f = new Ficha("Janta", Fachada.getInstance().getUsuarioLogado());
                fichasCompradas.add(f);
                Fachada.getInstance().adicionarFicha(f);
            }

            String pagamento;

            if (cbBoleto.isSelected())
                pagamento = "Boleto";
            else if (cbPix.isSelected())
                pagamento = "Pix";
            else
                pagamento = "Cartão";

            RegistroCompra rc = new RegistroCompra(fichasCompradas, Fachada.getInstance().getUsuarioLogado().getLogin(),
                    "online", pagamento, LocalDateTime.now());

            Fachada.getInstance().cadastrarRegistroCompra(rc);
            showInfoAlert("Compra realizada", "A operação foi um sucesso", "Compra Efetuada Com Sucesso!");
            ScreenManager.getInstance().getControllerCompraFichas().clearFields();
            clearFields();
            ScreenManager.getInstance().changeScreen(TelasEnum.COMPRA_FICHAS.name());
        }
        else
            showErrorAlert("Erro", "Campo não selecionado", "Você deve selecionar um método de pagamento para efetuar a compra");
    }

    @FXML
    void bttnVoltarPaginaOn(ActionEvent event) {
        ScreenManager.getInstance().changeScreen(TelasEnum.COMPRA_FICHAS.name());
    }

    @FXML
    void cbBoletoOn(ActionEvent event) {
        if (cbCartao.isSelected())
            cbCartao.setSelected(false);

        if (cbPix.isSelected())
            cbPix.setSelected(false);

    }

    @FXML
    void cbCartaoOn(ActionEvent event) {
        if (cbPix.isSelected())
            cbPix.setSelected(false);

        if (cbBoleto.isSelected())
            cbBoleto.setSelected(false);

    }

    @FXML
    void cbPixOn(ActionEvent event) {
        if (cbBoleto.isSelected())
            cbBoleto.setSelected(false);

        if (cbCartao.isSelected())
            cbCartao.setSelected(false);

    }

    public void inicializarValores() {
        double tFAlmoco = ScreenManager.getInstance().getControllerCompraFichas().getContadorAlmoco() * 3.5;

        int tFJantar = ScreenManager.getInstance().getControllerCompraFichas().getContadorJantar() * 3;

        double totalCompra = tFAlmoco + tFJantar;

        labelTFichasAlmoco.setText(Double.toString(tFAlmoco));
        labelTFichasJantar.setText(Integer.toString(tFJantar));
        labelTCompra.setText(Double.toString(totalCompra));
    }

    private void showInfoAlert(String titulo, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String titulo, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields(){
        cbBoleto.setSelected(false);
        cbCartao.setSelected(false);
        cbPix.setSelected(false);
        labelTFichasAlmoco.setText("");
        labelTFichasJantar.setText("");
        labelTCompra.setText("");
    }

}