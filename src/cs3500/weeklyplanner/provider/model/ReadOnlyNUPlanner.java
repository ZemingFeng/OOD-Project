package cs3500.weeklyplanner.provider.model;

import java.util.List;

/**
 * Interface to represent behavior for immutable version
 * of the model. Only contains observation methods.
 */
public interface ReadOnlyNUPlanner {

  /**
   * Checks who the current user of the planner system is.
   * @return the current user of the system.
   */
  User activeUser();

  /**
   * Gives the list of users present in the planner system.
   * @return the list of users that have been added to the system.
   */
  List<User> listOfUsers();

  /**
   * Checks if the given event conflicts with the existing schedules for all the
   * event's invited users.
   * @param event to check for overlapping time.
   * @return true if the event conflicts and false if not.
   * @throws IllegalStateException if the given event is already present in the system.
   */
  boolean conflicts(Event event);

  /**
   * Changes the user that is currently having their schedule shown to the given user.
   * @param user to change the schedule view to.
   */
  void changeUser(User user);
}
