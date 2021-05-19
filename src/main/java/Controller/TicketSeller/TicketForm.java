package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.TicketEntity;
import Model.TripInformationEntity;
import Services.BLL_Admin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TicketForm implements Initializable {
    @FXML
    private Pane pane;

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
    private Label lb_phonecustomer;

    @FXML
    private Label lb_name;

    @FXML
    private Label lb_pay;

    @FXML
    private Button btn_cancel;

    // Attributes
    private static TicketEntity modelTicket;
    private static LocalDate date;

    public TicketForm(TicketEntity ticket, LocalDate arg_date) {
        modelTicket = ticket;
        date = arg_date;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // IMPORTANT Init ticket detail
        lb_code.setText(modelTicket.getNameTicket());
        lb_type.setText(modelTicket.getTripInformationByIdTrip().getScheduleByIdSchedule().getBusByIdBus()
                .getTypeOfBusByIdType().getTypeName());
        lb_departdate.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                new SimpleDateFormat("HH:mm:ss").format(modelTicket.getTripInformationByIdTrip()
                        .getScheduleByIdSchedule().getDepartTime()));
        lb_startstation.setText(modelTicket.getTripInformationByIdTrip().getScheduleByIdSchedule()
                .getRouteByIdRoute().getStartStation());
        lb_destination.setText(modelTicket.getTripInformationByIdTrip().getScheduleByIdSchedule()
                .getRouteByIdRoute().getEndStation());
        lb_phone.setText(modelTicket.getTripInformationByIdTrip()
                .getScheduleByIdSchedule().getDriverByIdDriver().getPhone());
        lb_price.setText("0đ");
        lb_pay.setText(modelTicket.getIsPaid() ? "Paid" : "Unpaid");
        lb_name.setText(modelTicket.getNameCustomer());
        lb_phonecustomer.setText(modelTicket.getPhoneNumber());
        // Save price to var

        // Format VNĐ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        lb_price.setText(currencyVN.format(modelTicket.getPrice()));
        //
        // DONE

    }

    @FXML
    void btn_cancel_clicked(MouseEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btn_print_clicked(MouseEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(pane.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, pane);
            if (success) {
                job.endJob();
                Stage stage = (Stage) btn_cancel.getScene().getWindow();
                stage.close();
            }
        }
    }
}
