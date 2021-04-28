package Services;
import Model.*;
import Util.DB_Helper;

import java.sql.SQLException;
import java.util.*;

public class DAL {
    private static DAL instance;
    private List<Account> list_acc;
    private List<Bus> list_bus;

    public List<Bus> getList_bus() {
        return list_bus;
    }

    public void setList_bus(Bus bus) {
        this.list_bus.add(bus);
    }

    public void setAccount(Account account) {
        list_acc.add(account);
    }

    public List<Account> getAccount() {
        return list_acc;
    }

    private DAL(){
        list_acc = new ArrayList<Account> ();
    }

    public static DAL getInstance() {
        if (instance == null) {
            instance = new DAL();
        }
        return instance;
    }

    public void getListAcc() throws SQLException, ClassNotFoundException {
        DB_Helper.getInstance().getRecord("SELECT * FROM Account", "Account");
    }

    public void getListBus() throws SQLException, ClassNotFoundException {
        DB_Helper.getInstance().getRecord("SELECT * FROM Bus", "Bus");
    }
}
