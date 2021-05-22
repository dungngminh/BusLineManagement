package Controller.Admin;

import Model.AccountEntity;
import Model.ProvinceEntity;
import Services.BLL_Admin;
import Services.DAL;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXDrawer jfx_drawer;

    @FXML
    private JFXHamburger jfx_hambur;

    @FXML
    private Label lb_greet;

    @FXML
    private Label lb_revenue;

    @FXML
    private Label lb_trip;

    @FXML
    private Label lb_route;

    @FXML
    private Label lb_personnel;

    @FXML
    private ComboBox<String> cbx_time;

    @FXML
    private CheckComboBox<AccountEntity> cbx_staff;

    @FXML
    private ComboBox<ProvinceEntity> cbx_to;

    @FXML
    private ComboBox<ProvinceEntity> cbx_from;

    @FXML
    private Button btn_show1;

    @FXML
    private Button btn_show2;

    @FXML
    private AnchorPane paneLine;

    @FXML
    private AnchorPane paneArea;

    public MainWindow() {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Init for side bar
            InitSideBar.getInstance().initializeForNavBar(this.rootPane, this.jfx_drawer, this.jfx_hambur);
            //done

            // Set Greeting
            lb_greet.setText("Welcome, " + DAL.current.getUsername());

            // Set Total revenue
            lb_revenue.setText(BLL_Admin.getInstance().getRevenueTicket1YearAgo());

            // Set Routes today
            lb_route.setText(String.valueOf(BLL_Admin.getInstance().getNumberRoutesToday()));

            // Set number outdated of Schedule
            lb_trip.setText(String.valueOf(BLL_Admin.getInstance().getOutDatedSchedule().size()));

            // Set number of personnel
            lb_personnel.setText(String.valueOf(BLL_Admin.getInstance().getListAcc().size()));

            // Set for combobox Time Interval
            cbx_time.getItems().addAll("1 Month Ago", "1 Quarter Ago", "1 Year Ago");
            cbx_time.getSelectionModel().selectFirst();

            // Set for combobox staff
            final ObservableList<AccountEntity> listObj = FXCollections.observableArrayList(BLL_Admin.getInstance().
                    getListSeller());

            cbx_staff.getItems().addAll(listObj);

            // Set for combobox from - to
            cbx_from.getItems().addAll(BLL_Admin.getInstance().getProvinceName());
            cbx_to.getItems().addAll(BLL_Admin.getInstance().getProvinceName());
            cbx_from.getSelectionModel().selectFirst();
            cbx_to.getSelectionModel().selectFirst();

            // Show line Chart

            btn_show1_Clicked(new MouseEvent(MouseEvent.MOUSE_CLICKED, btn_show1.getLayoutX(), btn_show1.getLayoutY()
                    , btn_show1.getLayoutX(), btn_show1.getLayoutY(), MouseButton.PRIMARY, 1,
                    true, true, true, true, true, true, true, true, true, true, null));

            btn_show2_Clicked(new MouseEvent(MouseEvent.MOUSE_CLICKED, btn_show2.getLayoutX(), btn_show2.getLayoutY()
                    , btn_show2.getLayoutX(), btn_show2.getLayoutY(), MouseButton.PRIMARY, 1,
                    true, true, true, true, true, true, true, true, true, true, null));

            // DONE
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Show LineChart
    public XYChart.Series showLineChart(String type, AccountEntity acc) {
        XYChart.Series dataSeries = new XYChart.Series();
        switch(type) {

            case "1 Month Ago": {

                dataSeries.setName(acc == null ? "All" : acc.getUsername());

                BLL_Admin.getInstance().getDataForLineChart_1Month(acc).forEach(data -> {
                    dataSeries.getData().add(new XYChart.Data(data.getKey(), data.getValue()));
//                    System.out.println(data.getKey());
                });


                break;
            }
            case "1 Quarter Ago": {
                dataSeries.setName(acc == null ? "All" : acc.getUsername());

                BLL_Admin.getInstance().getDataForLineChart_1Quarter(acc).forEach(data -> {
                    dataSeries.getData().add(new XYChart.Data(data.getKey(), data.getValue()));
                });
                break;
            }

            case "1 Year Ago": {
                dataSeries.setName(acc == null ? "All" : acc.getUsername());

                BLL_Admin.getInstance().getDataForLineChart_1Year(acc).forEach(data -> {
                    dataSeries.getData().add(new XYChart.Data(data.getKey(), data.getValue()));
                });
                break;
            }
            default:
                break;
        }

        return dataSeries;
    }
    //

    // Show Bar Chart
    public XYChart.Series<String, Integer> showBarChart(ProvinceEntity fromProvince, ProvinceEntity toProvince) {
        XYChart.Series<String, Integer> dataSeries = new XYChart.Series<>();

        BLL_Admin.getInstance().getDataForBarChart(fromProvince, toProvince).forEach(data -> {
            dataSeries.getData().add(new XYChart.Data<>(data.getKey(), data.getValue()));
        });


        return dataSeries;
    }


    @FXML
    void btn_show1_Clicked(MouseEvent event) {
        ObservableList<AccountEntity> listAcc = cbx_staff.getCheckModel().getCheckedItems();

        switch(cbx_time.getSelectionModel().getSelectedItem()) {
            case "1 Month Ago": {
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Time Intervals");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Revenue( millions )");

                LineChart chart = new LineChart(xAxis, yAxis);

                if(listAcc.isEmpty()) chart.getData().add(showLineChart("1 Month Ago", null));
                else {
                    for(AccountEntity account: listAcc) {
                        chart.getData().add(showLineChart("1 Month Ago", account));
                    }
                }
                chart.setTitle("Ticket revenue 1 month ago");

                paneLine.getChildren().setAll(chart);
                AnchorPane.setTopAnchor(chart, 0.0);
                AnchorPane.setRightAnchor(chart, 0.0);
                AnchorPane.setBottomAnchor(chart, 0.0);
                AnchorPane.setLeftAnchor(chart, 0.0);
                break;
            }

            case "1 Quarter Ago": {
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Time Intervals");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Revenue( millions )");

                LineChart chart = new LineChart(xAxis, yAxis);

                if(listAcc.isEmpty()) chart.getData().add(showLineChart("1 Quarter Ago", null));
                else {
                    for(AccountEntity account: listAcc) {
                        chart.getData().add(showLineChart("1 Quarter Ago", account));
                    }
                }
                chart.setTitle("Ticket revenue 1 year ago");

                paneLine.getChildren().setAll(chart);
                AnchorPane.setTopAnchor(chart, 0.0);
                AnchorPane.setRightAnchor(chart, 0.0);
                AnchorPane.setBottomAnchor(chart, 0.0);
                AnchorPane.setLeftAnchor(chart, 0.0);
                break;
            }

            case "1 Year Ago": {
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setLabel("Time Intervals");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setLabel("Revenue( millions )");

                LineChart chart = new LineChart(xAxis, yAxis);

                if(listAcc.isEmpty()) chart.getData().add(showLineChart("1 Year Ago", null));
                else {
                    for(AccountEntity account: listAcc) {
                        chart.getData().add(showLineChart("1 Year Ago", account));
                    }
                }
                chart.setTitle("Ticket revenue 1 year ago");

                paneLine.getChildren().setAll(chart);
                AnchorPane.setTopAnchor(chart, 0.0);
                AnchorPane.setRightAnchor(chart, 0.0);
                AnchorPane.setBottomAnchor(chart, 0.0);
                AnchorPane.setLeftAnchor(chart, 0.0);
                break;
            }

            default:
                break;

        }
    }

    @FXML
    void btn_show2_Clicked(MouseEvent event) {
        ProvinceEntity from = cbx_from.getSelectionModel().getSelectedItem();
        ProvinceEntity to = cbx_to.getSelectionModel().getSelectedItem();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Time intervals");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of trip(s)");

        BarChart chart = new BarChart(xAxis, yAxis);
        chart.getData().addAll(showBarChart(from, to));

        chart.setTitle("Number of trips active in time periods");

        paneArea.getChildren().setAll(chart);
        AnchorPane.setTopAnchor(chart, 0.0);
        AnchorPane.setRightAnchor(chart, 0.0);
        AnchorPane.setBottomAnchor(chart, 0.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
    }

}
