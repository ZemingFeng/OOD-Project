package cs3500.weeklyplanner.model.Adapters;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

import cs3500.weeklyplanner.provider.model.Event;
import cs3500.weeklyplanner.provider.model.NUPlanner;
import cs3500.weeklyplanner.provider.model.Time;
import cs3500.weeklyplanner.provider.model.User;
import cs3500.weeklyplanner.model.*;
import cs3500.weeklyplanner.xmlutils.XMLUtils;

public class ModelAdapterImp implements NUPlanner {
  private MutablePlannerModel model;
  private XMLUtils xmlClass;
  private UserAdapter user;

  public ModelAdapterImp(MutablePlannerModel model, XMLUtils xmlClass, UserAdapter user) {
    this.model = Objects.requireNonNull(model);
    this.xmlClass = Objects.requireNonNull(xmlClass);
    this.user = Objects.requireNonNull(user);
  }

  @Override
  public void addUser(User user) {
    throw new UnsupportedOperationException("Our class do not support this operation.");
  }

  @Override
  public User activeUser() {
    return null;
  }

  @Override
  public List<User> listOfUsers() {
    return null;
  }

  @Override
  public boolean conflicts(Event event) {
    return false;
  }

  @Override
  public void changeUser(User user) {
    // myUser = userAdapter(user)
    model.select(user);
  }

  @Override
  public void addEvent(Event event) {

  }

  @Override
  public void removeEvent(Event event) {

  }

  @Override
  public void modifyEvent(Event event, String name, String location, boolean online, Time startTime, Time endTime, List<User> invitedUsers) {

  }

  @Override
  public void uploadSchedule(File xml) throws ParserConfigurationException, IOException, SAXException {
  }

  @Override
  public void saveSchedule(File xml) throws ParserConfigurationException, IOException, SAXException {

  }
}
