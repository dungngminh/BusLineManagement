package Services;

import Model.*;
import Model.ViewModel.BusEntity_ViewModel;
import Model.ViewModel.Notification_ViewModel;
import Model.ViewModel.ScheduleEntity_ViewModel;

import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class BLL_Admin {
    private static BLL_Admin instance;

    private BLL_Admin() {

    }

    public static BLL_Admin getInstance() {
        if (instance == null) {
            instance = new BLL_Admin();
        }
        return instance;
    }

    // BLL for login
    public Integer validate_Account(String username, String password) throws SQLException, ClassNotFoundException {
//        AtomicBoolean valid = new AtomicBoolean(false);
        AtomicReference<Integer> valid = new AtomicReference<>(0);
        for (AccountEntity account : DAL.getInstance().getListAcc()) {
            if (account.getUsername().equals(username) &&
                    account.getPassword().equals(DAL.getInstance().encryptSHA1(password))) {
                int idRole = account.getIdRole();
                valid.set(idRole);
                DAL.getInstance().setCurrent(account);
                System.out.println(DAL.getInstance().getCurrent().getUsername());
                break;
            }
            DAL.getInstance().setCurrent(account);
        }

        return valid.get();
    }

    public void toggleIsOnlineForAccout(AccountEntity acc, Boolean isOnline) {
        DAL.getInstance().toggleIsOnlineForAccout(acc, isOnline);
    }

    public Integer getIdTicketToClose() {
        return DAL.getInstance().getIdTicketToClose();
    }

    // DONE
    // BLL for BusPage
    public List<TypeOfBusEntity> getListTypeOfBus() {
        return DAL.getInstance().getListTypeOfBus();

    }

    public TypeOfBusEntity getTypeOfBusObj(int idType) {
        List<TypeOfBusEntity> l = DAL.getInstance().getListTypeOfBus();
        for (TypeOfBusEntity typeOfBusEntity : l) {
            if (typeOfBusEntity.getIdType() == idType) {
                return typeOfBusEntity;
            }
        }
        return null;
    }

    public List<BusEntity> getAllBus() {
        return DAL.getInstance().getDataForBusPage();
    }

    public void addBus(String busName, String plateNumber, TypeOfBusEntity tob, boolean del, int stt) {
        DAL.getInstance().insertBus(busName, plateNumber, tob, del, stt);
    }

    public List<BusEntity_ViewModel> updateTableBusPage(int slot, String name) {
        List<BusEntity_ViewModel> list = new ArrayList<>();
        DAL.getInstance().getDataForBusPage().forEach(b -> {
            if (slot == 0 && b.getBusName().toLowerCase().contains(name.toLowerCase()))
                list.add(new BusEntity_ViewModel(b.getIdBus(), b.getBusName(), b.getPlateNumber(),
                        b.getTypeOfBusByIdType().getTypeName(), b.getTypeOfBusByIdType().getBrandName(), b.getTypeOfBusByIdType().getSlot(), b.getStatus()));

            else if (slot == b.getTypeOfBusByIdType().getSlot() && b.getBusName().toLowerCase().contains(name.toLowerCase())) {
                list.add(new BusEntity_ViewModel(b.getIdBus(), b.getBusName(), b.getPlateNumber(),
                        b.getTypeOfBusByIdType().getTypeName(), b.getTypeOfBusByIdType().getBrandName(), b.getTypeOfBusByIdType().getSlot(), b.getStatus()));
            }
        });
        return list;
    }

    public void updateBus(int idBus, String busName, String plateNumber, TypeOfBusEntity tob, int stt) {
        DAL.getInstance().updateBus(idBus, busName, plateNumber, tob, stt);
    }

    public void deleteBus(int idBus) {
        DAL.getInstance().deleteBus(idBus);
    }
    // done BusPage

    // BLL for Decentralize Page
    public List<AccountEntity> getListAcc() throws SQLException, ClassNotFoundException {
        return DAL.getInstance().getListAcc();
    }

    public RoleEntity getRole(Integer idRole) {
        return DAL.getInstance().getRole(idRole);
    }

    public void addUserToAccount(String username, String password, Integer idRole, RoleEntity role) {
        String encryptPass = DAL.getInstance().encryptSHA1(password);

        DAL.getInstance().addUserToAccount(username, encryptPass, idRole, role);
    }

    public void updateRole(AccountEntity acc, int idRole) {
        DAL.getInstance().updateRole(acc, idRole);
    }

    public void updateAccount(AccountEntity acc, String newPassword) {
        acc.setPassword(DAL.getInstance().encryptSHA1(newPassword));
        DAL.getInstance().updateAccount(acc);
    }

    public void deleteUser(AccountEntity acc) {
        DAL.getInstance().deleteUser(acc);

    }
    //done Decentralize Page

    // BLL for Driver ?
    public List<DriverEntity> getListDriver(int status, String name) {
        List<DriverEntity> data = new ArrayList<>();
        DAL.getInstance().getListDriver().forEach(driver -> {
            if (status == -1 && driver.getNameDriver().contains(name)) {
                data.add(driver);
            } else if (status == driver.getStatus() && driver.getNameDriver().contains(name)) {
                data.add(driver);
            }
        });
        return data;
    }

    public void addDriver(String driverName, String phoneNumber, String address, int stt) {
        DriverEntity driver = new DriverEntity();
        driver.setNameDriver(driverName);
        driver.setPhone(phoneNumber);
        driver.setAddress(address);
        driver.setIsDelete(false);
        driver.setStatus(stt);
        DAL.getInstance().insertDriver(driver);
    }

    public void updateDriver(int idDriver, String driverName, String phoneNumber, String address, int stt) {
        DAL.getInstance().updateDriver(idDriver, driverName, phoneNumber, address, stt);
    }

    public void deleteDriver(int idDriver) {
        DAL.getInstance().deleteDriver(idDriver);
    }
    //done Driver ?

    //Province
    public List<ProvinceEntity> getProvinceName() {
        return DAL.getInstance().getProvinceName();
    }

    //done ?
    //Route
    public void addRoute(String startStation, String endStation, String note, int distance) {
        var route = new RouteEntity();
        route.setStartStation(startStation);
        route.setEndStation(endStation);
        route.setDistance(distance);
        route.setNote(note);
        DAL.getInstance().insertRoute(route);
    }

    //find route using route, start + " end"
    public List<RouteEntity> getRoutes(Integer status, String name) {
        List<RouteEntity> data = new ArrayList<>();
        DAL.getInstance().getRoutes().forEach(route -> {
            String routeName = route.getStartStation() + " " + route.getEndStation();
            final boolean contains = routeName.toLowerCase().contains(name.toLowerCase());
            if (status == null && contains) {
                data.add(route);
            } else if (status != null && status == route.getStatus() && contains) {
                data.add(route);
            }
        });
        return data;
    }

    public void updateRoute(int idRoute, String startStation, String endStation, String note, int distance, int stt) {
        DAL.getInstance().updateRoute(idRoute, startStation, endStation, note, distance, stt);
    }

    public void deleteRoute(int idRoute) {
        DAL.getInstance().deleteRoute(idRoute);
    }

    public boolean checkDuplicateRoute(String startStation, String endStation) {
        return getRoutes(0, "").stream().anyMatch(route ->
                (route.getStartStation() + route.getEndStation()).equals(startStation + endStation));
    }

    public int getDistance(int startProvinceIndex, int endProvinceIndex) throws IOException {
        // cell B - Q  = 2 - 17
        String[] cells = {"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"};
        String[] rowIndex = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
        HSSFWorkbook wb = new HSSFWorkbook(getClass().getResourceAsStream("/util/Distance.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);
        CellReference cellReference = new CellReference(cells[startProvinceIndex] + rowIndex[endProvinceIndex]);
        Row row = sheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());
        return Integer.parseInt(Double.toString(cell.getNumericCellValue()).replace(".0", ""));
    }
    // done ?

    //BLL for Schedule
    public List<ScheduleEntity_ViewModel> updateTableSchedulePage(String name) {
        List<ScheduleEntity_ViewModel> list = new ArrayList<>();
        DAL.getInstance().getScheduleData().forEach(data -> {
            if ((data.getRouteByIdRoute().getStartStation() + " - " + data.getRouteByIdRoute().getEndStation() +
                    " || " + data.getBusByIdBus().getTypeOfBusByIdType().getTypeName() + " || " +
                    new SimpleDateFormat("HH:mm:ss").format(data.getDepartTime())).toLowerCase().contains(name.toLowerCase()))

                list.add(new ScheduleEntity_ViewModel(data.getIdSchedule(),
                        (data.getRouteByIdRoute().getStartStation() + " - " + data.getRouteByIdRoute().getEndStation()),
                        data.getBusByIdBus().getBusName(), data.getBusByIdBus().getTypeOfBusByIdType().getTypeName(),
                        data.getDriverByIdDriver().getNameDriver(), new SimpleDateFormat("HH:mm:ss").format(data.getDepartTime()),
                        new SimpleDateFormat("dd/MM/yyyy").format(DAL.getInstance().getOutDateUpdate(data.getIdSchedule())),
                        data.getPrice(), data.getDuration(), data.getDpr(), data.getIsDelete()));

        });
        return list;
    }

    public void addSchedule(RouteEntity Route, BusEntity Bus, DriverEntity driver, Date departTimeInput, int durationInput
            , int priceInput, int dprInput) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setIdRoute(Route.getIdRoute());
        schedule.setIdBus(Bus.getIdBus());
        schedule.setIdDriver(driver.getIdDriver());
        schedule.setDepartTime(departTimeInput);
        schedule.setDpr(dprInput);
        schedule.setPrice(priceInput);
        schedule.setBusByIdBus(Bus);
        schedule.setRouteByIdRoute(Route);
        schedule.setDriverByIdDriver(driver);
        schedule.setDuration(durationInput);
        schedule.setIsDelete(false);
        DAL.getInstance().insertSchedule(schedule);
    }

    public void deleteSchedule(int idSchedule) {
        DAL.getInstance().removeSchedule(idSchedule);
    }

    public void updateSchedule(int idSchedule, RouteEntity routeSelected, BusEntity busSelected, DriverEntity driverSelected
            , Date departTimeInput, int durationInput, int priceInput, int dprInput) {
        DAL.getInstance().updateSchedule(idSchedule, routeSelected, busSelected, driverSelected, departTimeInput, durationInput
                , priceInput, dprInput);
    }

    public void updateScheduleNotDPR(int idSchedule, RouteEntity routeSelected, BusEntity busSelected,
                                     DriverEntity driverSelected, Date departTimeInput, int durationInput, int priceInput) {
        DAL.getInstance().updateScheduleNotDPR(idSchedule, routeSelected, busSelected, driverSelected, departTimeInput,
                durationInput, priceInput);
    }

    public void updateDPR(int idSchedule, int dpr) {
        DAL.getInstance().updateDPR(idSchedule, dpr);
    }

    // NOTICE BLL for MainWindow(Dashboard of Admin)

    public String getRevenueTicket1YearAgo() {
        AtomicReference<Long> result = new AtomicReference<>(0L);
        DAL.getInstance().getListTicket1YearAgo().forEach(ticket -> {
            result.updateAndGet(v -> v + ticket.getPrice());
        });

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(result.get());
    }

    public long getNumberRoutesToday() {
        return DAL.getInstance().getNumberRoutesToday();
    }

    public List<Integer> getOutDatedSchedule() {
        return DAL.getInstance().getOutDatedSchedule();
    }

    public List<AccountEntity> getListSeller() {
        return DAL.getInstance().getListSeller();
    }

    public List<Pair<String, Double>> getDataForLineChart_1Month(AccountEntity acc) {
        List<Pair<String, Double>> data = new ArrayList<>();

        LocalDate crr = LocalDate.now().plusMonths(1);
        LocalDate begin = crr.minusMonths(1);
        while (crr.compareTo(begin) >= 0) {

//            System.out.println(crr);

            String from = crr.minusDays(5).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String to = crr.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            AtomicReference<Double> sum = new AtomicReference<>(0D);
            DAL.getInstance().getListTicketInIntervalTime(from, to, acc).forEach(tic -> {
                sum.updateAndGet(v -> v + tic.getPrice() / 1000000D);
            });

            String interval = crr.minusDays(5).format(DateTimeFormatter.ofPattern("dd")) + " - " +
                    crr.format(DateTimeFormatter.ofPattern("dd")) + "/" + crr.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            if (data.isEmpty())
                data.add(new Pair<>(interval, sum.get()));
            else data.add(0, new Pair<>(interval, sum.get()));
            crr = crr.minusDays(5);
        }

        return data;
    }

    public List<Pair<String, Double>> getDataForLineChart_1Quarter(AccountEntity acc) {
        List<Pair<String, Double>> data = new ArrayList<>();

        LocalDate crr = LocalDate.now().plusMonths(3); // Fake data
        LocalDate begin = crr.minusMonths(3);
        while (crr.compareTo(begin) >= 0) {
            String from = crr.minusDays(10).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String to = crr.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            AtomicReference<Double> sum = new AtomicReference<>(0D);
            DAL.getInstance().getListTicketInIntervalTime(from, to, acc).forEach(tic -> {
                sum.updateAndGet(v -> v + tic.getPrice() / 1000000D);
            });

            String interval = crr.minusDays(10).format(DateTimeFormatter.ofPattern("dd")) + " - " +
                    crr.format(DateTimeFormatter.ofPattern("dd")) + "/" + crr.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            if (data.isEmpty())
                data.add(new Pair<>(interval, sum.get()));
            else data.add(0, new Pair<>(interval, sum.get()));
            crr = crr.minusDays(10);
        }

        return data;
    }

    public List<Pair<String, Double>> getDataForLineChart_1Year(AccountEntity acc) {
        List<Pair<String, Double>> data = new ArrayList<>();

        LocalDate crr = LocalDate.now().plusYears(1); // fake data
        LocalDate begin = crr.minusYears(1);
        while (crr.compareTo(begin) >= 0) {
            String from = crr.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String to = crr.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            AtomicReference<Double> sum = new AtomicReference<>(0D);
            DAL.getInstance().getListTicketInIntervalTime(from, to, acc).forEach(tic -> {
                sum.updateAndGet(v -> v + tic.getPrice() / 1000000D);
            });

            String interval = crr.minusMonths(1).format(DateTimeFormatter.ofPattern("dd")) + " - " +
                    crr.format(DateTimeFormatter.ofPattern("dd")) + "/" + crr.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            if (data.isEmpty())
                data.add(new Pair<>(interval, sum.get()));
            else data.add(0, new Pair<>(interval, sum.get()));
            crr = crr.minusMonths(1);
        }

        return data;
    }

    public List<Pair<String, Integer>> getDataForBarChart(ProvinceEntity fromProvince, ProvinceEntity toProvince) {
        List<Pair<String, Integer>> data = new ArrayList<>();

        LocalDate crr = LocalDate.now().plusDays(20); // fake data
        LocalDate begin = crr.minusDays(30);
        while (crr.compareTo(begin) >= 0) {
            String from = crr.minusDays(5).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String to = crr.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            String interval = crr.minusDays(5).format(DateTimeFormatter.ofPattern("dd")) + " - " +
                    crr.format(DateTimeFormatter.ofPattern("dd")) + "/" + crr.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            if (data.isEmpty())
                data.add(new Pair<>(interval,
                        DAL.getInstance().getListTripInIntervalTime(fromProvince, toProvince, from, to).size()));
            else data.add(0, new Pair<>(interval,
                    DAL.getInstance().getListTripInIntervalTime(fromProvince, toProvince, from, to).size()));
            crr = crr.minusDays(5);
        }

        return data;
    }

    //DONE

    // NOTICE BLL for Notifocation(Dashboard of Admin)

    public void pushMessageIntoDB(String message) {
        DAL.getInstance().pushMessageIntoDB(message);
    }

    public List<Notification_ViewModel> getAllNotification() {
        List<Notification_ViewModel> list = new ArrayList<>();

        DAL.getInstance().getAllNotification().forEach(noti -> {
            list.add(new Notification_ViewModel(noti.getIdNotify(), noti.getAccountByIdUser().getUsername(), noti.getNotifyContent(),
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(noti.getTime())));
        });
        return list;
    }

    public void updateNotification(Integer idNotify, String content) {
        DAL.getInstance().updateNotification(idNotify, content);
    }

    public void deleteNotification(Integer id) {
        DAL.getInstance().deleteNotification(id);
    }

    // DONE

}
