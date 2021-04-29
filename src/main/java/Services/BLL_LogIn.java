package Services;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class BLL_LogIn {
    private static BLL_LogIn instance;

    private BLL_LogIn() {

    }

    public static BLL_LogIn getInstance() {
        if (instance == null) {
            instance = new BLL_LogIn();
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
}
