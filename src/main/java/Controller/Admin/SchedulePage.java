package Controller.Admin;


import Model.BusEntity;

import Model.RouteEntity;
import Model.ViewModel.ScheduleEntity_ViewModel;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.ResourceBundle;

public class SchedulePage implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ComboBox<RouteEntity> cbx_route;

    @FXML
    private ComboBox<BusEntity> cbx_bus;


    @FXML
    private TextField tfx_typeofbus;

    @FXML
    private TextField tfx_price;


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
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_price;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_departTime;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_duration;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_dpr;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private Spinner<Integer> spn_timepickerH;

    @FXML
    private Spinner<Integer> spn_timepickerM;

    @FXML
    private Spinner<Integer> spn_timepickerS;

    @FXML
    private TextField tfx_duration;

    //SUPPORT PROPERTY
    private static String CRUDType;
    private static int idSchedule;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {

        toggleDetail();
    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        btn_ok.setText("Add");
        CRUDType = "Create";
        toggleDetail();
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {
        ScheduleEntity_ViewModel schedule = table_view.getSelectionModel().getSelectedItem();
        BLL_Admin.getInstance().deleteSchedule(schedule.getIdSchedule());
        show("");
    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {
        try {
            RouteEntity routeSelected = cbx_route.getSelectionModel().getSelectedItem();
            BusEntity busSelected = cbx_bus.getSelectionModel().getSelectedItem();
            int priceInput = Integer.parseInt(tfx_price.getText());
            int dprInput = Integer.parseInt(tfx_day_per_route.getText());
            int durationInput = Integer.parseInt(tfx_duration.getText());
            String time = spn_timepickerH.getValue().toString() + ":" + spn_timepickerM.getValue().toString() + ":" + spn_timepickerS.getValue().toString();
            Date departTimeInput = new SimpleDateFormat("HH:mm:ss").parse(time);
            if (cbx_route.getSelectionModel().getSelectedItem() == null || cbx_bus.getSelectionModel().getSelectedItem() == null) {
                new Alert(Alert.AlertType.ERROR, "Please choose station!").showAndWait();
            } else {
                switch (CRUDType) {
                    case "Create":
                        BLL_Admin.getInstance().addSchedule(routeSelected, busSelected, departTimeInput, durationInput, priceInput, dprInput);
                        show("");
                        break;
                    case "Update":
                        BLL_Admin.getInstance().updateSchedule(idSchedule, routeSelected, busSelected, departTimeInput, durationInput, priceInput, dprInput);
                        show("");
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Fail").showAndWait();
            e.printStackTrace();
        }
        toggleDetail();
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        cbx_route.getSelectionModel().select(null);
        cbx_bus.getSelectionModel().select(null);
        tfx_typeofbus.setText("");
        tfx_price.setText("");
        spn_timepickerH.getValueFactory().setValue(0);
        spn_timepickerM.getValueFactory().setValue(0);
        spn_timepickerS.getValueFactory().setValue(0);
        tfx_day_per_route.setText("");
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {

    }

    @FXML
    void btn_showmenu_clicked(MouseEvent event) {

    }

    @FXML
    void btn_update_clicked(MouseEvent event) {
        CRUDType = "Update";
        btn_ok.setText("OK");
        try {
            ScheduleEntity_ViewModel scheduleEntity_viewModel = table_view.getSelectionModel().getSelectedItem();
            idSchedule = scheduleEntity_viewModel.getIdSchedule();
            List<RouteEntity> routeEntity = BLL_Admin.getInstance().getRoutes(0, "");

            routeEntity.forEach(route -> {
                if (route.toString().equals(scheduleEntity_viewModel.getRouteName())) {
                    System.out.println(route);
                    cbx_route.getSelectionModel().select(route);
                }
            });

            List<BusEntity> busEntity = BLL_Admin.getInstance().getAllBus();
            busEntity.forEach(bus -> {
                if (bus.getBusName().equals(scheduleEntity_viewModel.getBusName())) {
                    cbx_bus.getSelectionModel().select(bus);
                }
            });

            tfx_price.setText(Integer.toString(scheduleEntity_viewModel.getPrice()));
            int hour = Integer.parseInt(scheduleEntity_viewModel.getDepartTime().split(":")[0]);
            int minute = Integer.parseInt(scheduleEntity_viewModel.getDepartTime().split(":")[1]);
            int second = Integer.parseInt(scheduleEntity_viewModel.getDepartTime().split(":")[2]);

            spn_timepickerH.getValueFactory().setValue(hour);
            spn_timepickerM.getValueFactory().setValue(minute);
            spn_timepickerS.getValueFactory().setValue(second);

            tfx_day_per_route.setText(Integer.toString(scheduleEntity_viewModel.getDpr()));
            tfx_duration.setText(Integer.toString(scheduleEntity_viewModel.getDuration()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO : show data text feild ft. combo box
        toggleDetail();
    }

    @FXML
    void onBusCBBAction(ActionEvent event) {
        Object[] objs = BLL_Admin.getInstance().getAllBus().toArray();
        List<Object> objsList = new ArrayList<Object>();
        if(cbx_bus.getSelectionModel().getSelectedItem() != null)
            tfx_typeofbus.setText(cbx_bus.getSelectionModel().getSelectedItem().getTypeOfBusByIdType().getTypeName());
        else System.out.println("null");
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

            BLL_Admin.getInstance().getAllBus().forEach(bus -> cbx_bus.getItems().add(bus));
            BLL_Admin.getInstance().getRoutes(0, "").forEach(route -> cbx_route.getItems().add(route));

            tfx_typeofbus.setEditable(false);

            spn_timepickerH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
            spn_timepickerM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
            spn_timepickerS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

            show("");

            toggleDetail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void show(String name) {
        ObservableList<ScheduleEntity_ViewModel> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().updateTableSchedulePage(name));
        col_id.setCellValueFactory(new PropertyValueFactory<>("idSchedule"));
        col_routename.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        col_busname.setCellValueFactory(new PropertyValueFactory<>("busName"));
        col_typeofbus.setCellValueFactory(new PropertyValueFactory<>("typeOfBus"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_departTime.setCellValueFactory(new PropertyValueFactory<>("departTime"));
        col_dpr.setCellValueFactory(new PropertyValueFactory<>("dpr"));
        col_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));


        table_view.setItems(listObj);
        table_view.refresh();
    }

    private void toggleDetail() {
        if (btn_ok.isVisible()) {
            btn_ok.setVisible(false);
            btn_reset.setVisible(false);
            btn_cancel.setVisible(false);
            grp_btn_tbl.setVisible(true);

            table_view.setLayoutX(-275);
            table_view.setPrefWidth(1150);

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
