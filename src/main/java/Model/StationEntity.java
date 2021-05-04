package Model;

import javax.persistence.*;

@Entity
@Table(name = "Station", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class StationEntity {
    private int idStation;
    private String stationName;
    private Integer idProvince;
    private ProvinceEntity provinceByIdProvince;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idStation", nullable = false)
    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    @Basic
    @Column(name = "stationName", nullable = false, length = 50)
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "idProvince", nullable = true, insertable = false, updatable = false)
    public Integer getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(Integer idProvince) {
        this.idProvince = idProvince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationEntity that = (StationEntity) o;

        if (idStation != that.idStation) return false;
        if (stationName != null ? !stationName.equals(that.stationName) : that.stationName != null) return false;
        if (idProvince != null ? !idProvince.equals(that.idProvince) : that.idProvince != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStation;
        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
        result = 31 * result + (idProvince != null ? idProvince.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idProvince", referencedColumnName = "idProvince")
    public ProvinceEntity getProvinceByIdProvince() {
        return provinceByIdProvince;
    }

    public void setProvinceByIdProvince(ProvinceEntity provinceByIdProvince) {
        this.provinceByIdProvince = provinceByIdProvince;
    }
    public String toString() {
        return this.getStationName();
    }
}
