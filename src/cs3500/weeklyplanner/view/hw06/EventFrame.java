package cs3500.weeklyplanner.view.hw06;

import java.awt.event.ActionListener;
import java.util.List;

import cs3500.weeklyplanner.model.Date;
import cs3500.weeklyplanner.model.IUser;

/**
 * Event Frame interface. Represents an Event frame.
 * for grading, please ignore this for now.
 */
public interface EventFrame {
  void setListener(ActionListener listener);

  String getEventName();

  String getLocationName();
  boolean getOnline();

  List<IUser> getInvitees();
  Date getStartingDate();
  int getStartingTime();
  Date getEndingDay();
  int getEndingTime();
  IUser getHost();
  void drawLayout();
  int getDuration();
  boolean getAnyTime();
}
