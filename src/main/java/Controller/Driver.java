package Controller;

import Model.DataTable.TableBusPage;
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
    private ComboBox<?> cbx_status;

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
    private TableColumn<DriverEntity, String> col_id;

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
    private TextField txf_slot;

    @FXML
    private TextField txf_search_nameofbus;

    @FXML
    private Button btn_search;

    // Var static
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
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/BusPage.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    public void showSettingPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/Setting.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    public void show(int slot, String name) {
        ObservableList<TableBusPage> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                updateTableBusPage(slot, name));

        col_id.setCellValueFactory(new PropertyValueFactory<>("idDriver"));
        col_nameofdriver.setCellValueFactory(new PropertyValueFactory<>("nameDriver"));
        col_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

//        table_view.setItems(listObj);
        table_view.refresh();
    }


    //done

    // Handle event
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
}
