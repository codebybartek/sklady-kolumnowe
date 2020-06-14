package pl.kielce.tu.cassandra.builder;

import com.datastax.oss.driver.api.core.CqlSession;
import pl.kielce.tu.cassandra.mapper.Ticket;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

	public static Integer counter = 0;

	public static void main(String[] args) {

		final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

		try (CqlSession session = CqlSession.builder().build()) {
			KeyspaceBuilderManager keyspaceManager = new KeyspaceBuilderManager(session, "cinema");
			keyspaceManager.dropKeyspace();
			keyspaceManager.selectKeyspaces();
			keyspaceManager.createKeyspace();
			keyspaceManager.useKeyspace();
			
			FilmsShowTableBuilderManager tableManager = new FilmsShowTableBuilderManager(session);
			tableManager.createTable();

			tableManager.insertIntoTable();

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
						int id=scanner.nextInt();
						tableManager.show(id);
						break;

					case "3":
						System.out.println("Do której godziny seans:");
						int hourTo=scanner.nextInt();
						tableManager.showBefore(hourTo);
						break;

					case "4":
						System.out.println("Który seans chcesz usunąć? Podaj ID: ");
						int idd =scanner.nextInt();
						tableManager.deleteFromTable(idd);
						break;

					case "5":
						System.out.println("Podaj id seansu do aktualizacji: ");
						String filmIDD = scanner.nextLine();
						System.out.println("Podaj nowa nazwe: ");
						String newName = scanner.nextLine();
						tableManager.updateIntoTable(Integer.parseInt(tripId),newName);
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
							Ticket ticket = new Ticket(type, place, dateTicket);
							tickets.add(ticket);
						}

						tableManager.addFilmShow(name, hour, date, tickets, hall);
						break;

					default:
						System.out.println("Błędna intrukcja");
				}
			} while (true);

		}
	}
}