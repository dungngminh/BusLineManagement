package Controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Setting implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

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
                            case "bus":{
                                try {
                                    showBusPage();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                break;
                            }
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/MainWindow.fxml"));
        this.pane.getChildren().setAll(newPane);

    }

    public void showBusPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/BusPage.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    public void showDecentralizePage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/Decentralize.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    public void showDriverPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/Driver.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    @FXML
    void btn_decen_clicked(MouseEvent event) throws IOException {
        showDecentralizePage();
    }

    @FXML
    void btn_driver_clicked(MouseEvent event) throws IOException {
        showDriverPage();
    }
}
