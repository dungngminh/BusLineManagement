package Model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Notification", schema = "dbo", catalog = "N2_19N12B")
public class NotificationEntity {
    private Integer idNotify;
    private Timestamp time;
    private String notifyContent;
    private Integer idUser;
    private AccountEntity accountByIdUser;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idNotify", nullable = false)
    public Integer getIdNotify() {
        return idNotify;
    }

    public void setIdNotify(Integer idNotify) {
        this.idNotify = idNotify;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "notifyContent", nullable = false, length = 100)
    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    @Basic
    @Column(name = "idUser", nullable = false, insertable = false, updatable = false)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationEntity that = (NotificationEntity) o;

        if (idNotify != that.idNotify) return false;
        if (idUser != that.idUser) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (notifyContent != null ? !notifyContent.equals(that.notifyContent) : that.notifyContent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = idNotify;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (notifyContent != null ? notifyContent.hashCode() : 0);
        result = 31 * result + idUser;
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
}
