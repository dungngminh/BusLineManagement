package Services;

import Model.BusEntity;
import Model.DataTable.TableBusPage;
import Model.TypeOfBusEntity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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

    public boolean validate_Account(String username, String password) throws SQLException, ClassNotFoundException {
        AtomicBoolean valid = new AtomicBoolean(false);
        DAL.getInstance().getListAcc().stream().filter(account -> account.getUsername().equals(username) &&
                account.getPassword().equals(DAL.getInstance().encryptSHA1(password))).map(account -> true).
                    forEach(valid::set);
        return valid.get();
    }

    public List<TypeOfBusEntity> getListTypeOfBus() {
        return DAL.getInstance().getListTypeOFBus();
    }

    public TypeOfBusEntity getTypeOfBusObj(int idType) {
        List<TypeOfBusEntity> l = DAL.getInstance().getListTypeOFBus();
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

    public List<TableBusPage> updateTableBusPage(int slot, String name) {
        List<TableBusPage> list = new ArrayList<TableBusPage>();
        DAL.getInstance().getDataForBusPage().forEach(b -> {
            if(slot == 0 && b.getBusName().contains(name))
                list.add(new TableBusPage(b.getIdBus(), b.getBusName(), b.getPlateNumber(), b.getTypeOfBusByIdType().getTypeName(), b.getTypeOfBusByIdType().getBrandName(), b.getTypeOfBusByIdType().getSlot(), b.getStatus()));
            else if(slot == b.getTypeOfBusByIdType().getSlot() && b.getBusName().contains(name)) {
                list.add(new TableBusPage(b.getIdBus(), b.getBusName(), b.getPlateNumber(), b.getTypeOfBusByIdType().getTypeName(), b.getTypeOfBusByIdType().getBrandName(), b.getTypeOfBusByIdType().getSlot(), b.getStatus()));
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
}
