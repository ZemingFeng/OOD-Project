package cs3500.weeklyplanner.view.hw06;

import java.awt.event.ActionListener;

import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.Schedule;

/**
 * Main frame.
 * for grading, please ignore this for now.
 */
public interface MainFrame {
  void setListener(ActionListener listener);
  void drawLayout();
  IUser getCurrentUser();
  SchedulePanel getPanel();
  void repaintAll();
}
