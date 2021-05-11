package Controller.Admin;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
        VBox box = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/admin_view/NavBar.fxml")));
        jfx_drawer.setSidePane(box);

        for (Node node : box.getChildren()) {
            if (node.lookup(".btn").getAccessibleText() != null) {
                node.lookup(".btn").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) -> {
                    try {
                        switch (node.lookup(".btn").getId()) {
                            case "dashboard": {
                                showPage(rootPane, "MainWindow");
                                break;
                            }
                            case "bus": {
                                showPage(rootPane, "BusPage");
                                break;
                            }
                            case "route": {
                                showPage(rootPane, "RoutePage");
                                break;
                            }

                            case "schedule":{
                                showPage(rootPane, "SchedulePage");
                                break;
                            }

                            case "setting": {
                                showPage(rootPane, "Setting");
                                break;
                            }
                            case "logout": {
                                FXMLLoader main_Page = new FXMLLoader();
                                main_Page.setLocation(getClass().getResource("/view/admin_view/LogIn.fxml"));

                                Scene scene = new Scene(main_Page.load());
                                Stage stage = new Stage();
                                stage.setTitle("Bus Management");
                                stage.setScene(scene);
                                stage.show();

                                Stage cl = (Stage) node.lookup(".btn").getScene().getWindow();
                                cl.close();
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

        // Init navbar transformation
        HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(jfx_hambur);
//        HamburgerBasicCloseTransition burgerTask = new HamburgerBasicCloseTransition(jfx_hambur);
        burgerTask.setRate(-1);
        jfx_hambur.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
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
    }

    // Method for task show page
    public void showPage(AnchorPane rootPane, String path) throws IOException {
        AnchorPane newPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/admin_view/" + path + ".fxml")));
        rootPane.getChildren().setAll(newPane);
    }
}
