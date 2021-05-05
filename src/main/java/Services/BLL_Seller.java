package Services;

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

}
