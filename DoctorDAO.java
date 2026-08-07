package pro;

import java.sql.*;
import java.util.ArrayList;

public class DoctorDAO 
{

    public void insert(Doctor doctor) 
    {
        String sql = "INSERT INTO doctors (doctor_id, username, password, name, specialty) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, doctor.getDoctorId());
            ps.setString(2, doctor.getUsername());
            ps.setString(3, doctor.getPassword());
            ps.setString(4, doctor.getName());
            ps.setString(5, doctor.getSpecialty());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Doctor findByCredentials(String username, String password) 
    {
        String sql = "SELECT * FROM doctors WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) return mapRow(rs);
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    // Replaces: findDoctorById()
    public Doctor findById(int doctorId) 
    {
        String sql = "SELECT * FROM doctors WHERE doctor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) return mapRow(rs);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    
    public Doctor findByUsername(String username) 
    {
        String sql = "SELECT * FROM doctors WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) return mapRow(rs);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public ArrayList<Doctor> getAll() 
    {
        ArrayList<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY doctor_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) 
        {
            while (rs.next()) list.add(mapRow(rs));
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return list;
    }

  
    public ArrayList<Doctor> findBySpecialty(String specialty) 
    {
        ArrayList<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE specialty = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, specialty);
            try (ResultSet rs = ps.executeQuery()) 
            {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return list;
    }

  
    public ArrayList<String> getUniqueSpecialties()
    {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT specialty FROM doctors ORDER BY specialty";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) list.add(rs.getString("specialty"));
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return list;
    }

    public int countDoctors() 
    {
        String sql = "SELECT COUNT(*) FROM doctors";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            if (rs.next()) return rs.getInt(1);
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    private Doctor mapRow(ResultSet rs) throws SQLException
    {
        return new Doctor(
            rs.getString("username"),
            rs.getString("password"),
            rs.getInt("doctor_id"),
            rs.getString("name"),
            rs.getString("specialty")
        );
    }
}
