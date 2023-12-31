package GUI.gerente;

import exception.CardapioJaCadastradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import negocio.Fachada;
import negocio.beans.Cardapio;
import view.ScreenManager;
import view.TelasEnum;

public class ControllerCadastroCardapio {

    @FXML
    private TextField tfGuarnicao;

    @FXML
    private TextField tfPrincipal;

    @FXML
    private TextField tfSalada;

    @FXML
    private TextField tfSobremesa;

    @FXML
    private TextField tfSuco;

    @FXML
    private TextField tfVegetariano;

    @FXML
    void bttnCadastrarOn(ActionEvent event) {
        if (validar()) {
            try {
                Cardapio c = new Cardapio(tfPrincipal.getText(), tfVegetariano.getText(), tfGuarnicao.getText(), tfSalada.getText(),
                        tfSobremesa.getText(), tfSuco.getText());

                Fachada.getInstance().cadastrarCardapio(c);
                showInfoMessage("Cadastro realizado", "O cardápio foi cadastrado com sucesso");
            }
            catch (CardapioJaCadastradoException e) {
                showErrorMessage("Erro: cardápio já existe.", e.getMessage());
            }

            clearFields();
        }
    }

    @FXML
    void bttnVoltarPaginaOn(ActionEvent event) {
        clearFields();
        ScreenManager.getInstance().getControllerListarCardapios().atualizarApresentacao();
        ScreenManager.getInstance().changeScreen(TelasEnum.VISUALIZAR_CARDAPIOS.name());
    }

    private void showErrorMessage(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.show();
    }

    private void showInfoMessage(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.show();
    }

    private boolean validar() {
        if (tfGuarnicao.getText().isBlank() || tfPrincipal.getText().isBlank() || tfSalada.getText().isBlank() ||
            tfVegetariano.getText().isBlank() || tfSobremesa.getText().isBlank() || tfSuco.getText().isBlank()) {
            showErrorMessage("Erro: campos não preenchidos" , "Informe os campos solicitados corretamente");
            return false;
        }

        return true;
    }

    private void clearFields() {
        tfSuco.setText("");
        tfSobremesa.setText("");
        tfVegetariano.setText("");
        tfSalada.setText("");
        tfGuarnicao.setText("");
        tfPrincipal.setText("");
    }

}
