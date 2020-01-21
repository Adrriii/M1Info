package refactoring.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import refactoring.model.*;

public class CustomerTest {

    @Test
    public void StatementTest() {
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

        String output = customer.statement();

        String expected = "Rental Record for Jean\n\tRogueOne\t15.0 \n\tReine des neiges\t7.5 \n\tStar Wars III\t5.0 \nAmount owned is 27.5\nYou earned 4 frequent renter points\n";

        System.out.println(output);
        System.out.println(expected);
        assertEquals(output,expected);
    }
}