package Controller.Admin;

import Model.ProvinceEntity;
import Model.RouteEntity;
import Model.StationEntity;
import Model.ViewModel.ScheduleEntity_ViewModel;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class RoutePage implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private BorderPane border_pane;

    @FXML
    private TitledPane titlepane_start;

    @FXML
    private TitledPane titlepane_end;

    @FXML
    private TitledPane titlepane_info;

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
    private FlowPane grp_btn_tbl;


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
    private JFXToggleButton toggle_returnRoute;

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

    // Static attributes
    private static String CRUDType;
    private static int idRoute;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        tfx_distance.setText("");
        tax_note.setText("");

        if (CRUDType.equals("Update")) {
            cbx_status.setVisible(false);
            lb_status.setVisible(false);
        }
        toggleDetail();
    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        toggle_returnRoute.setVisible(true);
        btn_ok.setText("Add");
        cbx_provinceStart.getSelectionModel().select(null);
        cbx_provinceEnd.getSelectionModel().select(null);
        cbx_startstation.getSelectionModel().select(null);
        cbx_endstation.getSelectionModel().select(null);
        CRUDType = "Create";
        lb_status.setVisible(false);
        cbx_status.setVisible(false);
        toggleDetail();
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {
        RouteEntity routeEntity = table_view.getSelectionModel().getSelectedItem();
        idRoute = routeEntity.getIdRoute();

        BLL_Admin.getInstance().deleteRoute(idRoute);
        new Alert(Alert.AlertType.INFORMATION, "Delete successful!").showAndWait();
        show(0, "");
    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {
        try {
            String startStation = cbx_startstation.getSelectionModel().getSelectedItem().toString();
            String endStation = cbx_endstation.getSelectionModel().getSelectedItem().toString();

            if (startStation.equals(endStation))
                new Alert(Alert.AlertType.ERROR, "Duplicate Station, please re fill!").showAndWait();

            else {
                    if(BLL_Admin.getInstance().checkDuplicateRoute(startStation,endStation))
                        new Alert(Alert.AlertType.ERROR, "Duplicate Data, please re fill").showAndWait();
                    else {
                        int distance = Integer.parseInt(tfx_distance.getText());
                        String note = tax_note.getText().trim();
                        if (cbx_startstation.getSelectionModel().getSelectedItem() == null || cbx_endstation.getSelectionModel().getSelectedItem() == null) {
                            new Alert(Alert.AlertType.ERROR, "Please choose station!").showAndWait();
                        } else {
                            switch (CRUDType) {
                                case "Create":
                                    if (toggle_returnRoute.isSelected()) {
                                        BLL_Admin.getInstance().addRoute(startStation, endStation, note, distance);
                                        BLL_Admin.getInstance().addRoute(endStation, startStation, note, distance);
                                    } else BLL_Admin.getInstance().addRoute(startStation, endStation, note, distance);
                                    new Alert(Alert.AlertType.INFORMATION, "Add route successful!").showAndWait();
                                    show(0, "");
                                    toggleDetail();
                                    break;
                                case "Update":
                                    int stt = cbx_status.getSelectionModel().getSelectedItem().equals("Available") ? 0 : 1;
                                    BLL_Admin.getInstance().updateRoute(idRoute, startStation, endStation, note, distance, stt);
                                    new Alert(Alert.AlertType.INFORMATION, "Update route successful!").showAndWait();
                                    show(0, "");
                                    toggleDetail();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
            }
        } catch (Exception ee) {
            new Alert(Alert.AlertType.ERROR, "Please fill information!").showAndWait();
        }
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        tfx_distance.setText("");
        tax_note.setText("");
        cbx_provinceStart.getSelectionModel().select(null);
        cbx_provinceEnd.getSelectionModel().select(null);
        cbx_startstation.getSelectionModel().select(null);
        cbx_endstation.getSelectionModel().select(null);
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
        toggle_returnRoute.setVisible(false);
        btn_ok.setText("Ok");
        try {
            CRUDType = "toggleUpdate";
            RouteEntity routeEntity = table_view.getSelectionModel().getSelectedItem();
            idRoute = routeEntity.getIdRoute();
            // cbx start station // end station
            List<StationEntity> listStation = new ArrayList<>();
            List<ProvinceEntity> listProvinces = BLL_Admin.getInstance().getProvinceName();
            listProvinces.forEach(province -> {
                List<Object> list = Arrays.asList(province.getStationsByIdProvince().toArray());
                list.forEach(station -> listStation.add((StationEntity) station));
            });
            listStation.forEach(station -> {
                if (routeEntity.getStartStation().equals(station.getStationName())) {
                    cbx_startstation.getSelectionModel().select(station);
                    cbx_provinceStart.getSelectionModel().select(station.getProvinceByIdProvince());
                }
                if (routeEntity.getEndStation().equals(station.getStationName())) {
                    cbx_endstation.getSelectionModel().select(station);
                    cbx_provinceEnd.getSelectionModel().select(station.getProvinceByIdProvince());
                }
            });
//            System.out.println(cbx_provinceStart.getSelectionModel().getSelectedIndex());
//            System.out.println(cbx_provinceEnd.getSelectionModel().getSelectedIndex());
//            System.out.println(cbx_startstation.getSelectionModel().getSelectedIndex());
//            System.out.println(cbx_endstation.getSelectionModel().getSelectedIndex());
            tfx_distance.setText(Integer.toString(BLL_Admin.getInstance().getDistance(cbx_provinceStart.getSelectionModel().getSelectedIndex(), cbx_provinceEnd.getSelectionModel().getSelectedIndex())));
            tax_note.setText(routeEntity.getNote());
            if (routeEntity.getStatus() == 0) cbx_status.getSelectionModel().selectFirst();
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
    void btn_export_clicked(MouseEvent event) throws IOException {
        if(table_view.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "List is empty!").showAndWait();
            return;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet spreadsheet = workbook.createSheet("route");

        HSSFRow row = spreadsheet.createRow(0);

        for (int j = 0; j < table_view.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table_view.getColumns().get(j).getText());
        }

        for (int i = 0; i < table_view.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table_view.getColumns().size(); j++) {
                if(table_view.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(table_view.getColumns().get(j).getCellData(i).toString());
                }
                else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        // Show Selected Directory
        Stage stage = new Stage();

        stage.setTitle("Export data route");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home"), "./"));

        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory != null) {
            if (!selectedDirectory.canRead()) {
                Boolean b = selectedDirectory.setReadable(true, false);
            }

            File myObj = new File(selectedDirectory.getAbsolutePath() + "/ListOfRoute_" +
                    DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss").format(LocalDateTime.now()) + ".xls");
            if (myObj.createNewFile()) {
                FileOutputStream fileOut = new FileOutputStream(myObj);
                workbook.write(fileOut);
                fileOut.close();
            }
        }
    }

    @FXML
    void cbx_StationEnd_Action(ActionEvent event) {
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
    void cbx_provinceEndAction(ActionEvent event) throws IOException {
        cbx_endstation.getItems().clear();
        Object[] list = cbx_provinceEnd.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
        List<Object> listToCBBEnd = Arrays.asList(list);
        listToCBBEnd.forEach(station -> cbx_endstation.getItems().add((StationEntity) station));

        if(!cbx_provinceStart.getSelectionModel().isEmpty() && !cbx_provinceEnd.getSelectionModel().isEmpty()) {
            tfx_distance.setText(Integer.toString(BLL_Admin.getInstance().getDistance(cbx_provinceStart.getSelectionModel().getSelectedIndex(), cbx_provinceEnd.getSelectionModel().getSelectedIndex())));
        }
    }

    @FXML
    void cbx_provinceStartAction(ActionEvent event) throws IOException {
        cbx_startstation.getItems().clear();
        Object[] list = cbx_provinceStart.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
        List<Object> listToCBBStart = Arrays.asList(list);
        listToCBBStart.forEach(station -> cbx_startstation.getItems().add((StationEntity) station));

        if(!cbx_provinceStart.getSelectionModel().isEmpty() && !cbx_provinceEnd.getSelectionModel().isEmpty()) {
            tfx_distance.setText(Integer.toString(BLL_Admin.getInstance().getDistance(cbx_provinceStart.getSelectionModel().getSelectedIndex(), cbx_provinceEnd.getSelectionModel().getSelectedIndex())));
        }
    }

    @FXML
    void cbx_stationStart_Action(ActionEvent event) {
//        cbx_endstation.getItems().clear();
//        Object[] list = cbx_provinceEnd.getSelectionModel().getSelectedItem().getStationsByIdProvince().toArray();
//        List<Object> listToCBBEnd = Arrays.asList(list);
//        listToCBBEnd.forEach(station -> cbx_endstation.getItems().add((StationEntity) station));
//        cbx_endstation.getItems().remove(cbx_startstation.getSelectionModel().getSelectedItem());
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
            tfx_distance.setEditable(false);

            show(0, "");
            toggleDetail();

            // Init search text field
            List<String> words = new ArrayList<>();

            BLL_Admin.getInstance().getRoutes(null, "").forEach(r -> {
                words.add(r.getStartStation() + " " + r.getEndStation());
            });
            TextFields.bindAutoCompletion(txf_search_nameofRoute, words);
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
            titlepane_start.setVisible(false);
            titlepane_end.setVisible(false);
            titlepane_info.setVisible(false);

            grp_btn_tbl.setVisible(true);
            AnchorPane.setLeftAnchor(border_pane, 2.0);
        } else {
            btn_reset.setVisible(true);
            btn_ok.setVisible(true);
            btn_cancel.setVisible(true);
            titlepane_start.setVisible(true);
            titlepane_end.setVisible(true);
            titlepane_info.setVisible(true);

            grp_btn_tbl.setVisible(false);

            AnchorPane.setLeftAnchor(border_pane, 280.0);
        }
        jfx_hambur.toFront();

    }
}

