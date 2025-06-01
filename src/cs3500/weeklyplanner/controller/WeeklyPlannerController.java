package cs3500.weeklyplanner.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs3500.weeklyplanner.model.Event;
import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.MutablePlannerModel;

import cs3500.weeklyplanner.view.hw06.MainFrame;
import cs3500.weeklyplanner.view.hw06.PlannerSystemView;

public class WeeklyPlannerController implements PlannerModelController, ActionListener {
  private MutablePlannerModel model;
  private PlannerSystemView view;
  private MainFrame mainFrameView;

  public WeeklyPlannerController(MutablePlannerModel model, PlannerSystemView view) {
    this.model = model;
    this.view = view;

    view.getMainFrame().setListener(this);
    view.getEventFrame().setListener(this);


  }

  @Override
  public void actionPerformed(ActionEvent e) {

    switch (e.getActionCommand()) {
      case "createEvent":
        IEvent event = new Event(view.getEventFrame().getEventName(),
                view.getEventFrame().getOnline(),
                view.getEventFrame().getLocationName(),
                view.getEventFrame().getStartingDate(),
                view.getEventFrame().getStartingTime(),
                view.getEventFrame().getEndingDay(),
                view.getEventFrame().getEndingTime(),
                view.getEventFrame().getHost(),
                view.getEventFrame().getInvitees());
        model.createEvent(event);
        model.loadEvent(event);
        view.getMainFrame().repaintAll();
        break;
      case "removeEvent":
        break;
      case "modifyEvent":
        break;
      case "scheduleEvent":
        model.scheduleEvent(
                view.getEventFrame().getEventName(),
                view.getEventFrame().getLocationName(),
                view.getEventFrame().getOnline(),
                view.getEventFrame().getDuration(),
                view.getEventFrame().getHost(),
                view.getEventFrame().getInvitees(),
                view.getEventFrame().getAnyTime()
        );
        break;
      default:
        throw new IllegalArgumentException("invalid operation");
    }
  }
}
