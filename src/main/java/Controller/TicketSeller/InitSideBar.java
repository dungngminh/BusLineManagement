package Controller.TicketSeller;

import Services.BLL_Admin;
import Services.BLL_Seller;
import Services.DAL;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InitSideBar {
    private static InitSideBar instance;

    private InitSideBar() {

    }

    public static InitSideBar getInstance() {
        if (instance == null) {
            instance = new InitSideBar();
        }
        return instance;
    }


    public void initializeForNavBar(AnchorPane rootPane, JFXDrawer jfx_drawer, JFXHamburger jfx_hambur) throws IOException {
//        VBox box = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/seller_view/NavBar.fxml")));
//        box.setFillWidth(true);
//        VBox.setVgrow(box, Priority.ALWAYS);

        AnchorPane box =  FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/seller_view/NavBar.fxml")));
        jfx_drawer.setSidePane(box);

        VBox vbox = (VBox)box.lookup(".vbox");

        for (Node node : vbox.lookupAll(".btn")) {
            if (node.lookup(".btn").getId() != null) {
                node.lookup(".btn").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) -> {
                    try {
                        switch (node.lookup(".btn").getId()) {
                            case "dashboard": {
                                showPage(rootPane,"Dashboard");
                                break;
                            }
                            case "ticket":{
                                showPage(rootPane,"Ticket");
                                break;
                            }
                            case "setting":{
                                showPage(rootPane,"Setting");
                                break;
                            }
                            default:
                                break;
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            }
        }

        Button logout = (Button)box.lookup("#logout");
        logout.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) -> {

            // Case ticket seller booking ticket
            Integer idTicket = BLL_Admin.getInstance().getIdTicketToClose();
            if(idTicket > 0)
                BLL_Seller.getInstance().deleteCurrentTicket(idTicket);

            //
            BLL_Admin.getInstance().toggleIsOnlineForAccout(DAL.getInstance().getCurrent(), false);
            FXMLLoader main_Page = new FXMLLoader();
            main_Page.setLocation(getClass().getResource("/view/admin_view/LogIn.fxml"));

            Scene scene = null;
            try {
                scene = new Scene(main_Page.load());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Bus Management");
            stage.setScene(scene);
            stage.show();

            Stage cl = (Stage) logout.getScene().getWindow();
            cl.close();
        });

        // Init navbar transformation
        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(jfx_hambur);
        burgerTask.setRate(-1);
        jfx_hambur.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            if (jfx_drawer.isShown()) {
                jfx_drawer.toBack();
                jfx_drawer.close();
                jfx_hambur.setId("");
            } else {
                jfx_drawer.open();
                jfx_drawer.toFront();
                jfx_hambur.toFront();
                jfx_hambur.setId("hamburger");
            }

        });
    }

    // Method for task show page
    public void showPage(AnchorPane rootPane, String path) throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/seller_view/" + path + ".fxml"));
        newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
        Scene scene= rootPane.getScene();
        scene.setRoot(newPane);
    }
}
