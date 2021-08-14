package Controller.Admin;

import Model.AccountEntity;
import Model.NotificationEntity;
import Model.ViewModel.BusEntity_ViewModel;
import Model.ViewModel.Notification_ViewModel;
import Services.BLL_Admin;
import Services.DAL;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class Notification implements Initializable {

    @FXML
    private Button btn_cancel;

    @FXML
    private TextArea txt_area_add;

    @FXML
    private TableView<Notification_ViewModel> tableview;

    @FXML
    private TableColumn<Notification_ViewModel, String> user_col;

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

        user_col.setCellValueFactory(new PropertyValueFactory<>("nameUser"));
        mess_col.setCellValueFactory(new PropertyValueFactory<>("notifyContent"));
        time_col.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableview.setItems(listObj);
        tableview.refresh();
    }

    public void btn_push_clicked(MouseEvent mouseEvent) {
        try {
            if(txt_area_add.getText().equals("")){
                new Alert(Alert.AlertType.WARNING, "Please provide a message!").showAndWait();
            }else {
                String text = txt_area_add.getText();
                BLL_Admin.getInstance().pushMessageIntoDB(text);
                new Alert(Alert.AlertType.INFORMATION, "Pushed message success!").showAndWait();
                txt_area_add.setText("");
                show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Cannot push this message! (number of words < 500)").showAndWait();
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
        try {
            String text = tableview.getSelectionModel().getSelectedItem().getNotifyContent();
            String time = tableview.getSelectionModel().getSelectedItem().getTime();
            new Alert(Alert.AlertType.INFORMATION, text + "\n" + time).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Select only one row!").showAndWait();
        }
    }

    @FXML
    void btn_update_edit_Click(MouseEvent event) {
        try {
            if(!tableview.getSelectionModel().getSelectedItem().getNameUser()
                    .equals(DAL.getInstance().getCurrent().getUsername())) {
                new Alert(Alert.AlertType.WARNING, "You don't have permission to delete this notification!").showAndWait();
                return;
            }

            Integer id = tableview.getSelectionModel().getSelectedItem().getIdNotify();
            String text = tableview.getSelectionModel().getSelectedItem().getNotifyContent();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update notification");
            alert.setHeaderText("Update your message");
            // Create expandable Exception.

            Label label = new Label("The content was:");

            TextArea textArea = new TextArea(text);
            textArea.setEditable(true);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // check user delete


            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setContent(expContent);

            ButtonType buttonConfirm = new ButtonType("Confirm");
            ButtonType buttonCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonConfirm){
                BLL_Admin.getInstance().updateNotification(id, textArea.getText());
                show();
                new Alert(Alert.AlertType.INFORMATION, "Updated success!").showAndWait();
                alert.close();
            } else {
                alert.close();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Something went wrong, cannot update!").showAndWait();
        }

    }

    @FXML
    void btn_delete_edit_Click(MouseEvent event) {
        try {
            if(!tableview.getSelectionModel().getSelectedItem().getNameUser()
                    .equals(DAL.getInstance().getCurrent().getUsername())) {
                new Alert(Alert.AlertType.WARNING, "You don't have permission to delete this notification!").showAndWait();
                return;
            }

            Integer id = tableview.getSelectionModel().getSelectedItem().getIdNotify();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation delete");
            alert.setHeaderText("delete your message");
            alert.setContentText("Are you ok with this?");

            // check user delete


            ButtonType buttonConfirm = new ButtonType("Confirm");
            ButtonType buttonCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonConfirm){
                BLL_Admin.getInstance().deleteNotification(id);
                show();
                new Alert(Alert.AlertType.INFORMATION, "deleted success!").showAndWait();
                alert.close();
            } else {
                alert.close();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, "Something went wrong, cannot delete!").showAndWait();
        }
    }
}
