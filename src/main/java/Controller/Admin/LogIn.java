package Controller.Admin;
import Services.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import org.controlsfx.control.textfield.TextFields;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LogIn implements Initializable {

    @FXML
    private TextField txf_username;

    @FXML
    private TextField txf_password;

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_login.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    btn_login_clicked(new MouseEvent(MouseEvent.MOUSE_CLICKED, btn_login.getLayoutX(), btn_login.getLayoutY()
                            , btn_login.getLayoutX(), btn_login.getLayoutY(), MouseButton.PRIMARY, 1,
                            true, true, true, true, true, true, true, true, true, true, null));
                } catch (SQLException | ClassNotFoundException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        btn_cancel.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Stage stage = (Stage) btn_cancel.getScene().getWindow();
                stage.close();
            }
        });
    }

    @FXML
    void btn_login_clicked(MouseEvent event) throws SQLException, ClassNotFoundException, IOException {
        String username = txf_username.getText();
        String password = txf_password.getText();
        try {
            int res = BLL_Admin.getInstance().validate_Account(username, password);
            if (DAL.getInstance().getCurrent().getIsOnline()) {
                new Alert(Alert.AlertType.WARNING, "This user is online, you are not allowed to login!").showAndWait();
            }
            else if(res == 1 || res == 3) {

//                  new Alert(Alert.AlertType.INFORMATION, "Successful!").showAndWait();
                showHomePage("admin_view/MainWindow");
            }
            else if (res == 2) {
                showHomePage("seller_view/Dashboard");
            }

            else {
                new Alert(Alert.AlertType.WARNING, "Your username or password was wrong!").showAndWait();
            }
        }
        catch (Exception err) {
              new Alert(Alert.AlertType.ERROR, "Connect to Internet and try again!").showAndWait();
//            new Alert(Alert.AlertType.ERROR, err.getMessage()).showAndWait();
        }
    }

    @FXML
    void onForgetClicked(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION,"If you are ADMIN, please contact to Le Quoc Thinh for resetting your password!\n" +
                "If you are TICKET SELLER, please contact to your manager!").showAndWait();
    }

    public void showHomePage(String path) throws IOException {
        //Set online for user
        BLL_Admin.getInstance().toggleIsOnlineForAccout(DAL.getInstance().getCurrent(), true);

        FXMLLoader main_Page = new FXMLLoader();
        main_Page.setLocation(getClass().getResource("/view/" + path +".fxml"));

        Scene scene = new Scene(main_Page.load());
        Stage stage = new Stage();
        stage.setTitle("Bus Management");
        stage.setScene(scene);

        // Event when click on X(Close button)
        stage.setOnCloseRequest(e -> {
            // Case ticket seller booking ticket
            Integer idTicket = BLL_Admin.getInstance().getIdTicketToClose();
            if(idTicket > 0)
                BLL_Seller.getInstance().deleteCurrentTicket(idTicket);

            //
            BLL_Admin.getInstance().toggleIsOnlineForAccout(DAL.getInstance().getCurrent(), false);
            Platform.exit();
            System.exit(0);
        });
        //
        stage.getIcons().add(new Image("/images/Icon/favicon.png"));
        stage.show();
        Stage cl = (Stage) btn_cancel.getScene().getWindow();
        cl.close();
    }

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();

    }

}
