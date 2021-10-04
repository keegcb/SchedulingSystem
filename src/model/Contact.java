package model;


/**
 * class Contact.java
 * Contact class simulates contact person
 */
public class Contact {
    /**
     * ID for contact.
     */
    private int contactId;
    /**
     * Name for contact.
     */
    private String contactName;
    /**
     * Email for contact.
     */
    private String contactEmail;

    /**
     * Constructor initializes contact object with following parameters.
     * @param name Name of contact
     * @param email Email of contact
     */
    public Contact(String name, String email){
        contactName = name;
        contactEmail = email;
    }

    /**
     * Contact name is printed value when changing object to String.
     * @return contact name
     */
    @Override
    public String toString(){
        return (contactName);
    }

    /**
     * Gets id for contact.
     * @return contact id to get
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets name of contact.
     * @return contact name to get
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets email of contact.
     * @return email to get
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets email of contact.
     * @param contactEmail Email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Sets id of contact.
     * @param contactId id to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Sets name of contact.
     * @param contactName name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
