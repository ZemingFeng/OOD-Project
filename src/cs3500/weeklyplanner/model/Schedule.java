package cs3500.weeklyplanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A schedule implementation of the ISchedule interface. This is
 * a simple implementation, which only allows the user to have a list
 * of events displayed on their schedule but no other features.
 */
public class Schedule implements ISchedule {
  private List<IEvent> events;


  /**
   * constructor for the Schedule class.
   *
   * @param events are the list of events that a schedule has.
   */
  public Schedule(List<IEvent> events) {
    if (this.isOverlap(events)) {
      throw new IllegalArgumentException("events overlapped");
    }
    this.events = events;
  }

  @Override
  public boolean isOverlap(List<IEvent> events) {
    for (int i = 0; i < events.size() - 1; i++) {
      for (int j = i + 1; j < events.size(); j++) {
        if (events.get(i).getStartingDay().equals(events.get(i).getEndingDay()) &&
                events.get(j).getStartingDay().equals(events.get(j).getEndingDay()) &&
                !events.get(i).getStartingDay().equals(events.get(j).getEndingDay())) {
          return false;
        }
        if ((events.get(j).getEndingDay().getValue()
                >= events.get(i).getStartingDay().getValue()
                && events.get(j).getStartingDay().getValue()
                >= events.get(i).getEndingDay().getValue())
                || (events.get(i).getEndingDay().getValue()
                >= events.get(j).getStartingDay().getValue()
                && events.get(i).getStartingDay().getValue()
                >= events.get(j).getEndingDay().getValue())) {
          if ((events.get(j).getEndingTime() >= events.get(i).getStartingTime()
                  && events.get(j).getStartingTime() >= events.get(i).getEndingTime())
                  || (events.get(i).getEndingTime() >= events.get(j).getStartingTime()
                  && events.get(i).getStartingTime() >= events.get(j).getEndingTime())) {
            return true;
          }
        }
      }


    }

    return false;
  }

  @Override
  public List<IEvent> getEvents() {
    return this.events;
  }

  @Override
  public void addEvent(IEvent event) {
    List<IEvent> events = new ArrayList<>(this.events);
    events.add(event);
    if (this.isOverlap(events)) {
      throw new IllegalStateException("events overlapped");
    }
    this.events.add(event);
  }

  @Override
  public void removeEvent(IEvent event) {
    events.remove(event);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Schedule)) {
      return false;
    }

    Schedule that = (Schedule) other;
    return this.events.equals(that.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(events);
  }

}


