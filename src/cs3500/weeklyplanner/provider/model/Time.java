package cs3500.weeklyplanner.provider.model;

import java.util.Objects;

/**
 * Time class to represent a specific instance in time.
 */
public class Time {

  private final Day day;
  private final int minutes;

  /**
   * Each time is created with a given day and number of minutes. The minutes are converted
   * to hours and minutes in the view.
   * @param day of the instance of time.
   * @param minutes of the instance of time.
   * @throws IllegalArgumentException if the number of minutes is greater than
   *     or equal to 24 hours long.
   */
  public Time(Day day, int minutes) {
    this.day = Objects.requireNonNull(day);
    this.minutes = minutes;
    // 1440 minutes in a day
    if (this.minutes > 1439 || this.minutes < 0) {
      throw new IllegalArgumentException("Minutes exceeded 1 day of time.");
    }
  }

  /**
   * Constructor for a Time object with only the number of minutes into the week given.
   * Minutes are converted to days and minutes.
   * @param minutes into the week.
   */
  public Time(int minutes) {
    this.day = Day.values()[minutes / 1440];
    this.minutes = minutes % 1440;
  }

  public int dayToMin(Day day) {
    return day.getDayValue() * 1440;
  }

  /**
   * Compares two times to see if they are the same moment in time.
   * @param time to compare this time with.
   * @return true if the two times are the same and false if not.
   */
  public boolean sameTime(Time time) {
    if (this.day.sameDay(time.day)) {
      if (this.minutes == time.minutes) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if this time is on the same day as the given day.
   * @param day to compare with.
   * @return true if the time is on the given day and false if not.
   */
  public boolean sameDay(Day day) {
    return this.day == day;
  }

  /**
   * Checks if this time occurs before the given time. First, checks if the two times
   * are on the same day. If they are, the minutes field of each time is compared to
   * see if the minutes of this time are less than the minutes of the given time. If they are
   * not, the day values are compared to see if this day comes before the given day in the week.
   * The week starts on Sunday at 0 minutes and ends on Saturday at 1439 minutes or 23 hrs 59 mins.
   * @param t time given to be compared with.
   * @return true if this time occurs before the given time during the week.
   */
  public boolean before(Time t) {
    if (this.sameDay(t.day)) {
      return this.minutes < t.minutes;
    }
    return this.day.compareTo(t.day) < 0;
  }

  public boolean timeOverlaps(Event event) {
    return this.before(event.end()) && event.start().before(this);
  }

  /**
   * Gives the day value of this day.
   * @return the day value of the day.
   */
  public Day day() {
    return this.day;
  }

  /**
   * Gives the minutes value of this day.
   * @return the number of minutes in this day.
   */
  public int minutes() {
    return this.minutes;
  }

  /**
   * Creates a string representation of this time. Calculates the number of hours in the minutes
   * field and then calculates the remainder after subtracting the number of hours. The time value
   * is displayed in hours/minutes format in 24-hour time (ex. 1:05 PM = 1305).
   * @return the string representation of this time.
   */
  public String toString() {
    int hrsVal = this.minutes / 60;
    int minsVal = this.minutes % 60;
    String hrsString = hrsVal + "";
    String minsString = minsVal + "";
    if (hrsVal < 10) {
      hrsString = "0" + hrsVal;
    }
    if (minsVal < 10) {
      minsString = "0" + minsVal;
    }
    return hrsString + minsString;
  }

  public Time addMinutes(int mins) {
    int numDaysAdded = mins / 1440;
    int remainMinsAdded = mins % 1440;
    int newDayVal = this.day.getDayValue() + numDaysAdded;
    Day returnedDay = Day.values()[newDayVal];
    return new Time(returnedDay, remainMinsAdded);
  }
}
