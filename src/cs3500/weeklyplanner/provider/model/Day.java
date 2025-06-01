package cs3500.weeklyplanner.provider.model;

/**
 * Day enumeration to keep track of the days of the week and their order.
 */
public enum Day {

  SUN(0),
  MON(1),
  TUES(2),
  WED(3),
  THURS(4),
  FRI(5),
  SAT(6);

  private int day;

  /**
   * Each day object is created with an associated integer value, which orders the days
   * of the week correctly.
   * @param day value associated with this day.
   */
  Day(int day) {
    this.day = day;
  }

  /**
   * Gives the integer value associated with this day.
   * @return the integer value associated with this day.
   */
  public int getDayValue() {
    return this.day;
  }

  /**
   * Checks if two days are the same day by comparing their integer values.
   * @param day to be compared with.
   * @return true if the two days are the same day and false if not.
   */
  public boolean sameDay(Day day) {
    return this.day == day.day;
  }

  /**
   * Creates a string representation of this day and displays the full name of the day of the
   * week.
   * @return a string with the full name of this day of the week.
   */
  public String toString() {
    switch (this.day) {
      case 0:
        return "Sunday";
      case 1:
        return "Monday";
      case 2:
        return "Tuesday";
      case 3:
        return "Wednesday";
      case 4:
        return "Thursday";
      case 5:
        return "Friday";
      case 6:
        return "Saturday";
      default:
        throw new IllegalArgumentException("Invalid day given.");
    }
  }


}
