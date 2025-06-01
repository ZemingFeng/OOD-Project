package cs3500.weeklyplanner.provider.strategies;

import planner.model.Event;
import planner.model.NUPlanner;
import planner.model.Time;
import planner.model.User;
import planner.model.WeekEvent;
import cs3500.weeklyplanner.provider.model.Time;
import cs3500.weeklyplanner.provider.model.User;
import cs3500.weeklyplanner.provider.model.NUPlanner;
import cs3500.weeklyplanner.provider.model.Day;

/**
 * A class that represents the strategy of scheduling an
 * event at the first possible time during the work week
 * that allows all invitees and the host to be present.
 */
public class AnyTimeStrategy implements PlannerStrategy {

  private final NUPlanner planner;

  /**
   * Constructor for AnyTimeStrategy class.
   * @param planner to take in for scheduling.
   */
  public AnyTimeStrategy(NUPlanner planner) {
    this.planner = planner;
  }

  /**
   * Schedules a given event at the earliest possible open time that can fit its duration
   * @param givenEvent event to be scheduled.
   */
  @Override
  public Event scheduleEvent(Event givenEvent) {
    int minOffset = 0;
    Time eventStartTime = new Time(minOffset);
    Time eventEndTime = new Time(minOffset + givenEvent.duration());
    boolean isScheduled = false;
    int minsInWeek = 24 * 60 * 7;

    while (minOffset + givenEvent.duration() < minsInWeek) {
      boolean openSpot = true;
      for (User user : this.planner.listOfUsers()) {
        for (Event userEvent : user.getSchedule()) {
          if (eventStartTime.timeOverlaps(userEvent)
          || eventEndTime.timeOverlaps(userEvent)
          || (eventStartTime.before(userEvent.start()) && !eventEndTime.before(userEvent.end()))
          || (eventStartTime.addMinutes(givenEvent.duration()).minutes() > minsInWeek)) {
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
    throw new IllegalStateException("Given event does not fit in the full week schedule");
  }
}
