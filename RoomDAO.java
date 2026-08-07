package pro;

import java.sql.*;
import java.util.ArrayList;

public class RoomDAO 
{

    public Room getFirstAvailableRoom() 
    {
        String sql = "SELECT * FROM rooms WHERE is_occupied = FALSE ORDER BY room_number LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                return mapRow(rs);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<Room> getVacantRooms()
    {
        ArrayList<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE is_occupied = FALSE ORDER BY room_number";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) 
        {
            while (rs.next())
            {
                list.add(mapRow(rs));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }


    public void assignRoom(int roomNumber)
    {
        String sql = "UPDATE rooms SET is_occupied = TRUE WHERE room_number = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, roomNumber);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void vacateRoom(int roomNumber) 
    {
        String sql = "UPDATE rooms SET is_occupied = FALSE WHERE room_number = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, roomNumber);
            ps.executeUpdate();
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int countRooms() 
    {
        String sql = "SELECT COUNT(*) FROM rooms";
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

  
    public void insertRoom(int roomNumber) 
    {
        String sql = "INSERT INTO rooms (room_number, is_occupied) VALUES (?, FALSE)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
        {
            ps.setInt(1, roomNumber);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private Room mapRow(ResultSet rs) throws SQLException 
    {
        Room room = new Room(rs.getInt("room_number"));
        if (rs.getBoolean("is_occupied"))
        {
            room.assignRoom(); 
        }
        return room;
    }
}
