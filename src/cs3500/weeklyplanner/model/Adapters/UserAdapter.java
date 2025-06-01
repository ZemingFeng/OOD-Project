package cs3500.weeklyplanner.model.Adapters;

import java.util.List;

import cs3500.weeklyplanner.provider.model.Event;
import cs3500.weeklyplanner.provider.model.Time;
import cs3500.weeklyplanner.provider.model.User;

public interface UserAdapter {
  /**
   * Add the given event to the user's schedule.
   * @param event that is added to the schedule.
   * @throws IllegalStateException if the given event overlaps with an event already existing
   *     in the user's schedule.
   * @throws IllegalStateException if the given event is already present in the user's schedule.
   */
  void addEvent(Event event);

  /**
   * Removes the given event from the user's schedule.
   * @param event that is removed from the schedule.
   * @throws IllegalStateException if the given event is not present in the user's schedule.
   */
  void removeEvent(Event event);

  /**
   * Modifies the given event by changing its parameters to the given parameters.
   * @param event that is taken to be modified.
   * @param name of the new event.
   * @param location of the new event.
   * @param online status of the new event.
   * @param startTime of the new event.
   * @param endTime of the new event.
   * @param invitedUsers of the new event.
   * @throws IllegalStateException if the given event is not present in the user's schedule.
   */
  void modifyEvent(Event event,
                   String name,
                   String location,
                   boolean online,
                   Time startTime, Time endTime,
                   List<User> invitedUsers);

  /**
   * Gets the list of the user's events that they have been invited to.
   * @return the schedule of the user.
   */
  List<Event> getSchedule();

  /**
   * Gives the name of the user.
   * @return a string representation of the user's name.
   */
  String name();
}
