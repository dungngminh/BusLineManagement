package Controller.Admin;


import Model.BusEntity;

import Model.DriverEntity;
import Model.RouteEntity;
import Model.ViewModel.ScheduleEntity_ViewModel;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SchedulePage implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lb_update;

    @FXML
    private BorderPane border_pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ComboBox<RouteEntity> cbx_route;

    @FXML
    private ComboBox<BusEntity> cbx_bus;

    @FXML
    private ComboBox<DriverEntity> cbx_driver;

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
    private FlowPane grp_btn_tbl;

    @FXML
    private TitledPane titlepane_setting;

    @FXML
    private TitledPane titlepane_info;

    @FXML
    private Button btn_showmenu;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private JFXToggleButton toggle_updateDpr;

    @FXML
    private HBox hbox;

    @FXML
    private TextField txf_search;

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
    private TableColumn<ScheduleEntity_ViewModel, String> col_nameofdriver;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_price;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_departTime;

    @FXML
    private TableColumn<ScheduleEntity_ViewModel, String> col_outdate;


    @FXML
    private TableColumn<ScheduleEntity_ViewModel, Integer> col_dpr;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private Button btn_reOutdate;

    @FXML
    private Spinner<Integer> spn_timepickerH;

    @FXML
    private Spinner<Integer> spn_timepickerM;

    @FXML
    private Spinner<Integer> spn_timepickerS;

    @FXML
    private TextField tfx_duration;

    @FXML
    private Label lb_textOutDate;

    @FXML
    private Button btn_reAll;

    //SUPPORT PROPERTY
    private static String CRUDType;
    private static int idSchedule;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        cbx_route.getSelectionModel().select(null);
        cbx_bus.getSelectionModel().select(null);
        tfx_typeofbus.setText("");
        tfx_price.setText("");
        spn_timepickerH.getValueFactory().setValue(0);
        spn_timepickerM.getValueFactory().setValue(0);
        spn_timepickerS.getValueFactory().setValue(0);
        tfx_day_per_route.setText("");
        toggleDetail();
    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        tfx_day_per_route.setDisable(false);
        btn_ok.setText("Add");
        CRUDType = "Create";
        toggle_updateDpr.setVisible(false);
        lb_update.setVisible(false);
        toggleDetail();
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {
        try {
            ScheduleEntity_ViewModel schedule = table_view.getSelectionModel().getSelectedItem();
            BLL_Admin.getInstance().deleteSchedule(schedule.getIdSchedule());
            new Alert(Alert.AlertType.INFORMATION, "Delete successful!").showAndWait();
            show("");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error while deleting!");
            e.printStackTrace();
        }
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
            DriverEntity driverSelected = cbx_driver.getSelectionModel().getSelectedItem();
            if (cbx_route.getSelectionModel().getSelectedItem() == null || cbx_bus.getSelectionModel().getSelectedItem() == null) {
                new Alert(Alert.AlertType.ERROR, "Please choose station!").showAndWait();
            } else {
                switch (CRUDType) {
                    case "Create":
                        BLL_Admin.getInstance().addSchedule(routeSelected, busSelected, driverSelected, departTimeInput, durationInput, priceInput, dprInput);
                        new Alert(Alert.AlertType.INFORMATION, "Create successful!").showAndWait();
                        show("");
                        break;
                    case "Update":
                        if (tfx_day_per_route.isDisabled())
                            BLL_Admin.getInstance().updateScheduleNotDPR(idSchedule, routeSelected, busSelected, driverSelected, departTimeInput, durationInput, priceInput);
                        else
                            BLL_Admin.getInstance().updateSchedule(idSchedule, routeSelected, busSelected, driverSelected, departTimeInput, durationInput, priceInput, dprInput);
                        new Alert(Alert.AlertType.INFORMATION, "Update successful!").showAndWait();

                        show("");
                        break;
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
        tfx_typeofbus.setText("");
        tfx_price.setText("");
        spn_timepickerH.getValueFactory().setValue(0);
        spn_timepickerM.getValueFactory().setValue(0);
        spn_timepickerS.getValueFactory().setValue(0);
        tfx_day_per_route.setText("");
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {
        String key = txf_search.getText();
        show(key);
    }

    @FXML
    void btn_showmenu_clicked(MouseEvent event) {
        show("");
    }

    @FXML
    void toggleClicked(ActionEvent event) {
        tfx_day_per_route.setDisable(toggle_updateDpr.isSelected());
    }

    @FXML
    void btn_update_clicked(MouseEvent event) {
        toggle_updateDpr.setVisible(true);
        lb_update.setVisible(true);
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

            List<DriverEntity> driverEntity = BLL_Admin.getInstance().getListDriver(0, "");
            driverEntity.forEach(driver -> {
                if (driver.toString().equals(scheduleEntity_viewModel.getNameofDriver())) {
                    System.out.println(driver);
                    cbx_driver.getSelectionModel().select(driver);
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
            new Alert(Alert.AlertType.ERROR, "Please choose 1 row !").showAndWait();
            e.printStackTrace();
        }
        toggleDetail();
    }

    @FXML
    void onReAllClicked(MouseEvent event) throws ParseException {
        try {
            for (ScheduleEntity_ViewModel item : table_view.getItems()) {
                if(checkOutDate(item.getOutDate()))
                    BLL_Admin.getInstance().updateDPR(item.getIdSchedule(), item.getDpr());
            }
            new Alert(Alert.AlertType.INFORMATION,"Update all OutDate Schedule successful!").showAndWait();
            show("");
        }catch(Exception ee){
            new Alert(Alert.AlertType.ERROR, "Update fail, try again").showAndWait();
        }
    }
    @FXML
    void btn_export_clicked(MouseEvent event) throws IOException {
        if(table_view.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "List is empty!").showAndWait();
            return;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet spreadsheet = workbook.createSheet("schedule");

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

        stage.setTitle("Export data schedule");
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
            File myObj = new File(selectedDirectory.getAbsolutePath() + "/ListOfSchedule_" +
                    DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss").format(LocalDateTime.now()) + ".xls");
            if (myObj.createNewFile()) {
                FileOutputStream fileOut = new FileOutputStream(myObj);
                workbook.write(fileOut);
                fileOut.close();
            }
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Init combobox for bus and route
        BLL_Admin.getInstance().getAllBus().forEach(bus -> cbx_bus.getItems().add(bus));
        BLL_Admin.getInstance().getRoutes(0, "").forEach(route -> cbx_route.getItems().add(route));
        BLL_Admin.getInstance().getListDriver(0, "").forEach(driver -> cbx_driver.getItems().add(driver));
        if (cbx_route.getItems().isEmpty() || cbx_bus.getItems().isEmpty() || cbx_driver.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "NOT ENOUGH DATA, PLEASE CHECK DATA FROM BUSES, ROUTES AND DRIVERS").showAndWait();
        } else {
            try {
                // Init for side bar
                InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
                tfx_typeofbus.setEditable(false);
                spn_timepickerH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
                spn_timepickerM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
                spn_timepickerS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

                spn_timepickerH.setEditable(true);
                spn_timepickerM.setEditable(true);
                spn_timepickerS.setEditable(true);

                show("");

                lb_textOutDate.setText("Tuyến màu đỏ là tuyến bị tới hạn 1 tuần,\n" +
                        "vui lòng chọn và nhấn Re Outdate để cập nhật");
                toggleDetail();

                // Init search text field
                List<String> words = new ArrayList<>();

                BLL_Admin.getInstance().updateTableSchedulePage("").forEach(s -> {
                    words.add(s.getRouteName() + " || " + s.getTypeOfBus() + " || " + s.getDepartTime());
                });
                TextFields.bindAutoCompletion(txf_search, words);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void show(String key) {
        ObservableList<ScheduleEntity_ViewModel> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().updateTableSchedulePage(key));
        col_id.setCellValueFactory(new PropertyValueFactory<>("idSchedule"));
        col_routename.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        col_busname.setCellValueFactory(new PropertyValueFactory<>("busName"));
        col_typeofbus.setCellValueFactory(new PropertyValueFactory<>("typeOfBus"));
        col_nameofdriver.setCellValueFactory(new PropertyValueFactory<>("nameofDriver"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_departTime.setCellValueFactory(new PropertyValueFactory<>("departTime"));
        col_outdate.setCellValueFactory(new PropertyValueFactory<>("outDate"));
        col_dpr.setCellValueFactory(new PropertyValueFactory<>("dpr"));

        table_view.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(ScheduleEntity_ViewModel item, boolean b) {
                super.updateItem(item, b);
                if (item == null)
                    setStyle("");
                else try {
                    if (checkOutDate(item.getOutDate())) {
                        setStyle("-fx-background-color: #D16666");
                    } else setStyle("");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        table_view.setItems(listObj);
        table_view.refresh();
    }

    boolean checkOutDate(String outdate) throws ParseException {
        return TimeUnit.DAYS.convert(new SimpleDateFormat("dd/MM/yyyy").
                parse(outdate).getTime() -
                Date.from(LocalDate.now().
                        atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime(), TimeUnit.MILLISECONDS) <= 7;
    }
    private void toggleDetail() {
        if (btn_ok.isVisible()) {
            btn_ok.setVisible(false);
            btn_reset.setVisible(false);
            btn_cancel.setVisible(false);
            titlepane_info.setVisible(false);
            titlepane_setting.setVisible(false);
            grp_btn_tbl.setVisible(true);
            AnchorPane.setLeftAnchor(border_pane, 2.0);
        } else {
            btn_reset.setVisible(true);
            btn_ok.setVisible(true);
            btn_cancel.setVisible(true);
            titlepane_info.setVisible(true);
            titlepane_setting.setVisible(true);
            grp_btn_tbl.setVisible(false);

            AnchorPane.setLeftAnchor(border_pane, 280.0);
        }
        jfx_hambur.toFront();

    }
}
