package cs3500.weeklyplanner.xmlutils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import cs3500.weeklyplanner.model.Date;
import cs3500.weeklyplanner.model.Event;
import cs3500.weeklyplanner.model.MutablePlannerModel;
import cs3500.weeklyplanner.model.User;
import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.ISchedule;
import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.Schedule;

/**
 * implementation of Utility class for the XMLUtils interface.
 * So far this class offers two functionalities, which are the
 * specified two methods specified in the interface.
 * This class delegate the PlannerModel interface to gain information
 * from the class, therefore to be able to read and write XML.
 */
public class XMLUtil implements XMLUtils {
  private MutablePlannerModel delegate;

  /**
   * constructor for the Util class.
   *
   * @param delegate is delegated from the PlannerModel interface.
   */
  public XMLUtil(MutablePlannerModel delegate) {
    this.delegate = delegate;
  }

  @Override
  public void uploadXML(String path) throws ParserConfigurationException,
          IOException, SAXException {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.parse(new File(path));
    doc.getDocumentElement().normalize();

    Node first = doc.getElementsByTagName("schedule").item(0);
    NamedNodeMap attrList = first.getAttributes();
    ArrayList<IEvent> events = new ArrayList();
    ISchedule schedule = new Schedule(events);
    User user = new User(attrList.item(0).getNodeValue(), schedule);
    try {
      for (int i = 0; i < doc.getElementsByTagName("event").getLength(); i++) {
        Node firstNode = doc.getElementsByTagName("event").item(i);
        NodeList nodeList = firstNode.getChildNodes();
        ArrayList<String> users = new ArrayList<>();
        for (int j = 0; j < nodeList.item(3).getChildNodes().getLength(); j++) {
          users.add(nodeList.item(3).getChildNodes().item(j).getTextContent());
        }
        Event event = new Event(nodeList.item(0).getTextContent(),
                toBool(nodeList.item(2).getChildNodes().item(0).getTextContent()),
                nodeList.item(2).getChildNodes().item(1).getTextContent(),
                toDate(nodeList.item(1).getChildNodes().item(1).getTextContent()),
                toInt(nodeList.item(1).getChildNodes().item(0).getTextContent()),
                toDate(nodeList.item(1).getChildNodes().item(2).getTextContent()),
                toInt(nodeList.item(1).getChildNodes().item(3).getTextContent()),
                user,
                toUsers(users));
        delegate.createEvent(event);
      }
    } catch (NullPointerException ignore) {
      System.out.println();
    }
  }

  /*
  converts a list of users' name to a list of IUser.
   */
  private List<IUser> toUsers(List<String> users) {
    List<IUser> userList = new ArrayList<>();
    for (String s : users) {
      for (IUser u : delegate.getUsers()) {
        if (s.equals(u.toString())) {
          userList.add(u);
        }
      }
    }
    if (userList.size() < users.size()) {
      throw new IllegalStateException("contains invalid user");
    }
    return userList;
  }

  /*
  converts a list of String to a List of int.
   */
  private int toInt(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid time");
    }
  }

  /*
  converts a String to a boolean.
   */
  private boolean toBool(String s) {
    switch (s) {
      case "false":
        return false;
      case "true":
        return true;
      default:
        throw new IllegalArgumentException("Invalid online argument");
    }
  }

  /*
  converts a String that represents a date to a Date as an enum.
   */
  private Date toDate(String s) {
    switch (s) {
      case "Monday":
        return Date.Monday;
      case "Tuesday":
        return Date.Tuesday;
      case "Wednesday":
        return Date.Wednesday;
      case "Thursday":
        return Date.Thursday;
      case "Friday":
        return Date.Friday;
      case "Saturday":
        return Date.Saturday;
      case "Sunday":
        return Date.Sunday;
      default:
        throw new IllegalArgumentException("Invalid Date");
    }
  }

  @Override
  public void saveSchedule() throws ParserConfigurationException, TransformerException {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    for (IUser u : delegate.getUsers()) {
      StreamResult result = new StreamResult(new File(
              "/Users/lhc/Desktop/OOD/group/HW5/schedules/" +
                      u.toString() + "_schedule" + ".xml"));
      DOMSource dom = new DOMSource(u.saveUserSchedule());
      transformer.transform(dom, result);
    }
  }
}
