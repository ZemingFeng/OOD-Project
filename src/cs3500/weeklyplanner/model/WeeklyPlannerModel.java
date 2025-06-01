package cs3500.weeklyplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation class of the PlannerModel system. This implementation
 * allows all users to make edits to the planner system.
 */
public class WeeklyPlannerModel implements MutablePlannerModel {
  private List<IUser> users;
  private List<IEvent> loadedEvents;

  /**
   * constructor of the model.
   *
   * @param users        are all users within the current system.
   * @param loadedEvents are the loaded events to be scheduled or removed.
   */
  public WeeklyPlannerModel(List<IUser> users, List<IEvent> loadedEvents) {
    this.users = users;
    this.loadedEvents = loadedEvents;
  }

  @Override
  public ISchedule select(IUser user) throws IllegalArgumentException {
    if (user == null) {
      throw new IllegalArgumentException("Invalid user");
    }
    return user.getSchedule();
  }

  @Override
  public void createEvent(IEvent event) throws IllegalArgumentException, IllegalStateException {
    if (event == null) {
      throw new IllegalArgumentException("Require non-null event.");
    }
    for (IEvent e : loadedEvents) {
      if (e.equals(event)) {
        throw new IllegalStateException("Duplicate events. ");
      }
    }
    loadedEvents.add(event);
  }

  @Override
  public void createEvent(String name, Boolean online,
                          String location, Date startDate, int startTime,
                          Date endDate, int endTime, IUser host, List<IUser> invitees)
          throws IllegalArgumentException {
    Event event = new Event(name, online, location, startDate,
            startTime, endDate, endTime, host, invitees);
    for (IEvent e : loadedEvents) {
      if (e.equals(event)) {
        throw new IllegalStateException("Duplicate events. ");
      }
    }
    loadedEvents.add(event);
  }

  @Override
  public void modifyEvent(IEvent oldEvent, IEvent newEvent) {
    this.removeEvent(oldEvent);
    this.createEvent(newEvent);
  }

  @Override
  public void removeEvent(IEvent event) throws IllegalArgumentException, IllegalStateException {
    if (event == null) {
      throw new IllegalArgumentException("Require non-null event. ");
    }
    List<IEvent> temp = new ArrayList<>();
    for (IUser u : event.getInvitees()) {
      temp.addAll(u.getSchedule().getEvents());
    }
    if (!temp.contains(event) && !loadedEvents.contains(event)) {
      throw new IllegalStateException("Event does not exist");
    }

    loadedEvents.remove(event);
    for (IUser u : event.getInvitees()) {
      u.removeEvent(event);
    }
  }


  @Override
  public void scheduleEvent(String name, String location, boolean online, int duration, IUser host,
                            List<IUser> invitees, boolean anyTime) {
    if (!anyTime) {
      if (duration > 480) {
        throw new IllegalArgumentException("duration cannot exceed 8 hours in case of work days");
      }
      for (int startingDay = 2; startingDay < 7; startingDay++) {
        for (int startingHour = 9; startingHour < 17; startingHour++) {
          workDayLoop(startingHour, duration, startingDay,name, online, location, host, invitees);
        }
      }
    }
    for (int startingDay = 1; startingDay < 8; startingDay++) {
      for (int startingHour = 0; startingHour < 24; startingHour++) {
        anyTimeLoop(startingHour, duration, startingDay,name, online, location, host, invitees);
      }
    }
  }

  private void anyTimeLoop(int startingHour, int duration, int startingDay, String name, boolean online, String location, IUser host, List<IUser> invitees) {
    for (int startingMin = 0; startingMin < 60; startingMin++) {
      int startingTime = startingHour * 100 + startingMin;
      int durationHr = duration / 60 * 100;
      int durationMin = duration - durationHr;
      int endingTime = startingHour * 100 + startingMin + durationHr + durationMin;
      int endingDay = endingTime % 1440 + startingDay;
      IEvent event = new Event(name, online, location, intToDate(startingDay), startingTime,
              intToDate(endingDay), endingTime, host, invitees);
      try {
        this.loadEvent(event);
      } catch (IllegalArgumentException | IllegalStateException e) {
        continue;
      }
    }
  }

  private void workDayLoop(int startingHour, int duration, int startingDay, String name, boolean online, String location, IUser host, List<IUser> invitees) {
    for (int startingMin = 0; startingMin < 60; startingMin++) {
      int startingTime = startingHour * 100 + startingMin;
      int durationHr = duration / 60 * 100;
      int durationMin = duration - durationHr;
      int endingTime = startingHour * 100 + startingMin + durationHr + durationMin;
      int endingDay = endingTime % 1440 + startingDay;
      if (endingTime >= 1700) {
        throw new IllegalStateException("cannot schedule event");
      }
      IEvent event = new Event(name, online, location, intToDate(startingDay), startingTime,
              intToDate(endingDay), endingTime, host, invitees);
      try {
        this.loadEvent(event);
      } catch (IllegalArgumentException | IllegalStateException e) {
        continue;
      }
    }
  }

  private Date intToDate(int startingDate) {
    switch (startingDate) {
      case 1:
        return Date.Sunday;
      case 2:
        return Date.Monday;
      case 3:
        return Date.Tuesday;
      case 4:
        return Date.Wednesday;
      case 5:
        return Date.Thursday;
      case 6:
        return Date.Friday;
      case 7:
        return Date.Saturday;
      default:
        throw new IllegalArgumentException("invalid date value");
    }

  }



  @Override
  public void loadEvent(IEvent event) {
    if (event == null) {
      throw new IllegalArgumentException("Requires non-null event. ");
    }
    for (IUser u : event.getInvitees()) {
      List<IEvent> temp = new ArrayList<>(u.getSchedule().getEvents());
      temp.add(event);
      if (u.getSchedule().isOverlap(temp)) {
        throw new IllegalStateException("Events overlapped");
      }
    }

    for (IUser user : event.getInvitees()) {
      user.addEvent(event);
    }
  }

  @Override
  public List<IEvent> seeEvents(Date startDate,
                                int startTime, Date endDate, int endTime, IUser user) {
    List<IEvent> events = new ArrayList<>();
    if (startDate == null || validateTime(startTime, endTime) || endDate == null || user == null) {
      throw new IllegalArgumentException("invalid parameter");
    }
    if (startTime == endTime) {
      throw new IllegalStateException("startTime cannot equal to endTime");
    }
    for (IEvent e : user.getSchedule().getEvents()) {
      if (hasEvent(e, startDate, startTime, endDate, endTime, user)) {
        events.add(e);
      }
    }
    return events;
  }

  /*
  validating the startTime and endTime to make sure that they are on a 24-hour scale.
   */
  private boolean validateTime(int startTime, int endTime) {
    return startTime < 0 || startTime > 2359 || endTime < 0 || endTime > 2359;
  }

  /*
  checks if there is an event during the given time or not.
   */
  private boolean hasEvent(IEvent e, Date startDate, int startTime,
                           Date endDate, int endTime, IUser user) {
    IEvent event = new Event("", false, "",
            startDate, startTime, endDate, endTime, user, List.of(user));

    return user.getSchedule().isOverlap(List.of(event, e));
  }

  @Override
  public List<IUser> getUsers() {
    return new ArrayList<>(this.users);
  }

  @Override
  public List<IEvent> getLoadedEvents() {
    return new ArrayList<>(this.loadedEvents);
  }
}
