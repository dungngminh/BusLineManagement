package Controller;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    private boolean flag = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/NavBar.fxml"));
            jfx_drawer.setSidePane(box);

            for (Node node: box.getChildren()) {
                if(node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch(node.getAccessibleText()) {
                            case "bus": {
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

            HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(jfx_hambur);
            jfx_hambur.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if(flag)
                    burgerTask.setRate(burgerTask.getRate() * -1);
                flag = true;
                burgerTask.play();
                if(jfx_drawer.isShowing()) {
                    jfx_drawer.close();
                }
                else jfx_drawer.open();

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showBusPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../../resources/view/BusPage.fxml"));
        this.rootPane.getChildren().setAll(newPane);

    }

}
