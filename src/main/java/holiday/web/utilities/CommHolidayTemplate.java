package holiday.web.utilities;

import holiday.web.entities.Holiday;

import java.util.List;

public class CommHolidayTemplate {
    public List<Holiday> getTheList() {
        return theList;
    }

    public void setTheList(List<Holiday> theList) {
        this.theList = theList;
    }

    List<Holiday> theList;
}