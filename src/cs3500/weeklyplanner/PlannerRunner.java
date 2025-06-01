package cs3500.weeklyplanner;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.weeklyplanner.controller.WeeklyPlannerController;
import cs3500.weeklyplanner.model.Date;
import cs3500.weeklyplanner.model.Event;
import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.ISchedule;
import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.MutablePlannerModel;
import cs3500.weeklyplanner.model.Schedule;
import cs3500.weeklyplanner.model.User;
import cs3500.weeklyplanner.model.WeeklyPlannerModel;
import cs3500.weeklyplanner.view.hw06.JEventFrameView;
import cs3500.weeklyplanner.view.hw06.JMainFrameView;

import cs3500.weeklyplanner.view.hw06.MainFrame;
import cs3500.weeklyplanner.view.hw06.PlannerSystemView;
import cs3500.weeklyplanner.view.hw06.WeeklyPlannerSystemView;

/**
 * main runner class of the model. It only displays the system for this assignment.
 */
public class PlannerRunner {

  /**
   * main runner method.
   *
   * @param args are the user inputs.
   */
  public static void main(String[] args) {
    ISchedule schedule1 = new Schedule(new ArrayList<>());
    ISchedule schedule2 = new Schedule(new ArrayList<>());
    IUser user1 = new User("user1", schedule1);
    IUser user2 = new User("user2", schedule2);
    IEvent event1 = new Event("event1", false, "location", Date.Monday, 900,
            Date.Monday, 1000, user1, List.of(user1, user2));
    IEvent event2 = new Event("event2", false, "location", Date.Tuesday, 924,
            Date.Tuesday, 954, user1, List.of(user1));
    IEvent event3 = new Event("event3", false, "location", Date.Wednesday, 1145,
            Date.Wednesday, 1322, user1, List.of(user1, user2));
    IEvent event4 = new Event("event4", false, "location", Date.Thursday, 1145,
            Date.Thursday, 1222, user1, List.of(user1, user2));
    IEvent event5 = new Event("event5", true, "location", Date.Sunday, 1430,
            Date.Sunday, 1730, user1, List.of(user1, user2));
    IEvent event6 = new Event("event6", true, "location", Date.Friday, 1430,
            Date.Tuesday, 1730, user1, List.of(user1));

    MutablePlannerModel model = new WeeklyPlannerModel(List.of(user1, user2), List.of());
    model.loadEvent(event1);
    model.loadEvent(event2);
    model.loadEvent(event3);
    model.loadEvent(event4);
    model.loadEvent(event6);
    model.loadEvent(event5);
    PlannerSystemView view = new WeeklyPlannerSystemView(model);
    WeeklyPlannerController controller = new WeeklyPlannerController(model, view);
    JList<IUser> users = new JList<>(model.getUsers().toArray(new IUser[2]));
    System.out.println(model.getUsers());
    users.setSelectedIndices(new int[]{0, 1});
    System.out.println(users.getSelectedValue());
  }
}