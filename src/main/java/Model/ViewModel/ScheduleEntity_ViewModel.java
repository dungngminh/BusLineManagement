package Model.ViewModel;


import java.util.Date;


public class ScheduleEntity_ViewModel {
    private int idSchedule;
    private String routeName;
    private String busName;
    private String typeOfBus;
    private String departTime;
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

    public ScheduleEntity_ViewModel(int idSchedule, String routeName, String busName, String typeOfBus, String departTime, int price, int dpr, int duration, boolean isDeleted) {
        this.idSchedule = idSchedule;
        this.routeName = routeName;
        this.busName = busName;
        this.typeOfBus = typeOfBus;
        this.departTime = departTime;
        this.price = price;
        this.dpr = dpr;
        this.duration = duration;
        this.isDeleted = isDeleted;
    }
}
