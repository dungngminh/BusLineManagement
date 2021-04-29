package Model;

import javax.persistence.*;

@Entity
@Table(name = "Bus", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class BusEntity {
    private int idBus;
    private String busName;
    private String plateNumber;
    private Boolean isDelete;
    private int status;

    @Id
    @Column(name = "idBus")
    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    @Basic
    @Column(name = "busName")
    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    @Basic
    @Column(name = "plateNumber")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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

        BusEntity busEntity = (BusEntity) o;

        if (idBus != busEntity.idBus) return false;
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
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + status;
        return result;
    }
}
