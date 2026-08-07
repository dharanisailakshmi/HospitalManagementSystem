package pro;

import java.sql.*;
import java.util.ArrayList;

/**
 * Replaces: the "patients" ArrayList plus registerPatient(), viewPatients(),
 * patientLogin()'s loop, findPatientById(), findPatientByUsername() in
 * HospitalManagementSystem.
 */
public class PatientDAO 
{

    
    public void insert(Patient patient)
    {
        String sql = "INSERT INTO patients (patient_id, username, password, name, age, address, disease, room_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, patient.getPatientId());
            ps.setString(2, patient.getUsername());
            ps.setString(3, patient.getPassword());
            ps.setString(4, patient.getName());
            ps.setInt(5, patient.getAge());
            ps.setString(6, patient.getAddress());
            ps.setString(7, patient.getDisease());
            ps.setInt(8, patient.getRoomNumber());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

   
    public Patient findByCredentials(String username, String password)
    {
        String sql = "SELECT * FROM patients WHERE username=? AND password=?";
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

  
    public Patient findById(int patientId) 
    {
        String sql = "SELECT * FROM patients WHERE patient_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

   
    public Patient findByUsername(String username)
    {
        String sql = "SELECT * FROM patients WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, username);
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

  
    public ArrayList<Patient> getAll() 
    {
        ArrayList<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id";
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

    public void updateDisease(int patientId, String disease)
    {
        String sql = "UPDATE patients SET disease=? WHERE patient_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setString(1, disease);
            ps.setInt(2, patientId);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int countPatients() 
    {
        String sql = "SELECT COUNT(*) FROM patients";
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

    private Patient mapRow(ResultSet rs) throws SQLException
    {
        return new Patient(
            rs.getString("username"),
            rs.getString("password"),
            rs.getInt("patient_id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("address"),
            rs.getString("disease"),
            rs.getInt("room_number")
        );
    }
}
