package restaurantrush.game;

import restaurantrush.restaurant.Customer;
import restaurantrush.restaurant.CustomerStatus;
import restaurantrush.restaurant.Table;

/**
 * Host seats at most one queued customer per turn.
 *
 * @author Jack
 */
public class Host {

    // Record the last turn in which the Host seated a customer
    private int lastSeatedTurn = -1;

    public Host() {
        // Empty constructor
    }

    public ActionResult seat(Customer customer, Table table, int turn) {

        // Check customer input
        if (customer == null) {
            return ActionResult.fail("No customer selected");
        }

        // Only customers in the waiting queue can be seated
        if (customer.getStatus() != CustomerStatus.WAITING) {
            return ActionResult.fail("Customer is not waiting in the queue");
        }

        // Check table input
        if (table == null) {
            return ActionResult.fail("No table selected");
        }

        // Check whether the table is free
        if (!table.isFree()) {
            return ActionResult.fail("Table has been taken");
        }

        // Host can seat only one customer per turn
        if (lastSeatedTurn == turn) {
            return ActionResult.fail("Host has already seated a customer this turn");
        }

        // Seat the customer
        table.seat(customer);
        customer.markSeated();

        // Record the turn
        lastSeatedTurn = turn;

        return ActionResult.ok("Host seated customer successfully");
    }
}