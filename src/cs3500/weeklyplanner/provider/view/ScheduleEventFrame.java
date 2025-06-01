package cs3500.weeklyplanner.provider.view;

import java.awt.BorderLayout;
import java.util.function.Consumer;

import javax.swing.JFrame;

import planner.model.Event;
import planner.model.ReadOnlyNUPlanner;

/**
 * Class to create frame for scheduling an event using
 * the created strategies. Uses duration and a strategy
 * instead of two given times.
 */
public class ScheduleEventFrame extends JFrame implements EventView {

  // Width and height values of the event frame
  private static final int FRAME_WIDTH = 400;
  private static final int FRAME_HEIGHT = 500;
  private ScheduleEventPanel panel;

  /**
   * Constructor for event frame with all detail fields empty. Is created when the user selects
   * create event, modify event, or remove event at the bottom of the main frame.
   * @param planner system being used currently.
   */
  public ScheduleEventFrame(ReadOnlyNUPlanner planner) {
    super();
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.panel = new ScheduleEventPanel(planner, FRAME_WIDTH, FRAME_HEIGHT);
    this.add(this.panel);
  }

  /**
   * Constructor for event frame with details populated by the details of the given event.
   * Is displayed when the current user clicks on an event that is already displayed on their
   * schedule panel.
   * @param planner system being used currently.
   * @param event given to populate the details fields with,
   */
  public ScheduleEventFrame(ReadOnlyNUPlanner planner, Event event) {
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
