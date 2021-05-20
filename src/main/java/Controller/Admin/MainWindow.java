package Controller.Admin;

import Model.AccountEntity;
import Model.ProvinceEntity;
import Model.ViewModel.BusEntity_ViewModel;
import Services.BLL_Admin;
import Services.DAL;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private CheckComboBox<AccountEntity> cbx_staff;

    @FXML
    private ComboBox<ProvinceEntity> cbx_to;

    @FXML
    private ComboBox<ProvinceEntity> cbx_from;

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

            // Set number outdated of Schedule
            lb_trip.setText(String.valueOf(BLL_Admin.getInstance().getOutDatedSchedule().size()));

            // Set number of personnel
            lb_personnel.setText(String.valueOf(BLL_Admin.getInstance().getListAcc().size()));

            // Set for combobox Time Interval
            cbx_time.getItems().addAll("1 Week Ago", "1 Quarter Ago", "1 Year Ago");
            cbx_time.getSelectionModel().selectFirst();

            // Set for combobox staff
            final ObservableList<AccountEntity> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                    getListSeller());

            cbx_staff.getItems().addAll(listObj);

            // Set for combobox from - to
            cbx_from.getItems().addAll(BLL_Admin.getInstance().getProvinceName());
            cbx_to.getItems().addAll(BLL_Admin.getInstance().getProvinceName());
            cbx_from.getSelectionModel().selectFirst();
            cbx_to.getSelectionModel().selectFirst();

            // DONE
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ...

}
