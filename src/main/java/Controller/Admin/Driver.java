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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Driver implements Initializable {
    @FXML
    private AnchorPane pane;

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
    private ComboBox<Integer> cbx_search_status;

    @FXML
    private TextField txf_search_nameofdriver;

    @FXML
    private Button btn_search;

    // Var static
    private static String CRUDType;
    private static Integer idDriver;
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
                            case "bus": {
                                try {
                                    showBusPage();
                                } catch (IOException ioException) {
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

    // Show nav bar pages
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
                BLL_Admin.getInstance().addDriver(name_of_driver, phonenumber,  address, 1);
                show(-1, "");
                break;
            }
            case "Update": {
                int stt = cbx_status.getSelectionModel().getSelectedItem().equals("Available") ? 1 : 0;
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
            cbx_status.getSelectionModel().select(tbl.getStatus() == 1 ? "Available" : "Unavailable");
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
            grp_btn_tbl.setVisible(true);
            table_view.setLayoutX(5);
            table_view.setPrefWidth(1155);
            hbox.setLayoutX(220);
            grp_btn_tbl.setLayoutX(279);
            titlepane.setVisible(false);
            table_view.toFront();
        }
        else {
            btn_ok.setVisible(true);
            btn_reset.setVisible(true);
            btn_cancel.setVisible(true);
            grp_btn_tbl.setVisible(false);
            table_view.setLayoutX(277);
            table_view.setPrefWidth(883);
            hbox.setLayoutX(343);
            grp_btn_tbl.setLayoutX(423);
            titlepane.setVisible(true);
        }
        jfx_hambur.toFront();

    }
}
