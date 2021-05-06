package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.ViewModel.FilterRoute_ViewModel;
import Services.BLL_Admin;
import Services.BLL_Seller;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FilterRoute implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private GridPane gridpane;

    @FXML
    private ImageView arrow_right;

    @FXML
    private ImageView arrow_left;

    @FXML
    private DatePicker calendar;

    @FXML
    private JFXHamburger jfx_hambur;


    @FXML
    private ImageView img01;

    @FXML
    private ImageView img02;

    @FXML
    private ImageView img03;

    @FXML
    private ImageView img04;

    @FXML
    private ImageView img05;

    @FXML
    private Label lbl11;

    @FXML
    private Label lbl21;

    @FXML
    private Label lbl31;

    @FXML
    private Label lbl41;

    @FXML
    private Label lbl12;

    @FXML
    private Label lbl22;

    @FXML
    private Label lbl32;

    @FXML
    private Label lbl42;

    @FXML
    private Label lbl13;

    @FXML
    private Label lbl23;

    @FXML
    private Label lbl33;

    @FXML
    private Label lbl43;

    @FXML
    private Label lbl14;

    @FXML
    private Label lbl24;

    @FXML
    private Label lbl34;

    @FXML
    private Label lbl44;

    @FXML
    private Label lbl15;

    @FXML
    private Label lbl25;

    @FXML
    private Label lbl35;

    @FXML
    private Label lbl45;

    // Attributes

    ProvinceEntity startProvince;
    ProvinceEntity endProvince;
    LocalDate date;
    List<FilterRoute_ViewModel> listRoute = new ArrayList<>();

    // Var static
    private static Integer beginIndex = 1;

    public FilterRoute(ProvinceEntity startProvince, ProvinceEntity endProvince, LocalDate date) {
        this.startProvince = startProvince;
        this.endProvince = endProvince;
        this.date = date;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            // Init gridpane
            listRoute.addAll(BLL_Seller.getInstance().setUpFilterRouteView(startProvince, endProvince));
            calendar.setValue(date);
            reloadTable();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initVariable(ProvinceEntity startProvince, ProvinceEntity endProvince, LocalDate date) {
        this.startProvince = startProvince;
        this.endProvince = endProvince;
        this.date = date;
    }

    public void reloadTable() {
        new Alert(Alert.AlertType.WARNING, String.valueOf(listRoute.size())).showAndWait();
        for(int i = beginIndex; i < beginIndex + 5; ++i) {
            if (i > listRoute.size()) break;
            switch (i - beginIndex + 1) {
                case 1: {
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(i - beginIndex).getPicture()));
                    img01.setImage(img);
                    lbl11.setText(listRoute.get(i - beginIndex).getTypeName());
                    lbl21.setText(listRoute.get(i - beginIndex).getStartStation());
                    lbl31.setText(listRoute.get(i - beginIndex).getDestStation());
                    lbl41.setText(listRoute.get(i - beginIndex).getDepartTime().toString() + "\nDuration: " +
                            String.valueOf(listRoute.get(i - beginIndex).getDuration()));
                    break;
                }
                case 2: {
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(i - beginIndex).getPicture()));
                    img02.setImage(img);
                    lbl12.setText(listRoute.get(i - beginIndex).getTypeName());
                    lbl22.setText(listRoute.get(i - beginIndex).getStartStation());
                    lbl32.setText(listRoute.get(i - beginIndex).getDestStation());
                    lbl42.setText(listRoute.get(i - beginIndex).getDepartTime().toString() + "\nDuration: " +
                            String.valueOf(listRoute.get(i - beginIndex).getDuration()));
                    break;
                }
                case 3: {
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(i - beginIndex).getPicture()));
                    img03.setImage(img);
                    lbl13.setText(listRoute.get(i - beginIndex).getTypeName());
                    lbl23.setText(listRoute.get(i - beginIndex).getStartStation());
                    lbl33.setText(listRoute.get(i - beginIndex).getDestStation());
                    lbl43.setText(listRoute.get(i - beginIndex).getDepartTime().toString() + "\nDuration: " +
                            String.valueOf(listRoute.get(i - beginIndex).getDuration()));
                    break;
                }
                case 4: {
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(i - beginIndex).getPicture()));
                    img04.setImage(img);
                    lbl14.setText(listRoute.get(i - beginIndex).getTypeName());
                    lbl24.setText(listRoute.get(i - beginIndex).getStartStation());
                    lbl34.setText(listRoute.get(i - beginIndex).getDestStation());
                    lbl44.setText(listRoute.get(i - beginIndex).getDepartTime().toString() + "\nDuration: " +
                            String.valueOf(listRoute.get(i - beginIndex).getDuration()));
                    break;
                }
                case 5: {
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(i - beginIndex).getPicture()));
                    img05.setImage(img);
                    lbl15.setText(listRoute.get(i - beginIndex).getTypeName());
                    lbl25.setText(listRoute.get(i - beginIndex).getStartStation());
                    lbl35.setText(listRoute.get(i - beginIndex).getDestStation());
                    lbl45.setText(listRoute.get(i - beginIndex).getDepartTime().toString() + "\nDuration: " +
                            String.valueOf(listRoute.get(i - beginIndex).getDuration()));
                    break;
                }

                default:
                    break;
            }


        }
    }

    @FXML
    void btn_arrow_right_clicked(MouseEvent event) {

    }

}
