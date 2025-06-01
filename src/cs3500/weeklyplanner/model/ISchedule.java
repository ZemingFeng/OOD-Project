package cs3500.weeklyplanner.model;

import java.util.List;

/**
 * Represents a schedule. Features are included below.
 */
public interface ISchedule {
  /**
   * checks if the given list of the events has any events that are overlapped.
   *
   * @param events is the list of events that we want to check.
   * @return true if there is an overlap.
   */
  boolean isOverlap(List<IEvent> events);

  /**
   * observes the events contained in the schedule.
   *
   * @return a list of Event.
   */
  List<IEvent> getEvents();

  /**
   * Adds the provided event to the schedule's list of events.
   *
   * @param event is the event that we want to add.
   */
  void addEvent(IEvent event);

  /**
   * Removes the specified event from the schedule's list of events.
   *
   * @param event is the event that we want to remove.
   */
  void removeEvent(IEvent event);


}
