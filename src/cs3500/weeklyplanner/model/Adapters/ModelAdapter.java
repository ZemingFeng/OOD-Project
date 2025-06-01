package cs3500.weeklyplanner.model.Adapters;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import cs3500.weeklyplanner.provider.model.Time;
import cs3500.weeklyplanner.provider.model.User;
import cs3500.weeklyplanner.provider.model.Event;

public interface ModelAdapter {
  /**
   * Adds the given user to the list of users present in the planner system.
   * @param user to be added into the system.
   */
  void addUser(cs3500.weeklyplanner.provider.model.User user);

  /**
   * Changes the current active user of the planner system to the given user.
   * @param user to become the current active user.
   * @throws IllegalArgumentException if the given user is not present in the planner system.
   */
  void changeUser(cs3500.weeklyplanner.provider.model.User user);

  /**
   * Add an event to the planner system, making sure to add it to the schedules of all
   * invited users.
   * @param event that is added to the system.
   */
  void addEvent(Event event);

  /**
   * Remove an event from the system. If the current user is the host, remove all instances of
   * the event. If they are not the host, only remove it from their schedule.
   * @param event that is removed from the system.
   */
  void removeEvent(Event event);

  /**
   * Modifies the given event by changing its parameters to the given parameters. Goes into
   * the given event and uses the event's list of invited users to modify each instance of the
   * event.
   * @param event that is being changed.
   * @param name of the modified event.
   * @param location of the modified event.
   * @param online of the modified event.
   * @param startTime of the modified event.
   * @param endTime of the modified event.
   * @param invitedUsers of the modified event.
   */
  void modifyEvent(Event event,
                   String name,
                   String location,
                   boolean online,
                   Time startTime, Time endTime,
                   List<User> invitedUsers);

  /**
   * Uploads a given XML file with a schedule system in it into the schedule system.
   * @param xml file containing a schedule.
   * @throws ParserConfigurationException if there is a serious configuration error.
   * @throws IOException if an IOException occurs when uploading.
   * @throws SAXException if it occurs when uploading.
   */
  void uploadSchedule(File xml) throws ParserConfigurationException, IOException, SAXException;

  /**
   * Saves the schedule system to a given XML file, formatting it correctly. If the given file
   * already has contents in it, they are overwritten.
   * @param xml file to write the schedule contents to.
   * @throws IOException if an IOException occurs when saving.
   */
  void saveSchedule(File xml) throws ParserConfigurationException, IOException, SAXException;

}
