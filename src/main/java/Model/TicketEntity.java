package Model;

import javax.persistence.*;

@Entity
@Table(name = "Ticket", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class TicketEntity {
    private int idTicket;
    private String nameTicket;
    private int price;
    private Boolean isDelete;
    private String phoneNumber;
    private String nameCustomer;
    private int destination;
    private Integer status;

    @Id
    @Column(name = "idTicket")
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    @Basic
    @Column(name = "nameTicket")
    public String getNameTicket() {
        return nameTicket;
    }

    public void setNameTicket(String nameTicket) {
        this.nameTicket = nameTicket;
    }

    @Basic
    @Column(name = "Price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "nameCustomer")
    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    @Basic
    @Column(name = "destination")
    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "status")
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
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (nameCustomer != null ? nameCustomer.hashCode() : 0);
        result = 31 * result + destination;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
