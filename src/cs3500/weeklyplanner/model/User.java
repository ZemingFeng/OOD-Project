package cs3500.weeklyplanner.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * An implementation of the IUser interface. In this project,
 * all users only have two fields, which would be their name
 * and their schedule. In future projects the user may have
 * different access or other properties.
 */
public class User implements IUser {
  private String name;
  private ISchedule schedule;

  /**
   * constructor of the User class.
   *
   * @param name     is the name of the user.
   * @param schedule is the schedule of the user.
   */
  public User(String name, ISchedule schedule) {
    if (name == null) {
      throw new IllegalArgumentException("Require non-null name");
    }
    this.name = name;
    this.schedule = schedule;
  }


  @Override
  public ISchedule getSchedule() {
    return this.schedule;
  }

  @Override
  public void addEvent(IEvent event) {
    schedule.addEvent(event);
  }

  @Override
  public void removeEvent(IEvent event) {
    schedule.removeEvent(event);
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public Document saveUserSchedule() throws ParserConfigurationException {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document userSchedule = builder.newDocument();
    //schedule
    Element schedule = userSchedule.createElement("schedule");
    userSchedule.appendChild(schedule);
    schedule.setAttribute("id", this.name);
    //schedule-events
    for (IEvent e : this.schedule.getEvents()) {
      Element event = userSchedule.createElement("event");
      schedule.appendChild(event);
      //schedule-event-name
      Element name = userSchedule.createElement("name");
      name.appendChild(userSchedule.createTextNode(e.getName()));
      event.appendChild(name);
      //schedule-event-time
      Element time = userSchedule.createElement("time");
      event.appendChild(time);
      //schedule-event-time-startDay
      Element startDay = userSchedule.createElement("start-day");
      startDay.appendChild(userSchedule.createTextNode(e.getStartingDay().toString()));
      time.appendChild(startDay);
      //schedule-event-time-startTime
      Element startTime = userSchedule.createElement("start");
      startTime.appendChild(userSchedule.createTextNode(intToString(e.getStartingTime())));
      time.appendChild(startTime);
      //schedule-event-time-endDay;
      Element endDay = userSchedule.createElement("end-day");
      endDay.appendChild(userSchedule.createTextNode(e.getEndingDay().toString()));
      time.appendChild(endDay);
      //schedule-event-time-endTime
      Element endTime = userSchedule.createElement("end");
      endTime.appendChild(userSchedule.createTextNode(intToString(e.getEndingTime())));
      time.appendChild(endTime);
      //schedule-event-location
      Element location = userSchedule.createElement("location");
      event.appendChild(location);
      //schedule-event-location-online
      Element online = userSchedule.createElement("online");
      online.appendChild(userSchedule.createTextNode(boolToString(e.getOnline())));
      location.appendChild(online);
      //schedule-event-location-place
      Element place = userSchedule.createElement("place");
      place.appendChild(userSchedule.createTextNode(e.getLocation()));
      location.appendChild(place);
      //schedule-event-users
      Element users = userSchedule.createElement("users");
      event.appendChild(users);
      //schedule-event-users-uid
      for (IUser u : e.getInvitees()) {
        Element uid = userSchedule.createElement("uid");
        uid.appendChild(userSchedule.createTextNode(u.toString()));
        users.appendChild(uid);
      }
    }
    return userSchedule;
  }

  /*
  converts the provided int to String.
   */
  private String intToString(int i) {
    return "" + i;
  }

  /*
  converts the provided boolean to String.
   */
  private String boolToString(boolean b) {
    if (b) {
      return "true";
    } else {
      return "false";
    }
  }

  @Override
  public String getName() {
    return this.name;
  }
}
