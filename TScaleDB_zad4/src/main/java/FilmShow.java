package main.java;


import java.util.List;

public class FilmShow {

	private Integer id;

	private String filmName;

	private String date;

	private String hour;

	private List<Ticket> ticket;

	private String cinemaHall;


	public FilmShow() {
	}

	public FilmShow(Integer id, List<Ticket> ticket, String date, String hour, String filmName, String cinemaHall) {
		this.id = id;
		this.ticket = ticket;
		this.hour = hour;
		this.date = date;
		this.filmName = filmName;
		this.cinemaHall = cinemaHall;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Ticket> getTicket() {
		return ticket;
	}

	public void setTicket(List<Ticket> ticket) {
		this.ticket = ticket;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getCinemaHall() {
		return cinemaHall;
	}

	public void setCinemaHall(String cinemaHall) {
		this.cinemaHall = cinemaHall;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("student {")
				.append(id + ",")
				.append(filmName + ",")
				.append(hour + ",")
				.append(cinemaHall + ",")
				.append(ticket.get(0).toString());
		return builder.toString();
	}
}
