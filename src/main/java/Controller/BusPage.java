package Controller;

import Model.DataTable.TableBusPage;
import Model.TypeOfBusEntity;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BusPage implements Initializable {
    public AnchorPane getPane() {
        return pane;
    }

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ComboBox<String> cbx_nameoftype;

    @FXML
    private TextField txf_brandname;

    @FXML
    private TextField txf_slots;

    @FXML
    private TextField txf_nameofbus;

    @FXML
    private TextField txf_platenumber;

    @FXML
    private Label lbl_status;

    @FXML
    private ComboBox<String> cbx_status;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_ok;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_cancel;

    @FXML
    private JFXHamburger jfx_hambur;

    // for tableview
    @FXML
    private TableView<TableBusPage> table_view;

    @FXML
    private TableColumn<TableBusPage, Integer> col_id;

    @FXML
    private TableColumn<TableBusPage, String> col_nameofbus;

    @FXML
    private TableColumn<TableBusPage, String> col_platenumber;

    @FXML
    private TableColumn<TableBusPage, String> col_nameoftype;

    @FXML
    private TableColumn<TableBusPage, String> col_brandname;

    @FXML
    private TableColumn<TableBusPage, Integer> col_slots;

    @FXML
    private TableColumn<TableBusPage, Integer> col_status;

    @FXML
    private ButtonBar grp_btn_tbl;

    @FXML
    private TextField txf_search_nameofbus;

    @FXML
    private Button btn_search;

    @FXML
    private TextField txf_slot;

    @FXML
    private Button btn_show;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private HBox hbox;

    private static String CRUDType;
    private static int idBus;
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
            // Init combobox for type of bus
            BLL_Admin.getInstance().getListTypeOfBus().forEach(type -> {
               cbx_nameoftype.getItems().add(type.getTypeName());
            });
            //done
            // Init combobox status
                cbx_status.getItems().add("Available");
                cbx_status.getItems().add("Unavailable");
            // done
            // Init tableview
            show(0, "");
            //done

            //Init button
            toggleDetail();
            //done

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(int slot, String name) {
        ObservableList<TableBusPage> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                updateTableBusPage(slot, name));

        col_id.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        col_nameofbus.setCellValueFactory(new PropertyValueFactory<>("busName"));
        col_platenumber.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        col_nameoftype.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        col_brandname.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        col_slots.setCellValueFactory(new PropertyValueFactory<>("slot"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        table_view.setItems(listObj);
        table_view.refresh();
    }

    public void showMainPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/MainWindow.fxml"));
        this.pane.getChildren().setAll(newPane);

    }

    public void showSettingPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("../view/admin_view/Setting.fxml"));
        this.pane.getChildren().setAll(newPane);
    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        CRUDType = "Create";
        toggleDetail();
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {
        try {
            if(txf_slot.getText().trim().equals(""))
                show(0, txf_search_nameofbus.getText().trim());
            else
                show(Integer.parseInt(txf_slot.getText().trim()), txf_search_nameofbus.getText().trim());
        }
        catch(Exception err) {
            new Alert(Alert.AlertType.WARNING, "Check again!").showAndWait();
        }
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {
        try {
            TableBusPage tbl = table_view.getSelectionModel().getSelectedItem();
            idBus = tbl.getIdBus();
            BLL_Admin.getInstance().deleteBus(idBus);
            show(0, "");
        } catch (Exception err) {
            new Alert(Alert.AlertType.INFORMATION, "Choose only 1 row!").showAndWait();
        }
    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {
        String name_of_bus = txf_nameofbus.getText().trim();
        String plate_number = txf_platenumber.getText().trim();
        TypeOfBusEntity type = BLL_Admin.getInstance().
                getTypeOfBusObj(cbx_nameoftype.getSelectionModel().getSelectedIndex() + 1);
        if(name_of_bus.equals("") || plate_number.equals("")) {
            new Alert(Alert.AlertType.WARNING, "Fill all field!").showAndWait();
            return;
        }
        switch(CRUDType) {
            case "Create": {
                BLL_Admin.getInstance().addBus(name_of_bus, plate_number,  type, false, 1);
                show(0, "");
                break;
            }
            case "Update": {
                int stt = cbx_status.getSelectionModel().getSelectedItem().equals("Available") ? 1 : 0;
                BLL_Admin.getInstance().updateBus(idBus, name_of_bus, plate_number, type, stt);
                show(0, "");
                break;
            }
            default:
                break;
        }
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_nameofbus.setText("");
        txf_platenumber.setText("");
    }

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        txf_brandname.setText("");
        txf_slots.setText("");
        txf_nameofbus.setText("");
        txf_platenumber.setText("");
        if(CRUDType.equals("Update")) {
            cbx_status.setVisible(false);
            lbl_status.setVisible(false);
        }
        toggleDetail();
    }

    @FXML
    void btn_show_clicked(MouseEvent event) {
        show(0, "");
    }

    @FXML
    void btn_update_clicked(MouseEvent event) {
        try {
            TableBusPage tbl = table_view.getSelectionModel().getSelectedItem();
            idBus = tbl.getIdBus();
            cbx_nameoftype.getSelectionModel().select(tbl.getTypeName());
            txf_brandname.setText(tbl.getBrandName());
            txf_slots.setText(String.valueOf(tbl.getSlot()));
            txf_nameofbus.setText(tbl.getBusName());
            txf_platenumber.setText(tbl.getPlateNumber());
            cbx_status.getSelectionModel().select(tbl.getStatus() == 1 ? "Available" : "Unavailable");
            cbx_status.setVisible(true);
            lbl_status.setVisible(true);
            CRUDType = "Update";
            toggleDetail();
        } catch (Exception err) {
            new Alert(Alert.AlertType.INFORMATION, "Choose only 1 row!").showAndWait();
        }

    }

    @FXML
    void cbx_nameoftype_Action(ActionEvent event) {
        TypeOfBusEntity tob = BLL_Admin.getInstance().getTypeOfBusObj(cbx_nameoftype.getSelectionModel().
                getSelectedIndex() + 1);
        txf_brandname.setText(tob.getBrandName());
        txf_slots.setText(String.valueOf(tob.getSlot()));
        txf_nameofbus.setEditable(true);
        txf_platenumber.setEditable(true);
    }

    public void toggleDetail(){
        if (btn_ok.isVisible()) {
            btn_ok.setVisible(false);
            btn_reset.setVisible(false);
            btn_cancel.setVisible(false);
            grp_btn_tbl.setVisible(true);
            table_view.setLayoutX(-273);
            table_view.setPrefWidth(1155);
            hbox.setLayoutX(0);
            grp_btn_tbl.setLayoutX(85);
            table_view.toFront();

        }
        else {
            btn_ok.setVisible(true);
            btn_reset.setVisible(true);
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
