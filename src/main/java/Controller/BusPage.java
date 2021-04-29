package Controller;

import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    private Button btn_reset;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private TableView<?> table_view;

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
                if (jfx_drawer.isShowing()) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainPage() throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/admin_view/MainWindow.fxml"));
        this.pane.getChildren().setAll(newPane);

    }

    @FXML
    void btn_create_clicked(MouseEvent event) {

    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {

    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_nameofbus.setText("");
        txf_platenumber.setText("");
    }

    @FXML
    void btn_show_clicked(MouseEvent event) {

    }

    @FXML
    void btn_update_clicked(MouseEvent event) {

    }

    @FXML
    void cbx_nameoftype_Action(ActionEvent event) {
        List<String> hlp = BLL_Admin.getInstance().getBrandSlotFromTypeName(cbx_nameoftype.getValue());
//        System.out.println(cbx_nameoftype.getValue());
        txf_brandname.setText(hlp.get(0));
        txf_slots.setText(hlp.get(1));
    }
}
