package Model;

import javax.persistence.*;

@Entity
@Table(name = "RoleAccount", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class RoleAccountEntity {
    private int stt;
    private Integer idUser;
    private Integer idRole;
    private AccountEntity accountByIdUser;
    private RoleEntity roleByIdRole;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "stt", nullable = false)
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
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
    @Column(name = "idRole", nullable = true, insertable = false, updatable = false)
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleAccountEntity that = (RoleAccountEntity) o;

        if (stt != that.stt) return false;
        if (idUser != null ? !idUser.equals(that.idUser) : that.idUser != null) return false;
        if (idRole != null ? !idRole.equals(that.idRole) : that.idRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stt;
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        result = 31 * result + (idRole != null ? idRole.hashCode() : 0);
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
    @JoinColumn(name = "idRole", referencedColumnName = "idRole")
    public RoleEntity getRoleByIdRole() {
        return roleByIdRole;
    }

    public void setRoleByIdRole(RoleEntity roleByIdRole) {
        this.roleByIdRole = roleByIdRole;
    }
}
