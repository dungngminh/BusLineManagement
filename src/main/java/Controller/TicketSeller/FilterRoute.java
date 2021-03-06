package Controller.TicketSeller;

import Model.ProvinceEntity;
import Model.ViewModel.FilterRoute_ViewModel;
import Services.BLL_Admin;
import Services.BLL_Seller;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    @FXML
    private Button btn1;

    @FXML
    private Button btn4;

    @FXML
    private Button btn3;

    @FXML
    private Button btn2;

    @FXML
    private Button btn5;

    @FXML
    private Label lbIndex;

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
            calendar.setValue(date);
            beginIndex = 1;
            listRoute.addAll(BLL_Seller.getInstance().setUpFilterRouteView(startProvince, endProvince, java.sql.Date.valueOf(calendar.getValue())));
            lbIndex.setText("1");
            reloadTable();
            //
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("init false");
        }
    }

    @FXML
    void btn1_clicked(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/TicketOrder.fxml"));


            TicketOrder controller = new TicketOrder(listRoute.get(beginIndex - 1).getModelTrip(), date, startProvince, endProvince);

            loader.setController(controller);
            AnchorPane newPane = loader.load();
            newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
            Scene scene = rootPane.getScene();
            scene.setRoot(newPane);
        }catch (Exception e){
            System.out.println("Error occurred, Please check again!");
            new Alert(Alert.AlertType.WARNING, "Error occurred, Please check again!").showAndWait();
        }
    }

    @FXML
    void btn2_clicked(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/TicketOrder.fxml"));


            TicketOrder controller = new TicketOrder(listRoute.get(beginIndex).getModelTrip(), date, startProvince, endProvince);

            loader.setController(controller);
            AnchorPane newPane = loader.load();
            newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
            Scene scene= rootPane.getScene();
            scene.setRoot(newPane);
        }catch (Exception e){
            System.out.println("Error occurred, Please check again!");
            new Alert(Alert.AlertType.WARNING, "Error occurred, Please check again!").showAndWait();
        }
    }


    @FXML
    void btn3_clicked(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/TicketOrder.fxml"));


            TicketOrder controller = new TicketOrder(listRoute.get(beginIndex + 1).getModelTrip(), date, startProvince, endProvince);

            loader.setController(controller);
            AnchorPane newPane = loader.load();
            newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
            Scene scene= rootPane.getScene();
            scene.setRoot(newPane);
        }catch (Exception e){
            System.out.println("Error occurred, Please check again!");
            new Alert(Alert.AlertType.WARNING, "Error occurred, Please check again!").showAndWait();
        }
    }

    @FXML
    void btn4_clicked(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/TicketOrder.fxml"));


            TicketOrder controller = new TicketOrder(listRoute.get(beginIndex + 2).getModelTrip(), date, startProvince, endProvince);

            loader.setController(controller);
            AnchorPane newPane = loader.load();
            newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
            Scene scene= rootPane.getScene();
            scene.setRoot(newPane);
        }catch (Exception e){
            System.out.println("Error occurred, Please check again!");
            new Alert(Alert.AlertType.WARNING, "Error occurred, Please check again!").showAndWait();
        }
    }

    @FXML
    void btn5_clicked(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/seller_view/TicketOrder.fxml"));


            TicketOrder controller = new TicketOrder(listRoute.get(beginIndex + 3).getModelTrip(), date, startProvince, endProvince);

            loader.setController(controller);
            AnchorPane newPane = loader.load();
            newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
            Scene scene= rootPane.getScene();
            scene.setRoot(newPane);
        }catch (Exception e){
            System.out.println("Error occurred, Please check again!");
            new Alert(Alert.AlertType.WARNING, "Error occurred, Please check again!").showAndWait();
        }
    }

    @FXML
    void btn_arrow_left_clicked(MouseEvent event) {
        if(beginIndex - 5 > 0) {
            beginIndex -= 5;
            lbIndex.setText(String.valueOf(Integer.parseInt(lbIndex.getText()) - 1));
            setGridpaneUnvisible();
            reloadTable();
        }
        else {
            new Alert(Alert.AlertType.WARNING, "Out of data!").showAndWait();
        }
    }

    @FXML
    void btn_arrow_right_clicked(MouseEvent event) {
        if(beginIndex + 5 <= listRoute.size()) {
            beginIndex += 5;
            lbIndex.setText(String.valueOf(Integer.parseInt(lbIndex.getText()) + 1));
            setGridpaneUnvisible();
            reloadTable();
        }
        else {
            new Alert(Alert.AlertType.WARNING, "Out of data!").showAndWait();
        }
    }

    @FXML
    void btnBack_Click(MouseEvent event) throws IOException {
        AnchorPane newPane = FXMLLoader.load(getClass().getResource("/view/seller_view/Dashboard.fxml"));
        newPane.requestLayout();
//        rootPane.getChildren().setAll(newPane);
        Scene scene= rootPane.getScene();
        scene.setRoot(newPane);
    }

    @FXML
    void calendar_onAction(ActionEvent event) {
        listRoute.clear();
        setGridpaneUnvisible();
        listRoute.addAll(BLL_Seller.getInstance().setUpFilterRouteView(startProvince, endProvince, java.sql.Date.valueOf(calendar.getValue())));
        lbIndex.setText("1");
        beginIndex = 1;
        reloadTable();
    }

    public void reloadTable() {
//        new Alert(Alert.AlertType.WARNING, String.valueOf(listRoute.size())).showAndWait();
        for(int i = beginIndex; i < beginIndex + 5; ++i) {
            if (i > listRoute.size()) break;
            switch (i - beginIndex + 1) {
                case 1: {
                    img01.setVisible(true);
                    lbl11.setVisible(true);
                    lbl21.setVisible(true);
                    lbl31.setVisible(true);
                    lbl41.setVisible(true);
                    btn1.setVisible(true);
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(beginIndex - 1).getPicture()));
                    img01.setImage(img);
                    lbl11.setText(listRoute.get(beginIndex - 1).getTypeName());
                    lbl21.setText(listRoute.get(beginIndex - 1).getStartStation());
                    lbl31.setText(listRoute.get(beginIndex - 1).getDestStation());
                    String time = new SimpleDateFormat("HH:mm").format(listRoute.get(beginIndex - 1).getDepartTime());

                    lbl41.setText("Depart time: " + time + "h\n" +
                            "Price: " + listRoute.get(beginIndex - 1).getPrice());
                    break;
                }
                case 2: {
                    img02.setVisible(true);
                    lbl12.setVisible(true);
                    lbl22.setVisible(true);
                    lbl32.setVisible(true);
                    lbl42.setVisible(true);
                    btn2.setVisible(true);
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(beginIndex).getPicture()));
                    img02.setImage(img);
                    lbl12.setText(listRoute.get(beginIndex).getTypeName());
                    lbl22.setText(listRoute.get(beginIndex).getStartStation());
                    lbl32.setText(listRoute.get(beginIndex).getDestStation());
                    String time = new SimpleDateFormat("HH:mm").format(listRoute.get(beginIndex).getDepartTime());
                    lbl42.setText("Depart time: " + time + "h\n" +
                            "Price: " + listRoute.get(beginIndex - 1).getPrice());
                    break;
                }
                case 3: {
                    img03.setVisible(true);
                    lbl13.setVisible(true);
                    lbl23.setVisible(true);
                    lbl33.setVisible(true);
                    lbl43.setVisible(true);
                    btn3.setVisible(true);
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(beginIndex + 1).getPicture()));
                    img03.setImage(img);
                    lbl13.setText(listRoute.get(beginIndex + 1).getTypeName());
                    lbl23.setText(listRoute.get(beginIndex + 1).getStartStation());
                    lbl33.setText(listRoute.get(beginIndex + 1).getDestStation());
                    String time = new SimpleDateFormat("HH:mm").format(listRoute.get(beginIndex + 1).getDepartTime());
                    lbl43.setText("Depart time: " + time + "h\n" +
                            "Price: " + listRoute.get(beginIndex - 1).getPrice());
                    break;
                }
                case 4: {
                    img04.setVisible(true);
                    lbl14.setVisible(true);
                    lbl24.setVisible(true);
                    lbl34.setVisible(true);
                    lbl44.setVisible(true);
                    btn4.setVisible(true);
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(beginIndex + 2).getPicture()));
                    img04.setImage(img);
                    lbl14.setText(listRoute.get(beginIndex + 2).getTypeName());
                    lbl24.setText(listRoute.get(beginIndex + 2).getStartStation());
                    lbl34.setText(listRoute.get(beginIndex + 2).getDestStation());
                    String time = new SimpleDateFormat("HH:mm").format(listRoute.get(beginIndex + 2).getDepartTime());
                    lbl44.setText("Depart time: " + time + "h\n" +
                            "Price: " + listRoute.get(beginIndex - 1).getPrice());
                    break;
                }
                case 5: {
                    img05.setVisible(true);
                    lbl15.setVisible(true);
                    lbl25.setVisible(true);
                    lbl35.setVisible(true);
                    lbl45.setVisible(true);
                    btn5.setVisible(true);
                    Image img = new Image(new ByteArrayInputStream(listRoute.get(beginIndex + 3).getPicture()));
                    img05.setImage(img);
                    lbl15.setText(listRoute.get(beginIndex + 3).getTypeName());
                    lbl25.setText(listRoute.get(beginIndex + 3).getStartStation());
                    lbl35.setText(listRoute.get(beginIndex + 3).getDestStation());
                    String time = new SimpleDateFormat("HH:mm").format(listRoute.get(beginIndex + 3).getDepartTime());
                    lbl45.setText("Depart time: " + time + "h\n" +
                            "Price: " + listRoute.get(beginIndex - 1).getPrice());
                    break;
                }

                default:
                    break;
            }
        }
    }

    public void setGridpaneUnvisible() {
        img01.setVisible(false);
        img02.setVisible(false);
        img03.setVisible(false);
        img04.setVisible(false);
        img05.setVisible(false);
        lbl11.setVisible(false);
        lbl21.setVisible(false);
        lbl31.setVisible(false);
        lbl41.setVisible(false);
        lbl12.setVisible(false);
        lbl22.setVisible(false);
        lbl32.setVisible(false);
        lbl42.setVisible(false);
        lbl13.setVisible(false);
        lbl23.setVisible(false);
        lbl33.setVisible(false);
        lbl43.setVisible(false);
        lbl14.setVisible(false);
        lbl24.setVisible(false);
        lbl34.setVisible(false);
        lbl44.setVisible(false);
        lbl15.setVisible(false);
        lbl25.setVisible(false);
        lbl35.setVisible(false);
        lbl45.setVisible(false);
        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
        btn4.setVisible(false);
        btn5.setVisible(false);
    }
}
