package cs3500.weeklyplanner.model.Adapters;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;

import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.ISchedule;
import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.provider.model.Event;
import cs3500.weeklyplanner.provider.model.Time;
import cs3500.weeklyplanner.provider.model.User;

public class UserAdapterImp implements IUser {
  User user;

  public UserAdapterImp(User user) {
    this.user = Objects.requireNonNull(user);
  }


  @Override
  public ISchedule getSchedule() {
    return null;
  }

  @Override
  public void addEvent(IEvent event) {

  }

  @Override
  public void removeEvent(IEvent event) {

  }

  @Override
  public Document saveUserSchedule() throws ParserConfigurationException {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }
}
