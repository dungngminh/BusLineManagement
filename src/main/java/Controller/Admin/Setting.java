package Controller.Admin;

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
            //done

            // ...

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDecentralizePage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/Decentralize.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    public void showDriverPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/Driver.fxml"));
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
