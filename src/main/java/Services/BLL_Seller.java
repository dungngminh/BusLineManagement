package Services;


import Model.*;
import Model.ViewModel.FilterRoute_ViewModel;
import Model.ViewModel.Ticket_ViewModel;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    public List<FilterRoute_ViewModel> setUpFilterRouteView(ProvinceEntity startPro, ProvinceEntity endPro, Date departDate) {
        List<FilterRoute_ViewModel> result = new ArrayList<>();

        DAL.getInstance().getFilterTrip(startPro, endPro, new SimpleDateFormat("yyyy/MM/dd").format(departDate))
            .forEach(trip -> {
                byte[] picture = trip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getPicture();
                String typeName = trip.getScheduleByIdSchedule().getBusByIdBus().getTypeOfBusByIdType().getTypeName();
                String startStation = trip.getScheduleByIdSchedule().getRouteByIdRoute().getStartStation();
                String destStation = trip.getScheduleByIdSchedule().getRouteByIdRoute().getEndStation();
                Date departTime = trip.getScheduleByIdSchedule().getDepartTime();
                int duration = trip.getScheduleByIdSchedule().getDuration();

                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

                String price = currencyVN.format(trip.getScheduleByIdSchedule().getPrice());

                result.add(new FilterRoute_ViewModel(trip, picture, typeName, startStation, destStation, departTime, duration, price));
        });

        return result;
    }

    public TicketEntity pendingTicketOrderToTicket(TripInformationEntity trip) {
        return DAL.getInstance().pendingTicketOrderToTicket(DAL.getInstance().getCurrent(), trip);
    }

    public List<String> getOrderedTicket(Integer idTrip) {
        List<String> listOrderedTicket = new ArrayList<>();

        DAL.getInstance().getListTicket(idTrip).forEach(ticket -> {
            if(ticket.getStatus() == 1) {
                listOrderedTicket.addAll(Arrays.asList(ticket.getNameTicket().split("-")));
            }
        });

        return listOrderedTicket;
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

    public List<Ticket_ViewModel> getAllTicket(ProvinceEntity fromProvince, ProvinceEntity toProvince, String paid, Date departDate
            , String nameCustomer, String phoneCustomer) {
        List<Ticket_ViewModel> result = new ArrayList<>();

        String date = departDate == null ? "" : new SimpleDateFormat("yyyy/MM/dd").format(departDate);
        DAL.getInstance().getAllTicket(fromProvince, toProvince, paid, date).forEach(ticket -> {
            if(ticket.getNameCustomer().toLowerCase().contains(nameCustomer.toLowerCase()) &&
                    ticket.getPhoneNumber().contains(phoneCustomer)) {
                String nameTicket = ticket.getNameTicket();
                String route = ticket.getTripInformationByIdTrip().getScheduleByIdSchedule().getRouteByIdRoute().getStartStation() +
                        "-" + ticket.getTripInformationByIdTrip().getScheduleByIdSchedule().getRouteByIdRoute().getEndStation();
                String departTime = new SimpleDateFormat("HH:mm").format(ticket.getTripInformationByIdTrip()
                        .getScheduleByIdSchedule().getDepartTime());
                String departDateDisplay = new SimpleDateFormat("dd/MM/yyyy").format(ticket.getTripInformationByIdTrip().getDepartDate());
                String name = ticket.getNameCustomer();
                String phone = ticket.getPhoneNumber();
                String isPaid = ticket.getIsPaid() ? "Paid" : "Unpaid";

                // Format VNĐ
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String price = currencyVN.format(ticket.getPrice());
                //

                result.add(new Ticket_ViewModel(ticket.getIdTicket(), nameTicket, route, departTime,departDateDisplay, name, phone, isPaid, price));
            }

        });

        return result;
    }

    public void setPaidTicket(Integer idTicket) {
        DAL.getInstance().setPaidTicket(idTicket);
    }

    public TicketEntity getOneTicket(Integer idTicket) {
        return DAL.getInstance().getOneTicket(idTicket);
    }
}