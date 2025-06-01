package cs3500.weeklyplanner.view.hw06;

import java.util.Objects;

import cs3500.weeklyplanner.controller.WeeklyPlannerController;
import cs3500.weeklyplanner.model.ReadOnlyPlannerModel;
import cs3500.weeklyplanner.model.WeeklyPlannerModel;

public class WeeklyPlannerSystemView implements PlannerSystemView {
  private ReadOnlyPlannerModel model;
  private MainFrame mainFrame;
  private EventFrame eventFrame;

  public WeeklyPlannerSystemView(ReadOnlyPlannerModel model) {
    this.model = Objects.requireNonNull(model);
    this.mainFrame = new JMainFrameView(model);
    this.eventFrame = new JEventFrameView(model, mainFrame.getCurrentUser());
  }

  @Override
  public MainFrame getMainFrame() {
    return mainFrame;
  }

  @Override
  public EventFrame getEventFrame() {
    return eventFrame;
  }

  public void displayMainFrame() {
    mainFrame.drawLayout();
  }

  public void displayEventFrame() {
    eventFrame.drawLayout();
  }

}
