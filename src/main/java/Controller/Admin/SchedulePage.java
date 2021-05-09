package Controller.Admin;

import Model.BusEntity;
import Model.ViewModel.ScheduleEntity_ViewModel;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SchedulePage implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ComboBox<String> cbx_route;

    @FXML
    private ComboBox<String> cbx_bus;

    @FXML
    private TextField tfx_typeofbus;

    @FXML
    private TextField tfx_price;

    @FXML
    private TextField tfx_departTime;

    @FXML
    private Button btn_ok;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField tfx_day_per_route;

    @FXML
    private ButtonBar grp_btn_tbl;

    @FXML
    private SplitMenuButton btn_showmenu;

    @FXML
    private MenuItem btmenu_avaliable;

    @FXML
    private MenuItem btmenu_unavaliable;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private HBox hbox;

    @FXML
    private TextField txf_search_nameofbus;

    @FXML
    private Button btn_search;

    @FXML
    private TableView<ScheduleEntity_ViewModel> table_view;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_id;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, String> col_routename;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, String> col_busname;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, String> col_typeofbus;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_departTime;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_status;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        toggleDetail();
    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        toggleDetail();
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {

    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {
        toggleDetail();
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {

    }

    @FXML
    void btn_search_clicked(MouseEvent event) {

    }

    @FXML
    void btn_showmenu_clicked(MouseEvent event) {

    }

    @FXML
    void btn_update_clicked(MouseEvent event) {
        toggleDetail();
    }

    @FXML
    void onBusCBBAction(ActionEvent event) {
        Object[] objs = BLL_Admin.getInstance().getAllBus().toArray();
        List<Object> objsList = new ArrayList<Object>();

    }

    @FXML
    void onMenuClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
            // Init combobox for bus and route

            BLL_Admin.getInstance().getAllBus().forEach(bus -> cbx_bus.getItems().add(bus.getBusName()));
            BLL_Admin.getInstance().getRoutes(0,"").forEach(route -> cbx_route.getItems().add(route.getStartStation()+ " - " + route.getEndStation()));

            tfx_typeofbus.setEditable(false);


//            show(0, "");
            toggleDetail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toggleDetail() {
        if (btn_ok.isVisible()) {
            btn_ok.setVisible(false);
            btn_reset.setVisible(false);
            btn_cancel.setVisible(false);
            grp_btn_tbl.setVisible(true);
            table_view.setLayoutX(-290);
            table_view.setPrefWidth(1165);
            hbox.setLayoutX(80);
            grp_btn_tbl.setLayoutX(85);
            table_view.toFront();
        } else {
            btn_reset.setVisible(true);
            btn_ok.setVisible(true);
            btn_cancel.setVisible(true);
            grp_btn_tbl.setVisible(false);
            table_view.setLayoutX(0);
            table_view.setPrefWidth(885);
            hbox.setLayoutX(114);
            grp_btn_tbl.setLayoutX(253);
        }
        jfx_hambur.toFront();

    }


}
