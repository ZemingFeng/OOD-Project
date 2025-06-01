package cs3500.weeklyplanner.view.hw05;

import java.util.ArrayList;
import java.util.List;


import cs3500.weeklyplanner.model.Date;
import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.ISchedule;
import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.WeeklyPlannerModel;

/**
 * A text implementation class of the View. This implementation takes in a model
 * and renders the model to a String.
 */
public class ScheduleTextView implements ScheduleView {
  private WeeklyPlannerModel model;

  /**
   * the constructor for the view class.
   *
   * @param model is the model to be viewed.
   */
  public ScheduleTextView(WeeklyPlannerModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    String result = "";
    for (IUser u : model.getUsers()) {
      result += "User: " + u.toString() + "\n" + this.scheduleToText(u.getSchedule());
    }
    return result;
  }

  /*
  converts the schedule to a String to visualize it.
   */
  private String scheduleToText(ISchedule schedule) {
    String result = "";
    List<Date> dates = List.of(Date.Sunday, Date.Monday, Date.Tuesday,
            Date.Wednesday, Date.Thursday, Date.Friday, Date.Saturday);
    for (int i = 0; i < dates.size(); i++) {
      result += dates.get(i).toString() + ": \n";
      for (IEvent e : schedule.getEvents()) {
        if (e.getStartingDay().equals(dates.get(i))) {
          result += eventToText(e) + "\n";
        }
      }
    }
    return result;
  }

  /*
  converts the provided event to a String to visualize it.
   */
  private String eventToText(IEvent e) {
    return "        name:" + e.getName() + "\n        "
            + "time:" + e.getStartingDay().toString() + ": " + e.getStartingTime() + " -> "
            + e.getEndingDay().toString() + ": " + e.getEndingTime() + "\n        " +
            "location: " + e.getLocation() + "\n        "
            + "online: " + e.getOnline() + "\n        " +
            "invitees: " + String.join("\n        ", this.usersToList(e.getInvitees()));


  }

  /*
  converts the provided list of IUser to a list of String.
   */
  private List<String> usersToList(List<IUser> users) {
    List<String> result = new ArrayList<>();
    for (IUser u : users) {
      result.add(u.toString());
    }
    return result;
  }
}

