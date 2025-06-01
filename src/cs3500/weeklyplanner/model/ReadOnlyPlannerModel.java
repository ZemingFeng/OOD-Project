package cs3500.weeklyplanner.model;

import java.util.List;

/**
 * the Read Only version of the Planner Model, which prevents
 * view from modifying data.
 */
public interface ReadOnlyPlannerModel {

  /**
   * observes all the users of the weekly planner system.
   *
   * @return a list of IUser
   */
  List<IUser> getUsers();

  /**
   * observes what events are loaded now.
   *
   * @return a list of IEvent.
   */
  List<IEvent> getLoadedEvents();
}

// 1. conflict event. Should it return a boolean?
// 2. should we only change all interface?
// 3. getter methods.
