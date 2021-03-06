package Model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Role", schema = "dbo", catalog = "N2_19N12B")
public class RoleEntity {
    private int idRole;
    private String roleName;
    private Collection<AccountEntity> accountsByIdRole;

    @Id
    @Column(name = "idRole", nullable = false)
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Basic
    @Column(name = "roleName", nullable = false, length = 50)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleEntity that = (RoleEntity) o;

        if (idRole != that.idRole) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRole;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "roleByIdRole")
    public Collection<AccountEntity> getAccountsByIdRole() {
        return accountsByIdRole;
    }

    public void setAccountsByIdRole(Collection<AccountEntity> accountsByIdRole) {
        this.accountsByIdRole = accountsByIdRole;
    }
}
