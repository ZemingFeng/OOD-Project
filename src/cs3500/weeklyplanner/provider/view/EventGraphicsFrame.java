package cs3500.weeklyplanner.provider.view;

import java.awt.BorderLayout;
import java.util.function.Consumer;

import javax.swing.JFrame;

import planner.model.Event;
import planner.model.ReadOnlyNUPlanner;

/**
 * Event frame to take in event details, create new events, remove events, and modify events.
 * Has an event name panel, location/details panel, list of available users in the system, and
 * buttons to create, remove, and modify events.
 */
public class EventGraphicsFrame extends JFrame implements EventView {

  // Width and height values of the event frame
  private static final int FRAME_WIDTH = 400;
  private static final int FRAME_HEIGHT = 500;
  private EventPanel panel;

  /**
   * Constructor for event frame with all detail fields empty. Is created when the user selects
   * create event, modify event, or remove event at the bottom of the main frame.
   * @param planner system being used currently.
   */
  public EventGraphicsFrame(ReadOnlyNUPlanner planner) {
    super();
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.requestFocus();
    this.setLayout(new BorderLayout());
    this.panel = new EventPanel(planner, FRAME_WIDTH, FRAME_HEIGHT);
    this.add(this.panel);
  }

  /**
   * Constructor for event frame with details populated by the details of the given event.
   * Is displayed when the current user clicks on an event that is already displayed on their
   * schedule panel.
   * @param planner system being used currently.
   * @param event given to populate the details fields with,
   */
  public EventGraphicsFrame(ReadOnlyNUPlanner planner, Event event) {
    super();
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.add(new EventPanel(planner, event, FRAME_WIDTH, FRAME_HEIGHT));
    this.setResizable(false);
  }

  public void setCommandCallback(Consumer<String> callback) {
    this.panel.setCommandCallback(callback);
  }

}
