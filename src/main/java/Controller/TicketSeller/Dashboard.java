package Controller.TicketSeller;

import Model.ProvinceEntity;
import Services.BLL_Admin;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            //Init combobox and datetime
            cbx_start.getItems().addAll(BLL_Admin.getInstance().getProvinceName());
            cbx_dest.getItems().addAll(BLL_Admin.getInstance().getProvinceName());
            cbx_start.getSelectionModel().selectFirst();
            cbx_dest.getSelectionModel().selectFirst();
//            BLL_Admin.getInstance().getProvinceName().forEach(type ->{
//                cbx_dest.getItems().add(type);
//            });

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
    void btn_search_clicked(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/FilterRoute.fxml"));


            FilterRoute controller = new FilterRoute(cbx_start.getSelectionModel().getSelectedItem(),
                    cbx_dest.getSelectionModel().getSelectedItem(), datetime.getValue());
            loader.setController(controller);

            AnchorPane newPane = loader.load();

            newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
            Scene scene = rootPane.getScene();
            scene.setRoot(newPane);
        }catch(Exception e){
            new Alert(Alert.AlertType.WARNING, "Error occurred, Check again!").showAndWait();
            System.out.println("Error occurred, Check again!");
            e.printStackTrace();
        }
    }

    @FXML
    void on_btnNotification_Clicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/Notification.fxml"));

            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Recent notification");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );

            stage.show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

}
