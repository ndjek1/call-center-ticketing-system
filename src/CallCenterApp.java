import Models.Ticket;
import Services.TicketingSystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CallCenterApp {

    private static final TicketingSystem system = new TicketingSystem();
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {

        boolean running = true;
        while (running) {
            printMenu();
            int choice = -1;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Skip to next iteration
            }

// Allow user to select what CRUD action they want to execute using a switch case
            switch (choice) {
                case 1:
                    createTicket();
                    break;
                case 2:
                    system.viewAllTickets();
                    break;
                case 3:
                    viewTicketById();
                    break;
                case 4:
                    updateTicket();
                    break;
                case 5:
                    deleteTicket();
                    break;
                case 6:
                    searchTickets();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();

    }
// A method to display the menu to the user
    private static void printMenu() {
        System.out.println("\n--- Virtual Solutions Call Center Ticketing System ---");
        System.out.println("1. Create New Ticket");
        System.out.println("2. View All Tickets");
        System.out.println("3. View Ticket by ID");
        System.out.println("4. Update Ticket");
        System.out.println("5. Delete Ticket");
        System.out.println("6. Search Tickets");
        System.out.println("0. Exit");
        System.out.println("----------------------------------------------------");
    }
//This is a method to Allow the user to create new ticket
private static void createTicket() {
    System.out.println("\n--- Create New Ticket ---");

    String name;
    while (true) {
        System.out.print("Enter Customer Name: ");
        name = scanner.nextLine().trim(); // .trim() removes leading/trailing whitespace
        if (!name.isEmpty()) {
            break; // Exit loop if name is not empty
        }
        System.out.println("Customer Name cannot be empty. Please try again.");
    }

    System.out.print("Enter Contact Information (Email/Phone) (Optional - press Enter to skip): ");
    String contact = scanner.nextLine().trim();
    if (contact.isEmpty()) {
        contact = "N/A"; // Or handle optional input as you see fit
    }

    System.out.print("Enter Ticket Category (e.g., Billing, Technical, Inquiry) (Optional - press Enter to skip): ");
    String category = scanner.nextLine().trim();
    if (category.isEmpty()) {
        category = "General"; // Default category or handle as optional
    }

    String description;
    while (true) {
        System.out.print("Enter Issue Description: ");
        description = scanner.nextLine().trim(); // .trim() removes leading/trailing whitespace
        if (!description.isEmpty()) {
            break; // Exit loop if description is not empty
        }
        System.out.println("Issue Description cannot be empty. Please try again.");
    }

    Ticket.PriorityLevel priority = selectPriority(); // Assuming selectPriority() returns Ticket.Priority

    system.createTicket(name, contact, category, description, priority);
}
//Method to allow a user to view a single ticket using its id
    private static void viewTicketById() {
        System.out.println("\n--- View Ticket by ID ---");
        System.out.print("Enter Ticket ID to view: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            system.viewTicketById(id);
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
        }
    }
// This method allows a user to update a property of a ticket
    private static void updateTicket() {
        System.out.println("\n--- Update Ticket ---");
        System.out.print("Enter Ticket ID to update: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine(); // Consume newline because user needs to click enter
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        if (system.findTicketById(id).isEmpty()) {
            System.out.println("Ticket #" + id + " not found.");
            return;
        }

        System.out.println("Ticket Found. Current details:");
        system.viewTicketById(id);

//Allow user to choose what property to update
        System.out.println("\nWhat do you want to update?");
        System.out.println("1. Status");
        System.out.println("2. Priority");
        System.out.println("3. Add Comment");
        System.out.println("4. Customer Details (Name, Contact, Category, Description)");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");
        int updateChoice;
        try {
            updateChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        switch (updateChoice) {
            case 1:
                Ticket.Status newStatus = selectStatus();
                system.updateTicketStatus(id, newStatus);
                break;
            case 2:
                Ticket.PriorityLevel newPriority = selectPriority();
                system.updateTicketPriority(id, newPriority);
                break;
            case 3:
                System.out.print("Enter comment to add: ");
                String comment = scanner.nextLine();
                system.addComment(id, comment);
                break;
            case 4:
                System.out.print("Enter new Customer Name (or press Enter to skip): ");
                String name = scanner.nextLine();
                System.out.print("Enter new Contact Info (or press Enter to skip): ");
                String contact = scanner.nextLine();
                System.out.print("Enter new Category (or press Enter to skip): ");
                String category = scanner.nextLine();
                System.out.print("Enter new Description (or press Enter to skip): ");
                String description = scanner.nextLine();
                system.updateTicketDetails(id, name, contact, category, description);
                break;
            case 0:
                System.out.println("Update cancelled.");
                break;
            default:
                System.out.println("Invalid update choice.");
        }
    }


    //Method to delete a given ticket referenced by its id
    private static void deleteTicket() {
        System.out.println("\n--- Delete Ticket ---");
        System.out.print("Enter Ticket ID to delete: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
            return;
        }

        if (system.findTicketById(id).isEmpty()) {
            System.out.println("Ticket #" + id + " not found.");
            return;
        }
        System.out.println("Ticket Found. Current details:");
        system.viewTicketById(id);
        System.out.println("\n Are you sure you want to delete this Ticket?");
        System.out.println("\n Enter Yes to delete, No to cancel: ");
        String deleteChoice = scanner.nextLine();
        if (deleteChoice.equalsIgnoreCase("yes")) {
            system.deleteTicket(id);
        }else {
            System.out.println("Cancelled delete.");
        }
    }

    // Search tickets by using ticket attributes as filters
    private static void searchTickets() {
        System.out.println("\n--- Search Tickets ---");
    System.out.println("What do you want to use for search criteria?");
    System.out.println("1. Id");
    System.out.println("2. Category");
    System.out.println("3. Status");
    System.out.println("4. Customer name");
    System.out.println("0. Cancel");
    System.out.print("Enter your choice: ");
    int searchChoice ;

    try {
        searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    } catch (InputMismatchException e) {
        System.out.println("Invalid choice. Please enter a number.");
        scanner.nextLine(); // Consume invalid input
        return;
    }
    switch (searchChoice) {
        case 1:
            System.out.println("Enter Ticket ID: ");
            int id = scanner.nextInt();
            system.findTicketById(id);
            break;
        case 2:
            System.out.println("Enter Category: ");
            String category = scanner.nextLine();
            tickets = system.searchTicketByCategory(category);
            system.displaySearchResults(tickets, "Category");
            break;
        case 3:
            System.out.println("Enter Status: ");
            String status = scanner.nextLine();
            tickets = system.searchTicketByStatus(status);
            system.displaySearchResults(tickets, "Status");
            break;
        case 4:
            System.out.println("Enter Customer name: ");
            String name = scanner.nextLine();
            tickets = system.searchTicketByCustomerName(name);
            system.displaySearchResults(tickets, "Customer name");
            break;
        case 0:
            System.out.println("Search Cancelled.");
            break;
        default:
            System.out.println("Invalid search choice.");
            break;


    }
}
    //Select priority level for a given ticket
    private static Ticket.PriorityLevel selectPriority() {
        while (true) {
            System.out.println("Select Priority:");
            int i = 1;
            for (Ticket.PriorityLevel p : Ticket.PriorityLevel.values()) {
                System.out.println(i++ + ". " + p);
            }
            System.out.print("Enter priority choice (number): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= Ticket.PriorityLevel.values().length) {
                    return Ticket.PriorityLevel.values()[choice - 1];
                } else {
                    System.out.println("Invalid priority choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }


    //Select a status for a given ticket
    private static Ticket.Status selectStatus() {
        while (true) {
            System.out.println("Select New Status:");
            int i = 1;
            for (Ticket.Status s : Ticket.Status.values()) {
                System.out.println(i++ + ". " + s);
            }
            System.out.print("Enter status choice (number): ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= Ticket.Status.values().length) {
                    return Ticket.Status.values()[choice - 1];
                } else {
                    System.out.println("Invalid status choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }



}
