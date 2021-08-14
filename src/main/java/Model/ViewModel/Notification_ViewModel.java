package Model.ViewModel;

public class Notification_ViewModel {
    private Integer idNotify;



    private String nameUser;
    private String notifyContent;
    private String time;

    public Integer getIdNotify() {
        return idNotify;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public String getTime() {
        return time;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setIdNotify(Integer idNotify) {
        this.idNotify = idNotify;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Notification_ViewModel(Integer idNotify, String nameUser, String notifyContent, String time) {
        this.idNotify = idNotify;
        this.nameUser = nameUser;
        this.notifyContent = notifyContent;
        this.time = time;
    }
}
