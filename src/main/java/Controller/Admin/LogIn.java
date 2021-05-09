package Controller.Admin;
import Services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

import java.io.IOException;
import java.sql.SQLException;

public class LogIn {

    @FXML
    private TextField txf_username;

    @FXML
    private TextField txf_password;
    @FXML
    private Button btn_cancel;

    @FXML
    void btn_login_clicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        String username = txf_username.getText();
        String password = txf_password.getText();
        try {
            int res = BLL_Admin.getInstance().validate_Account(username, password);
            if(res == 1) {
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
//              new Alert(Alert.AlertType.ERROR, "Connect to Internet and try again!").showAndWait();
            new Alert(Alert.AlertType.ERROR, err.getMessage()).showAndWait();
        }
    }

    public void showHomePage(String path) throws IOException {
        FXMLLoader main_Page = new FXMLLoader();
        main_Page.setLocation(getClass().getResource("/view/" + path +".fxml"));

        Scene scene = new Scene(main_Page.load());
        Stage stage = new Stage();
        stage.setTitle("Bus Management");
        stage.setScene(scene);
        stage.setResizable(false);
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
