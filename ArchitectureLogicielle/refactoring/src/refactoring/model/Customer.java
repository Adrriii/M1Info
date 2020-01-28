package refactoring.model;

import java.util.*;

public class Customer {
	private String _name;
	private Vector<Rental> _rentals;

	public Customer(String name) {
		_name = name;
		_rentals = new Vector<Rental>();
	}

	public void addRental(Rental rental) {
		_rentals.addElement(rental);
	}

	public String getName() {
		return _name;
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = getFrequentRenterPoints();
		Enumeration<Rental> rentals = _rentals.elements();
		String result = "Rental Record for " + getName() + "\n";

		while (rentals.hasMoreElements()) {
			Rental currentRental = (Rental) rentals.nextElement();
			
			double thisAmount = currentRental.getAmount();

			result += "\t" + currentRental.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + " \n";
			totalAmount += thisAmount;
		}

		result += "Amount owned is " + String.valueOf(totalAmount) + "\n";
		result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";

		return result;
	}

	public double getOwnedAmount() {
		double totalAmount = 0;
		Enumeration<Rental> rentals = _rentals.elements();

		while (rentals.hasMoreElements()) {
			totalAmount += ((Rental) rentals.nextElement()).getAmount();
		}

		return totalAmount;
	}

	public int getFrequentRenterPoints() {
		int frequentRenterPoints = 0;
		Enumeration<Rental> rentals = _rentals.elements();

		while (rentals.hasMoreElements()) {
			frequentRenterPoints += ((Rental) rentals.nextElement()).getFrequentRenterPoints();
		}

		return frequentRenterPoints;
	}
}
