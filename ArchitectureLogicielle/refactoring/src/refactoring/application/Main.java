package refactoring.application;

import refactoring.model.*;

public class Main {

    public static void main(String args[]) {
        Movie rogueOne = new Movie("RogueOne",Movie.NEW_RELEASE);
        Movie rdn = new Movie("Reine des neiges",Movie.CHILDRENS);
        Movie sw3 = new Movie("Star Wars III", Movie.REGULAR);

        Customer customer = new Customer("Jean");

        Rental rental = new Rental(rogueOne, 5);
        Rental rental2 = new Rental(rdn, 7);
        Rental rental3 = new Rental(sw3, 4);

        customer.addRental(rental);
        customer.addRental(rental2);
        customer.addRental(rental3);

        String output = customer.getName() + ":\n";
        output += customer.statement();

        System.out.println(output);
    }

}