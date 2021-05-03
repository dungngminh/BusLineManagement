package Services;

import Model.AccountEntity;
import Model.BusEntity;
import Model.DataTable.TableBusPage;
import Model.RoleAccountEntity;
import Model.TypeOfBusEntity;
import Util.HibernateUtils;
import org.hibernate.Session;

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

    // BLL for BusPage
    public boolean validate_Account(String username, String password) throws SQLException, ClassNotFoundException {
        AtomicBoolean valid = new AtomicBoolean(false);
        DAL.getInstance().getListAcc().forEach(account -> {
           if(account.getUsername().equals(username) &&
                   account.getPassword().equals(DAL.getInstance().encryptSHA1(password))) {
               valid.set(true);
               DAL.getInstance().setCurrent(account);
           }
        });
        return valid.get();
    }

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

    // BLL for

    //


}
