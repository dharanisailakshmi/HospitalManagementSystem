package pro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentDAO
{
    public void insert(Appointment appt) 
    {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_date) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            ps.setInt(1, appt.getDoctorId());
            ps.setInt(2, appt.getPatientId());
            ps.setDate(3, Date.valueOf(appt.getDate()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) 
            {
                if (keys.next())
                {
                    appt.setAppointmentId(keys.getInt(1));
                }
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

   
    public ArrayList<Appointment> getByDoctorId(int doctorId) 
    {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_date";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, doctorId);
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

   
    public ArrayList<Appointment> getByPatientAndMonth(int patientId, int month, int year)
    {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ? " +
                     "AND MONTH(appointment_date) = ? AND YEAR(appointment_date) = ? " +
                     "ORDER BY appointment_date";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, patientId);
            ps.setInt(2, month);
            ps.setInt(3, year);
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

    public boolean existsForPatientOnDate(int patientId, LocalDate date) 
    {
        String sql = "SELECT 1 FROM appointments WHERE patient_id = ? AND appointment_date = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, patientId);
            ps.setDate(2, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) 
            {
                return rs.next();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private Appointment mapRow(ResultSet rs) throws SQLException
    {
        return new Appointment(
            rs.getInt("appointment_id"),
            rs.getInt("doctor_id"),
            rs.getInt("patient_id"),
            rs.getDate("appointment_date").toLocalDate()
        );
    }
}
