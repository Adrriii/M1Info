package refactoring.application;

import refactoring.model.*;

public class Main {

    public static void main(String args[]) {
        Movie rogueOne = new Movie("RogueOne",1);
        Movie rdn = new Movie("Reine des neiges",1);
        Movie sw3 = new Movie("Star Wars III",1);

        Customer customer = new Customer("Jean");

        Rental rental = new Rental(rogueOne, 5);
        Rental rental2 = new Rental(rdn, 7);
        Rental rental3 = new Rental(sw3, 4);
    }

}