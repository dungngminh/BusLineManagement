package Model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Driver", schema = "dbo", catalog = "N2_19N12B")
public class DriverEntity {
    private int idDriver;
    private String nameDriver;
    private String phone;
    private String address;
    private int status;
    private Boolean isDelete;
    private Collection<TripInformationEntity> tripInformationsByIdDriver;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idDriver", nullable = false)
    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    @Basic
    @Column(name = "nameDriver", nullable = false, length = 50)
    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }

    @Basic
    @Column(name = "phone", nullable = false, unique = true, length = 10)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "isDelete", nullable = false)
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean delete) {
        this.isDelete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverEntity that = (DriverEntity) o;

        if (idDriver != that.idDriver) return false;
        if (status != that.status) return false;
        if (isDelete != that.isDelete) return false;
        if (nameDriver != null ? !nameDriver.equals(that.nameDriver) : that.nameDriver != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDriver;
        result = 31 * result + (nameDriver != null ? nameDriver.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (isDelete ? 1 : 0);
        return result;
    }

    @OneToMany(mappedBy = "driverByIdDriver")
    public Collection<TripInformationEntity> getTripInformationsByIdDriver() {
        return tripInformationsByIdDriver;
    }

    public void setTripInformationsByIdDriver(Collection<TripInformationEntity> tripInformationsByIdDriver) {
        this.tripInformationsByIdDriver = tripInformationsByIdDriver;
    }
}
