package Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.*;

@Entity
@Table(name = "Schedule", schema = "dbo", catalog = "N2_19N12B")
public class ScheduleEntity {

    private int idSchedule;
    private int idRoute;
    private int idBus;
    private Date departTime;
    private int duration;
    private Boolean isDelete;

    private Integer price;
    private Integer dpr;

    private RouteEntity routeByIdRoute;
    private BusEntity busByIdBus;
    private Collection<TripInformationEntity> tripInformationsByIdSchedule;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean delete) {
        this.isDelete = delete;
    }

    @Basic
    @Column(name = "Price", nullable = false)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "dpr", nullable = false)
    public Integer getDpr() {
        return dpr;
    }

    public void setDpr(Integer dpr) {
        this.dpr = dpr;
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
        if (!dpr.equals(that.dpr)) return false;
        if (!price.equals(that.price)) return false;
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
        result = 31 * result + dpr;
        result = 31 * result + (isDelete ? 1 : 0);
        result = 31 * result + price;
        return result;
    }

    @ManyToOne(targetEntity = RouteEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idRoute", referencedColumnName = "idRoute", nullable = false)
    public RouteEntity getRouteByIdRoute() {
        return routeByIdRoute;
    }

    public void setRouteByIdRoute(RouteEntity routeByIdRoute) {
        this.routeByIdRoute = routeByIdRoute;
    }

    @ManyToOne(targetEntity = BusEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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