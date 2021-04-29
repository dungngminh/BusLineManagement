package Model;

import javax.persistence.*;

@Entity
@Table(name = "Route", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class RouteEntity {
    private int idRoute;
    private String startStation;
    private String endStation;
    private Integer distance;
    private String note;
    private Boolean isDelete;
    private int status;

    @Id
    @Column(name = "idRoute")
    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    @Basic
    @Column(name = "startStation")
    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    @Basic
    @Column(name = "endStation")
    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    @Basic
    @Column(name = "distance")
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "isDelete")
    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteEntity that = (RouteEntity) o;

        if (idRoute != that.idRoute) return false;
        if (status != that.status) return false;
        if (startStation != null ? !startStation.equals(that.startStation) : that.startStation != null) return false;
        if (endStation != null ? !endStation.equals(that.endStation) : that.endStation != null) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRoute;
        result = 31 * result + (startStation != null ? startStation.hashCode() : 0);
        result = 31 * result + (endStation != null ? endStation.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
