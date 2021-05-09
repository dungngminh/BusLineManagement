package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.TicketEntity;
import Model.TripInformationEntity;
import Model.ViewModel.FilterRoute_ViewModel;
import Services.BLL_Seller;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.commons.codec.language.bm.Rule;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private TextField txf_namecustomer;

    @FXML
    private TextField txf_phonecustomer;

    @FXML
    private Button btn_confirm;

    @FXML
    private Button btn_cancel;

    // Attributes

    private static TripInformationEntity modelTrip;
    private static TicketEntity currentTicket;
    private static LocalDate date;
    private static ProvinceEntity startProvince;
    private static ProvinceEntity endProvince;
    private static Integer price;


    Pane pane;

    public TicketOrder(TripInformationEntity trip, LocalDate arg_date, ProvinceEntity arg_startProvince,
                       ProvinceEntity arg_endProvince) {
        modelTrip = trip;
        date = arg_date;
        startProvince = arg_startProvince;
        endProvince = arg_endProvince;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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

            // IMPORTANT Init ticket detail
            lb_code.setText("Choose your slots!");
            lb_type.setText(modelTrip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getTypeName());
            lb_departdate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                    new SimpleDateFormat("HH:mm:ss").format(modelTrip.getScheduleByIdSchedule().getDepartTime()));
            lb_startstation.setText(modelTrip.getScheduleByIdSchedule().getRouteByIdRoute().getStartStation());
            lb_destination.setText(modelTrip.getScheduleByIdSchedule().getRouteByIdRoute().getEndStation());
            lb_phone.setText(modelTrip.getDriverByIdDriver().getPhone());
            lb_price.setText("0đ");
            cbx_payment.getItems().add("Paid");
            cbx_payment.getItems().add("Unpaid");
            cbx_payment.getSelectionModel().selectFirst();
            // Save price to var
            price = modelTrip.getScheduleByIdSchedule().getPrice();
            // DONE

            // IMPORTANT InitTicketSlot
            currentTicket = BLL_Seller.getInstance().pendingTicketOrderToTicket(modelTrip);
            refreshTicketForSLots();

            // DONE

            // IMPORTANT Event for slot
            String floor1 = "9162935";
            if(floor1.contains(String.valueOf(modelTrip.getScheduleByIdSchedule().getBusByIdBus().
                    getTypeOfBusByIdType().getSlot()))) {
                eventHandle_OneFloor();
            }

            else eventHandle_TwoFloors();


            // DONE

            pane2.hoverProperty().addListener((event)->refreshTicketForSLots());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // NOTICE event for cancel and confirm ticket
    @FXML
    void btn_cancel_clicked(MouseEvent event) throws IOException {
        BLL_Seller.getInstance().deleteCurrentTicket(currentTicket.getIdTicket());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/FilterRoute.fxml"));

        FilterRoute controller = new FilterRoute(startProvince, endProvince, date);
        loader.setController(controller);

        AnchorPane newPane = loader.load();
        this.rootPane.getChildren().setAll(newPane);
    }

    @FXML
    void btn_confirm_clicked(MouseEvent event) throws IOException {
        try {
            if(txf_namecustomer.getText().equals("") || txf_phonecustomer.getText().equals("")) {
                new Alert(Alert.AlertType.WARNING, "Fill all field!").showAndWait();
            } else if(!Pattern.matches("((\\+84)|09|03|07|08|05|[2|5|7|8|9])+([0-9]{8})\\b", txf_phonecustomer.getText())) {
                new Alert(Alert.AlertType.WARNING, "Phone customer is incorrect!").showAndWait();
            } else if(lb_code.getText().equals("") || lb_code.getText().equals("Choose your slots!")) {
                new Alert(Alert.AlertType.WARNING, "You did not order any slot!").showAndWait();
            } else {
                ButtonType acc = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
                ButtonType den = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Are you sure you want to confirm your booking?",
                        acc, den);

                alert.setTitle("Confirm your booking!");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(den) == acc) {
                    BLL_Seller.getInstance().updateCurrentTicket(currentTicket, lb_code.getText() ,txf_namecustomer.getText(),
                            txf_phonecustomer.getText(), 1,
                            Integer.parseInt(lb_price.getText().substring(0, lb_price.getText().length() - 1)),
                            cbx_payment.getSelectionModel().getSelectedItem().equals("Paid"));

                    AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/seller_view/Dashboard.fxml"));
                    rootPane.getChildren().setAll(newPane);
                }
            }
        } catch (Exception err) {
            new Alert(Alert.AlertType.WARNING, "Unknown Error").showAndWait();
        }

    }
    // DONE

    // IMPORTANT add component to pane2
    public void showFigureOfBusType(String path) throws IOException {
        this.pane = FXMLLoader.load(getClass().getResource("/view/bus_type/" + path + ".fxml"));
        pane2.getChildren().setAll(this.pane);
    }

    // NOTICE Handle for 2 floors event here
    public void eventHandle_OneFloor() {
        for (Node node : this.pane.lookupAll(".slot")) {
            node.lookup(".slot").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) ->{
                toggleEventHandle(node.lookup(".slot"));
            });
        }
    }

    public void eventHandle_TwoFloors() {
        TabPane newTabPane = (TabPane)this.pane.lookup(".tabpane");
        Pane pane1 = (Pane)newTabPane.lookup(".floor1");
        Pane pane2 = (Pane)newTabPane.lookup(".floor2");

        for (Node node : pane1.lookupAll(".slot")) {
            node.lookup(".slot").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) ->{
                toggleEventHandle(node.lookup(".slot"));
            });
        }

        for (Node node : pane2.lookupAll(".slot")) {
            node.lookup(".slot").addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (e) ->{
                toggleEventHandle(node.lookup(".slot"));
            });
        }
    }
    // DONE

    // IMPORTANT refresh, update for current status of Slots
    public void refreshTicketForSLots() {
            List<String> slotOrdered = BLL_Seller.getInstance().getOrderedTicket(modelTrip.getIdTrip());

            List<String> slotPending = BLL_Seller.getInstance().getPendingTicket(modelTrip.getIdTrip(), currentTicket.getIdTicket());

            String floor1 = "9162935";
            if(floor1.contains(String.valueOf(modelTrip.getScheduleByIdSchedule().getBusByIdBus().
                    getTypeOfBusByIdType().getSlot()))) {
                for (Node node : this.pane.lookupAll(".slot")) {

                    // If this slot was ordered, disable it
                    if(!slotOrdered.isEmpty() && slotOrdered.contains(node.lookup(".slot").getId())) {
                        node.lookup(".slot").setStyle("-fx-background-color: #8C271E;");
                        node.lookup(".slot").setDisable(true);
                        continue;
                    }

                    // If this slot is pending, disable it
                    if(!slotPending.isEmpty() && slotPending.contains(node.lookup(".slot").getId())) {
                        node.lookup(".slot").setStyle("-fx-background-color: #F1D302;");
                        node.lookup(".slot").setDisable(true);
                        continue;
                    }

                    // update remaining slot
                    if(!node.lookup(".slot").getStyle().equals("-fx-background-color: #4cc9f0;"))
                        node.lookup(".slot").setStyle("");
                    node.lookup(".slot").setDisable(false);
                }

            }

            // Below is the same
            else {
                TabPane newTabPane = (TabPane)this.pane.lookup(".tabpane");
                Pane pane1 = (Pane)newTabPane.lookup(".floor1");
                Pane pane2 = (Pane)newTabPane.lookup(".floor2");

                for (Node node : pane1.lookupAll(".slot")) {
                    if(!slotOrdered.isEmpty() && slotOrdered.contains(node.lookup(".slot").getId())) {
                        node.lookup(".slot").setStyle("-fx-background-color: #8C271E;");
                        node.lookup(".slot").setDisable(true);
                        continue;
                    }

                    if(!slotPending.isEmpty() && slotPending.contains(node.lookup(".slot").getId())) {
                        node.lookup(".slot").setStyle("-fx-background-color: #F1D302;");
                        node.lookup(".slot").setDisable(true);
                        continue;
                    }

                    if(!node.lookup(".slot").getStyle().equals("-fx-background-color: #4cc9f0;"))
                        node.lookup(".slot").setStyle("");
                    node.lookup(".slot").setDisable(false);
                }

                for (Node node : pane2.lookupAll(".slot")) {
                    if(!slotOrdered.isEmpty() && slotOrdered.contains(node.lookup(".slot").getId())) {
                        node.lookup(".slot").setStyle("-fx-background-color: #8C271E;");
                        node.lookup(".slot").setDisable(true);
                        continue;
                    }

                    if(!slotPending.isEmpty() && slotPending.contains(node.lookup(".slot").getId())) {
                        node.lookup(".slot").setStyle("-fx-background-color: #F1D302;");
                        node.lookup(".slot").setDisable(true);
                        continue;
                    }

                    if(!node.lookup(".slot").getStyle().equals("-fx-background-color: #4cc9f0;"))
                        node.lookup(".slot").setStyle("");
                    node.lookup(".slot").setDisable(false);

                }
            }

    }

    // IMPORTANT handle event in order to select and unselected slots
    public void toggleEventHandle(Node node) {

        // In case this slot is unselected
        if(node.getStyle().equals("")) {
            refreshTicketForSLots();
            if(!node.getStyle().equals("-fx-background-color: #F1D302;")) {
                // After refresh slot
                node.setStyle("-fx-background-color: #4cc9f0;");

                if(lb_code.getText().equals("Choose your slots!"))
                    lb_code.setText("");
                // Set text for label code ticket
                String name = lb_code.getText() + "-" + node.getId();
                if(name.charAt(0) == '-')
                    name = name.substring(1);
                lb_code.setText(name);

                String val_price = lb_price.getText().substring(0, lb_price.getText().length() - 1);
                lb_price.setText((Integer.parseInt(val_price) + price) + "đ");
                //
                // Send Sync to DB
                BLL_Seller.getInstance().updateCurrentTicket(currentTicket, lb_code.getText() ,txf_namecustomer.getText(),
                        txf_phonecustomer.getText(), 0,
                        Integer.parseInt(lb_price.getText().substring(0, lb_price.getText().length() - 1)),
                        cbx_payment.getSelectionModel().getSelectedItem().equals("Paid"));
                //
            }

        }

        // In case this slot is selected
        else if(node.getStyle().equals("-fx-background-color: #4cc9f0;")){
            // After refresh slot
            node.setStyle("");
            // Set text for label code ticket
            List<String> listSlot = new ArrayList<>(Arrays.asList(lb_code.getText().split("-")));
            listSlot.remove(node.getId());

            lb_code.setText(String.join("-", listSlot));
            lb_price.setText(price * listSlot.size() + "đ");
            ///
            // Send sync to DB
            BLL_Seller.getInstance().updateCurrentTicket(currentTicket, lb_code.getText() ,txf_namecustomer.getText(),
                    txf_phonecustomer.getText(), 0,
                    Integer.parseInt(lb_price.getText().substring(0, lb_price.getText().length() - 1)),
                    cbx_payment.getSelectionModel().getSelectedItem().equals("Paid"));
            ///

        }
    }

}
