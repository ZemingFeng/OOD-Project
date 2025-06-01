package cs3500.weeklyplanner.provider.view;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;


import planner.model.Day;
import planner.model.Event;
import planner.model.ReadOnlyNUPlanner;
import planner.model.User;

/**
 * Event panel present in the event frame. Handles taking in user inputs to create, remove, and
 * modify events. Can be created with no details, or can be given an event to populate its
 * details fields with.
 */
public class EventPanel extends JPanel {

  // Name of the event, location, starting time, ending time
  private JTextField eventNameInput;
  private JTextField locationNameInput;
  private JTextField startTimeBox;
  private JTextField endTimeBox;


  // Multiple choice combo boxes to select online status, start and end days
  private JComboBox<String> onlineComboBox;
  private JComboBox<String> startDayChooser;
  private JComboBox<String> endDayChooser;

  // List of available users with selection capability
  private JList<String> usersList;
  private JLabel availableUsersLabel;

  // Displays list of available users
  private JScrollPane userScrollPane;
  private final ReadOnlyNUPlanner planner;

  // Width and height of the panel
  private final int width;
  private final int height;

  private Consumer<String> commandCallback;

  /**
   * Constructor for blank event panel with inputs for event details.
   * @param planner system currently being used.
   * @param width of the event panel.
   * @param height of the event panel
   */
  public EventPanel(ReadOnlyNUPlanner planner, int width, int height) {
    this.planner = planner;
    this.width = width;
    this.height = height;
    this.commandCallback = null;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // ============================
    // Event name panel
    // Event name text
    JPanel eventNamePanel = this.makeEventNamePanel();
    eventNamePanel.setMinimumSize(new Dimension(this.width, this.height / 4));
    eventNamePanel.setMaximumSize(new Dimension(this.width, this.height / 4));
    this.add(eventNamePanel);
    //==============================
    // Location panel
    JPanel locationPanel = this.makeLocationPanel();
    locationPanel.setMinimumSize(new Dimension(this.width, this.height / 2));
    locationPanel.setMinimumSize(new Dimension(this.width, this.height / 2));
    this.add(locationPanel);
    //==============================
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.setMinimumSize(new Dimension(this.width, this.height / 4));
    southPanel.setMaximumSize(new Dimension(this.width, this.height / 4));
    // Available users panel
    JPanel availableUsersPanel = makeAvailableUsersPanel(this.planner);
    //==============================
    // Button panel
    JPanel buttonPanel = makeButtonPanel();
    buttonPanel.setMaximumSize(new Dimension(this.width, this.height / 8));
    //==============================
    southPanel.add(availableUsersPanel, BorderLayout.NORTH);
    southPanel.add(buttonPanel, BorderLayout.SOUTH);
    this.add(southPanel);
    //--------------------------

  }

  /**
   * Constructor for event with populated details fields. Uses given event to fill in blank details.
   * @param planner system currently being used.
   * @param event given to populate fields with.
   * @param width of the event panel.
   * @param height of the event panel.
   */
  public EventPanel(ReadOnlyNUPlanner planner, Event event, int width, int height) {
    this.planner = planner;
    this.width = width;
    this.height = height;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // ============================
    // Event name panel
    // Event name text
    JPanel eventNamePanel = makePopulatedEventNamePanel(event);
    eventNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    eventNamePanel.setMinimumSize(new Dimension(this.width, this.height / 4));
    eventNamePanel.setMaximumSize(new Dimension(this.width, this.height / 4));
    this.add(eventNamePanel);
    //==============================
    // Location panel
    JPanel locationPanel = makePopulatedLocationPanel(event);
    this.add(locationPanel);
    //==============================
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.setMinimumSize(new Dimension(this.width, this.height / 4));
    southPanel.setMaximumSize(new Dimension(this.width, this.height / 4));
    // Available users panel
    JPanel availableUsersPanel = makePopulatedAvailableUsersPanel(this.planner, event);
    //==============================
    // Button panel
    JPanel buttonPanel = makeButtonPanel();
    buttonPanel.setMaximumSize(new Dimension(this.width, this.height / 8));
    //==============================
    southPanel.add(availableUsersPanel, BorderLayout.NORTH);
    southPanel.add(buttonPanel, BorderLayout.SOUTH);
    this.add(southPanel);
    //--------------------------

  }

  /**
   * Makes a new JPanel to handle the display and input of the event name.
   * @return JPanel to represent input of event name.
   */
  private JPanel makeEventNamePanel() {
    JPanel eventNamePanel = new JPanel(new GridLayout(2, 1));
    // Event name text
    eventNamePanel.add(new JLabel("Event name:"));

    // Text box
    this.eventNameInput = new JTextField();
    eventNamePanel.add(this.eventNameInput);
    eventNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    return eventNamePanel;
  }

  /**
   * Creates panel with details already filled in with given event details.
   * @param event to fill in the details fields with.
   * @return JPanel of the event name with details already filled in.
   */
  private JPanel makePopulatedEventNamePanel(Event event) {
    JPanel eventNamePanel = new JPanel(new GridLayout(2, 1));
    // Event name text
    eventNamePanel.add(new JLabel("Event name:"));

    // Text box
    this.eventNameInput = new JTextField(event.name());
    eventNamePanel.add(this.eventNameInput);
    eventNamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    return eventNamePanel;
  }

  /**
   * Creates blank panel to handle inputs of location and time details.
   * @return JPanel to handle location and time.
   */
  private JPanel makeLocationPanel() {
    JPanel locationPanel = new JPanel();
    locationPanel.setMinimumSize(new Dimension(getWidth(), getHeight()));
    locationPanel.setBorder(BorderFactory.createTitledBorder("Location:"));
    locationPanel.setLayout(new BorderLayout());

    // Create panel for online status and name
    JPanel onlineAndNamePanel = new JPanel();
    onlineAndNamePanel.setLayout(new BorderLayout());

    // Online section
    this.onlineComboBox = new JComboBox<>();
    this.onlineComboBox.addItem("Online");
    this.onlineComboBox.addItem("Offline");
    onlineAndNamePanel.add(this.onlineComboBox, BorderLayout.WEST);

    // Name section
    this.locationNameInput = new JTextField();
    onlineAndNamePanel.add(this.locationNameInput, BorderLayout.CENTER);
    locationPanel.add(onlineAndNamePanel, BorderLayout.NORTH);

    // Start and end day and time panel
    JPanel timePanel = new JPanel(new GridLayout(4, 2));

    // Start day section
    timePanel.add(new JLabel("Starting Day:"));
    this.startDayChooser = new JComboBox<>();
    for (Day day : Day.values()) {
      this.startDayChooser.addItem(day.toString());
    }
    timePanel.add(this.startDayChooser);

    // Start time section
    this.startTimeBox = new JTextField();
    timePanel.add(new JLabel("Starting time:"));
    timePanel.add(this.startTimeBox);

    // Ending day section
    timePanel.add(new JLabel("Ending Day:"));
    this.endDayChooser = new JComboBox<>();
    for (Day day : Day.values()) {
      this.endDayChooser.addItem(day.toString());
    }
    timePanel.add(this.endDayChooser);

    // Ending time section
    timePanel.add(new JLabel("Ending time:"));
    this.endTimeBox = new JTextField();
    timePanel.add(this.endTimeBox);

    locationPanel.add(timePanel, BorderLayout.CENTER);
    return locationPanel;
  }

  /**
   * Creates populated location panel with details already filled in from the given event.
   * @param event to populate the fields with.
   * @return JPanel with location and time details filled in.
   */
  private JPanel makePopulatedLocationPanel(Event event) {
    JPanel locationPanel = new JPanel();
    locationPanel.setBorder(BorderFactory.createTitledBorder("Location:"));
    locationPanel.setLayout(new BorderLayout());

    // Create panel for online status and name
    JPanel onlineAndNamePanel = new JPanel();
    onlineAndNamePanel.setLayout(new BorderLayout());

    // Online section
    this.onlineComboBox = new JComboBox<>();
    this.onlineComboBox.addItem("Online");
    this.onlineComboBox.addItem("Offline");
    if (event.online().equals("true")) {
      this.onlineComboBox.setSelectedItem("Online");
    } else {
      this.onlineComboBox.setSelectedItem("Offline");
    }
    onlineAndNamePanel.add(this.onlineComboBox, BorderLayout.WEST);

    // Name section
    this.locationNameInput = new JTextField(event.location());
    onlineAndNamePanel.add(this.locationNameInput, BorderLayout.CENTER);
    locationPanel.add(onlineAndNamePanel, BorderLayout.NORTH);

    // Start and end day and time panel
    JPanel timePanel = new JPanel(new GridLayout(4, 2));

    // Start day section
    timePanel.add(new JLabel("Starting Day:"));
    this.startDayChooser = new JComboBox<>();
    for (Day day : Day.values()) {
      this.startDayChooser.addItem(day.toString());
    }
    this.startDayChooser.setSelectedIndex(event.start().day().getDayValue());
    timePanel.add(this.startDayChooser);

    // Start time section
    this.startTimeBox = new JTextField(event.start().minutes() + "");
    timePanel.add(new JLabel("Starting time:"));
    timePanel.add(this.startTimeBox);

    // Ending day section
    timePanel.add(new JLabel("Ending Day:"));
    this.endDayChooser = new JComboBox<>();
    for (Day day : Day.values()) {
      this.endDayChooser.addItem(day.toString());
    }
    this.endDayChooser.setSelectedIndex(event.end().day().getDayValue());
    timePanel.add(this.endDayChooser);

    // Ending time section
    timePanel.add(new JLabel("Ending time:"));
    this.endTimeBox = new JTextField(event.end().minutes() + "");
    timePanel.add(this.endTimeBox);

    locationPanel.add(timePanel, BorderLayout.CENTER);
    return locationPanel;
  }

  /**
   * Panel to show the available users in the system. Is used for selecting users to invite.
   * @param planner system currently being used.
   * @return JPanel of the available users.
   */
  private JPanel makeAvailableUsersPanel(ReadOnlyNUPlanner planner) {
    JPanel availableUsersPanel = new JPanel(new BorderLayout());
    availableUsersPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    10, 10, 10, 10));
    this.availableUsersLabel = new JLabel("Available users");
    availableUsersPanel.add(this.availableUsersLabel, BorderLayout.NORTH);

    DefaultListModel<String> usernames = new DefaultListModel<>();
    for (User user : planner.listOfUsers()) {
      if (!user.name().equals("<none>")) {
        usernames.addElement(user.name());
      }
    }
    this.usersList = new JList<>(usernames);
    this.userScrollPane = new JScrollPane(this.usersList);
    this.userScrollPane.setPreferredSize(new Dimension(getWidth(), 100));
    this.userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    availableUsersPanel.add(this.userScrollPane, BorderLayout.CENTER);
    return availableUsersPanel;
  }

  /**
   * Creates JPanel with invited users of the given event already selected.
   * @param planner system currently being used.
   * @param event given to show invited users of.
   * @return JPanel of available users with invited users already selected.
   */
  private JPanel makePopulatedAvailableUsersPanel(ReadOnlyNUPlanner planner, Event event) {
    JPanel availableUsersPanel = new JPanel(new BorderLayout());
    availableUsersPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    10, 10, 10, 10));
    this.availableUsersLabel = new JLabel("Available users");
    availableUsersPanel.add(this.availableUsersLabel, BorderLayout.NORTH);

    DefaultListModel<String> usernames = new DefaultListModel<>();
    for (User user : planner.listOfUsers()) {
      if (!user.name().equals("<none>")) {
        usernames.addElement(user.name());
      }
    }
    this.usersList = new JList<>(usernames);
    int[] nameIndices = new int[usernames.size()];
    int count = 0;
    for (int nameIndex = 0; nameIndex < usernames.size(); nameIndex++) {
      for (User user : event.getInvitedUsers()) {
        if (user.name().equals(usernames.get(nameIndex))) {
          nameIndices[count++] = nameIndex;
        }
      }
    }
    this.usersList.setSelectedIndices(nameIndices);
    this.userScrollPane = new JScrollPane(this.usersList);
    this.userScrollPane.setPreferredSize(new Dimension(getWidth(), 100));
    this.userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    availableUsersPanel.add(this.userScrollPane, BorderLayout.CENTER);
    return availableUsersPanel;
  }

  /**
   * Creates JPanel with create, modify, and remove event buttons at the bottom. Each button
   * prints out the details of the event corresponding to the action.
   * @return JPanel with buttons.
   */
  private JPanel makeButtonPanel() {
    JPanel buttonPanel = new JPanel(new BorderLayout());
    buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(
                    10, 10, 10, 10));
    // Buttons
    JButton createEventButton = new JButton("Create event");
    createEventButton.addActionListener((ActionEvent e) -> {
      if (notAllInfoInput()) {
        System.out.print("\nError: Must fill all fields to create an event\n");
      } else {
        if (this.commandCallback != null) {
          String cmd = this.createEventStringFromInput();
          this.commandCallback.accept("CreateEvent " + cmd);
        }
      }
    });

    JButton modifyEventButton = new JButton("Modify event");
    modifyEventButton.addActionListener((ActionEvent e) -> {
      if (notAllInfoInput()) {
        System.out.print("\nError: Must fill all fields to create an event\n");
      } else {
        if (this.commandCallback != null) {
          String cmd = this.createEventStringFromInput();
          this.commandCallback.accept("ModifyEvent " + cmd);
        }
        System.out.print("\nModify Event:"
                + printEvent());
      }
    });


    JButton removeEventButton = new JButton("Remove event");
    removeEventButton.addActionListener((ActionEvent e) -> {
      if (notAllInfoInput()) {
        // TODO: Display error message
        System.out.print("\nError: Must fill all fields to create an event\n");
      } else {
        if (this.commandCallback != null) {
          String cmd = this.createEventStringFromInput();
          this.commandCallback.accept("RemoveEvent " + cmd);
        }
        System.out.print("\nRemove Event:"
                + printEvent()
                + "Removing from user "
                + this.planner.activeUser().name() + "\n");
      }
    });

    buttonPanel.add(createEventButton, BorderLayout.WEST);
    buttonPanel.add(modifyEventButton, BorderLayout.CENTER);
    buttonPanel.add(removeEventButton, BorderLayout.EAST);
    return buttonPanel;
  }

  /**
   * Helper method to print the details of event correctly.
   * @return string representing the event details
   */
  private String printEvent() {
    String host = "";
    for (User user : this.planner.listOfUsers()) {
      for (Event event : user.getSchedule()) {
        if (event.name().equals(this.eventNameInput.getText())) {
          host = event.getInvitedUsers().get(0).name();
        }
      }
    }
    return "\nEvent name: " + this.eventNameInput.getText() +
            "\nOnline status: " + this.onlineComboBox.getSelectedItem() +
            "\nStarting Day: " + this.startDayChooser.getSelectedItem() +
            "\nStarting time: " + this.startTimeBox.getText() +
            "\nEnding Day: " + this.endDayChooser.getSelectedItem() +
            "\nEnding time: " + this.endTimeBox.getText() +
            "\nInvited users: " + this.usersList.getSelectedValuesList() +
            "\nHosted by user " + host + "\n";
  }

  private boolean notAllInfoInput() {
    return this.eventNameInput.getText().isEmpty()
            || this.onlineComboBox.getSelectedItem() == null
            || this.startDayChooser.getSelectedItem() == null
            || this.startTimeBox.getText().isEmpty()
            || this.endDayChooser.getSelectedItem() == null
            || this.endTimeBox.getText().isEmpty()
            || this.usersList.getSelectedValuesList().isEmpty()
            || this.planner.listOfUsers().isEmpty();
  }

  // Method to create an Event object from the input fields
  private String createEventStringFromInput() {
    StringBuilder commandCreateEvent = new StringBuilder();
    commandCreateEvent.append(this.eventNameInput.getText()).append(" ");
    commandCreateEvent.append(this.locationNameInput.getText()).append(" ");
    commandCreateEvent.append(this.onlineComboBox.getSelectedItem()).append(" ");
    commandCreateEvent.append(this.startDayChooser.getSelectedItem()).append(" ");
    commandCreateEvent.append(this.startTimeBox.getText()).append(" ");
    commandCreateEvent.append(this.endDayChooser.getSelectedItem()).append(" ");
    commandCreateEvent.append(this.endTimeBox.getText()).append(" ");
    for (String username : usersList.getSelectedValuesList()) {
      commandCreateEvent.append(username).append(" ");
    }
    return String.valueOf(commandCreateEvent);
  }

  public void setCommandCallback(Consumer<String> callback) {
    this.commandCallback = callback;
  }
}
