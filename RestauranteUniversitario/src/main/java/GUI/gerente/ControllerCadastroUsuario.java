package GUI.gerente;

import exception.CPFInvalidoException;
import exception.CpfJaCadastradoException;
import exception.EmailJaCadastradoException;
import exception.LoginJaCadastradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.Usuario;
import view.ScreenManager;
import view.TelasEnum;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Long.parseLong;

public class ControllerCadastroUsuario implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxTipo;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfNome;

    @FXML
    private PasswordField senhaField;

    @FXML
    void bttnCadastrarOn(ActionEvent event) {
        if (validar()) {
            try {
                Usuario u =  new Usuario(senhaField.getText(), tfLogin.getText(), tfEmail.getText(), tfNome.getText(),
                        Long.parseLong(tfCPF.getText()), true, choiceBoxTipo.getValue());

                Fachada.getInstance().cadastrarUsuario(u);
                showInfoMessage("Cadastro realizado", "O usuário de CPF " + tfCPF.getText() +
                        " foi cadastrado com sucesso");
            }
            catch (CpfJaCadastradoException e) {
                showErrorMessage("Erro: CPF já cadastrado", e.getMessage());
            }
            catch (LoginJaCadastradoException e) {
                showErrorMessage("Erro: Login já cadastrado", e.getMessage());
            }
            catch (EmailJaCadastradoException e) {
                showErrorMessage("Erro: E-mail já cadastrado", e.getMessage());
            }
            catch (CPFInvalidoException e) {
                showErrorMessage("Erro: CPF inválido", e.getMessage());
            }
            catch (NumberFormatException e) {
                showErrorMessage("Erro: CPF inválido", "O CPF deve ser numérico");
            }

            clearFields();
        }
    }

    @FXML
    void bttnVoltarPaginaOn(ActionEvent event) {
        clearFields();
        ScreenManager.getInstance().getControllerListarUsuarios().atualizarApresentacao();
        ScreenManager.getInstance().changeScreen(TelasEnum.LISTAR_USUARIO.name());
    }

    private boolean validar() {
        if (tfCPF.getText().isBlank() || tfLogin.getText().isBlank() || tfEmail.getText().isBlank() ||
                tfNome.getText().isBlank() || senhaField.getText().isBlank() || choiceBoxTipo.getValue() == null)
        {
            showErrorMessage("Erro: campos preenchidos incorretamente", "Informe os campos solicitados corretamente");
            return false;
        }

        return true;
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

    private void clearFields() {
        tfEmail.setText("");
        tfLogin.setText("");
        tfNome.setText("");
        tfEmail.setText("");
        tfCPF.setText("");
        senhaField.setText("");
        choiceBoxTipo.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxTipo.getItems().addAll("Gerente", "Vendedor", "Cliente");
    }

    private boolean validarCpf(String cpf) {
        try {
            Long.parseLong(cpf);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
