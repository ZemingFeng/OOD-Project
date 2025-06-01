
package cs3500.weeklyplanner.view.hw06;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import javax.swing.JPanel;

import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.MutablePlannerModel;
import cs3500.weeklyplanner.model.ReadOnlyPlannerModel;

/**
 * represents the panel of a schedule. It takes in a user and a model to
 * present relevant information and draw graphics.
 */
public class JSchedulePanel extends JPanel implements SchedulePanel {
  private IUser user;
  private final Map<Rectangle, IEvent> eventBounds = new HashMap<>();

  /**
   * constructor for the panel class. Graphics are drawn here.
   *
   * @param user  is the user of the schedule.
   * @param model is the model that the system takes in.
   */
  public JSchedulePanel(int index, ReadOnlyPlannerModel model) {
    this.user = model.getUsers().get(index);
    ReadOnlyPlannerModel model1 = Objects.requireNonNull(model);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        for (IEvent event : user.getSchedule().getEvents()) {
          eventBounds.put(createOneDayEventRect(event), event);
        }
        for (Map.Entry<Rectangle, IEvent> entry : eventBounds.entrySet()) {
          if (entry.getKey().contains(e.getPoint())) {
            new JEventFrameView(model, entry.getValue());
          }
        }
      }
    });
  }

  @Override
  public void changeSelectedUser(IUser user) {
    this.user = user;
  }

  // rectangle that represents a single day event.
  private Rectangle createOneDayEventRect(IEvent event) {
    return new Rectangle(locateRect(event)[0], locateRect(event)[1],
            110, getSameDayDuration(event));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    eventBounds.clear();
    for (IEvent event : user.getSchedule().getEvents()) {
      this.drawEvents(event, g2d);
    }
    Rectangle bounds = this.getBounds();
    for (int i = 0; i < bounds.y + 720; i += 120) {
      g2d.setColor(Color.BLACK);
      g2d.setStroke(new BasicStroke(5.0f));
      g2d.drawLine(bounds.x, i, bounds.x + 770, i);
      g2d.setStroke(new BasicStroke(1.0f));
      g2d.drawLine(bounds.x, i + 30, bounds.x + 770, i + 30);
      g2d.drawLine(bounds.x, i + 60, bounds.x + 770, i + 60);
      g2d.drawLine(bounds.x, i + 90, bounds.x + 770, i + 90);
    }
    for (int i = 0; i <= bounds.x + 770; i += 110) {
      g2d.setColor(Color.BLACK);
      g2d.drawLine(i, bounds.y, i, bounds.y + 720);
    }
  }

  private void drawEvents(IEvent e, Graphics2D g2d) {
    if (e.getStartingDay().equals(e.getEndingDay())) {
      g2d.setColor(Color.RED);
      g2d.fillRect(this.locateRect(e)[0], this.locateRect(e)[1], 110, this.getSameDayDuration(e));
      eventBounds.put(new Rectangle(this.locateRect(e)[0],
                      this.locateRect(e)[1], 110, this.getSameDayDuration(e)),
              e);
    } else if (e.getEndingDay().getValue() < e.getStartingDay().getValue()) {
      g2d.setColor(Color.RED);
      g2d.fillRect(this.locateRect(e)[0], this.locateRect(e)[1], 110, 720 - this.locateRect(e)[1]);
      eventBounds.put(new Rectangle(this.locateRect(e)[0], this.locateRect(e)[1], 110,
                      720 - this.locateRect(e)[1]),
              e);
      for (int i = 1; i < 8 - e.getStartingDay().getValue(); i++) {
        this.drawAllDayRect(i + e.getStartingDay().getValue(), g2d);
        eventBounds.put(new Rectangle((i + e.getStartingDay().getValue() - 1) * 110, 0, 110, 720),
                e);
      }
    } else {
      g2d.setColor(Color.RED);
      g2d.fillRect(this.locateRect(e)[0], this.locateRect(e)[1], 110, 720 - this.locateRect(e)[1]);
      eventBounds.put(new Rectangle(this.locateRect(e)[0], this.locateRect(e)[1], 110,
                      720 - this.locateRect(e)[1]),
              e);
      g2d.fillRect(this.locateEndingRect(e)[0], 0, 110, this.locateEndingRect(e)[1]);
      eventBounds.put(new Rectangle(this.locateEndingRect(e)[0], 0, 110, this.locateEndingRect(e)[1]),
              e);
      for (int i = 1; i < e.getEndingDay().getValue() - e.getStartingDay().getValue(); i++) {
        this.drawAllDayRect(i + e.getStartingDay().getValue(), g2d);
        eventBounds.put(new Rectangle((i + e.getStartingDay().getValue() - 1) * 110, 0, 110, 720),
                e);
      }
    }
  }

  private void drawAllDayRect(int dateValue, Graphics2D g2d) {
    g2d.setColor(Color.RED);
    g2d.fillRect((dateValue - 1) * 110, 0, 110, 720);
  }

  private int[] locateRect(IEvent e) {
    int[] intSet = new int[]{100, 100};
    switch (e.getStartingDay()) {
      case Sunday:
        intSet[0] = 0;
        break;
      case Monday:
        intSet[0] = 110;
        break;
      case Tuesday:
        intSet[0] = 220;
        break;
      case Wednesday:
        intSet[0] = 330;
        break;
      case Thursday:
        intSet[0] = 440;
        break;
      case Friday:
        intSet[0] = 550;
        break;
      case Saturday:
        intSet[0] = 660;
        break;
      default:
        throw new IllegalArgumentException("invalid event");
    }
    intSet[1] = (e.getStartingTime() / 100 * 30) +
            ((e.getStartingTime() - e.getStartingTime() / 100 * 100) / 2);
    return intSet;
  }

  private int[] locateEndingRect(IEvent e) {
    int[] intSet = new int[]{100, 100};
    switch (e.getEndingDay()) {
      case Sunday:
        intSet[0] = 0;
        break;
      case Monday:
        intSet[0] = 110;
        break;
      case Tuesday:
        intSet[0] = 220;
        break;
      case Wednesday:
        intSet[0] = 330;
        break;
      case Thursday:
        intSet[0] = 440;
        break;
      case Friday:
        intSet[0] = 550;
        break;
      case Saturday:
        intSet[0] = 660;
        break;
      default:
        throw new IllegalArgumentException("invalid event");
    }
    intSet[1] = (e.getEndingTime() / 100 * 30) +
            ((e.getEndingTime() - e.getEndingTime() / 100 * 100) / 2);
    return intSet;
  }

  private int getSameDayDuration(IEvent e) {
    int hr = e.getEndingTime() / 100 - e.getStartingTime() / 100;
    int min = 0;
    min = e.getEndingTime() - e.getEndingTime() / 100 *
            100 - (e.getStartingTime() - e.getStartingTime() / 100 * 100);
    return (hr * 30) + (min / 2);
  }
}
 