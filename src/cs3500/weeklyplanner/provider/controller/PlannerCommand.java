package cs3500.weeklyplanner.provider.controller;

import planner.model.NUPlanner;

/**
 * An interface representing the methods needed by commands that are given to the controller.
 */
public interface PlannerCommand {

  /**
   * Executes the given command correctly.
   */
  void execute(NUPlanner planner);

}
