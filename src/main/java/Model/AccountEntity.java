package Model;

import Services.DAL;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Account", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class AccountEntity {
    private Integer idUser;
    private String username;
    private String password;
    private Collection<RoleAccountEntity> roleAccountsByIdUser;
    private Collection<TicketEntity> ticketsByIdUser;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "Username", nullable = false, length = 50, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "Password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (idUser != that.idUser) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getUsername();
    }

    @OneToMany(mappedBy = "accountByIdUser", fetch = FetchType.EAGER )
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<RoleAccountEntity> getRoleAccountsByIdUser() {
        return roleAccountsByIdUser;
    }

    public void setRoleAccountsByIdUser(Collection<RoleAccountEntity> roleAccountsByIdUser) {
        this.roleAccountsByIdUser = roleAccountsByIdUser;
    }

    @OneToMany(mappedBy = "accountByIdUser", fetch = FetchType.LAZY )
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<TicketEntity> getTicketsByIdUser() {
        return ticketsByIdUser;
    }

    public void setTicketsByIdUser(Collection<TicketEntity> ticketsByIdUser) {
        this.ticketsByIdUser = ticketsByIdUser;
    }
}
