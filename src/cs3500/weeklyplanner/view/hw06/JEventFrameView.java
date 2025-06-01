package cs3500.weeklyplanner.view.hw06;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.GroupLayout;
import javax.swing.ListSelectionModel;


import cs3500.weeklyplanner.model.Date;
import cs3500.weeklyplanner.model.IEvent;
import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.ReadOnlyPlannerModel;

/**
 * the frame of an Event. It visually shows all the details about an event.
 * User can utilize specific functionalities such as modify events, remove events, etc.
 * by clicking on the corresponding buttons.
 */
public class JEventFrameView extends JFrame implements EventFrame, ActionListener {
  protected IEvent event;
  protected IUser currentUser;
  protected ReadOnlyPlannerModel model;
  protected JLabel eventNameLabel;
  protected JLabel locationLabel;
  protected JLabel onlineLabel;
  protected JLabel startingDayLabel;
  protected JLabel startingTimeLabel;
  protected JLabel endingDayLabel;
  protected JLabel endingTimeLabel;
  protected JLabel availableUsersLabel;
  protected JTextField eventNameField;
  protected JTextField locationField;
  protected JComboBox<String> isOnlineComboBox;
  protected JComboBox<String> startingDayCombo;
  protected JTextField startingTimeField;
  protected JComboBox<String> endingDayCombo;
  protected JTextField endingTimeField;
  protected JList<String> userList;
  protected JButton modifyEventButton;
  protected JButton removeEventButton;
  protected JButton createEventButton;
  protected List<IUser> invitees;
  protected JButton addUserButton;
  protected JButton removeUserButton;
  protected JLabel durationLabel;
  protected JTextField durationText;
  protected JLabel workDayLabel;
  protected JComboBox<String> onlineComboBox;
  protected JComboBox<String> workDayComboBox;
  protected JButton scheduleEventButton;

  /**
   * constructor for the Event frame view class. constructs the visuals.
   *
   * @param model is the model.
   * @param event is the event.
   */
  public JEventFrameView(ReadOnlyPlannerModel model, IEvent event) {
    super("Event Frame");
    this.event = event;
    this.model = Objects.requireNonNull(model);
    this.currentUser = event.getHost();


    // Define the labels
    eventNameLabel = new JLabel("Event name:");
    locationLabel = new JLabel("Location:");
    onlineLabel = new JLabel("online: ");
    startingDayLabel = new JLabel("Starting Day:");
    startingTimeLabel = new JLabel("Starting time:");
    endingDayLabel = new JLabel("Ending Day:");
    endingTimeLabel = new JLabel("Ending time:");
    availableUsersLabel = new JLabel("Available users");

    // Define the fields and other components
    eventNameField = new JTextField(event.getName());
    locationField = new JTextField(event.getLocation());
    isOnlineComboBox = new JComboBox<>(new String[]{"true", "false"});
    isOnlineComboBox.setSelectedItem(boolToString(event.getOnline()));
    startingDayCombo = new JComboBox<>(new String[]{
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
    startingDayCombo.setSelectedItem(event.getStartingDay().toString());
    startingTimeField = new JTextField(this.timeToString(event.getStartingTime()));
    endingDayCombo = new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"});
    endingDayCombo.setSelectedItem(event.getEndingDay().toString());
    endingTimeField = new JTextField(this.timeToString(event.getEndingTime()));
    userList = new JList<>(this.getUserNames());
    userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    userList.setSelectedIndices(this.getSelectedUsers(event));
    modifyEventButton = new JButton("Modify event");
    modifyEventButton.setActionCommand("modifyEvent");
    modifyEventButton.addActionListener(this);
    removeEventButton = new JButton("Remove event");
    removeEventButton.setActionCommand("removeEvent");
    removeEventButton.addActionListener(this);

    //
    durationLabel = new JLabel("Duration in minutes: ");
    workDayLabel = new JLabel("Any Time / Work Day");
    durationText = new JTextField();
    onlineComboBox = new JComboBox<>(new String[]{"Not online", "Online"});
    workDayComboBox = new JComboBox<>(new String[]{"Any time", "Work day"});
    scheduleEventButton = new JButton("Schedule event");


    // Create a GroupLayout for the frame
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    // Automatically add gaps between components
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    // Define horizontal group
    layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(eventNameLabel)
                    .addComponent(locationLabel)
                    .addComponent(onlineLabel)
                    .addComponent(startingDayLabel)
                    .addComponent(startingTimeLabel)
                    .addComponent(endingDayLabel)
                    .addComponent(endingTimeLabel)
                    .addComponent(availableUsersLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(eventNameField)
                    .addComponent(locationField)
                    .addComponent(isOnlineComboBox)
                    .addComponent(startingDayCombo)
                    .addComponent(startingTimeField)
                    .addComponent(endingDayCombo)
                    .addComponent(endingTimeField)
                    .addComponent(userList)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(modifyEventButton)
                            .addComponent(removeEventButton)))
    );

    // Define vertical group
    layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(eventNameLabel)
                    .addComponent(eventNameField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(locationLabel)
                    .addComponent(locationField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineLabel)
                    .addComponent(isOnlineComboBox))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startingDayLabel)
                    .addComponent(startingDayCombo))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startingTimeLabel)
                    .addComponent(startingTimeField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(endingDayLabel)
                    .addComponent(endingDayCombo))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(endingTimeLabel)
                    .addComponent(endingTimeField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(availableUsersLabel)
                    .addComponent(userList))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(modifyEventButton)
                    .addComponent(removeEventButton))
    );
    pack();
    setSize(600, 600);
    setLocation(200, 200);

    this.setVisible(true);
  }



  protected static String[] convertStrings(IEvent event) {
    List<String> userList = new ArrayList<>();
    for (IUser user : event.getInvitees()) {
      userList.add(user.toString());
    }
    if (event.getInvitees().isEmpty()) {
      return new String[]{"<None>"};
    } else {
      return userList.toArray(new String[0]);
    }
  }

  protected String boolToString(boolean b) {
    if (b) {
      return "true";
    } else {
      return "false";
    }
  }


  /**
   * override constructor. This constructor takes in teh currentUser.
   *
   * @param model       is the model.
   * @param currentUser is the current user.
   */
  public JEventFrameView(ReadOnlyPlannerModel model, IUser currentUser) {
    super("Event Frame");
    this.model = model;
    this.currentUser = currentUser;
    this.invitees = new ArrayList<>();
    invitees.add(currentUser);
    // Define the labels
    eventNameLabel = new JLabel("Event name:");
    locationLabel = new JLabel("Location:");
    onlineLabel = new JLabel("online: ");
    startingDayLabel = new JLabel("Starting Day:");
    startingTimeLabel = new JLabel("Starting time:");
    endingDayLabel = new JLabel("Ending Day:");
    endingTimeLabel = new JLabel("Ending time:");
    availableUsersLabel = new JLabel("Available users");

    // Define the fields and other components
    eventNameField = new JTextField();
    locationField = new JTextField();
    isOnlineComboBox = new JComboBox<>(new String[]{"true", "false"});
    startingDayCombo = new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"});
    startingTimeField = new JTextField();
    endingDayCombo = new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"});
    endingTimeField = new JTextField();
    userList = new JList<>(this.getUserNames());
    addUserButton = new JButton("Add to invitees");
    addUserButton.setActionCommand("Add User");
    addUserButton.addActionListener(this);
    removeUserButton = new JButton("remove from invitees");
    removeUserButton.setActionCommand("Remove User");
    removeUserButton.addActionListener(this);
    createEventButton = new JButton("Create event");
    createEventButton.setActionCommand("createEvent");
    createEventButton.addActionListener(this);

//    this.drawLayout();

    pack();
    setSize(500, 600);
    setMinimumSize(new Dimension(600, 600));
    setLocation(200, 200);

    this.setVisible(true);
  }

  @Override
  public void drawLayout() {
    // Create a GroupLayout for the frame
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    // Automatically add gaps between components
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    // Define horizontal group
    layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(eventNameLabel)
                    .addComponent(locationLabel)
                    .addComponent(onlineLabel)
                    .addComponent(startingDayLabel)
                    .addComponent(startingTimeLabel)
                    .addComponent(endingDayLabel)
                    .addComponent(endingTimeLabel)
                    .addComponent(availableUsersLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(eventNameField)
                    .addComponent(locationField)
                    .addComponent(isOnlineComboBox)
                    .addComponent(startingDayCombo)
                    .addComponent(startingTimeField)
                    .addComponent(endingDayCombo)
                    .addComponent(endingTimeField)
                    .addComponent(userList)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(createEventButton)
                            .addComponent(addUserButton)
                            .addComponent(removeUserButton)))
    );

    // Define vertical group
    layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(eventNameLabel)
                    .addComponent(eventNameField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(locationLabel)
                    .addComponent(locationField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineLabel)
                    .addComponent(isOnlineComboBox))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startingDayLabel)
                    .addComponent(startingDayCombo))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startingTimeLabel)
                    .addComponent(startingTimeField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(endingDayLabel)
                    .addComponent(endingDayCombo))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(endingTimeLabel)
                    .addComponent(endingTimeField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(availableUsersLabel)
                    .addComponent(userList))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(createEventButton)
                    .addComponent(addUserButton)
                    .addComponent(removeUserButton))
    );
  }

  @Override
  public int getDuration() {
    return this.getEndingTime() - this.getStartingTime();
  }

  protected String[] getUserNames() {
    return getStrings(model);
  }

  protected static String[] getStrings(ReadOnlyPlannerModel model) {
    List<String> userList = new ArrayList<>();
    for (IUser user : model.getUsers()) {
      userList.add(user.toString());
    }
    if (model.getUsers().isEmpty()) {
      return new String[]{"<None>"};
    } else {
      return userList.toArray(new String[0]);
    }
  }


  public int[] getSelectedUsers(IEvent e) {
    int[] intList = new int[e.getInvitees().size()];
    for (int i = 0; i < e.getInvitees().size(); i++) {
      for (IUser user : model.getUsers()) {
        if (e.getInvitees().get(i).equals(user)) {
          intList[i] = e.getInvitees().indexOf(e.getInvitees().get(i));
        }
      }
    }
    return intList;
  }

  protected String timeToString(int time) {
    return time / 100 + ":" + (time - time / 100 * 100);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "createEvent":
        if (eventNameField.getText().isEmpty() ||
                locationField.getText().isEmpty() ||
                startingTimeField.getText().isEmpty() ||
                endingTimeField.getText().isEmpty() ||
                userList.getSelectedValue().isEmpty()) {
          throw new IllegalStateException("Empty field");
        }
        System.out.println("Create event: \n" +
                "Event name: " + eventNameField.getText() + "\n" +
                "Online: " + isOnlineComboBox.getSelectedItem() + "\n" +
                "Location:" + locationField.getText() + "\n" +
                "Starting Day: " + startingDayCombo.getSelectedItem() + "\n" +
                "Starting Time: " + startingTimeField.getText() + "\n" +
                "Ending Day: " + endingDayCombo.getSelectedItem() + "\n" +
                "Ending Time: " + endingTimeField.getText() + "\n" +
                "Host: " + this.currentUser + "\n" +
                "Invitees: " + invitees
        );
        break;
      case "removeEvent":
        if (eventNameField.getText().isEmpty() ||
                locationField.getText().isEmpty() ||
                startingTimeField.getText().isEmpty() ||
                endingTimeField.getText().isEmpty() ||
                userList.getSelectedValue().isEmpty()) {
          throw new IllegalStateException("Empty field");
        }
        System.out.println("Remove event: \n" +
                "Event name: " + eventNameField.getText() + "\n" +
                "Online: " + isOnlineComboBox.getSelectedItem() + "\n" +
                "Location:" + locationField.getText() + "\n" +
                "Starting Day: " + startingDayCombo.getSelectedItem() + "\n" +
                "Starting Time: " + startingTimeField.getText() + "\n" +
                "Ending Day: " + endingDayCombo.getSelectedItem() + "\n" +
                "Ending Time: " + endingTimeField.getText() + "\n" +
                "Host: " + this.currentUser + "\n" +
                "Invitees: " + invitees
        );
        break;
      case "Add User":
        if (invitees.contains(stringToUser(userList.getSelectedValue()))) {
          throw new IllegalArgumentException("user already added");
        }
        invitees.add(stringToUser(userList.getSelectedValue()));
        System.out.println("selected users: " + invitees);
        break;
      case "Remove User":
        if (!invitees.contains(stringToUser(userList.getSelectedValue()))) {
          throw new IllegalArgumentException("user does not exist");
        }
        invitees.remove(stringToUser(userList.getSelectedValue()));
        System.out.println("selected users: " + invitees);
        break;
      default:
        throw new UnsupportedOperationException("Unsupported Operation. ");
    }
  }


  ////-----------------------------------------------------
  @Override
  public IUser getHost() {
    return this.currentUser;
  }

  @Override
  public int getEndingTime() {
    return stringToInt(endingTimeField.getText());
  }

  @Override
  public Date getEndingDay() {
    return stringToDate(endingDayCombo.getItemAt(endingDayCombo.getSelectedIndex()));
  }

  @Override
  public int getStartingTime() {
    return stringToInt(startingTimeField.getText());
  }

  protected int stringToInt(String time) {
    return Integer.parseInt(time);
  }

  @Override
  public Date getStartingDate() {
    return stringToDate(startingDayCombo.getItemAt(startingDayCombo.getSelectedIndex()));
  }

  protected Date stringToDate(String day) {
    switch (day) {
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
        throw new IllegalArgumentException("invalid input");
    }
  }

  @Override
  public String getLocationName() {
    return locationField.getText();
  }

  @Override
  public String getEventName() {
    return eventNameField.getText();
  }

  @Override
  public boolean getOnline() {
    return stringToBool(isOnlineComboBox.getItemAt(isOnlineComboBox.getSelectedIndex()));
  }

  @Override
  public List<IUser> getInvitees() {
    return new ArrayList<>(invitees);
  }

  protected boolean stringToBool(String bool) {
    if (bool.equals("true") || bool.equals("Online") || bool.equals("Any time")) {
      return true;
    } else {
      return false;
    }
  }


  @Override
  public void setListener(ActionListener listener) {
    createEventButton.addActionListener(listener);
    removeEventButton.addActionListener(listener);
    modifyEventButton.addActionListener(listener);
  }

  protected IUser stringToUser(String name) {
    for (IUser user : model.getUsers()) {
      if (name.equals(user.getName())) {
        return user;
      }
    }
    throw new IllegalArgumentException("name not found");
  }

  @Override
  public boolean getAnyTime() {
    return true;
  }
}



