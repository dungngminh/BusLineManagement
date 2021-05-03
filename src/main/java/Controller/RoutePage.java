package Controller;

import Model.DataTable.TableRoutePage;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RoutePage {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private ComboBox<?> cbx_provinceStart;

    @FXML
    private ComboBox<?> cbx_startstation;

    @FXML
    private Button btn_ok;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_cancel;

    @FXML
    private ComboBox<String> cbx_provinceEnd;

    @FXML
    private ComboBox<String> cbx_endstation;

    @FXML
    private TableView<TableRoutePage> table_view;

    @FXML
    private TableColumn<?, ?> col_idStation;

    @FXML
    private TableColumn<?, ?> col_startstation;

    @FXML
    private TableColumn<?, ?> col_endstation;

    @FXML
    private TableColumn<?, ?> col_distance;

    @FXML
    private TableColumn<?, ?> col_status;

    @FXML
    private TableColumn<?, ?> col_note;

    @FXML
    private ButtonBar grp_btn_tbl;

    @FXML
    private Button btn_show;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private HBox hbox;

    @FXML
    private TextField txf_search_nameofRoute;

    @FXML
    private Button btn_search;

    @FXML
    void btn_cancel_clicked(MouseEvent event) {

    }

    @FXML
    void btn_create_clicked(MouseEvent event) {

    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {

    }

    @FXML
    void btn_ok_clicked(MouseEvent event) {

    }

    @FXML
    void btn_reset_clicked(MouseEvent event) {

    }

    @FXML
    void btn_search_clicked(MouseEvent event) {

    }

    @FXML
    void btn_show_clicked(MouseEvent event) {

    }

    @FXML
    void btn_update_clicked(MouseEvent event) {

    }

    @FXML
    void cbx_StationEnd_Action(ActionEvent event) {

    }

    @FXML
    void cbx_provinceAction(ActionEvent event) {

    }

    @FXML
    void cbx_stationStart_Action(ActionEvent event) {

    }

}

