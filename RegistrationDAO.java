import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {
    private final String url = "https://sqliteonline.com/#sharedb=p%3Agrady";
    private final String user = "rohini";
    private final String password = "1234";

    
    public boolean createRegistration(String name, String email, Date dateOfBirth, String gender, String address,
                                      String city, String state, String country, String postalCode) {
        String sql = "INSERT INTO Registration (Name, Email, DateOfBirth, Gender, Address, City, State, Country, PostalCode) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setDate(3, dateOfBirth);
            stmt.setString(4, gender);
            stmt.setString(5, address);
            stmt.setString(6, city);
            stmt.setString(7, state);
            stmt.setString(8, country);
            stmt.setString(9, postalCode);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public Registration readRegistration(int id) {
        String sql = "SELECT * FROM Registration WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Registration(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Gender"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("Country"),
                        rs.getString("PostalCode")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public boolean updateRegistration(int id, String city) {
        String sql = "UPDATE Registration SET City = ? WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteRegistration(int id) {
        String sql = "DELETE FROM Registration WHERE ID = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public static void main(String[] args) {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        
        
        registrationDAO.createRegistration("Rohini M", "rohini@gmail.com", Date.valueOf("1999-05-15"),
                "female", "18 Main St", "Rajajinagar", "Karnatake", "India", "5546");

        
        Registration registration = registrationDAO.readRegistration(1);
        System.out.println("Read registration: " + registration);

        
        registrationDAO.updateRegistration(1, "Newtown");

        
        registrationDAO.deleteRegistration(1);
    }
}

class Registration {
    private int id;
    private String name;
    private String email;
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;


    public Registration(int id, String name, String email, Date dateOfBirth, String gender, String address, String city, String state, String country, String postalCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }

}


