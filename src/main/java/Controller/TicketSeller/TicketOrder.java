package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.TripInformationEntity;
import Model.ViewModel.FilterRoute_ViewModel;
import Services.BLL_Seller;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TicketOrder implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private Pane pane2;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private Label lb_code;

    @FXML
    private Label lb_destination;

    @FXML
    private Label lb_startstation;

    @FXML
    private Label lb_departdate;

    @FXML
    private Label lb_phone;

    @FXML
    private Label lb_type;

    @FXML
    private Label lb_price;

    @FXML
    private ComboBox<String> cbx_payment;

    @FXML
    private Button btn_submit;

    @FXML
    private Button btn_cancel;

    // Attributes

    private TripInformationEntity modelTrip;
    private LocalDate date;

    Pane pane;

    public TicketOrder(TripInformationEntity modelTrip, LocalDate date) {
        this.modelTrip = modelTrip;
        this.date = date;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            // Init Figure Of Bus Type
            switch(modelTrip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getIdType()) {
                case 1: {
                    showFigureOfBusType("16_Slots");
                    break;
                }
                case 2: {
                    showFigureOfBusType("29_Slots");
                    break;
                }
                case 3: {
                    showFigureOfBusType("35_Slots");
                    break;
                }
                case 4: {
                    showFigureOfBusType("9_Slots");
                    break;
                }
                case 5: {
                    showFigureOfBusType("40_Slots");
                    break;
                }
                case 6: {
                    showFigureOfBusType("34_Slots_WC");
                    break;
                }
                case 7: {
                    showFigureOfBusType("22_Slots");
                    break;
                }
                case 8: {
                    showFigureOfBusType("34_Slots");
                    break;
                }
                default:
                    break;
            }
            // done

            // Init ticket detail
            lb_code.setText("Choose your slots!");
            lb_type.setText(modelTrip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getTypeName());
            lb_departdate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            lb_startstation.setText(modelTrip.getScheduleByIdSchedule().getRouteByIdRoute().getStartStation());
            lb_destination.setText(modelTrip.getScheduleByIdSchedule().getRouteByIdRoute().getEndStation());
            lb_phone.setText(modelTrip.getDriverByIdDriver().getPhone());
            cbx_payment.getItems().add("Paid");
            cbx_payment.getItems().add("Unpaid");
            cbx_payment.getSelectionModel().selectFirst();
            lb_price.setText("0đ");
            //done!

            // Event for slot
            String floor1 = "9162935";
            if(floor1.contains(String.valueOf(modelTrip.getScheduleByIdSchedule().getBusByIdBus().
                    getTypeOfBusByIdType().getSlot())))
                eventHandle_OneFloor();
            else
                eventHandle_TwoFloors();

            // done


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFigureOfBusType(String path) throws IOException {
        this.pane = FXMLLoader.load(getClass().getResource("/view/bus_type/" + path + ".fxml"));
        pane2.getChildren().setAll(this.pane);
    }

    public void eventHandle_OneFloor() {
        for (Node node : this.pane.lookupAll(".slot")) {
            node.lookup(".slot").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) ->{
                System.out.println(node.lookup(".slot").getId());
            });
        }
    }

    public void eventHandle_TwoFloors() {
        TabPane newTabPane = (TabPane)this.pane.lookup(".tabpane");
        Pane pane1 = (Pane)newTabPane.lookup(".floor1");
        Pane pane2 = (Pane)newTabPane.lookup(".floor2");

        for (Node node : pane1.lookupAll(".slot")) {
            node.lookup(".slot").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) ->{
                System.out.println(node.lookup(".slot").getId());
            });
        }

        for (Node node : pane2.lookupAll(".slot")) {
            node.lookup(".slot").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) ->{
                System.out.println(node.lookup(".slot").getId());
            });
        }
    }

}
