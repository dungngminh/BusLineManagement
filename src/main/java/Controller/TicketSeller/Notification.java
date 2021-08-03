package Controller.TicketSeller;

import Model.ViewModel.Notification_ViewModel;
import Services.BLL_Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Notification implements Initializable {

    @FXML
    private TableView<Notification_ViewModel> tableview;

    @FXML
    private TableColumn<Notification_ViewModel, String> mess_col;

    @FXML
    private TableColumn<Notification_ViewModel, String> time_col;

    @FXML
    private Button btn_cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
    }

    public void show() {
        ObservableList<Notification_ViewModel> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                getAllNotification());

        mess_col.setCellValueFactory(new PropertyValueFactory<>("notifyContent"));
        time_col.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableview.setItems(listObj);
        tableview.refresh();
    }

    public void btn_show_Click(MouseEvent mouseEvent) {
        try {
            String text = tableview.getSelectionModel().getSelectedItem().getNotifyContent();
            String time = tableview.getSelectionModel().getSelectedItem().getTime();
            new Alert(Alert.AlertType.INFORMATION, text + "\n" + time).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Select only one row!").showAndWait();
        }
    }

    public void btn_cancel_Click(MouseEvent mouseEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
}
