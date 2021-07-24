package Model.ViewModel;

public class ScheduleEntity_ViewModel {
    private int idSchedule;
    private String routeName;
    private String busName;
    private String typeOfBus;
    private String nameofDriver;
    private String departTime;
    private String outDate;
    private int price;
    private int duration;
    private int dpr;
    private boolean isDeleted;


    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getBusName() {
        return busName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getTypeOfBus() {
        return typeOfBus;
    }

    public void setTypeOfBus(String typeOfBus) {
        this.typeOfBus = typeOfBus;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getDpr() {
        return dpr;
    }

    public void setDpr(int dpr) {
        this.dpr = dpr;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNameofDriver() {
        return nameofDriver;
    }

    public void setNameofDriver(String nameofDriver) {
        this.nameofDriver = nameofDriver;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public ScheduleEntity_ViewModel(int idSchedule, String routeName, String busName, String typeOfBus, String nameofDriver,
                                    String departTime, String outDate, int price, int duration, int dpr, boolean isDeleted) {
        this.idSchedule = idSchedule;
        this.routeName = routeName;
        this.busName = busName;
        this.typeOfBus = typeOfBus;
        this.nameofDriver = nameofDriver;
        this.departTime = departTime;
        this.outDate = outDate;
        this.price = price;
        this.duration = duration;
        this.dpr = dpr;
        this.isDeleted = isDeleted;
    }
}
