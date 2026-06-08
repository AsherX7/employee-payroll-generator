package dao;

import java.sql.*;

import model.Company;
public class CompanyDAO {
	Connection conn=DBConnection.getConnection();
	
	//validating login credentials
	public boolean validateLogin(String company_id,
            String password) {
		

String sql =
"SELECT * FROM company "
+ "WHERE company_id=? AND password=?";

try {

PreparedStatement ps =
conn.prepareStatement(sql);

ps.setString(1, company_id);
ps.setString(2, password);

ResultSet rs = ps.executeQuery();

return rs.next();

} catch (SQLException e) {
e.printStackTrace();
}

return false;
}
	
	// adding companies
public boolean addCompany(Company company) {

        String sql =
            "INSERT INTO company(company_name,email,password) "
          + "VALUES(?,?,?,?)";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, company.getCompanyName());
            ps.setString(2, company.getEmail());
            ps.setString(3, company.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
}
public boolean emailExists(String email) {

    String sql =
        "SELECT * FROM company WHERE email=?";

    try {

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        return rs.next();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
}