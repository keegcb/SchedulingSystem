package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

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

    private ZonedDateTime zdtStart;
    private ZonedDateTime zdtEnd;

    public Appointment(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, String contact){
        appId = id;
        appTitle = title;
        appDescription = description;
        appLocation = location;
        appType = type;
        appStart = start;
        appEnd = end;
        appContact = contact;

        zdtStart = SchedulingSystem.SchedulingSystem.convertToZDT(start);
        zdtEnd = SchedulingSystem.SchedulingSystem.convertToZDT(end);
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

    public Timestamp getAppEnd(){
        return appEnd;
    }

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

    public void setAppEnd(Timestamp appEnd) {
        this.appEnd = appEnd;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public void setAppCustomer(String appCustomer) { this.appCustomer = appCustomer;}

    public ZonedDateTime getZdtStart() {
        return zdtStart;
    }

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
}
