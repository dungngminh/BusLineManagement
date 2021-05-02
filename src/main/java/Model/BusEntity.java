package Model;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "BusEntity")
@Table(name = "Bus", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class BusEntity {
    private int idBus;
    private String busName;
    private String plateNumber;
    private int idType;
    private Boolean isDelete;
    private int status;
    private TypeOfBusEntity typeOfBusByIdType;
    private Collection<ScheduleEntity> schedulesByIdBus;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idBus", nullable = false)
    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    @Basic
    @Column(name = "busName", nullable = false, length = 50)
    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    @Basic
    @Column(name = "plateNumber", nullable = false, length = 10)
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Basic
    @Column(name = "idType", nullable = false, insertable = false, updatable = false)
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Basic
    @Column(name = "isDelete", nullable = true)
    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "status", nullable = false)
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

        BusEntity busEntity = (BusEntity) o;

        if (idBus != busEntity.idBus) return false;
        if (idType != busEntity.idType) return false;
        if (status != busEntity.status) return false;
        if (busName != null ? !busName.equals(busEntity.busName) : busEntity.busName != null) return false;
        if (plateNumber != null ? !plateNumber.equals(busEntity.plateNumber) : busEntity.plateNumber != null)
            return false;
        if (isDelete != null ? !isDelete.equals(busEntity.isDelete) : busEntity.isDelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBus;
        result = 31 * result + (busName != null ? busName.hashCode() : 0);
        result = 31 * result + (plateNumber != null ? plateNumber.hashCode() : 0);
        result = 31 * result + idType;
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }

    @ManyToOne(targetEntity = TypeOfBusEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idType")
    public TypeOfBusEntity getTypeOfBusByIdType() {
        return typeOfBusByIdType;
    }

    public void setTypeOfBusByIdType(TypeOfBusEntity typeOfBusByIdType) {
        this.typeOfBusByIdType = typeOfBusByIdType;
    }

    @OneToMany(mappedBy = "busByIdBus", fetch = FetchType.LAZY)
    public Collection<ScheduleEntity> getSchedulesByIdBus() {
        return schedulesByIdBus;
    }

    public void setSchedulesByIdBus(Collection<ScheduleEntity> schedulesByIdBus) {
        this.schedulesByIdBus = schedulesByIdBus;
    }
}
