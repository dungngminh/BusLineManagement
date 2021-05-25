package Controller.Admin;

import Model.AccountEntity;
import Services.BLL_Admin;

import Services.DAL;

import Util.HibernateUtils;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class Decentralize implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML

    private TextField txf_username;

    @FXML
    private PasswordField txf_oldpassword;

    @FXML
    private PasswordField txf_newpassword;

    @FXML
    private PasswordField txf_newpassword_confirm;


    @FXML
    private PasswordField txf_password;

    @FXML
    private PasswordField txf_password_confirm;

    @FXML

    private ComboBox<AccountEntity> cbx_user_delete;


    @FXML
    private ComboBox<AccountEntity> cbx_user;

    @FXML
    private ComboBox<String> cbx_role;

    @FXML
    private ComboBox<String> cbx_role_add;

    @FXML

    private Tab tab_adduser;

    @FXML
    private Tab tab_changerole;

    @FXML
    private Tab tab_deleteuser;

    // Var static
    private static AccountEntity currentAccount;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
            //done

            //Init role

            currentAccount = DAL.getInstance().getCurrent();
            int idRole = DAL.getInstance().getCurrent().getIdRole();
            if(idRole == 3) {
                cbx_role_add.getItems().add("Admin");
                cbx_role.getItems().add("Admin");
                cbx_role.getItems().add("Ticket Seller");
                cbx_role.getSelectionModel().selectFirst();
                tab_changerole.setDisable(false);
                tab_deleteuser.setDisable(false);
            }

            cbx_role_add.getItems().add("Ticket Seller");
            cbx_role_add.getSelectionModel().selectFirst();

            //done

            // IMPORTANT Handler when select tab change role
            tab_changerole.setOnSelectionChanged(event -> {
                if (tab_changerole.isSelected()) {
                    try {
                        cbx_user.getItems().clear();
                        BLL_Admin.getInstance().getListAcc().forEach(acc -> {
                            if(!acc.equals(currentAccount)) {
                                cbx_user.getItems().add(acc);
                            }
                        });
                        cbx_user.getSelectionModel().selectFirst();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    //Do stuff here
                }
            });

            // DONE

            // IMPORTANT Handler when select tab delete
            tab_deleteuser.setOnSelectionChanged(event -> {
                if (tab_deleteuser.isSelected()) {
                    try {
                        cbx_user_delete.getItems().clear();
                        BLL_Admin.getInstance().getListAcc().forEach(acc -> {
                            if(!acc.equals(currentAccount)) {
                                cbx_user_delete.getItems().add(acc);
                            }
                        });
                        cbx_user_delete.getSelectionModel().selectFirst();
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    //Do stuff here
                }
            });
            // DONE

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showDialog(String type, Integer idRole, AccountEntity acc) {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Account verification");
        dialog.setHeaderText("Do you a super admin?");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("/images/Icon/login.png").toString()));

        // Set the button types.
        ButtonType authorize = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(authorize, ButtonType.CANCEL);
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == authorize) {
                return  password.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(currentAccount.getPassword().equals(DAL.getInstance().encryptSHA1(result.get()))) {
                if(type.equals("changerole")) {
                    BLL_Admin.getInstance().updateRole(cbx_user.getSelectionModel().getSelectedItem(), idRole);
                } else if(type.equals("delete")) {
                    BLL_Admin.getInstance().deleteUser(acc);
                }

            } else {
                new Alert(Alert.AlertType.WARNING, "Password is incorrect!").showAndWait();
            }
        }
    }

    @FXML
    void btn_add_clicked(MouseEvent event) {
        String userName = txf_username.getText().trim();
        String passWord = txf_password.getText().trim();
        String confirm_password = txf_password_confirm.getText().trim();

        int role = cbx_role_add.getSelectionModel().getSelectedItem().equals("Ticket Seller") ? 2 : 1;
        if(userName.equals("") || passWord.equals("") || confirm_password.equals("")) {
            new Alert(Alert.AlertType.WARNING, "Fill all field!").showAndWait();
        } else if(!passWord.equals(confirm_password)) {
            new Alert(Alert.AlertType.WARNING, "Check password again!").showAndWait();
        } else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", passWord)) {
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
//            try {
                BLL_Admin.getInstance().addUserToAccount(userName, passWord, role, BLL_Admin.getInstance().getRole(role));
                new Alert(Alert.AlertType.INFORMATION, "User added!").showAndWait();
//            } catch (Exception err) {
//                new Alert(Alert.AlertType.WARNING, "Maybe username was exist, Check again!").showAndWait();
//            }
        }
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_username.setText("");
        txf_password.setText("");
        txf_password_confirm.setText("");
    }

    @FXML
    void cbx_user_Action(ActionEvent event) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        if(!cbx_user.getItems().isEmpty()) {
            AccountEntity acc = cbx_user.getSelectionModel().getSelectedItem();
            cbx_role.getSelectionModel().select(acc.getIdRole() - 1);
            session.close();
        }
    }

    @FXML
    void btn_confirm_clicked(MouseEvent event) {

        try {
            String userName = String.valueOf(cbx_user.getSelectionModel().getSelectedItem());
            int idRole = cbx_role.getSelectionModel().getSelectedItem().equals("Ticket Seller") ? 2 : 1;
            if(userName.equals("") || cbx_role.getSelectionModel().getSelectedItem().equals("")) {
                new Alert(Alert.AlertType.WARNING, "Select all field!").showAndWait();
                return;
            }

            // Show dialog account verification
            showDialog("changerole", idRole, null);
        } catch (Exception err) {
            System.out.println(err.getMessage());

            new Alert(Alert.AlertType.WARNING, "Error occurred, Check again!").showAndWait();
        }
    }

    // NOTICE event handler delete here
    @FXML
    void btn_delete_clicked(MouseEvent event) {
        AccountEntity acc = cbx_user_delete.getSelectionModel().getSelectedItem();
        showDialog("delete", null, acc);
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
    //
}
