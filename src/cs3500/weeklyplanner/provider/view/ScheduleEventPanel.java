package cs3500.weeklyplanner.provider.view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import planner.model.ReadOnlyNUPlanner;
import planner.model.User;

/**
 * ScheduleEventPanel allows for the creation and scheduling
 * of a new event that uses a duration and scheduling strategy
 * instead of a start and end time.
 */
public class ScheduleEventPanel extends JPanel {

  // Name of the event, location, starting time, ending time
  private JTextField eventNameInput;
  private JTextField locationNameInput;
  private JTextField durationTimeBox;

  // Multiple choice combo boxes to select online status, start and end days
  private JComboBox<String> onlineComboBox;

  // List of available users with selection capability
  private JList<String> usersList;

  private final ReadOnlyNUPlanner planner;

  private Consumer<String> commandCallback;

  /**
   * Constructor for blank event panel with inputs for event details.
   * @param planner system currently being used.
   * @param width of the event panel.
   * @param height of the event panel
   */
  public ScheduleEventPanel(ReadOnlyNUPlanner planner, int width, int height) {
    this.planner = planner;
    // Width and height of the panel
    this.commandCallback = null;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // ============================
    // Event name panel
    // Event name text
    JPanel eventNamePanel = this.makeEventNamePanel();
    eventNamePanel.setMinimumSize(new Dimension(width, height / 4));
    eventNamePanel.setMaximumSize(new Dimension(width, height / 4));
    this.add(eventNamePanel);
    //==============================
    // Location panel
    JPanel locationPanel = this.makeLocationPanel();
    locationPanel.setMinimumSize(new Dimension(width, height / 2));
    locationPanel.setMinimumSize(new Dimension(width, height / 2));
    this.add(locationPanel);
    //==============================
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.setMinimumSize(new Dimension(width, height / 4));
    southPanel.setMaximumSize(new Dimension(width, height / 4));
    // Available users panel
    JPanel availableUsersPanel = makeAvailableUsersPanel(this.planner);
    //==============================
    // Button panel
    JPanel buttonPanel = makeButtonPanel();
    buttonPanel.setMaximumSize(new Dimension(width, height / 8));
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
    timePanel.add(new JLabel("Duration in minutes:"));
    this.durationTimeBox = new JTextField();
    timePanel.add(this.durationTimeBox);
    timePanel.add(new JLabel("Scheduling strategy:"));
    JComboBox<Object> strategyComboBox = new JComboBox<>();
    strategyComboBox.addItem("Work Hours Only");
    strategyComboBox.addItem("Any Available Time");
    timePanel.add(strategyComboBox);
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
    JLabel availableUsersLabel = new JLabel("Available users");
    availableUsersPanel.add(availableUsersLabel, BorderLayout.NORTH);

    DefaultListModel<String> usernames = new DefaultListModel<>();
    for (User user : planner.listOfUsers()) {
      if (!user.name().equals("<none>")) {
        usernames.addElement(user.name());
      }
    }
    this.usersList = new JList<>(usernames);
    // Displays list of available users
    JScrollPane userScrollPane = new JScrollPane(this.usersList);
    userScrollPane.setPreferredSize(new Dimension(getWidth(), 100));
    userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    availableUsersPanel.add(userScrollPane, BorderLayout.CENTER);
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
          StringBuilder commandCreateEvent = new StringBuilder("CreateEvent ");
          commandCreateEvent.append(this.eventNameInput.getText()).append(" ");
          commandCreateEvent.append(this.locationNameInput.getText()).append(" ");
          commandCreateEvent.append(this.onlineComboBox.getSelectedItem()).append(" ");
          commandCreateEvent.append(this.durationTimeBox.getText()).append(" ");
          for (String username : usersList.getSelectedValuesList()) {
            commandCreateEvent.append(username).append(" ");
          }
          System.out.print(String.valueOf(commandCreateEvent));
          this.commandCallback.accept(String.valueOf(commandCreateEvent));
        }
      }
    });

    JButton modifyEventButton = new JButton("Modify event");
    modifyEventButton.addActionListener((ActionEvent e) -> {
      if (notAllInfoInput()) {
        System.out.print("\nError: Must fill all fields to create an event\n");
      } else {
        this.commandCallback.accept("ModifyEvent ");
      }
    });


    JButton removeEventButton = new JButton("Remove event");
    removeEventButton.addActionListener((ActionEvent e) -> {
      if (notAllInfoInput()) {
        // TODO: Display error message
        System.out.print("\nError: Must fill all fields to create an event\n");
      } else {

        this.commandCallback.accept("RemoveEvent ");
      }
    });

    buttonPanel.add(createEventButton, BorderLayout.WEST);
    buttonPanel.add(modifyEventButton, BorderLayout.CENTER);
    buttonPanel.add(removeEventButton, BorderLayout.EAST);
    return buttonPanel;
  }

  /**
   * Checks if the event frame was filled with valid information completely.
   * @return true if so and false otherwise.
   */
  private boolean notAllInfoInput() {
    return this.eventNameInput.getText().isEmpty()
            || this.onlineComboBox.getSelectedItem() == null
            || this.durationTimeBox == null
            || this.usersList.getSelectedValuesList().isEmpty()
            || this.planner.listOfUsers().isEmpty();
  }

  public void setCommandCallback(Consumer<String> callback) {
    this.commandCallback = callback;
  }
}
