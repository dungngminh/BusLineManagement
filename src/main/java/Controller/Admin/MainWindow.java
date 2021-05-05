package Controller.Admin;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            // ...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ...

}
