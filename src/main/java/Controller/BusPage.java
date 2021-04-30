package Controller;

import Model.BusEntity;
import Model.DataTable.TableBusPage;
import Model.TypeOfBusEntity;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.property.SimpleStringProperty;
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

    private int IdType;
    private boolean flag = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/admin_view/NavBar.fxml"));
            jfx_drawer.setSidePane(box);

            for (Node node : box.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "dashboard": {
                                try {
                                    showMainPage();
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
            // Init tableview
            show(0, "");

            //
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

        table_view.setItems(listObj);
        table_view.refresh();
    }

    public void showMainPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/MainWindow.fxml"));
        this.pane.getChildren().setAll(newPane);

    }

    @FXML
    void btn_create_clicked(MouseEvent event) {
        String name_of_bus = txf_nameofbus.getText().trim();
        String plate_number = txf_platenumber.getText().trim();
        if(name_of_bus.equals("") || plate_number.equals("")) {
            new Alert(Alert.AlertType.WARNING, "Fill all field!").showAndWait();
            return;
        }

        BLL_Admin.getInstance().addBus(name_of_bus, plate_number,  BLL_Admin.getInstance().
                getTypeOfBusObj(cbx_nameoftype.getSelectionModel().getSelectedIndex() + 1), false, 1);

    }

    @FXML
    void btn_search_clicked(MouseEvent event) {
        try {
            show(Integer.parseInt(txf_slot.getText().trim()), txf_search_nameofbus.getText().trim());
        }
        catch(Exception err) {
            new Alert(Alert.AlertType.WARNING, "Check again!").showAndWait();
        }
    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {

    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {

    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_nameofbus.setText("");
        txf_platenumber.setText("");
    }

    @FXML
    void btn_cancel_clicked(MouseEvent event) {

    }

    @FXML
    void btn_show_clicked(MouseEvent event) {
        show(0, "");
    }

    @FXML
    void btn_update_clicked(MouseEvent event) {

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

}
