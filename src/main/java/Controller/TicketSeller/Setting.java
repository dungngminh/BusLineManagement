package Controller.TicketSeller;

import Model.AccountEntity;
import Services.BLL_Admin;
import Services.DAL;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Setting implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private GridPane grid_change;

    @FXML
    private PasswordField txf_newpassword;

    @FXML
    private PasswordField txf_newpassword_confirm;

    @FXML
    private PasswordField txf_oldpassword;

    // Var static
    private static AccountEntity currentAccount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            currentAccount = DAL.getInstance().getCurrent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btn_changepassword_confirm_clicked(MouseEvent event) {
        String oldPassword = txf_oldpassword.getText().trim();
        String newPassword = txf_newpassword.getText().trim();
        String newPasswordConfirm = txf_newpassword_confirm.getText().trim();

        if (Arrays.asList(oldPassword, newPassword, newPasswordConfirm).contains("")) {
            new Alert(Alert.AlertType.WARNING, "Select all field!").showAndWait();
        } else if(!currentAccount.getPassword().equals(DAL.getInstance().encryptSHA1(oldPassword))) {
            new Alert(Alert.AlertType.WARNING, "Password is incorrect!").showAndWait();
        } else if(!newPassword.equals(newPasswordConfirm)) {
            new Alert(Alert.AlertType.WARNING, "Check password again!").showAndWait();
        } else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", newPassword)) {
            new Alert(Alert.AlertType.WARNING, "At least 8 chars\n" +
                    "\n" +
                    "Contains at least one digit\n" +
                    "\n" +
                    "Contains at least one lower alpha char and one upper alpha char\n" +
                    "\n" +
                    "Contains at least one char within a set of special chars (@#%$^ etc.)\n" +
                    "\n" +
                    "Does not contain space, tab, etc.!").showAndWait();
        } else {

            try {
                BLL_Admin.getInstance().updateAccount(currentAccount, newPassword);
                new Alert(Alert.AlertType.INFORMATION, "Password was changed!").showAndWait();
            } catch (Exception err) {
                new Alert(Alert.AlertType.WARNING, "Maybe username was exist, Check again!").showAndWait();
            }
        }
    }

    @FXML
    void changepassword_reset_onClicked(MouseEvent event) {
        txf_oldpassword.setText("");
        txf_newpassword.setText("");
        txf_newpassword_confirm.setText("");
    }
}
