package Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.*;

@Entity
@Table(name = "Schedule", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class ScheduleEntity {
    private int idSchedule;
    private int idRoute;
    private int idBus;
    private Date departTime;
    private int duration;
    private boolean isDelete;
    private RouteEntity routeByIdRoute;
    private BusEntity busByIdBus;
    private Collection<TripInformationEntity> tripInformationsByIdSchedule;

    @Id
    @Column(name = "idSchedule", nullable = false)
    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    @Basic
    @Column(name = "idRoute", nullable = false, insertable = false, updatable = false)
    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    @Basic
    @Column(name = "idBus", nullable = false, insertable = false, updatable = false)
    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    @Basic
    @Column(name = "departTime", nullable = false)
    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    @Basic
    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "isDelete", nullable = false)
    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (idSchedule != that.idSchedule) return false;
        if (idRoute != that.idRoute) return false;
        if (idBus != that.idBus) return false;
        if (duration != that.duration) return false;
        if (isDelete != that.isDelete) return false;
        if (departTime != null ? !departTime.equals(that.departTime) : that.departTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSchedule;
        result = 31 * result + idRoute;
        result = 31 * result + idBus;
        result = 31 * result + (departTime != null ? departTime.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (isDelete ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idRoute", referencedColumnName = "idRoute", nullable = false)
    public RouteEntity getRouteByIdRoute() {
        return routeByIdRoute;
    }

    public void setRouteByIdRoute(RouteEntity routeByIdRoute) {
        this.routeByIdRoute = routeByIdRoute;
    }

    @ManyToOne
    @JoinColumn(name = "idBus", referencedColumnName = "idBus", nullable = false)
    public BusEntity getBusByIdBus() {
        return busByIdBus;
    }

    public void setBusByIdBus(BusEntity busByIdBus) {
        this.busByIdBus = busByIdBus;
    }

    @OneToMany(mappedBy = "scheduleByIdSchedule")
    public Collection<TripInformationEntity> getTripInformationsByIdSchedule() {
        return tripInformationsByIdSchedule;
    }

    public void setTripInformationsByIdSchedule(Collection<TripInformationEntity> tripInformationsByIdSchedule) {
        this.tripInformationsByIdSchedule = tripInformationsByIdSchedule;
    }
}
