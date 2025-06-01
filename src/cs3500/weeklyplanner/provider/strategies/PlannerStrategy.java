package cs3500.weeklyplanner.provider.strategies;

import planner.model.Event;

/**
 * An interface that contains the actions
 * that are shared across different strategies
 * for the controller.
 */
public interface PlannerStrategy {

  /**
   * Schedules an event at a given time in a given model with a strategy.
   * @param event - event given to schedule with duration field
   */
  Event scheduleEvent(Event event);

}
