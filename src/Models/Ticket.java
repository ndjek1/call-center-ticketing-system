package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static  int nextTicetId = 1;
    private int id;
    private String customerName;
    private String customerContact;
    private  String category;
    private String description;
    public Status status;
    public PriorityLevel priorityLevel;
    private  String comments;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public enum Status{
        OPEN, IN_PROGRESS, RESOLVED, CLOSED
    }
    public enum PriorityLevel{
        LOW, MEDIUM, HIGH, URGENT
    }
    public Ticket( String customerName, String customerContact, String category, String description, PriorityLevel priorityLevel) {
        this.id = nextTicetId++;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.category = category;
        this.description = description;
        this.status = Status.OPEN;
        this.priorityLevel = PriorityLevel.LOW;
        this.comments = "";
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    //Getter to get field value since they are private, they can only be access from here
    //Encapsulation
    public int getId() {
        return id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerContact() {
        return customerContact;
    }
    public String getCategory() {
        return category;

    }
    public String getDescription() {
        return description;
    }
    public Status getStatus() {
        return status;
    }
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }
    public String getComments() {
        return comments;
    }
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    //Setter to allow modification of fields that can be updated
    //Also encapsulation
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        this.dateUpdated = LocalDateTime.now();
    }

    public void setContactInfo(String contactInfo) {
        this.customerContact = contactInfo;
        this.dateUpdated = LocalDateTime.now();
    }

    public void setCategory(String category) {
        this.category = category;
        this.dateUpdated = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.dateUpdated = LocalDateTime.now();
    }

    public void setStatus(Status status) {
        this.status = status;
        this.dateUpdated = LocalDateTime.now();
    }

    public void setPriority(PriorityLevel priority) {
        this.priorityLevel = priority;
        this.dateUpdated = LocalDateTime.now();
    }

    public void addComment(String comment) {
        if (this.comments.isEmpty()) {
            this.comments = comment;
        } else {
            this.comments += "\n - " + comment; // Append new comments
        }
        this.dateUpdated = LocalDateTime.now();
    }


    //This will print the information about a given ticket object in a string format.
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "----------------------------------------\n" +
                "Ticket ID      : " + id + "\n" +
                "Customer Name  : " + customerName + "\n" +
                "Contact Info   : " + customerContact + "\n" +
                "Category       : " + category + "\n" +
                "Description    : " + description + "\n" +
                "Status         : " + status + "\n" +
                "Priority       : " + priorityLevel + "\n" +
                "Comments       : " + (comments.isEmpty() ? "N/A" : "\n - " + comments) + "\n" +
                "Created        : " + dateCreated.format(formatter) + "\n" +
                "Last Updated   : " + dateUpdated.format(formatter) + "\n" +
                "----------------------------------------";
    }
}
