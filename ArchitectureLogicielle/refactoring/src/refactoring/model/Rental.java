package refactoring.model;

public class Rental {
	private Movie _movie;
	private int _daysRented;

	private final double daysRentMultiplier = 1.5;
	private final double newReleaseMultiplier = 2;

	public Rental(Movie movie, int daysRented) {
		_movie = movie;
		_daysRented = daysRented;
	}

	public double getAmount() {
		double thisAmount = 0;
		switch (getMovie().getPriceCode()) {
			case Movie.REGULAR:
				thisAmount += 2;
				if (getDaysRented() > 2) {
					thisAmount += (getDaysRented() - 2) * daysRentMultiplier;
				}
				break;
			case Movie.NEW_RELEASE:
				thisAmount += getDaysRented() * daysRentMultiplier * newReleaseMultiplier;
				break;
			case Movie.CHILDRENS:
				thisAmount += 1.5;
				if (getDaysRented() > 3)
					thisAmount += (getDaysRented() - 3) * daysRentMultiplier;
				break;
		}
		return thisAmount;
	}

	public int getFrequentRenterPoints() {
		return 1 + (((getMovie().getPriceCode() == Movie.NEW_RELEASE) && (getDaysRented() > 1)) ? 1 : 0);
	}

	public int getDaysRented() {
		return _daysRented;
	}

	public Movie getMovie() {
		return _movie;
	}
}
