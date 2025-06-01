package cs3500.weeklyplanner.model;

/**
 * Enumeration of 7 week dates.
 */
public enum Date {
  Sunday(1),
  Monday(2),
  Tuesday(3),
  Wednesday(4),
  Thursday(5),
  Friday(6),
  Saturday(7);


  private final int value;

  Date(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    switch (this) {
      case Monday:
        return "Monday";
      case Tuesday:
        return "Tuesday";
      case Wednesday:
        return "Wednesday";
      case Thursday:
        return "Thursday";
      case Friday:
        return "Friday";
      case Saturday:
        return "Saturday";
      case Sunday:
        return "Sunday";
      default:
        throw new IllegalArgumentException("invalid date");
    }
  }

}
