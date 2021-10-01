package model;

import view.SchedulingSystem;

import java.sql.Timestamp;
import java.time.*;

/**
 * class Appointment.java
 * Appointment class simulates appointment meeting
 */
public class Appointment {
    /**
     * ID for appointment.
     */
    private int appId;
    /**
     * Title of appointment.
     */
    private String appTitle;
    /**
     * Description of appointment purpose.
     */
    private String appDescription;
    /**
     * Location of appointment.
     */
    private String appLocation;
    /**
     * Type of appointment.
     */
    private String appType;
    /**
     * Starting date/time of appointment.
     */
    private Timestamp appStart;
    /**
     * Ending date/time of appointment.
     */
    private Timestamp appEnd;
    /**
     * Contact for appointment.
     */
    private String appContact;
    /**
     * Customer for appointment.
     */
    private String appCustomer;
    /**
     * Id of customer for appointment.
     */
    private int appCustId;
    /**
     * Id of user scheduling appointment.
     */
    private int appUserId;
    /**
     * Id of contact for appointment.
     */
    private int appContactId;
    /**
     * Starting date/time of appointment in specific timezone.
     */
    private ZonedDateTime zdtStart;
    /**
     * Ending date/time of appointment in specific timezone.
     */
    private ZonedDateTime zdtEnd;

    /**
     * Constructor initializes an appointment object with following parameters.
     * @param id Id for appointment
     * @param title Title of appointment
     * @param description Description of appointment
     * @param location Location of appointment
     * @param type Type of appointment
     * @param start Starting timestamp of appointment
     * @param end Ending timestamp of appointment
     * @param customerId Id of customer for appointment
     * @param contactId Id of contact for appointment
     */
    public Appointment(int id, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerId, int contactId){
        appId = id;
        appTitle = title;
        appDescription = description;
        appLocation = location;
        appType = type;
        appStart = start;
        appEnd = end;
        appCustId = customerId;
        appContactId = contactId;

        zdtStart = SchedulingSystem.convertToZDT(start);
        zdtEnd = SchedulingSystem.convertToZDT(end);
    }

    /**
     * Constructor initializes appointment without any parameters.
     */
    public Appointment(){}

    /**
     * Gets appointment id value.
     * @return appointment id
     */
    public int getAppId(){
        return appId;
    }

    /**
     * Gets appointment title value.
     * @return appointment title
     */
    public String getAppTitle(){
        return appTitle;
    }

    /**
     * Gets appointment description value.
     * @return appointment description
     */
    public String getAppDescription(){
        return appDescription;
    }

    /**
     * Gets appointment location value.
     * @return appointment location
     */
    public String getAppLocation(){
        return appLocation;
    }

    /**
     * Gets appointment type value.
     * @return appointment type
     */
    public String getAppType(){
        return appType;
    }

    /**
     * Get appointments starting time value.
     * @return appointment start
     */
    public Timestamp getAppStart(){
        return appStart;
    }

    public ZonedDateTime getZoneStart(){return zdtStart;}

    /**
     * Gets appointments ending time value.
     * @return appointment end
     */
    public Timestamp getAppEnd(){
        return appEnd;
    }

    public ZonedDateTime getZoneEnd(){return zdtEnd;}

    /**
     * Gets appointment contact value.
     * @return
     */
    public String getAppContact(){
        return appContact;
    }

    public String getAppCustomer(){return appCustomer;}

    /**
     * Sets appointment id value.
     * @param appId id to be set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * Sets appointment title value.
     * @param appTitle title to be set
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * Sets appointment description value.
     * @param appDescription description to be set
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     * Sets appointment location value.
     * @param appLocation location to be set
     */
    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    /**
     * Sets appointment type value.
     * @param appType type to be set
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * Sets appointment start value.
     * @param appStart start time to be set
     */
    public void setAppStart(Timestamp appStart) {
        this.appStart = appStart;
    }

    /**
     * Sets appointment start zone time value.
     * @param start start timestamp to convert and set
     */
    public void setZoneStart(Timestamp start){this.zdtStart = start.toLocalDateTime().atZone(ZoneId.systemDefault());}

    /**
     * Sets appointment end timestamp value.
     * @param appEnd end timestamp to be set
     */
    public void setAppEnd(Timestamp appEnd) {
        this.appEnd = appEnd;
    }

    /**
     * Sets appointment end zone time value.
     * @param end end timestamp to convert and set
     */
    public void setZoneEnd(Timestamp end){this.zdtEnd = end.toLocalDateTime().atZone(ZoneId.systemDefault());}

    /***
     * Sets appointment contact value.
     * @param appContact contact to be set
     */
    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    /**
     * Sets appointment customer value.
     * @param appCustomer customer to be set
     */
    public void setAppCustomer(String appCustomer) { this.appCustomer = appCustomer;}

    /**
     * Gets appointment start in local date time value.
     * @return local date time start
     */
    public LocalDate getAppDate() {
        return zdtStart.toLocalDate();
    }

    /**
     * Gets appointment end in local date time value.
     * @return local date time end
     */
    public LocalTime getAppTime(){return zdtStart.toLocalTime();}

    public void setZdtStart(ZonedDateTime zdtStart) {
        this.zdtStart = zdtStart;
    }

    public ZonedDateTime getZdtEnd() {
        return zdtEnd;
    }

    public void setZdtEnd(ZonedDateTime zdtEnd) {
        this.zdtEnd = zdtEnd;
    }

    /**
     * Gets appointment customer id value.
     * @return appointment customer id
     */
    public int getAppCustId() {
        return appCustId;
    }

    /**
     * Sets appointment customer id value.
     * @param appCustId customer id to be set
     */
    public void setAppCustId(int appCustId) {
        this.appCustId = appCustId;
    }

    /**
     * Gets appointment contact id value.
     * @return appointment contact id
     */
    public int getAppContactId() {
        return appContactId;
    }

    /**
     * Sets appointment contact id value.
     * @param appContactId appointment contact id to be set
     */
    public void setAppContactId(int appContactId) {
        this.appContactId = appContactId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    /**
     * Sets appointment user id value.
     * @param appUserId user id to be set
     */
    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
}
