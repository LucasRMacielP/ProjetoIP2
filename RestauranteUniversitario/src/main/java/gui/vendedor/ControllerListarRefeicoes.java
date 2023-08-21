package gui.vendedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.ScreenManager;
import view.TelasEnum;

public class ControllerListarRefeicoes {

    @FXML
    private Button buttonVoltarPagina;

    @FXML
    void bttnVoltarPaginaOn(ActionEvent event) {
        ScreenManager.changeScreen(TelasEnum.PRINCIPAL_VENDEDOR.name());
    }

}