package model;

public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contact(String name, String email){
        contactName = name;
        contactEmail = email;
    }

    @Override
    public String toString(){
        return (contactName);
    }

    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
