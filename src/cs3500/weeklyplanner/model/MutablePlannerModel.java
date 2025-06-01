package cs3500.weeklyplanner.model;

import java.util.List;

/**
 * Represents the Model of weekly planner system. Features are included below.
 */
public interface MutablePlannerModel extends ReadOnlyPlannerModel {


  /**
   * Select one of the users to display their schedule.
   *
   * @param user is the user whose schedule will be displayed.
   * @return the user's schedule.
   * @throws IllegalArgumentException if the given user is invalid.
   */
  ISchedule select(IUser user) throws IllegalArgumentException;

  /**
   * creates an event and add that to the loaded events list. For GUI users,
   * in the future projects, they can schedule the events in the loaded events
   * field and therefore have the event scheduled in all related users' schedule.
   *
   * @param event is the event we want to add.
   * @throws IllegalArgumentException if the parameter is not an event.
   * @throws IllegalStateException    if an identical event has already been created. the reason
   *                                  why we are not allowing this to happen is that
   *                                  it may cause aliasing issues.
   */
  void createEvent(IEvent event) throws IllegalArgumentException, IllegalStateException;

  /**
   * creates an event with the provided information and add that to the loaded events list.
   * For GUI users, in the future projects, they can schedule the events in the loaded events
   * field and therefore have the event scheduled in all related users' schedule.
   *
   * @param name      is the name of the event.
   * @param online    tells whether the event is online or not.
   * @param location  is the location of the event.
   * @param startDate is the startDate of the event.
   * @param startTime is the startTime of the event.
   * @param endDate   is the endDate of the event.
   * @param endTime   is the endTime of the event.
   * @param host      is the host of the event, which is a User in this case.
   * @param invitees  is the invitees of the event, which is a list of IUser.
   */
  void createEvent(String name, Boolean online, String location, Date startDate, int startTime,
                   Date endDate, int endTime, IUser host, List<IUser> invitees);

  /**
   * Modifies the event by providing a new event that the user wants the old event to become.
   *
   * @param oldEvent is the old event to be modified.
   * @param newEvent is the desired event that the user want to modify to.
   */
  void modifyEvent(IEvent oldEvent, IEvent newEvent);

  /**
   * Removes an event from the schedule. This will also affect all other users' schedule if the
   * event also shows up on their schedule.
   *
   * @param event is the event to be removed.
   */
  void removeEvent(IEvent event);

  /**
   * schedule the provided event and add them to the user's schedule. This will
   * also schedule the event to all invitees' schedule.
   *
   * @param event is the event to be scheduled.
   */
  void scheduleEvent(String name, String location, boolean online, int duration, IUser host,
                          List<IUser> invitees, boolean anyTime);

  /**
   * returns the event specified at the given time period of the given user.
   *
   * @param startDate is the start date of the event.
   * @param startTime is the start time of the event.
   * @param endDate   is the end date of the event
   * @param endTime   is the end time of the event
   * @param user      is the user of the event
   * @return the event
   * @throws IllegalArgumentException if any parameter is invalid.
   */
  List<IEvent> seeEvents(Date startDate, int startTime, Date endDate, int endTime, IUser user);

  void loadEvent(IEvent event);
}




