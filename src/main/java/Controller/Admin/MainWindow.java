package Controller.Admin;

import Model.AccountEntity;
import Model.ProvinceEntity;
import Services.BLL_Admin;
import Services.DAL;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    @FXML
    private Label lb_greet;

    @FXML
    private Label lb_revenue;

    @FXML
    private Label lb_trip;

    @FXML
    private Label lb_route;

    @FXML
    private Label lb_personnel;

    @FXML
    private ComboBox<String> cbx_time;

    @FXML
    private ComboBox<AccountEntity> cbx_staff;

    @FXML
    private ComboBox<ProvinceEntity> cbx_toProvince;

    @FXML
    private ComboBox<ProvinceEntity> cbx_fromProvince;

    public MainWindow() {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            // Set Greeting
            lb_greet.setText("Welcome, " + DAL.current.getUsername());

            // Set Total revenue
            lb_revenue.setText(BLL_Admin.getInstance().getRevenueTicket1YearAgo());

            // Set Routes today
            lb_route.setText(String.valueOf(BLL_Admin.getInstance().getNumberRoutesToday()));
            // DONE
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ...

}
