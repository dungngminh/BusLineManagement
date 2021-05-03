package Controller;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class NavBar {
    @FXML
    private Button btn_dashboard;

    @FXML
    private Button btn_bus;

    @FXML
    private Button btn_route;

    @FXML
    private Button btn_ticket;

    @FXML
    private Button btn_trip;

    @FXML
    private Button btn_decen;


    public void btn_bus_clicked(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
    }
}
