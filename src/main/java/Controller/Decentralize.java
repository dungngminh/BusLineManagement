package Controller;

import Model.AccountEntity;
import Model.RoleAccountEntity;
import Services.BLL_Admin;
import Util.HibernateUtils;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Decentralize implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private GridPane grid_add;

    @FXML
    private GridPane grid_role;

    @FXML
    private Button btn_adduser;

    @FXML
    private Button btn_changerole;

    @FXML
    private TextField txf_username;

    @FXML
    private PasswordField txf_password;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_reset;

    @FXML
    private ComboBox<AccountEntity> cbx_user;

    @FXML
    private ComboBox<String> cbx_role;

    @FXML
    private ComboBox<String> cbx_role_add;

    @FXML
    private Button btn_confirm;

    // Var static
    private static String taskType;
    private static boolean flag = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/admin_view/NavBar.fxml"));
            jfx_drawer.setSidePane(box);

            for (Node node : box.getChildren()) {
                if (node.lookup(".btn").getAccessibleText() != null) {
                    node.lookup(".btn").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.lookup(".btn").getId()) {
                            case "dashboard": {
                                try {
                                    showMainPage();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                break;
                            }
                            case "bus":
                                try {
                                    showBusPage();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    });
                }
            }

            // Init navbar transformation
            HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(jfx_hambur);
            jfx_hambur.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (flag)
                    burgerTask.setRate(burgerTask.getRate() * -1);
                flag = true;
                burgerTask.play();
                if (jfx_drawer.isShown()) {
                    jfx_drawer.toBack();
                    jfx_drawer.close();
                } else {
                    jfx_drawer.open();
                    jfx_drawer.toFront();
                    jfx_hambur.toFront();
                }

            });

            //Init role
            cbx_role_add.getItems().add("Admin");
            cbx_role_add.getItems().add("Ticket Seller");
            cbx_role.getItems().add("Admin");
            cbx_role.getItems().add("Ticket Seller");
            cbx_role_add.getSelectionModel().selectFirst();
            cbx_role.getSelectionModel().selectFirst();
            //done



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Show nav bar pages
    public void showMainPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/MainWindow.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    public void showBusPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/BusPage.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    //done


    @FXML
    void btn_adduser_clicked(MouseEvent event) {
        if(!grid_add.isVisible())  toggleStack();
    }

    @FXML
    void btn_changerole_clicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(!grid_role.isVisible()) {
            toggleStack();
            cbx_user.getItems().setAll(BLL_Admin.getInstance().getListAcc());
        }
    }

    @FXML
    void btn_add_clicked(MouseEvent event) {
        String userName = txf_username.getText().trim();
        String passWord = txf_password.getText().trim();
        int role = cbx_role_add.getSelectionModel().getSelectedIndex() + 1;
        if(userName.equals("") || passWord.equals("") ||
                cbx_role_add.getSelectionModel().getSelectedItem().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Fill all field!").showAndWait();
            return;
        }
        try {
            BLL_Admin.getInstance().addUserToAccount(userName, passWord, role);
        } catch (Exception err) {
            new Alert(Alert.AlertType.WARNING, "Maybe username was exist, Check again!").showAndWait();
        }
    }

    @FXML
    void btn_confirm_clicked(MouseEvent event) {
        String userName = String.valueOf(cbx_user.getSelectionModel().getSelectedItem());
        int idRole = cbx_role.getSelectionModel().getSelectedIndex() + 1;
        if(userName.equals("") || cbx_role.getSelectionModel().getSelectedItem().equals("")) {
            new Alert(Alert.AlertType.WARNING, "Select all field!").showAndWait();
            return;
        }
        try {
            BLL_Admin.getInstance().updateRole(cbx_user.getSelectionModel().getSelectedItem(), idRole);
        } catch (Exception err) {
            new Alert(Alert.AlertType.WARNING, "Error occurred, Check again!").showAndWait();
        }
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_username.setText("");
        txf_password.setText("");
    }

    @FXML
    void cbx_user_Action(ActionEvent event) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        AccountEntity acc = cbx_user.getSelectionModel().getSelectedItem();
        cbx_role.getSelectionModel().select(((RoleAccountEntity)acc.getRoleAccountsByIdUser().toArray()[0]).getIdRole() - 1);
        session.close();
    }

    //Stuff and Toggle
    public void toggleStack(){
        if(grid_add.isVisible()) {
            txf_username.setText("");
            txf_password.setText("");
            grid_add.setVisible(false);
            grid_role.setVisible(true);
        }
        else {
            grid_role.setVisible(false);
            grid_add.setVisible(true);
        }
    }

    //
}
