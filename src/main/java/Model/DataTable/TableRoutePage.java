package Model.DataTable;

public class TableRoutePage {
    private int idRoute;
    private String startStation;
    private String endStation;
    private int distance;
    private String note;
    private int status;

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TableRoutePage(int idRoute, String startStation, String endStation, int distance, String note, boolean isDelete, int status) {
        this.idRoute = idRoute;
        this.startStation = startStation;
        this.endStation = endStation;
        this.distance = distance;
        this.note = note;
        this.isDelete = isDelete;
        this.status = status;
    }
}
