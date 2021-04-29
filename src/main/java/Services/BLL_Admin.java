package Services;

import Model.TypeOfBusEntity;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
        DAL.getInstance().getListAcc();
        DAL.getInstance().getListAcc().forEach(account -> {
            if(account.getUsername().equals(username) && account.getPassword().equals(password))
                valid.set(true);
        });
        return valid.get();
    }

    public List<TypeOfBusEntity> getListTypeOfBus() {
        return DAL.getInstance().getListTypeOFBus();
    }

    public List<String> getBrandSlotFromTypeName(String nameType) {
        List<String> ans = new ArrayList<String>();
        DAL.getInstance().getListTypeOFBus().forEach(type -> {
            if(type.getTypeName().equals(nameType)) {
                ans.add(type.getBrandName());
                ans.add(String.valueOf(type.getSlot()));
            }
        });
        return ans;
    }
}
