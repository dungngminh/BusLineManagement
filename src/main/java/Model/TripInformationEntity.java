package Model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TripInformation", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class TripInformationEntity {
    private int idTrip;
    private Timestamp departDate;
    private Integer avaliableSlot;

    @Id
    @Column(name = "idTrip")
    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    @Basic
    @Column(name = "departDate")
    public Timestamp getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Timestamp departDate) {
        this.departDate = departDate;
    }

    @Basic
    @Column(name = "avaliableSlot")
    public Integer getAvaliableSlot() {
        return avaliableSlot;
    }

    public void setAvaliableSlot(Integer avaliableSlot) {
        this.avaliableSlot = avaliableSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripInformationEntity that = (TripInformationEntity) o;

        if (idTrip != that.idTrip) return false;
        if (departDate != null ? !departDate.equals(that.departDate) : that.departDate != null) return false;
        if (avaliableSlot != null ? !avaliableSlot.equals(that.avaliableSlot) : that.avaliableSlot != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTrip;
        result = 31 * result + (departDate != null ? departDate.hashCode() : 0);
        result = 31 * result + (avaliableSlot != null ? avaliableSlot.hashCode() : 0);
        return result;
    }
}
