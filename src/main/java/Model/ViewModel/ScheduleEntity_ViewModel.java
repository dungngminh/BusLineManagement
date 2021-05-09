package Model.ViewModel;

public class ScheduleEntity_ViewModel {
    private int idSchedule;
    private String routeName;
    private String busName;
    private String typeOfBus;
    private int departTime;
    private int status;

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

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getTypeOfBus() {
        return typeOfBus;
    }

    public void setTypeOfBus(String typeOfBus) {
        this.typeOfBus = typeOfBus;
    }

    public int getDepartTime() {
        return departTime;
    }

    public void setDepartTime(int departTime) {
        this.departTime = departTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ScheduleEntity_ViewModel(int idSchedule, String routeName, String busName, String typeOfBus, int departTime, int status) {
        this.idSchedule = idSchedule;
        this.routeName = routeName;
        this.busName = busName;
        this.typeOfBus = typeOfBus;
        this.departTime = departTime;
        this.status = status;
    }
}
