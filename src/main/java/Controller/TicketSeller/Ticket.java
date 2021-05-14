package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.RouteEntity;
import Model.ViewModel.Ticket_ViewModel;
import Services.BLL_Admin;
import Services.BLL_Seller;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

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
    private TableColumn<Ticket_ViewModel, Integer> price;

    @FXML
    private ComboBox<ProvinceEntity> cbx_from;

    @FXML
    private ComboBox<ProvinceEntity> cbx_to;

    @FXML
    private TextField txf_name;

    @FXML
    private TextField txf_phone;

    @FXML
    private DatePicker datepicker;



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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        ProvinceEntity fromProvince = cbx_from.getSelectionModel().getSelectedItem();
        ProvinceEntity toProvince = cbx_to.getSelectionModel().getSelectedItem();

//        Date date = new Date(datepicker.getValue().toEpochDay());
        Date date = Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String name = txf_name.getText();
        String phone = txf_phone.getText();
        ObservableList<Ticket_ViewModel> listObj = FXCollections.observableArrayList(BLL_Seller.getInstance().getAllTicket(
                fromProvince, toProvince, date, name, phone));

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
    void btn_delete_onClicked(MouseEvent event) {

    }

    @FXML
    void btn_print_onClicked(MouseEvent event) {

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
    void btn_setpaid_onClicked(MouseEvent event) {

    }
}
