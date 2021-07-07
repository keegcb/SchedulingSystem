package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int appId;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private String appStart;
    private String appEnd;
    private String appContact;
    private String appCustomer;

    public Appointment(int id, String title, String description, String location, String type, String start, String end, String contact){
        appId = id;
        appTitle = title;
        appDescription = description;
        appLocation = location;
        appType = type;
        appStart = start;
        appEnd = end;
        appContact = contact;
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

    public String getAppStart(){
        return appStart;
    }

    public String getAppEnd(){
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

    public void setAppStart(String appStart) {
        this.appStart = appStart;
    }

    public void setAppEnd(String appEnd) {
        this.appEnd = appEnd;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public void setAppCustomer(String appCustomer) { this.appCustomer = appCustomer;}

    public String getAppStartTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime local = LocalDateTime.parse(appStart, formatter);
        ZonedDateTime zone = local.atZone(ZoneId.of("UTC"));
        ZoneId idZone = ZoneId.systemDefault();
        ZonedDateTime formatTime = zone.withZoneSameInstant(idZone);
        return formatTime.toLocalDateTime().toString();
    }

    public String getAppEndTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime local = LocalDateTime.parse(appEnd, formatter);
        ZonedDateTime zone = local.atZone(ZoneId.of("UTC"));
        ZoneId idZone = ZoneId.systemDefault();
        ZonedDateTime formatTime = zone.withZoneSameInstant(idZone);
        return formatTime.toLocalDateTime().toString();
    }

}
