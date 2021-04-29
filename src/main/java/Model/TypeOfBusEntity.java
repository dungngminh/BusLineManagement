package Model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "TypeOfBus", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class TypeOfBusEntity {
    private int idType;
    private String typeName;
    private String brandName;
    private int slot;
    private byte[] picture;
    private boolean isDelete;
    private Collection<BusEntity> busesByIdType;

    @Id
    @Column(name = "idType", nullable = false)
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Basic
    @Column(name = "typeName", nullable = false, length = 50)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "brandName", nullable = false, length = 50)
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Basic
    @Column(name = "slot", nullable = false)
    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Basic
    @Column(name = "picture", nullable = true)
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
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

        TypeOfBusEntity that = (TypeOfBusEntity) o;

        if (idType != that.idType) return false;
        if (slot != that.slot) return false;
        if (isDelete != that.isDelete) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null) return false;
        if (!Arrays.equals(picture, that.picture)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idType;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + slot;
        result = 31 * result + Arrays.hashCode(picture);
        result = 31 * result + (isDelete ? 1 : 0);
        return result;
    }

    @OneToMany(mappedBy = "typeOfBusByIdType")
    public Collection<BusEntity> getBusesByIdType() {
        return busesByIdType;
    }

    public void setBusesByIdType(Collection<BusEntity> busesByIdType) {
        this.busesByIdType = busesByIdType;
    }
}
