package Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "TripInformation", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class TripInformationEntity {
    private int idTrip;
    private Timestamp departDate;
    private Integer idSchedule;
    private Integer avaliableSlot;
    private int idDriver;
    private Collection<TicketEntity> ticketsByIdTrip;
    private ScheduleEntity scheduleByIdSchedule;
    private DriverEntity driverByIdDriver;

    @Id
    @Column(name = "idTrip", nullable = false)
    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    @Basic
    @Column(name = "departDate", nullable = true)
    public Timestamp getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Timestamp departDate) {
        this.departDate = departDate;
    }

    @Basic
    @Column(name = "idSchedule", nullable = true, insertable = false, updatable = false)
    public Integer getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(Integer idSchedule) {
        this.idSchedule = idSchedule;
    }

    @Basic
    @Column(name = "avaliableSlot", nullable = true)
    public Integer getAvaliableSlot() {
        return avaliableSlot;
    }

    public void setAvaliableSlot(Integer avaliableSlot) {
        this.avaliableSlot = avaliableSlot;
    }

    @Basic
    @Column(name = "idDriver", nullable = false, insertable = false, updatable = false)
    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripInformationEntity that = (TripInformationEntity) o;

        if (idTrip != that.idTrip) return false;
        if (idDriver != that.idDriver) return false;
        if (departDate != null ? !departDate.equals(that.departDate) : that.departDate != null) return false;
        if (idSchedule != null ? !idSchedule.equals(that.idSchedule) : that.idSchedule != null) return false;
        if (avaliableSlot != null ? !avaliableSlot.equals(that.avaliableSlot) : that.avaliableSlot != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTrip;
        result = 31 * result + (departDate != null ? departDate.hashCode() : 0);
        result = 31 * result + (idSchedule != null ? idSchedule.hashCode() : 0);
        result = 31 * result + (avaliableSlot != null ? avaliableSlot.hashCode() : 0);
        result = 31 * result + idDriver;
        return result;
    }

    @OneToMany(mappedBy = "tripInformationByIdTrip")
    public Collection<TicketEntity> getTicketsByIdTrip() {
        return ticketsByIdTrip;
    }

    public void setTicketsByIdTrip(Collection<TicketEntity> ticketsByIdTrip) {
        this.ticketsByIdTrip = ticketsByIdTrip;
    }

    @ManyToOne
    @JoinColumn(name = "idSchedule", referencedColumnName = "idSchedule")
    public ScheduleEntity getScheduleByIdSchedule() {
        return scheduleByIdSchedule;
    }

    public void setScheduleByIdSchedule(ScheduleEntity scheduleByIdSchedule) {
        this.scheduleByIdSchedule = scheduleByIdSchedule;
    }

    @ManyToOne
    @JoinColumn(name = "idDriver", referencedColumnName = "idDriver", nullable = false)
    public DriverEntity getDriverByIdDriver() {
        return driverByIdDriver;
    }

    public void setDriverByIdDriver(DriverEntity driverByIdDriver) {
        this.driverByIdDriver = driverByIdDriver;
    }
}
