package Controller.TicketSeller;

import Model.ProvinceEntity;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private ComboBox<ProvinceEntity> cbx_start;

    @FXML
    private Button btn_swap;

    @FXML
    private ComboBox<ProvinceEntity> cbx_dest;

    @FXML
    private DatePicker datetime;

    @FXML
    private Button btn_search;

    // Var static
    private static boolean flag = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/seller_view/NavBar.fxml"));
            jfx_drawer.setSidePane(box);

            for (Node node : box.getChildren()) {
                if (node.lookup(".btn").getAccessibleText() != null) {
                    node.lookup(".btn").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        try {
                            switch (node.lookup(".btn").getId()) {
                                case "dashboard": {
                                    showPage("Dashboard");
                                    break;
                                }
                                case "ticketinfo":{
                                    showPage("TicketInfo");
                                    break;
                                }
                                case "ticketupdate":{
                                    showPage("TicketUpdate");
                                    break;
                                }
                                case "setting":{
                                    showPage("Setting");
                                    break;
                                }
                                default:
                                    break;
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
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

            //Init combobox and datetime
            BLL_Admin.getInstance().getProvinceName().forEach(type -> {
                cbx_start.getItems().add(type);
            });
            BLL_Admin.getInstance().getProvinceName().forEach(type ->{
                cbx_dest.getItems().add(type);
            });

            datetime.setValue(LocalDate.now());
            //done!

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPage(String path) throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/seller_view/" + path +".fxml"));
        this.rootPane.getChildren().setAll(newPane);
    }

    @FXML
    void btn_swap_clicked(MouseEvent event) {
        ProvinceEntity start = cbx_start.getSelectionModel().getSelectedItem();
        ProvinceEntity dest = cbx_dest.getSelectionModel().getSelectedItem();
        cbx_start.getSelectionModel().select(dest);
        cbx_dest.getSelectionModel().select(start);
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {

    }
}
