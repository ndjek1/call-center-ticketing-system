package Services;

import Models.Ticket;
import Models.Ticket.PriorityLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketingSystem {

    List<Ticket>  tickets;

    public TicketingSystem() {
        this.tickets = new ArrayList<>();
    }

//    private void addSampleData() {
//        createTicket("Alice Smith", "alice@example.com", "Billing", "Incorrect invoice amount", Ticket.Priority.HIGH);
//        createTicket("Bob Johnson", "bob.j@example.com", "Technical Support", "Cannot log in to portal", Ticket.Priority.URGENT);
//        tickets.get(1).setStatus(Ticket.Status.IN_PROGRESS);
//        tickets.get(1).addComment("Agent assigned. Investigating login issue.");
//        createTicket("Carol Williams", "carol.w@example.com", "General Inquiry", "Question about service plans", Ticket.Priority.MEDIUM);
//    }


    //Method to create a new ticket and add it to the collection(ArrayList)
    public  void  createTicket(String name, String contact, String category, String description, Ticket.PriorityLevel priority){
        Ticket newTicket = new Ticket(name,contact,category,description,priority);
        tickets.add(newTicket);
        System.out.println("Ticket #"+ newTicket.getId() +" created");
    }


    //Method to view all tickets
    public void viewAllTickets(){
        if(tickets.isEmpty()){
            System.out.println("No tickets found");
            return;
        }
        System.out.println("\n--- All Tickets ---");
            for(Ticket ticket:tickets){
                System.out.println(ticket);
            }

    }


    // Helper method to find a ticket by ID
    //The Option class is used for better handling of null point Exceptions
    public Optional<Ticket> findTicketById(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == ticketId) {
                return Optional.of(ticket);
            }
        }
        return Optional.empty();
    }

    // Method to view a single ticket using its id
    public void viewTicketById(int ticketId){
        Optional<Ticket> ticket = findTicketById(ticketId);
        if(ticket.isPresent()){
            System.out.println(ticket);
        }else {
            System.out.println("Ticket #"+ ticketId + " not found");
        }
    }
// Method to update the status of a Ticket
    public boolean updateTicketStatus(int id,Ticket.Status status){
        Optional<Ticket> ticket = findTicketById(id);
        if(ticket.isPresent()){
            ticket.get().setStatus(status);
            System.out.println("Ticket #"+ ticket.get().getId() +" updated");
            return true;
        }
        System.out.println("Ticket #"+ ticket.get().getId() +" not found");
        return  false;
    }

    // Method to update the priority level of a Ticket
    public boolean updateTicketPriority(int id,PriorityLevel priority){
        Optional<Ticket> ticket = findTicketById(id);
        if(ticket.isPresent()){
            ticket.get().setPriority(priority);
            System.out.println("Ticket #"+ ticket.get().getId() +" updated");
            return  true;
        }
        System.out.println("Ticket #"+ ticket.get().getId() +" not found");
        return false;
    }

    //Method to add comments
    public void addComment(int id,String comment){
        Optional<Ticket> ticket = findTicketById(id);
        if(ticket.isPresent()){
            ticket.get().addComment(comment);
            System.out.println("Ticket #"+ ticket.get().getId() +" added comment");
            return;
        }
        System.out.println("Ticket #"+ ticket.get().getId() +" not found");
    }

    public boolean updateTicketDetails(int id, String customerName, String contact, String category, String description){
        Optional<Ticket> ticket = findTicketById(id);
        if(ticket.isPresent()){
            if((customerName != null) && !customerName.trim().isEmpty()){
                ticket.get().setCustomerName(customerName);
            }
            if(((contact != null)) && !contact.trim().isEmpty()){
                ticket.get().setContactInfo(contact);
            }
            if(((description != null) && !description.trim().isEmpty())){
                ticket.get().setDescription(description);
            }
            if(category != null && category.trim().isEmpty()){
                ticket.get().setCategory(category);
            }
            System.out.println("Ticket #"+ ticket.get().getId() +"  details updated");
            return true;
        }
        System.out.println("Ticket #"+ id +" not found");
        return false;
    }

    // Method to delete a ticket when it is no longer relevant to the call center
    public boolean deleteTicket(int id){
        Optional<Ticket> ticket = findTicketById(id);
        if(ticket.isPresent()){
            tickets.remove(ticket.get());
            System.out.println("Ticket #"+ ticket.get().getId() +" deleted");
            return true;
        }
        System.out.println("Ticket #"+ ticket.get().getId() +" not found");
        return false;
    }

    public List<Ticket> searchTicketByCustomerName(String name){
        return tickets.stream().filter(ticket ->
                ticket.getCustomerName().equals(name)).collect(Collectors.toList());
    }
    public List<Ticket> searchTicketByCategory(String category){
        return tickets.stream().filter(ticket ->
                ticket.getCategory().equals(category)).collect(Collectors.toList());
    }
    public List<Ticket> searchTicketByStatus(String status){
        return tickets.stream().filter(ticket ->
                ticket.getStatus().equals(status)).collect(Collectors.toList());
    }
    public void displaySearchResults(List<Ticket> tickets, String criteria){
        if(tickets.isEmpty()){
            System.out.println("No tickets found");
        }else  {
            System.out.println("Tickets found");
            for(Ticket ticket:tickets){
                System.out.println(ticket);
            }
        }
    }
}
