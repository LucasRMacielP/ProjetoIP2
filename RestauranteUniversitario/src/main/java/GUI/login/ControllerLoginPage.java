package GUI.login;

import negocio.Fachada;
import view.TelasEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import view.ScreenManager;

public class ControllerLoginPage {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void hyperLinkEsqueceuSenha(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Envie um e-mail para a administração resolver seu problema: adminru@ufrpe.br");

        alert.setTitle("ESQUECEU A SENHA!");
        alert.show();
    }

    public void entrarButtonClicked(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        int tipoDeEntrada = Fachada.getInstance().controladorUsuario.permicaoLogin(login, password);

        if (tipoDeEntrada == 1) {
            showAlert("Login bem-sucedido!", Alert.AlertType.INFORMATION);
            ScreenManager.getInstance().changeScreen(TelasEnum.PRINCIPAL_CLIENTE.name());
        }
        else if(tipoDeEntrada == 2){
            showAlert("Login bem-sucedido!", Alert.AlertType.INFORMATION);
            ScreenManager.getInstance().changeScreen(TelasEnum.PRINCIPAL_VENDEDOR.name());
        }
        else if(tipoDeEntrada == 3){
            showAlert("Login bem-sucedido!", Alert.AlertType.INFORMATION);
            ScreenManager.getInstance().changeScreen(TelasEnum.PRINCIPAL_GERENTE.name());
        }
        else {
            showAlert("Credenciais inválidas!", Alert.AlertType.ERROR);
        }
        clearFields();
    }

    private void clearFields() {
        loginField.setText("");
        passwordField.setText("");
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}