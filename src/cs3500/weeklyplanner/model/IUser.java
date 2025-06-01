package cs3500.weeklyplanner.model;

import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Represents a User of the planner system. features are included below.
 */
public interface IUser {

  /**
   * observes the schedule of the user.
   *
   * @return an ISchedule, which is the schedule of the User.
   */
  ISchedule getSchedule();

  /**
   * Adds the provided event to the user's schedule.
   *
   * @param event is the event we want to add.
   */
  void addEvent(IEvent event);

  /**
   * Removes the provided event from the user's schedule.
   *
   * @param event is the event we want to remove.
   */
  void removeEvent(IEvent event);

  /**
   * save a User's schedule to an XML file.
   *
   * @return a XML file as an Document.
   * @throws ParserConfigurationException if we can't parse the schedule to a Document.
   */
  Document saveUserSchedule() throws ParserConfigurationException;

  /**
   * observes a user's name.
   * @return a String that represents the user's name.
   */
  String getName();

}
