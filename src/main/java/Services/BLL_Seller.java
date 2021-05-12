package Services;


import Controller.TicketSeller.TicketOrder;
import Model.*;
import Model.ViewModel.FilterRoute_ViewModel;
import Util.HibernateUtils;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;
import org.hibernate.query.Query;


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


    // NOTICE BLL for FilterRout
    public List<List<String>> getPairStationFromTwoProvince(ProvinceEntity startPro, ProvinceEntity endPro) {
        List<List<String>> res = new ArrayList<List<String>>();

        Object[] list1 = startPro.getStationsByIdProvince().toArray();
        Object[] list2 = endPro.getStationsByIdProvince().toArray();

        for(Object s1: list1) {
            for(Object s2: list2) {
                res.add(Arrays.asList(((StationEntity)s1).getStationName(), ((StationEntity)s2).getStationName()));
                System.out.println(((StationEntity)s1).getStationName() + " " + ((StationEntity)s2).getStationName());

            }
        }

        return res;
    }

    public List<FilterRoute_ViewModel> setUpFilterRouteView(ProvinceEntity startPro, ProvinceEntity endPro, Date departDate) {
        List<RouteEntity> listRoute = DAL.getInstance().getFilterRoute(getPairStationFromTwoProvince(startPro, endPro));

        List<TripInformationEntity> listTrip = DAL.getInstance().getFilterTrip(listRoute);
        List<FilterRoute_ViewModel> result = new ArrayList<>();

        listTrip.forEach(trip ->{
            if(trip.getDepartDate().compareTo(departDate) == 0) {
                byte[] picture = trip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getPicture();
                String typeName = trip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getTypeName();
                String startStation = trip.getScheduleByIdSchedule().getRouteByIdRoute().getStartStation();
                String destStation = trip.getScheduleByIdSchedule().getRouteByIdRoute().getEndStation();
                Date departTime = trip.getScheduleByIdSchedule().getDepartTime();
                int duration = trip.getScheduleByIdSchedule().getDuration();

                result.add(new FilterRoute_ViewModel(trip, picture, typeName, startStation, destStation, departTime, duration));
            }

        });

        return result;
    }


    // DONE FilterRoute ?

    // NOTICE BLL for TickeOrder ?
    public TicketEntity pendingTicketOrderToTicket(TripInformationEntity trip) {
        return DAL.getInstance().pendingTicketOrderToTicket(DAL.getInstance().getCurrent(), trip);
    }

    public List<String> getOrderedTicket(Integer idTrip) {
        List<String> ans = new ArrayList<>();


        DAL.getInstance().getListTicket(idTrip).forEach(ticket -> {
            if(ticket.getStatus() == 1) {
                ans.addAll(Arrays.asList(ticket.getNameTicket().split("-")));
            }
        });
        return ans;
    }

    public List<String> getPendingTicket(Integer idTrip, Integer crrId) {
        List<String> ans = new ArrayList<String>();

        DAL.getInstance().getListTicket(idTrip).forEach(ticket -> {
            if(ticket.getStatus() == 0 && !crrId.equals(ticket.getIdTicket())) {
                ans.addAll(Arrays.asList(ticket.getNameTicket().split("-")));
            }
        });
        return ans;
    }

    public void updateCurrentTicket(TicketEntity ticket, String nameTicket, String nameCustomer, String phone,
                                    Integer stt, Integer price, Boolean isPaid) {
        ticket.setNameTicket(nameTicket);
        ticket.setNameCustomer(nameCustomer);
        ticket.setPhoneNumber(phone);
        ticket.setStatus(stt);
        ticket.setPrice(price);
        ticket.setIsPaid(isPaid);
        DAL.getInstance().updateCurrentTicket(ticket);
    }

    public void deleteCurrentTicket(Integer idTicket) {
        DAL.getInstance().deleteCurrentTicket(idTicket);
    }
    // DONE for TicketOrder


}
