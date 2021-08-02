package Controller.Admin;

import Model.AccountEntity;
import Model.NotificationEntity;
import Model.ViewModel.BusEntity_ViewModel;
import Model.ViewModel.Notification_ViewModel;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Notification implements Initializable {
    @FXML
    private Tab tab_adduser;

    @FXML
    private GridPane grid_add;

    @FXML
    private Button btn_push;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextArea txt_area_add;

    @FXML
    private TableView<Notification_ViewModel> tableview;

    @FXML
    private TableColumn<Notification_ViewModel, Integer> ID_col;

    @FXML
    private TableColumn<Notification_ViewModel, String> mess_col;

    @FXML
    private TableColumn<Notification_ViewModel, String> time_col;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        show();
    }

    public void show() {
        ObservableList<Notification_ViewModel> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                getAllNotification());

        ID_col.setCellValueFactory(new PropertyValueFactory<>("idNotify"));
        mess_col.setCellValueFactory(new PropertyValueFactory<>("notifyContent"));
        time_col.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableview.setItems(listObj);
        tableview.refresh();
    }

    public void btn_push_clicked(MouseEvent mouseEvent) {
        try {
            String text = txt_area_add.getText();

            BLL_Admin.getInstance().pushMessageIntoDB(text);
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        }
    }

    public void btn_reset_onClicked(MouseEvent mouseEvent) {
        txt_area_add.setText("");
    }

    public void btn_cancel_onClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btn_show_edit_Click(MouseEvent event) {

    }

    @FXML
    void btn_update_edit_Click(MouseEvent event) {

    }

    @FXML
    void btn_delete_edit_Click(MouseEvent event) {

    }
}
