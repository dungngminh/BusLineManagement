package Model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "Ticket", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class TicketEntity {
    private Integer idTicket;
    private String nameTicket;
    private Integer idUser;
    private Integer idTrip;
    private Boolean isDelete;
    private String phoneNumber;
    private String nameCustomer;
    private Integer status;
    private AccountEntity accountByIdUser;
    private TripInformationEntity tripInformationByIdTrip;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idTicket", nullable = false)
    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    @Basic
    @Column(name = "nameTicket", nullable = true, length = 50)
    public String getNameTicket() {
        return nameTicket;
    }

    public void setNameTicket(String nameTicket) {
        this.nameTicket = nameTicket;
    }

    @Basic
    @Column(name = "idUser", nullable = true, insertable = false, updatable = false)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "idTrip", nullable = true, insertable = false, updatable = false)
    public Integer getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(Integer idTrip) {
        this.idTrip = idTrip;
    }

    @Basic
    @Column(name = "isDelete", nullable = false)
    @ColumnDefault(value = "false")
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean delete) {
        this.isDelete = delete;
    }

    @Basic
    @Column(name = "phoneNumber", nullable = true, length = 10)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "nameCustomer", nullable = true, length = 50)
    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    @Basic
    @Column(name = "status", nullable = false)
    @ColumnDefault(value = "0")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (idTicket != that.idTicket) return false;
        if (nameTicket != null ? !nameTicket.equals(that.nameTicket) : that.nameTicket != null) return false;
        if (idUser != null ? !idUser.equals(that.idUser) : that.idUser != null) return false;
        if (idTrip != null ? !idTrip.equals(that.idTrip) : that.idTrip != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (nameCustomer != null ? !nameCustomer.equals(that.nameCustomer) : that.nameCustomer != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTicket;
        result = 31 * result + (nameTicket != null ? nameTicket.hashCode() : 0);
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        result = 31 * result + (idTrip != null ? idTrip.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (nameCustomer != null ? nameCustomer.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    public AccountEntity getAccountByIdUser() {
        return accountByIdUser;
    }

    public void setAccountByIdUser(AccountEntity accountByIdUser) {
        this.accountByIdUser = accountByIdUser;
    }

    @ManyToOne
    @JoinColumn(name = "idTrip", referencedColumnName = "idTrip")
    public TripInformationEntity getTripInformationByIdTrip() {
        return tripInformationByIdTrip;
    }

    public void setTripInformationByIdTrip(TripInformationEntity tripInformationByIdTrip) {
        this.tripInformationByIdTrip = tripInformationByIdTrip;
    }
}
