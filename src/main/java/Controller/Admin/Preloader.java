package Controller.Admin;

import Controller.TicketSeller.InitSideBar;
import Model.ProvinceEntity;
import Services.BLL_Admin;
import Services.BLL_Seller;
import Services.DAL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class Preloader implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView loading;

    // Var static
    private static Integer role;

    public Preloader(Integer role) {
        Preloader.role = role;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new PreloadScreen().start();
    }

    class PreloadScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                if(role == 1 || role == 3) {
                    try {
                        showHomePage("admin_view/MainWindow");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else if (role == 2) {
                    try {
                        showHomePage("seller_view/Dashboard");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

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
            stage.setMaximized(true);
            stage.setMinWidth(1166);
            stage.setMinHeight(686);

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
                Stage cl = (Stage) loading.getScene().getWindow();
                cl.close();

        }
    }
}
