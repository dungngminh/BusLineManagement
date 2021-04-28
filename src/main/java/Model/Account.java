package Model;


public class Account {
    private int idUSer;
    private String m_username;
    private String m_password;

    public int getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(int idUSer) {
        this.idUSer = idUSer;
    }

    public String getUsername() {
        return m_username;
    }

    public String getPassword() {
        return m_password;
    }

    public void setUsername(String username) {
        this.m_username = username;
    }

    public void setPassword(String password) {
        this.m_password = password;
    }

    public Account(String username, String password) {
        m_username = username;
        m_password = password;
    }


}
