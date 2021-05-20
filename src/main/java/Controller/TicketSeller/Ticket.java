package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.RouteEntity;
import Model.StationEntity;
import Model.ViewModel.Ticket_ViewModel;
import Services.BLL_Admin;
import Services.BLL_Seller;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Ticket implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private TableView<Ticket_ViewModel> tableview;

    @FXML
    private TableColumn<Ticket_ViewModel, String> nameTicket;

    @FXML
    private TableColumn<Ticket_ViewModel, String> route;

    @FXML
    private TableColumn<Ticket_ViewModel, String> departTime;

    @FXML
    private TableColumn<Ticket_ViewModel, String> nameCustomer;

    @FXML
    private TableColumn<Ticket_ViewModel, String> phoneCustomer;

    @FXML
    private TableColumn<Ticket_ViewModel, String> paid;

    @FXML
    private TableColumn<Ticket_ViewModel, String> price;

    @FXML
    private ComboBox<ProvinceEntity> cbx_from;

    @FXML
    private ComboBox<ProvinceEntity> cbx_to;

    @FXML
    private ComboBox<String> cbx_pay;

    @FXML
    private TextField txf_name;

    @FXML
    private TextField txf_phone;

    @FXML
    private DatePicker datepicker;

    @FXML
    private JFXToggleButton btn_toggle;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.pane, this.jfx_drawer, this.jfx_hambur);
            //done

            BLL_Admin.getInstance().getProvinceName().forEach(type -> {
                cbx_from.getItems().add(type);
            });
            BLL_Admin.getInstance().getProvinceName().forEach(type -> {
                cbx_to.getItems().add(type);
            });

            cbx_from.getSelectionModel().selectFirst();
            cbx_to.getSelectionModel().selectFirst();

            datepicker.setValue(LocalDate.now());
//            tableview.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

            cbx_pay.getItems().addAll("All", "Paid", "Unpaid");
            cbx_pay.getSelectionModel().select("All");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        String name = txf_name.getText();
        String phone = txf_phone.getText();
        ObservableList<Ticket_ViewModel> listObj = FXCollections.observableArrayList(BLL_Seller.getInstance().getAllTicket(
                null, null, null, null, name, phone));

        if(!cbx_from.isDisable()) {
            ProvinceEntity fromProvince = cbx_from.getSelectionModel().getSelectedItem();
            ProvinceEntity toProvince = cbx_to.getSelectionModel().getSelectedItem();

    //        Date date = new Date(datepicker.getValue().toEpochDay());
            Date date = Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String pay = cbx_pay.getSelectionModel().getSelectedItem();
            listObj = FXCollections.observableArrayList(BLL_Seller.getInstance().getAllTicket(
                    fromProvince, toProvince, date, pay, name, phone));
        }

        nameTicket.setCellValueFactory(new PropertyValueFactory<>("nameTicket"));
        route.setCellValueFactory(new PropertyValueFactory<>("route"));
        departTime.setCellValueFactory(new PropertyValueFactory<>("departTime"));
        nameCustomer.setCellValueFactory(new PropertyValueFactory<>("nameCustomer"));
        phoneCustomer.setCellValueFactory(new PropertyValueFactory<>("phoneCustomer"));
        paid.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableview.setItems(listObj);
        tableview.refresh();
    }


    @FXML
    void btn_print_onClicked(MouseEvent event) throws IOException {
        try {
            Ticket_ViewModel ticket = tableview.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/TicketForm.fxml"));

            TicketForm controller = new TicketForm(BLL_Seller.getInstance().getOneTicket(ticket.getIdTicket()), datepicker.getValue());
            loader.setController(controller);

            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Print ticket");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );

            stage.show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Choose 1 row").showAndWait();
        }
    }

    @FXML
    void btn_setpaid_onClicked(MouseEvent event) {
        try {
            Ticket_ViewModel ticket = tableview.getSelectionModel().getSelectedItem();
            if(ticket.getIsPaid().equals("Unpaid")) {
                ButtonType acc = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType den = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "The customer paid for this ticket, right?",
                        acc, den);

                alert.setTitle("Confirm customer's payment!");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(den) == acc) {
                    BLL_Seller.getInstance().setPaidTicket(ticket.getIdTicket());
                    new Alert(Alert.AlertType.INFORMATION, "Customer paid this ticket, thank you!").showAndWait();
                    show();
                }
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Choose 1 row").showAndWait();
        }
    }

    @FXML
    void btn_delete_onClicked(MouseEvent event) {
        try {
            Ticket_ViewModel ticket = tableview.getSelectionModel().getSelectedItem();
            ButtonType acc = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType den = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Are you sure you want to delete this ticket permanently from the system ?\n" +
                            "This task cannot restore!",
                    acc, den);

            alert.setTitle("Warning your delete!");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(den) == acc) {
                BLL_Seller.getInstance().deleteCurrentTicket(ticket.getIdTicket());
                new Alert(Alert.AlertType.INFORMATION, "This ticket has been completely deleted from the system!").showAndWait();
                show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Choose 1 row").showAndWait();
        }
    }

    @FXML
    void btn_search_clicked(MouseEvent event) {
        try {
            show();
        } catch (Exception err) {
            new Alert(Alert.AlertType.ERROR, "Unknown error!").showAndWait();
        }
        show();
    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {
        txf_name.setText("");
        txf_phone.setText("");
        cbx_pay.getSelectionModel().select("All");
    }

    @FXML
    void btn_toggle_Clicked(ActionEvent event) {
        if(btn_toggle.isSelected()) {
            cbx_from.setDisable(true);
            cbx_to.setDisable(true);
            datepicker.setDisable(true);
            cbx_pay.setDisable(true);
        }
        else {
            cbx_from.setDisable(false);
            cbx_to.setDisable(false);
            datepicker.setDisable(false);
            cbx_pay.setDisable(false);
        }
    }
}
