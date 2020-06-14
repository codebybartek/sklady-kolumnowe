package main.java;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static main.java.Main.stmt;


public class FilmsShowTableBuilderManager{


	public void addFilmShow(String name, String hour, String date, List<Ticket> tickets, String hall) throws SQLException {
        String sqlInsert = "INSERT INTO cinema (film_name, hour, date, ticket, cinema_hall) VALUES ('"+name+"', '"+hour+"', '"+date+"', '"+tickets.toString()+"', '"+hall+"')";
            stmt.execute(sqlInsert);

    }

	public void updateIntoTable(String id, String name) {
        String sqlUpdate = "UPDATE cinema SET film_name = '"+name+"' WHERE id ='"+id+"'";
        try {
            stmt.executeUpdate(sqlUpdate);
            System.out.print("The film with id: "+id+" was updated!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
	}

	public void deleteFromTable(String id) {
        String deleteOne= "Delete from cinema where id='"+id+"'";
        try {
            stmt.executeUpdate(deleteOne);
            System.out.print("The film with id: "+id+" was deleted!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
	}

	public void showAll() throws SQLException {
        String showAll = "Select * from cinema";
        ResultSet rs = null;
            rs = stmt.executeQuery(showAll);
            while(rs.next())
            {
                System.out.print("Film show: ");
                System.out.print(rs.getInt("id") + ", ");
                System.out.print(rs.getString("film_name") + ", ");
                System.out.print(rs.getString("hour") + ", ");
                System.out.print(rs.getString("date") + ", ");
                System.out.print(rs.getString("ticket") + ", ");
                System.out.print(rs.getString("cinema_hall") + ", ");
                System.out.println();
            }


	}

	public void show(String id) {
        String showAOne = "Select * from cinema where id='"+id+"'";
        ResultSet rs2 = null;
        try {
            rs2 = stmt.executeQuery(showAOne);
            while(rs2.next())
            {
                System.out.print("Film show: ");
                System.out.print(rs2.getInt("id") + ", ");
                System.out.print(rs2.getString("film_name") + ", ");
                System.out.print(rs2.getString("hour") + ", ");
                System.out.print(rs2.getString("date") + ", ");
                System.out.print(rs2.getString("ticket") + ", ");
                System.out.print(rs2.getString("cinema_hall") + ", ");
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

	}

	public void showBefore(String hour) throws SQLException {
        String showAOne2 = "Select * from cinema where cast(hour as time)<'"+hour+"'";
        ResultSet rs3 = null;
        try {
            rs3 = stmt.executeQuery(showAOne2);
            while(rs3.next())
            {
                System.out.print("Film show: ");
                System.out.print(rs3.getInt("id") + ", ");
                System.out.print(rs3.getString("film_name") + ", ");
                System.out.print(rs3.getString("hour") + ", ");
                System.out.print(rs3.getString("date") + ", ");
                System.out.print(rs3.getString("ticket") + ", ");
                System.out.print(rs3.getString("cinema_hall") + ", ");
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
	}
}
