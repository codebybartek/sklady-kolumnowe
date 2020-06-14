package main.java;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class Main {

    public static Integer counter = 5;
    public static Statement stmt;

    public static void main(String[] args) {

        final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";




        Properties props = new Properties();
        props.put("jdbc.url", "jdbc:postgresql://localhost:5432/cinema");
        props.put("user", "postgres");
        props.put("password", "bartek");

        String sql = "CREATE TABLE IF NOT EXISTS Cinema (\n"
                + "id SERIAL PRIMARY KEY,\n"
                + "film_name text NOT NULL,\n"
                + "hour time NOT NULL,\n"
                + "date date NOT NULL,\n"
                + "ticket JSON,\n"
                + "cinema_hall text NOT NULL\n"
                + ");";


        Ticket ticket = new Ticket("normalny", "Sala1", "20:12:20");
        Ticket ticket2 = new Ticket("ulgowy", "Sala12", "21:12:20");



        String sql2 = "INSERT INTO cinema (film_name, hour, date, ticket, cinema_hall) VALUES ('Przeminelo z wiatrem', '11:45:12', '2020-12-12', '"+ticket.toString()+"', '1AC')";
        String sql3 = "INSERT INTO cinema (film_name, hour, date, ticket, cinema_hall) VALUES ('Szybcy i wściekli', '12:45:12', '2002-12-12', '"+ticket2.toString()+"', '12C')";
        String sql4 = "INSERT INTO cinema (film_name, hour, date, ticket, cinema_hall) VALUES ('Obcy', '13:15:12', '2002-12-12', '"+ticket.toString()+"', '12C')";
        String sql5 = "INSERT INTO cinema (film_name, hour, date, ticket, cinema_hall) VALUES ('Wieczny student', '12:45:12', '2002-12-12', '"+ticket2.toString()+"', '12C')";


        try(Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url"), props); Statement stmt2 = conn.createStatement()) {

            stmt = stmt2;
            FilmsShowTableBuilderManager tableManager = new FilmsShowTableBuilderManager();
            System.out.println("Success");

            // create a new table
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
            stmt.execute(sql5);

            do {
                Scanner scanner=new Scanner(System.in);
                System.out.println("<--------------KINO-------------->");
                System.out.println("-- 1. Wyświetl wszystkich seanse");
                System.out.println("-- 2. Wyświetl wycieczke o podanym ID");
                System.out.println("-- 3. Wyszukaj seans przed godzina");
                System.out.println("-- 4. Usuń wybrany seans");
                System.out.println("-- 5. Aktualizuj seans");
                System.out.println("-- 6. Dodaj seans");


                System.out.println("Podaj numer operacji do wykonania: ");
                String option=scanner.nextLine();
                switch (option) {
                    case "1":
                        tableManager.showAll();


                        break;

                    case "2":
                        System.out.println("Podaj ID seansu do wyświetlenia: ");
                        String id=scanner.nextLine();
                        tableManager.show(id);
                        break;

                    case "3":
                        System.out.println("Do której godziny seans:");
                        String hourTo=scanner.nextLine();
                        tableManager.showBefore(hourTo);
                        break;

                    case "4":
                        System.out.println("Który seans chcesz usunąć? Podaj ID: ");
                        String idToDel=scanner.nextLine();
                        tableManager.deleteFromTable(idToDel);
                        break;

                    case "5":
                        System.out.println("Podaj id seansu do aktualizacji: ");
                        String filmId = scanner.nextLine();
                        System.out.println("Podaj nowa nazwe: ");
                        String newName = scanner.nextLine();
                        tableManager.updateIntoTable(filmId,newName);
                        break;

                    case "6":
                        System.out.println("Podaj nazwe seansu");
                        String name = scanner.nextLine();
                        System.out.println("Podaj godzine: ");
                        String hour = scanner.nextLine();
                        System.out.println("Podaj date: ");
                        String date = scanner.nextLine();
                        System.out.println("Podaj sale: ");
                        String hall = scanner.nextLine();
                        List<Ticket> tickets = new ArrayList<>();
                        int i =0;
                        while (true) {
                            System.out.println("Podaj rodzaj biletu[c-close]: ");
                            String type = scanner.nextLine();
                            if(type.equals("c")){
                                break;
                            }
                            System.out.println("Podaj miejsce[c-close]: ");
                            String place = scanner.nextLine();
                            if(place.equals("c")){
                                break;
                            }
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                            String dateTicket = sdf.format(cal.getTime());
                            Ticket ticketToAdd = new Ticket(type, place, dateTicket);
                            tickets.add(ticketToAdd);
                        }

                        tableManager.addFilmShow(name, hour, date, tickets, hall);
                        break;

                    default:
                        System.out.println("Błędna intrukcja");
                }
            } while (true);





        } catch (Exception e) {
            e.printStackTrace();
        }









    }
}