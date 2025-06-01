package cs3500.weeklyplanner.model;

import java.util.List;

/**
 * Interface that represents an Event. features are included below.
 */
public interface IEvent {
  /**
   * observes the specified field, in this case is the startingDay.
   *
   * @return the startingDay of the event.
   */
  Date getStartingDay();

  /**
   * observes the specified field, in this case is the endingDay.
   *
   * @return the endingDay of the event.
   */
  Date getEndingDay();

  /**
   * observes the corresponding field, in this case is the startingTime.
   *
   * @return the startingTime of the event.
   */
  int getStartingTime();

  int getEndingTime();

  List<IUser> getInvitees();

  String getName();

  boolean getOnline();

  String getLocation();

  IUser getHost();
}
