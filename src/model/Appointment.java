package model;

import view.SchedulingSystem;

import java.sql.Timestamp;
import java.time.*;

public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private Timestamp appStart;
    private Timestamp appEnd;
    private String appContact;
    private String appCustomer;
    private int appCustId;
    private int appUserId;
    private int appContactId;

    private ZonedDateTime zdtStart;
    private ZonedDateTime zdtEnd;

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

    public Appointment(){}

    public int getAppId(){
        return appId;
    }

    public String getAppTitle(){
        return appTitle;
    }

    public String getAppDescription(){
        return appDescription;
    }

    public String getAppLocation(){
        return appLocation;
    }

    public String getAppType(){
        return appType;
    }

    public Timestamp getAppStart(){
        return appStart;
    }

    public ZonedDateTime getZoneStart(){return zdtStart;}

    public Timestamp getAppEnd(){
        return appEnd;
    }

    public ZonedDateTime getZoneEnd(){return zdtEnd;}

    public String getAppContact(){
        return appContact;
    }

    public String getAppCustomer(){return appCustomer;}

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public void setAppStart(Timestamp appStart) {
        this.appStart = appStart;
    }

    public void setZoneStart(Timestamp start){this.zdtStart = start.toLocalDateTime().atZone(ZoneId.systemDefault());}

    public void setAppEnd(Timestamp appEnd) {
        this.appEnd = appEnd;
    }

    public void setZoneEnd(Timestamp end){this.zdtEnd = end.toLocalDateTime().atZone(ZoneId.systemDefault());}

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public void setAppCustomer(String appCustomer) { this.appCustomer = appCustomer;}

    public LocalDate getAppDate() {
        return zdtStart.toLocalDate();
    }

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

    public int getAppCustId() {
        return appCustId;
    }

    public void setAppCustId(int appCustId) {
        this.appCustId = appCustId;
    }

    public int getAppContactId() {
        return appContactId;
    }

    public void setAppContactId(int appContactId) {
        this.appContactId = appContactId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }
}
