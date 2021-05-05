package Controller.TicketSeller;

import Model.ProvinceEntity;
import Services.BLL_Admin;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FilterRoute implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ColumnConstraints gridpane;

    @FXML
    private ImageView arrow_right;

    @FXML
    private ImageView arrow_left;

    @FXML
    private DatePicker calendar;

    @FXML
    private JFXHamburger jfx_hambur;

    // Attributes

    ProvinceEntity startProvince;
    ProvinceEntity endProvince;
    LocalDate date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initVariable(ProvinceEntity startProvince, ProvinceEntity endProvince, LocalDate date) {
        this.startProvince = startProvince;
        this.endProvince = endProvince;
        this.date = date;
        calendar.setValue(date);
    }

}
