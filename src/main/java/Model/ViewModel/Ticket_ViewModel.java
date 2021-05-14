package Model.ViewModel;

public class Ticket_ViewModel {
    private String nameTicket;
    private String route;
    private String departTime;
    private String nameCustomer;
    private String phoneCustomer;
    private String isPaid;
    private Integer price;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Ticket_ViewModel(String nameTicket, String route, String departTime, String nameCustomer, String phoneCustomer, String isPaid, Integer price) {
        this.nameTicket = nameTicket;
        this.route = route;
        this.departTime = departTime;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.isPaid = isPaid;
        this.price = price;
    }
}
