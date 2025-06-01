package cs3500.weeklyplanner.view.hw06.Adapters;

import java.util.Objects;
import java.util.function.Consumer;

import cs3500.weeklyplanner.provider.view.EventView;
import cs3500.weeklyplanner.view.hw06.EventFrame;
import cs3500.weeklyplanner.view.hw06.PlannerSystemView;

public class MainViewAdapterImp implements MainViewAdapter{
  private PlannerSystemView plannerView;
  private EventFrame eventFrame;


  public MainViewAdapterImp(PlannerSystemView plannerView, EventFrame eventFrame) {
    this.plannerView = Objects.requireNonNull(plannerView);
    this.eventFrame = Objects.requireNonNull(eventFrame);
  }
  @Override
  public void setVisible(boolean b) {
    throw new UnsupportedOperationException("our implementation does not support this operation");
  }

  @Override
  public void refresh() {
    plannerView.getMainFrame().repaintAll();
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {

  }
}
