package Model.ViewModel;

public class Ticket_ViewModel {

    private Integer idTicket;
    private String nameTicket;
    private String route;
    private String departTime;
    private String departDate;
    private String nameCustomer;
    private String phoneCustomer;
    private String isPaid;
    private String price;

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getNameTicket() {
        return nameTicket;
    }

    public void setNameTicket(String nameTicket) {
        this.nameTicket = nameTicket;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Ticket_ViewModel(Integer idTicket, String nameTicket, String route, String departTime, String departDate, String nameCustomer, String phoneCustomer, String isPaid, String price) {
        this.idTicket = idTicket;
        this.nameTicket = nameTicket;
        this.route = route;
        this.departTime = departTime;
        this.departDate = departDate;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.isPaid = isPaid;
        this.price = price;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }
}
