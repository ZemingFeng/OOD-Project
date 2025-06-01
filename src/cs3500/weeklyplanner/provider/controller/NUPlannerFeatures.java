package cs3500.weeklyplanner.provider.controller;

/**
 * An interface representing the controller for the NUPlanner system.
 */
public interface NUPlannerFeatures {

  /**
   * Accepts the command from view to the controller for processing.
   * @param command - a given command.
   */
  void accept(String command);

  /**
   * Processes a given command given to the controller.
   * @param command - the given command for the controller to process.
   */
  void processCommand(String command);

  /**
   * Initializes the controller and sets it as the command callback for the view.
   */
  void control();


}
