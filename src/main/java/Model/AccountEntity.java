package Model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Account", schema = "dbo", catalog = "N2_19N12B")
public class AccountEntity {
    private int idUser;
    private String username;
    private String password;
    private boolean isOnline;
    private Boolean isDelete;
    private RoleEntity roleByIdRole;
    private Collection<TicketEntity> ticketsByIdUser;
    private Integer idRole;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "Username", nullable = false, length = 50)
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

    @Basic
    @Column(name = "isOnline", nullable = false)
    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean online) {
        isOnline = online;
    }

    @Basic
    @Column(name = "isDelete", nullable = true)
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean delete) {
        isDelete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (idUser != that.idUser) return false;
        if (isOnline != that.isOnline) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (isDelete != null ? !isDelete.equals(that.isDelete) : that.isDelete != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (isOnline ? 1 : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idRole", referencedColumnName = "idRole")
    public RoleEntity getRoleByIdRole() {
        return roleByIdRole;
    }

    public void setRoleByIdRole(RoleEntity roleByIdRole) {
        this.roleByIdRole = roleByIdRole;
    }

    @OneToMany(mappedBy = "accountByIdUser")
    public Collection<TicketEntity> getTicketsByIdUser() {
        return ticketsByIdUser;
    }

    public void setTicketsByIdUser(Collection<TicketEntity> ticketsByIdUser) {
        this.ticketsByIdUser = ticketsByIdUser;
    }

    @Basic
    @Column(name = "idRole", nullable = false, insertable = false, updatable = false)
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String toString() {
        return this.getUsername();
    }
}
