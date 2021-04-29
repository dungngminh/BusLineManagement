package Model;

import javax.persistence.*;

@Entity
@Table(name = "Ticket", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class TicketEntity {
    private int idTicket;
    private String nameTicket;
    private int price;
    private Integer idUser;
    private Integer idTrip;
    private Boolean isDelete;
    private String phoneNumber;
    private String nameCustomer;
    private int destination;
    private Integer status;
    private AccountEntity accountByIdUser;
    private TripInformationEntity tripInformationByIdTrip;

    @Id
    @Column(name = "idTicket", nullable = false)
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
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
    @Column(name = "Price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
    @Column(name = "isDelete", nullable = true)
    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Basic
    @Column(name = "phoneNumber", nullable = false, length = 10)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "nameCustomer", nullable = false, length = 50)
    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    @Basic
    @Column(name = "destination", nullable = false)
    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "status", nullable = true)
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
        if (price != that.price) return false;
        if (destination != that.destination) return false;
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
        result = 31 * result + price;
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        result = 31 * result + (idTrip != null ? idTrip.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (nameCustomer != null ? nameCustomer.hashCode() : 0);
        result = 31 * result + destination;
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
