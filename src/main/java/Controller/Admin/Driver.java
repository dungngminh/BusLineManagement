package Controller.Admin;

import Model.DriverEntity;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Driver implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private BorderPane border_pane;

    @FXML
    private TitledPane titlepane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private TextField txf_nameofdriver;

    @FXML
    private TextField txf_phonenumber;

    @FXML
    private Label lbl_status;

    @FXML
    private ComboBox<String> cbx_status;

    @FXML
    private TextField txf_address;

    @FXML
    private Button btn_ok;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_cancel;

    @FXML
    private TableView<DriverEntity> table_view;

    @FXML
    private TableColumn<DriverEntity, Integer> col_id;

    @FXML
    private TableColumn<DriverEntity, String> col_nameofdriver;

    @FXML
    private TableColumn<DriverEntity, String> col_phonenumber;

    @FXML
    private TableColumn<DriverEntity, String> col_address;

    @FXML
    private TableColumn<DriverEntity, Integer> col_status;

    @FXML
    private FlowPane grp_btn_tbl;

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
    private ComboBox<Integer> cbx_search_status;

    @FXML
    private TextField txf_search_nameofdriver;

    @FXML
    private Button btn_search;

    // Var static
    private static String CRUDType;
    private static Integer idDriver;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
            //done

            toggleDetail();
            show(-1, "");
            // Add cbx_search_status
            cbx_search_status.getItems().add(0);
            cbx_search_status.getItems().add(1);
            cbx_status.getItems().add("Available");
            cbx_status.getItems().add("Unavailable");
            cbx_search_status.getSelectionModel().selectFirst();
            //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(int status, String name) {
        ObservableList<DriverEntity> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                getListDriver(status, name));

        col_id.setCellValueFactory(new PropertyValueFactory<>("idDriver"));
        col_nameofdriver.setCellValueFactory(new PropertyValueFactory<>("nameDriver"));
        col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        table_view.setItems(listObj);
        table_view.refresh();
    }


    //done

    // Handle event
    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        btn_reset.fire();
        if(CRUDType.equals("Update")) {
            cbx_status.setVisible(false);
            lbl_status.setVisible(false);
        }
        toggleDetail();
    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        CRUDType = "Create";
        toggleDetail();
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {
//        try {
            DriverEntity tbl = table_view.getSelectionModel().getSelectedItem();
            idDriver = tbl.getIdDriver();
            BLL_Admin.getInstance().deleteDriver(idDriver);
            show(-1, "");
//        } catch (Exception err) {
//            new Alert(Alert.AlertType.INFORMATION, "Choose only 1 row!").showAndWait();
//        }
    }

    @FXML
    void btn_export_clicked(MouseEvent event) throws IOException {
        if(table_view.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "List is empty!").showAndWait();
            return;
        }
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("driver");

        Row row = spreadsheet.createRow(0);

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

        stage.setTitle("Export data driver");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        File selectedDirectory = directoryChooser.showDialog(stage);
        if(selectedDirectory != null) {
            if (!selectedDirectory.canRead()) {
                Boolean b = selectedDirectory.setReadable(true, false);
            }

            File myObj = new File(selectedDirectory.getAbsolutePath() + "/ListOfDriver_" +
                    DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss").format(LocalDateTime.now()) + ".xls");
            if (myObj.createNewFile()) {
                FileOutputStream fileOut = new FileOutputStream(myObj);
                workbook.write(fileOut);
                fileOut.close();
            }
        }
    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {
        String name_of_driver = txf_nameofdriver.getText().trim();
        String phonenumber = txf_phonenumber.getText().trim();
        String address = txf_address.getText().trim();
        if(name_of_driver.equals("") || phonenumber.equals("") || address.equals("")) {
            new Alert(Alert.AlertType.WARNING, "Fill all field!").showAndWait();
            return;
        }
        switch(CRUDType) {
            case "Create": {
                BLL_Admin.getInstance().addDriver(name_of_driver, phonenumber,  address, 0);
                show(-1, "");
                break;
            }
            case "Update": {
                int stt = cbx_status.getSelectionModel().getSelectedItem().equals("Available") ? 0 : 1;
                BLL_Admin.getInstance().updateDriver(idDriver, name_of_driver, phonenumber, address, stt);
                show(-1, "");
                break;
            }
            default:
                break;
        }
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_nameofdriver.setText("");
        txf_phonenumber.setText("");
        txf_address.setText("");
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {
        try {
            show(cbx_search_status.getSelectionModel().getSelectedItem(), txf_search_nameofdriver.getText().trim());
        }
        catch(Exception err) {
            new Alert(Alert.AlertType.WARNING, "Check again!").showAndWait();
        }
    }

    @FXML
    void btn_show_clicked(MouseEvent event) {
        show(-1, "");
    }

    @FXML
    void btn_update_clicked(MouseEvent event) {
        try {
            DriverEntity tbl = table_view.getSelectionModel().getSelectedItem();
            idDriver = tbl.getIdDriver();
            txf_nameofdriver.setText(tbl.getNameDriver());
            txf_phonenumber.setText(tbl.getPhone());
            txf_address.setText(tbl.getAddress());
            cbx_status.getSelectionModel().select(tbl.getStatus() == 0 ? "Available" : "Unavailable");
            cbx_status.setVisible(true);
            lbl_status.setVisible(true);
            CRUDType = "Update";
            toggleDetail();
        } catch (Exception err) {
            new Alert(Alert.AlertType.INFORMATION, "Choose only 1 row!").showAndWait();
        }
    }

    // Stuff
    public void toggleDetail(){
        if (btn_ok.isVisible()) {
            btn_ok.setVisible(false);
            btn_reset.setVisible(false);
            btn_cancel.setVisible(false);
            titlepane.setVisible(false);

            grp_btn_tbl.setVisible(true);

            AnchorPane.setLeftAnchor(border_pane, 2.0);
        }
        else {
            btn_ok.setVisible(true);
            btn_reset.setVisible(true);
            btn_cancel.setVisible(true);
            titlepane.setVisible(true);

            grp_btn_tbl.setVisible(false);
            AnchorPane.setLeftAnchor(border_pane, 280.0);

        }
        jfx_hambur.toFront();

    }
}
