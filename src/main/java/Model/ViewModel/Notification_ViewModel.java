package Model.ViewModel;

public class Notification_ViewModel {
    private Integer idNotify;
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

    public void setIdNotify(Integer idNotify) {
        this.idNotify = idNotify;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Notification_ViewModel(Integer idNotify, String notifyContent, String time) {
        this.idNotify = idNotify;
        this.notifyContent = notifyContent;
        this.time = time;
    }
}
