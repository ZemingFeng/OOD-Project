package cs3500.weeklyplanner.provider.view;

import java.util.function.Consumer;
import javax.swing.JFrame;

/**
 * Interface for the Event frame.
 */
public interface EventView {

  /**
   * Sets the event frame as visible.
   * @param b boolean true for visible and false for invisible.
   */
  void setVisible(boolean b);

  /**
   * Sets the controller as the command callback for this frame.
   * @param callback consumer to send string-commands to the controller.
   */
  void setCommandCallback(Consumer<String> callback);
}
