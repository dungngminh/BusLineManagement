package Model.DataTable;

import Services.BLL_Admin;
import javafx.application.Application;
import javafx.stage.Stage;

public class TableBusPage {
    private int idBus;
    private String busName;
    private String plateNumber;
    private String typeName;
    private String brandName;
    private int slot;

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }




    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }


    public TableBusPage(int idBus, String busName, String plateNumber, String typeName, String brandName, int slot) {
        this.idBus = idBus;
        this.busName = busName;
        this.plateNumber = plateNumber;
        this.typeName = typeName;
        this.brandName = brandName;
        this.slot = slot;
    }
}
