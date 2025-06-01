package cs3500.weeklyplanner.provider.view;

import java.util.function.Consumer;

/**
 * Interface for display of main panel and frame.
 */
public interface MainView {

  /**
   * Makes the main frame visible to the user.
   * @param b boolean - true means visible, false means invisible.
   */
  void setVisible(boolean b);

  /**
   * Repaints this frame.
   */
  void refresh();

  /**
   * Sets the controller to be the command callback of the main frame. Allows the main
   * frame to send string-based commands to the controller.
   * @param callback consumer to send string-based commands.
   */
  void setCommandCallback(Consumer<String> callback);

}
