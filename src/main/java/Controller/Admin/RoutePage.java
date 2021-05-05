package Controller.Admin;

import Model.ProvinceEntity;
import Model.RouteEntity;
import Model.StationEntity;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RoutePage implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ComboBox<ProvinceEntity> cbx_provinceStart;

    @FXML
    private ComboBox<StationEntity> cbx_startstation;

    @FXML
    private Button btn_ok;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_cancel;

    @FXML
    private ComboBox<ProvinceEntity> cbx_provinceEnd;

    @FXML
    private ComboBox<StationEntity> cbx_endstation;

    @FXML
    private TableView<RouteEntity> table_view;

    @FXML
    private TableColumn<RouteEntity, Integer> col_idStation;

    @FXML
    private TableColumn<RouteEntity, String> col_startstation;

    @FXML
    private TableColumn<RouteEntity, String> col_endstation;

    @FXML
    private TableColumn<RouteEntity, Integer> col_distance;

    @FXML
    private TableColumn<RouteEntity, Integer> col_status;

    @FXML
    private TableColumn<RouteEntity, String> col_note;

    @FXML
    private ButtonBar grp_btn_tbl;


    @FXML
    private Button btn_create;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private HBox hbox;

    @FXML
    private TextField txf_search_nameofRoute;

    @FXML
    private Button btn_search;

    @FXML
    private SplitMenuButton btn_showmenu;

    @FXML
    private MenuItem btmenu_avaliable;

    @FXML
    private MenuItem btmenu_unavaliable;

    @FXML
    private ComboBox<String> cbx_status;

    @FXML
    private TextField tfx_distance;

    @FXML
    private TextArea tax_note;

    @FXML
    private Label lb_status;

    private static String CRUDType;
    private static int idRoute;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        tfx_distance.setText("");
        tax_note.setText("");
        cbx_provinceStart.getSelectionModel().select(null);
        cbx_provinceEnd.getSelectionModel().select(null);
        cbx_startstation.getSelectionModel().select(null);
        cbx_endstation.getSelectionModel().select(null);
        if (CRUDType.equals("Update")) {
            cbx_status.setVisible(false);
            lb_status.setVisible(false);
        }
        toggleDetail();
    }
    @FXML
    void btn_create_clicked(MouseEvent event) {
        CRUDType = "Create";
        lb_status.setVisible(false);
        cbx_status.setVisible(false);
        toggleDetail();
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {
        RouteEntity routeEntity = table_view.getSelectionModel().getSelectedItem();
        idRoute = routeEntity.getIdRoute();
        System.out.println(idRoute);
        BLL_Admin.getInstance().deleteRoute(idRoute);
        show(0, "");
    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {
        try {
            String startStation = cbx_startstation.getSelectionModel().getSelectedItem().toString();
            String endStation = cbx_endstation.getSelectionModel().getSelectedItem().toString();
            int distance = Integer.parseInt(tfx_distance.getText());
            String note = tax_note.getText().trim();
            if (cbx_startstation.getSelectionModel().getSelectedItem() == null || cbx_endstation.getSelectionModel().getSelectedItem() == null) {
                new Alert(Alert.AlertType.ERROR, "Please choose station!").showAndWait();
            }
            switch (CRUDType) {
                case "Create":
                    BLL_Admin.getInstance().addRoute(startStation, endStation, note, distance);
                    new Alert(Alert.AlertType.INFORMATION, "Add route successful!").showAndWait();
                    show(0, "");
                    break;
                case "Update":
                    int stt = cbx_status.getSelectionModel().getSelectedItem().equals("Available") ? 0 : 1;
                    BLL_Admin.getInstance().updateRoute(idRoute, startStation, endStation, note, distance, stt);
                    new Alert(Alert.AlertType.INFORMATION, "Update route successful!").showAndWait();
                    show(0, "");
                    break;
                default:
                    break;
            }
        }catch(Exception ee){
            new Alert(Alert.AlertType.ERROR,"Please fill information!").showAndWait();
        }
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        tfx_distance.setText("");
        tax_note.setText("");
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {
        show(0, txf_search_nameofRoute.getText());
    }

    @FXML
    void btn_showmenu_clicked(MouseEvent event) {
        show(0, "");
    }

    @FXML
    void btn_update_clicked(MouseEvent event) {
        try {
            RouteEntity routeEntity = table_view.getSelectionModel().getSelectedItem();
            idRoute = routeEntity.getIdRoute();
            // cbx start station // end station
            List<StationEntity> listStation = new ArrayList<>();
            List<ProvinceEntity> listProvinces = BLL_Admin.getInstance().getProvinceName();
            listProvinces.forEach(province ->{
                List<Object> list = Arrays.asList(province.getStationsByIdProvince().toArray());
                list.forEach(station -> {
                    listStation.add((StationEntity)station);
                });
            });
            listStation.forEach(station -> {
                if(routeEntity.getStartStation().equals(station.getStationName())){
                    cbx_startstation.getSelectionModel().select(station);
                    cbx_provinceStart.getSelectionModel().select(station.getProvinceByIdProvince());
                }
                if(routeEntity.getEndStation().equals(station.getStationName())){
                    cbx_endstation.getSelectionModel().select(station);
                    cbx_provinceEnd.getSelectionModel().select(station.getProvinceByIdProvince());
                }
            });
            tfx_distance.setText(routeEntity.getDistance().toString());
            tax_note.setText(routeEntity.getNote().toString());
            if(routeEntity.getStatus() == 0) cbx_status.getSelectionModel().selectFirst();
            else cbx_status.getSelectionModel().selectLast();
            CRUDType = "Update";
            lb_status.setVisible(true);
            cbx_status.setVisible(true);
            toggleDetail();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please choose 1 row").showAndWait();
        }
    }

    @FXML
    void cbx_StationEnd_Action(ActionEvent event) {
        //TODO đổ dữ liệu distance
    }

    @FXML
    void cbx_status(ActionEvent event) {

    }

    @FXML
    void onMenuClicked(ActionEvent event) {
        if (event.getSource() == btmenu_avaliable) {
            show(0, "");
        }
        if (event.getSource() == btmenu_unavaliable) {
            show(1, "");
        }
    }

    @FXML
    void cbx_provinceEndAction(ActionEvent event) {
        Object[] list = cbx_provinceEnd.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
        List<Object> listToCBBEnd = Arrays.asList(list);
        listToCBBEnd.forEach(station -> cbx_endstation.getItems().add((StationEntity) station));
        cbx_endstation.getItems().remove(cbx_startstation.getSelectionModel().getSelectedItem());
    }

    @FXML
    void cbx_provinceStartAction(ActionEvent event) {
        cbx_startstation.getItems().clear();
        Object[] list = cbx_provinceStart.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
        List<Object> listToCBBStart = Arrays.asList(list);
        listToCBBStart.forEach(station -> cbx_startstation.getItems().add((StationEntity) station));
    }

    @FXML
    void cbx_stationStart_Action(ActionEvent event) {
        if (cbx_endstation.getSelectionModel().getSelectedItem() != null) {
            cbx_endstation.getItems().clear();
            Object[] list = cbx_provinceEnd.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
            List<Object> listToCBBEnd = Arrays.asList(list);
            listToCBBEnd.forEach(station -> cbx_endstation.getItems().add((StationEntity) station));
            cbx_endstation.getItems().remove(cbx_startstation.getSelectionModel().getSelectedItem());
        }
    }
    @FXML
    void searchTextChanged(InputMethodEvent event) {
        //TODO realtime textchanged
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
            // Init combobox for start and end Station
            BLL_Admin.getInstance().getProvinceName().forEach(type -> {
                cbx_provinceStart.getItems().add(type);
            });
            BLL_Admin.getInstance().getProvinceName().forEach(type -> {
                cbx_provinceEnd.getItems().add(type);
            });
            cbx_status.getItems().add("Available");
            cbx_status.getItems().add("Unavailable");

            show(0, "");
            toggleDetail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(int status, String nameRoute) {
        ObservableList<RouteEntity> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().getRoutes(status, nameRoute));
        col_idStation.setCellValueFactory(new PropertyValueFactory<>("idRoute"));
        col_startstation.setCellValueFactory(new PropertyValueFactory<>("startStation"));
        col_endstation.setCellValueFactory(new PropertyValueFactory<>("endStation"));
        col_distance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("note"));

        table_view.setItems(listObj);
        table_view.refresh();
    }

    private void toggleDetail() {
        if (btn_ok.isVisible()) {
            btn_ok.setVisible(false);
            btn_reset.setVisible(false);
            btn_cancel.setVisible(false);
            grp_btn_tbl.setVisible(true);
            table_view.setLayoutX(-290);
            table_view.setPrefWidth(1165);
            hbox.setLayoutX(0);
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

