package cs3500.weeklyplanner.provider.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;

import planner.model.ReadOnlyNUPlanner;
import planner.model.User;

// Main panel containing the schedule display, file saving
// buttons, create event button, schedule event button, and user
// selection menu.

/**
 * Main panel containing the schedule display, file saving
 * buttons, create event button, schedule event button, and user
 * selection menu.
 */
public class MainPanel extends JPanel {

  private final WeekPanel weekPanel;
  private JComboBox<String> userBox;
  private final ReadOnlyNUPlanner planner;
  private Consumer<String> commandCallback;
  private EventView eventFrame;

  /**
   * Constructor for main panel.
   * @param planner given to display.
   */
  public MainPanel(ReadOnlyNUPlanner planner) {
    super();
    this.planner = planner;
    this.weekPanel = new WeekPanel(this.planner);
    this.setLayout(new BorderLayout());
    this.add(this.weekPanel, BorderLayout.CENTER);
    JPanel mainButtonPanel = this.makeMainButtonPanel();
    this.add(mainButtonPanel, BorderLayout.SOUTH);
    JPanel fileMenuPanel = this.makeFileMenuPanel();
    this.add(fileMenuPanel, BorderLayout.NORTH);
    this.commandCallback = null;
  }

  private JPanel makeFileMenuPanel() {
    JPanel fileMenuPanel = new JPanel();

    // Create and place buttons
    JButton addCalendarButton = new JButton("Add calendar");
    addCalendarButton.addActionListener((ActionEvent e) -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.showOpenDialog(this);
      if (fileChooser.getSelectedFile() != null) {
        System.out.print(fileChooser.getSelectedFile() + "\n");
      }
    });
    JButton saveCalendarsButton = new JButton("Save calendars");
    saveCalendarsButton.addActionListener((ActionEvent e) -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fileChooser.showSaveDialog(this);
      if (fileChooser.getSelectedFile() != null) {
        System.out.print(fileChooser.getSelectedFile().getAbsolutePath() + "\n");
      }
    });
    fileMenuPanel.add(addCalendarButton);
    fileMenuPanel.add(saveCalendarsButton);
    return fileMenuPanel;
  }

  private JPanel makeMainButtonPanel() {
    JPanel mainButtonPanel = new JPanel(new FlowLayout((FlowLayout.CENTER)));

    // Create and place buttons
    JButton createEventButton = new JButton("Create event");
    createEventButton.addActionListener((ActionEvent e) -> {
      this.eventFrame = new EventGraphicsFrame(this.planner);
      if (this.commandCallback != null) {
        this.commandCallback.accept("CreateBlankEventFrame");
      }
    });
    JButton scheduleEventButton = new JButton("Schedule event");
    scheduleEventButton.addActionListener((ActionEvent e) -> {
      this.commandCallback.accept("CreateScheduleEventFrame");
    });

    mainButtonPanel.add(createEventButton);
    mainButtonPanel.add(scheduleEventButton);

    // Combo box of users to display schedules of
    this.userBox = new JComboBox<>();
    for (User user : this.planner.listOfUsers()) {
      this.userBox.addItem(user.name());
    }
    this.userBox.addActionListener(e -> {
      String selectedUserName = Objects.requireNonNull(this.userBox.getSelectedItem()).toString();
      if (this.commandCallback != null) {
        String selectedUserCommand = "ChangeUser " + selectedUserName;
        this.commandCallback.accept(selectedUserCommand);
      }

      System.out.println("Selected user: " + selectedUserName);
    });
    mainButtonPanel.add(this.userBox);
    return mainButtonPanel;
  }

  public void setCommandCallback(Consumer<String> callback) {
    this.commandCallback = callback;
    this.weekPanel.setCommandCallback(callback);
  }

  @Override
  public void repaint() {
    if (this.weekPanel != null) {
      this.weekPanel.repaint();
    }
  }

}

