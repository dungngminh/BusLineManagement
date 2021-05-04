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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
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
    private Button btn_show;

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
    void btn_cancel_clicked(MouseEvent event) {

    }

    @FXML
    void btn_create_clicked(MouseEvent event) {

    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {

    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {

    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {

    }

    @FXML
    void btn_search_clicked(MouseEvent event) {

    }

    @FXML
    void btn_show_clicked(MouseEvent event) {

    }

    @FXML
    void btn_update_clicked(MouseEvent event) {

    }

    @FXML
    void cbx_StationEnd_Action(ActionEvent event) {
    }

    @FXML
    void cbx_provinceEndAction(ActionEvent event) {
        Object[] list = cbx_provinceEnd.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
        List<Object> listToCBBEnd = Arrays.asList(list);
        listToCBBEnd.forEach(station -> cbx_endstation.getItems().add((StationEntity)station));
        cbx_endstation.getItems().remove(cbx_startstation.getSelectionModel().getSelectedItem());
    }

    @FXML
    void cbx_provinceStartAction(ActionEvent event) {
        cbx_startstation.getItems().clear();
        Object[] list = cbx_provinceStart.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
        List<Object> listToCBBStart = Arrays.asList(list);
        listToCBBStart.forEach(station -> cbx_startstation.getItems().add((StationEntity)station));
    }
    @FXML
    void cbx_stationStart_Action(ActionEvent event) {
        if(cbx_endstation.getSelectionModel().getSelectedItem() != null){
            cbx_endstation.getItems().clear();
            Object[] list = cbx_provinceEnd.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
            List<Object> listToCBBEnd = Arrays.asList(list);
            listToCBBEnd.forEach(station -> cbx_endstation.getItems().add((StationEntity)station));
            cbx_endstation.getItems().remove(cbx_startstation.getSelectionModel().getSelectedItem());
        }
    }
    private static boolean flag = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/admin_view/NavBar.fxml"));
            jfx_drawer.setSidePane(box);
            for (Node node : box.getChildren()) {
                if (node.lookup(".btn").getAccessibleText() != null) {
                    node.lookup(".btn").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.lookup(".btn").getId()) {
                            case "dashboard": {
                                try {
                                    showMainPage();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                break;
                            }
                            case "bus":{
                                try{
                                    showBusPage();
                                }catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                break;
                            }
                            case "setting": {
                                try {
                                    showSettingPage();
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

            // Init navbar transformation
            HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(jfx_hambur);
            jfx_hambur.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (flag)
                    burgerTask.setRate(burgerTask.getRate() * -1);
                flag = true;
                burgerTask.play();
                if (jfx_drawer.isShown()) {
                    jfx_drawer.toBack();
                    jfx_drawer.close();
                } else {
                    jfx_drawer.open();
                    jfx_drawer.toFront();
                    jfx_hambur.toFront();
                }

            });
            //done
            // Init combobox for start and end Station
            BLL_Admin.getInstance().getProvinceName().forEach(type -> {
                cbx_provinceStart.getItems().add(type);
            });
            BLL_Admin.getInstance().getProvinceName().forEach(type ->{
                cbx_provinceEnd.getItems().add(type);
            });
            //done
            // Init combobox status
            // done
            // Init tableview
            show(0);
            //done

            //Init button
            //done

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showMainPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/MainWindow.fxml"));
        this.pane.getChildren().setAll(newPane);
    }
    public void showBusPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/BusPage.fxml"));
        this.pane.getChildren().setAll(newPane);
    }
    public void showSettingPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/Setting.fxml"));
        this.pane.getChildren().setAll(newPane);
    }
    public void show(int status){
        ObservableList<RouteEntity> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().getRoutes(status));
        col_idStation.setCellValueFactory(new PropertyValueFactory<>("idRoute"));
        col_startstation.setCellValueFactory(new PropertyValueFactory<>("startStation"));
        col_endstation.setCellValueFactory(new PropertyValueFactory<>("endStation"));
        col_distance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("note"));

        table_view.setItems(listObj);
        table_view.refresh();
    }
}

