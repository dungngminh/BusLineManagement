package Model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Province", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class ProvinceEntity {
    private int idProvince;
    private String provinceName;
    private Collection<StationEntity> stationsByIdProvince;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idProvince", nullable = false)
    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    @Basic
    @Column(name = "provinceName", nullable = false, length = 50)
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvinceEntity that = (ProvinceEntity) o;

        if (idProvince != that.idProvince) return false;
        if (provinceName != null ? !provinceName.equals(that.provinceName) : that.provinceName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProvince;
        result = 31 * result + (provinceName != null ? provinceName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "provinceByIdProvince")
    public Collection<StationEntity> getStationsByIdProvince() {
        return stationsByIdProvince;
    }

    public void setStationsByIdProvince(Collection<StationEntity> stationsByIdProvince) {
        this.stationsByIdProvince = stationsByIdProvince;
    }
}
