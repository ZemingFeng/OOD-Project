package cs3500.weeklyplanner.provider.view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JPanel;

import planner.model.Day;
import planner.model.Event;
import planner.model.ReadOnlyNUPlanner;
import planner.model.Time;
import planner.model.User;

/**
 * WeekPanel class represents the central panel in the main frame.
 * Shows the current user's schedule if a user is selected, otherwise the schedule is blank.
 * If an event in the schedule is clicked on, an event frame is opened with the event's
 * information present.
 */
public class WeekPanel extends JPanel {

  private final ReadOnlyNUPlanner planner;
  private Consumer<String> commandCallback;

  /**
   * Constructor for week panel.
   * @param planner to be used.
   */
  public WeekPanel(ReadOnlyNUPlanner planner) {
    this.planner = Objects.requireNonNull(planner);
    this.setLayout(new GridLayout(1, 7));
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Time mouseClickTime = posToTime(e.getX(), e.getY());
        String cmd = null;
        for (Event userEvent : planner.activeUser().getSchedule()) {
          if ((mouseClickTime.before(userEvent.end())
                  && !mouseClickTime.before(userEvent.start()))
                  || (!mouseClickTime.before(userEvent.start())
                  && userEvent.end().before(userEvent.start()))) {
            cmd = "CreatePopulatedEventFrame " +
                    WeekPanel.createEventCommandString(userEvent);
            System.out.print(cmd);

          }
        }
        WeekPanel.this.commandCallback.accept(cmd);
      }
    });
  }

  private static String createEventCommandString(Event event) {
    StringBuilder cmd = new StringBuilder();
    cmd.append(event.name()).append(" ");
    cmd.append(event.location()).append(" ");
    cmd.append(event.online()).append(" ");
    cmd.append(event.start().day()).append(" ");
    cmd.append(event.start().minutes()).append(" ");
    cmd.append(event.end().day()).append(" ");
    cmd.append(event.end().minutes()).append(" ");
    for (User user : event.getInvitedUsers()) {
      cmd.append(user.name()).append(" ");
    }
    return String.valueOf(cmd);
  }

  /**
   * Converts a given position on the schedule panel to a time value.
   * @param x position on the board.
   * @param y position on the board.
   * @return Time value at the given position with the correct day and minutes values.
   * @throws IllegalArgumentException if the selected position is not inside the panel.
   */
  private Time posToTime(int x, int y) {
    int dayWidth = getWidth() / 7;
    int minutes = y * 1440 / getHeight();
    switch (x / dayWidth) {
      case 0:
        return new Time(Day.SUN, minutes);
      case 1:
        return new Time(Day.MON, minutes);
      case 2:
        return new Time(Day.TUES, minutes);
      case 3:
        return new Time(Day.WED, minutes);
      case 4:
        return new Time(Day.THURS, minutes);
      case 5:
        return new Time(Day.FRI, minutes);
      case 6:
        return new Time(Day.SAT, minutes);
      default:
        throw new IllegalArgumentException("Value out of bounds.");
    }
  }

  /**
   * Overrides paintComponent method to correctly paint the schedule with lines for the hours and
   * days, and fills in the events of the selected user.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.CYAN);
    this.paintEvents(this.planner.activeUser(), g2d);

    // Draw lines for each day
    g2d.setColor(Color.BLACK);
    for (int day = 1; day < 7; day++) {
      g2d.drawLine(getWidth() / 7 * day, 0, getWidth() / 7 * day, getHeight());
    }

    // Draw lines for each hour
    g2d.setColor(Color.BLACK);
    for (int hour = 0; hour < 25; hour++) {
      if (hour % 4 == 0) {
        Stroke oldStroke = g2d.getStroke();

        g2d.setStroke(new BasicStroke(3));

        g2d.drawLine(0, hour * getHeight() / 24, getWidth(), hour * getHeight() / 24);

        g2d.setStroke(oldStroke);

      } else {
        g2d.drawLine(0, hour * getHeight() / 24, getWidth(), hour * getHeight() / 24);
      }
    }
  }

  /**
   * Helper method to paint the events of the given user's schedule onto the panel.
   * @param user with a given schedule to paint.
   * @param g2d Graphics object to help paint.
   */
  private void paintEvents(User user, Graphics2D g2d) {
    int dayWidth = getWidth() / 7;
    for (Event event : user.getSchedule()) {
      for (Day day : Day.values()) {
        if (event.start().sameDay(day)) {
          int xStart = day.getDayValue() * dayWidth;
          int yStart = event.start().minutes() * getHeight() / 1440;

          // Event starts and ends same day
          if (event.start().sameDay(event.end().day())) {
            if (event.end().before(event.start())) {
              this.paintToWeekend(event, g2d);
            } else {
              g2d.fillRect(xStart, yStart, dayWidth,
                      (event.end().minutes() - event.start().minutes()) * getHeight() / 1440);
            }
          } else {
            // Event starts before it ends in the week (Sunday to Saturday)
            if (event.start().before(event.end())) {
              g2d.fillRect(xStart, yStart, dayWidth, getHeight() - yStart);
              // Fully fill in days between start day and end day
              for (int nextDays = day.getDayValue() + 1; nextDays < event.end().day().getDayValue();
                   nextDays++) {
                g2d.fillRect(nextDays * dayWidth, 0, dayWidth, (getHeight()));
              }
              g2d.fillRect(event.end().day().getDayValue() * dayWidth, 0,
                      dayWidth, event.end().minutes() * getHeight() / 1440);
              // Event goes over weekend, so everything following start is filled in
            } else {
              this.paintToWeekend(event, g2d);
            }
          }
        }
      }
    }
  }

  /**
   * Fills in the schedule from the start of the event until the weekend. Is used
   * if the event starts and ends on different weeks (Event crosses over Sunday at 00:00).
   * @param event time of the event to be painted.
   * @param g2d Graphics object to help paint.
   */
  private void paintToWeekend(Event event, Graphics2D g2d) {
    int dayWidth = getWidth() / 7;
    int xStart = event.start().day().getDayValue() * dayWidth;
    int yStart = event.start().minutes() * getHeight() / 1440;
    g2d.fillRect(xStart, yStart, dayWidth, getHeight() - yStart);
    // Paint every day after the start day until the weekend
    for (int nextDays = event.start().day().getDayValue() + 1; nextDays < Day.values().length;
         nextDays++) {
      g2d.fillRect(
              nextDays * dayWidth, 0, dayWidth, (getHeight()));
    }
  }

  public void setCommandCallback(Consumer<String> callback) {
    this.commandCallback = callback;
  }
}
