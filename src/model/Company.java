package model;


public class Company {

    private String companyId;
    private String companyName;
    private String email;
    private String password;

    public Company() {}

    public Company(String companyId, String companyName,
                   String email, String password) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.email = email;
        this.password = password;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}