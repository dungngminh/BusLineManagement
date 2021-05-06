package Services;

import Model.ProvinceEntity;
import Model.RouteEntity;
import Model.StationEntity;
import Model.TripInformationEntity;
import Model.ViewModel.FilterRoute_ViewModel;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BLL_Seller {
    private static BLL_Seller instance;

    private BLL_Seller() {

    }

    public static BLL_Seller getInstance() {
        if (instance == null) {
            instance = new BLL_Seller();
        }
        return instance;
    }

    public List<List<String>> getPairStationFromTwoProvince(ProvinceEntity startPro, ProvinceEntity endPro) {
        List<List<String>> res = new ArrayList<List<String>>();

        Object[] list1 = startPro.getStationsByIdProvince().toArray();
        Object[] list2 = endPro.getStationsByIdProvince().toArray();

        for(Object s1: list1) {
            for(Object s2: list2) {
                res.add(Arrays.asList(((StationEntity)s1).getStationName(), ((StationEntity)s2).getStationName()));
            }
        }

        return res;
    }

    public List<FilterRoute_ViewModel> setUpFilterRouteView(ProvinceEntity startPro, ProvinceEntity endPro) {
        List<RouteEntity> listRoute = DAL.getInstance().getFilterRoute(getPairStationFromTwoProvince(startPro, endPro));
        List<TripInformationEntity> listTrip = DAL.getInstance().getFilterTrip(listRoute);
        List<FilterRoute_ViewModel> result = new ArrayList<>();

        AtomicInteger id = new AtomicInteger(1);
        listTrip.forEach(trip ->{
            byte[] picture = trip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getPicture();
            String typeName = trip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getTypeName();
            String startStation = trip.getScheduleByIdSchedule().getRouteByIdRoute().getStartStation();
            String destStation = trip.getScheduleByIdSchedule().getRouteByIdRoute().getEndStation();
            Date departTime = trip.getScheduleByIdSchedule().getDepartTime();
            int duration = trip.getScheduleByIdSchedule().getDuration();

            result.add(new FilterRoute_ViewModel(id.get(), picture, typeName, startStation, destStation, departTime, duration));
            id.addAndGet(1);
        });

        return result;
    }

    public Node getNodeByCoordinate(Integer row, Integer column, GridPane grid) {
        for (Node node : grid.getChildren()) {
            if(GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return node;
            }
        }
        return null;
    }

}
