package Services;

import Model.*;
import Model.ViewModel.BusEntity_ViewModel;

import java.sql.SQLException;
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
        DAL.getInstance().getListAcc().forEach(account -> {
           if(account.getUsername().equals(username) &&
                   account.getPassword().equals(DAL.getInstance().encryptSHA1(password))) {
                int idRole = ((RoleAccountEntity)account.getRoleAccountsByIdUser().toArray()[0]).getIdRole();
                valid.set(idRole);
                DAL.getInstance().setCurrent(account);
           }
        });
        return valid.get();
    }

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

    public void addBus(String busName, String plateNumber, TypeOfBusEntity tob, boolean del, int stt) {
        DAL.getInstance().insertBus(busName, plateNumber, tob, del, stt);
    }

    public List<BusEntity_ViewModel> updateTableBusPage(int slot, String name) {
        List<BusEntity_ViewModel> list = new ArrayList<BusEntity_ViewModel>();
        DAL.getInstance().getDataForBusPage().forEach(b -> {
            if(slot == 0 && b.getBusName().contains(name))
                list.add(new BusEntity_ViewModel(b.getIdBus(), b.getBusName(), b.getPlateNumber(), b.getTypeOfBusByIdType().getTypeName(), b.getTypeOfBusByIdType().getBrandName(), b.getTypeOfBusByIdType().getSlot(), b.getStatus()));
            else if(slot == b.getTypeOfBusByIdType().getSlot() && b.getBusName().contains(name)) {
                list.add(new BusEntity_ViewModel(b.getIdBus(), b.getBusName(), b.getPlateNumber(), b.getTypeOfBusByIdType().getTypeName(), b.getTypeOfBusByIdType().getBrandName(), b.getTypeOfBusByIdType().getSlot(), b.getStatus()));
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
        return  DAL.getInstance().getListAcc();
    }

    public void addUserToAccount(String username, String password, int idRole) {
        String encryptPass = DAL.getInstance().encryptSHA1(password);
        DAL.getInstance().addUserToAccount(username, encryptPass, idRole);
    }

    public void updateRole(AccountEntity acc, int idRole) {
        DAL.getInstance().updateRole(acc, idRole);
    }
    //done Decentralize Page

    // BLL for Driver ?
    public List<DriverEntity> getListDriver(int status, String name) {
        List<DriverEntity> data = new ArrayList<>();
        DAL.getInstance().getListDriver().forEach(driver -> {
           if(status == -1 && driver.getNameDriver().contains(name)) {
               data.add(driver);
           }
           else if(status == driver.getStatus() && driver.getNameDriver().contains(name)) {
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
        driver.setStatus(1);
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
    public List<ProvinceEntity> getProvinceName(){
        return DAL.getInstance().getProvinceName();
    }
    //done ?
    //Route
    public void addRoute(String startStation, String endStation,String note, int distance) {
        var route = new RouteEntity();
        route.setStartStation(startStation);
        route.setEndStation(endStation);
        route.setDistance(distance);
        route.setNote(note);
        DAL.getInstance().insertRoute(route);
    }
    //find route using route, start + " end"
    public List<RouteEntity> getRoutes(int status, String name) {
        List<RouteEntity> data = new ArrayList<>();
        DAL.getInstance().getRoutes().forEach(route ->{
            String routeName = route.getStartStation() + " " + route.getEndStation();
            if(status == route.getStatus() && routeName.contains(name)){
                data.add(route);
            }
        });
        return data;
    }
    public void updateRoute(int idRoute, String startStation, String endStation,String note, int distance, int stt) {
        DAL.getInstance().updateRoute(idRoute,startStation,endStation,note,distance,stt);
    }
    public void deleteRoute(int idRoute){
        DAL.getInstance().deleteRoute(idRoute);
    }

    public void getStationName() {
    }
    // done ?
}
