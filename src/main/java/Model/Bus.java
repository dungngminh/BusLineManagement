package Model;

public class Bus {
    private String busName;
    private String plateNumber;
    private int idType;
    private boolean isDelete;
    private int status;

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

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public Bus(String busName, String plateNumber, int idType, boolean isDelete, int status) {
        this.busName = busName;
        this.plateNumber = plateNumber;
        this.idType = idType;
        this.isDelete = isDelete;
        this.status = status;
    }
}
