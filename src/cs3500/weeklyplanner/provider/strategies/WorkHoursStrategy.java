package cs3500.weeklyplanner.provider.strategies;

import planner.model.Event;
import planner.model.NUPlanner;
import planner.model.Time;
import planner.model.User;
import planner.model.WeekEvent;

/**
 * A class that represents a strategy of finding
 * the first possible time from Monday to Friday (inclusive)
 * between the hours of 0900 and 1700 (inclusive) where all
 * invitees and the host can attend the even and
 * return an event with that block of time.
 */
public class WorkHoursStrategy implements PlannerStrategy {

  private NUPlanner planner;

  /**
   * Constructor for the WorkHoursStrategy.
   * @param planner taken in to be used to schedule.
   */
  public WorkHoursStrategy(NUPlanner planner) {
    this.planner = planner;
  }

  @Override
  public Event scheduleEvent(Event givenEvent) {
    int minOffset = 24 * 60;
    Time eventStartTime = new Time(minOffset);
    Time eventEndTime = new Time(minOffset + givenEvent.duration());
    boolean isScheduled = false;
    int minsInWorkWeek = 24 * 60 * 5;
    if (givenEvent.duration() > 480) {
      throw new IllegalArgumentException("Given event is longer than a workday");
    }

    while (minOffset + givenEvent.duration() < minsInWorkWeek) {
      boolean openSpot = true;
      for (User user : this.planner.listOfUsers()) {
        for (Event userEvent : user.getSchedule()) {
          if (this.timeOutsideWorkHours(eventStartTime)
                  || this.timeOutsideWorkHours(eventEndTime)
                  || eventStartTime.timeOverlaps(userEvent)
                  || eventEndTime.timeOverlaps(userEvent)
                  || (eventStartTime.before(userEvent.start()) && !eventEndTime.before(userEvent.end()))) {
            openSpot = false;
            break;
          }
        }
        if (!openSpot) {
          break;
        }
      }

      if (openSpot) {
        return new WeekEvent(
                givenEvent.name(),
                givenEvent.location(),
                givenEvent.isOnline(),
                eventStartTime,
                eventEndTime,
                givenEvent.getInvitedUsers());
      } else {
        minOffset += 1;
        eventStartTime = new Time(minOffset);
        eventEndTime = new Time(minOffset + givenEvent.duration());
      }
    }
    throw new IllegalStateException("Given event does not fit in the work week schedule");
  }

  /**
   * Helper method that determines if a given time occurs outside the
   * hours from 9 AM to 5 PM.
   * @param time given to check.
   * @return true if the time is outside work hours and false if it is inside work hours
   */
  private boolean timeOutsideWorkHours(Time time) {
    return time.minutes() < 540 || time.minutes() > 1020;
  }
}
