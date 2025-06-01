package cs3500.weeklyplanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple Event implementation of IEvent interface.
 */
public class Event implements IEvent {

  private final String name;
  private final boolean online;
  private final String location;
  private final Date startingDay;
  private final int startingTime;
  private final Date endingDay;
  private final int endingTime;
  private final IUser host;
  private final List<IUser> invitees;

  /**
   * Constructor of Event class.
   *
   * @param name         is the name of the event.
   * @param online       tells whether the event is online or not.
   * @param location     is the location of the event.
   * @param startingDay  is the startingDay of the event.
   * @param startingTime is the startingTime of the event.
   * @param endingDay    is the endingDay of the event.
   * @param endingTime   is the endingTime of the event.
   * @param host         is the host of the event, which is an IUser.
   * @param invitees     is the invitees of the event, which is a list of IUser.
   * @throws IllegalArgumentException if any of the parameters is invalid.
   */
  public Event(String name, boolean online, String location, Date startingDay,
               int startingTime, Date endingDay, int endingTime, IUser host, List<IUser> invitees)
          throws IllegalArgumentException {
    if (name == null || location == null || startingDay == null
            || endingDay == null || host == null || invitees == null) {
      throw new IllegalArgumentException("Require non-null arguments. ");
    }
    if (startingTime < 0 || startingTime > 2359 || endingTime < 0 || endingTime > 2359) {
      throw new IllegalArgumentException("Invalid Time");
    }
    this.name = name;
    this.online = online;
    this.location = location;
    this.startingDay = startingDay;
    this.startingTime = startingTime;
    this.endingDay = endingDay;
    if (startingDay.equals(endingDay)) {
      if (endingTime == startingTime) {
        throw new IllegalArgumentException("ending time cannot be the same as starting time");
      }
    }
    this.endingTime = endingTime;
    this.host = host;
    this.invitees = invitees;
  }

  @Override
  public Date getStartingDay() {
    return this.startingDay;
  }

  @Override
  public Date getEndingDay() {
    return this.endingDay;
  }

  @Override
  public int getStartingTime() {
    return this.startingTime;
  }

  @Override
  public int getEndingTime() {
    return this.endingTime;
  }

  @Override
  public List<IUser> getInvitees() {
    return new ArrayList<>(invitees);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean getOnline() {
    return online;
  }

  @Override
  public String getLocation() {
    return location;
  }


  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Event)) {
      return false;
    }

    Event that = (Event) other;
    return this.name.equals(that.name) &&
            this.online == that.online &&
            this.location.equals(that.location) &&
            this.startingDay.equals(that.startingDay) &&
            this.startingTime == that.startingTime &&
            this.endingDay.equals(that.endingDay) &&
            this.endingTime == that.endingTime &&
            this.host.equals(that.host) &&
            this.invitees.equals(that.invitees);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            startingTime * endingTime * Math.random());
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public IUser getHost() {
    return this.host;
  }
}
