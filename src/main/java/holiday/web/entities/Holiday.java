package holiday.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


/**
 *
 * @author vishav
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Holiday implements Serializable {


    private String startDate = null;
    private int startYear = 0;
    private int startMonth = 0;
    private int startDay = 0;
    private String endDate = null;
    private String name = null;
    private String region = null;
    private String country = null;
    private String state = null;
    private String city = null;
    private String holidayNote = null;
    private String businessesClosed = null;
    private String banksClosed = null;
    private String religiousHoliday = null;
    private String religion = null;
    private String dis = null;

//    public Holiday() {
//
//    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHolidayNote() {
        return holidayNote;
    }

    public void setHolidayNote(String holidayNote) {
        this.holidayNote = holidayNote;
    }

    public String getBusinessesClosed() {

        return (businessesClosed.equalsIgnoreCase("Yes") ? "businesses are closed" : "businesses operate at usual schedule");
    }

    public void setBusinessesClosed(String businessesClosed) {
        this.businessesClosed = businessesClosed;
    }

    public String getBanksClosed() {
        return (banksClosed.equalsIgnoreCase("Yes") ? "banks are closed" : "banks operate at usual schedule");
    }

    public void setBanksClosed(String banksClosed) {
        this.banksClosed = banksClosed;
    }

    public String getReligiousHoliday() {

        return (religiousHoliday.equalsIgnoreCase("Yes") ? name + " is a religious Holiday" : name + " is not a religious Holiday");
    }

    public void setReligiousHoliday(String religiousHoliday) {
        this.religiousHoliday = religiousHoliday;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

}