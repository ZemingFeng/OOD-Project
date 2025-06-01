package cs3500.weeklyplanner.view.hw06;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;


import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.MutablePlannerModel;
import cs3500.weeklyplanner.model.ReadOnlyPlannerModel;

/**
 * the main frame view class. It visually shows the main system, with
 * grids representing different time slots and red rectangles represents
 * different events.
 */
public class JMainFrameView extends JFrame implements MainFrame, ActionListener {
  private final JComboBox<IUser> users;
  private final ReadOnlyPlannerModel model;
  private IUser currentUser;
  private JButton create;
  private JButton schedule;
  private JSchedulePanel panel1;
  private EventFrame eventFrame;

  /**
   * constructor for the main frame view class. sets up the entire visuals.
   *
   * @param model is the model being displayed.
   */
  public JMainFrameView(ReadOnlyPlannerModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.model = Objects.requireNonNull(model);
    this.currentUser = model.getUsers().get(0);

    JMenu file = new JMenu("File");
    JMenuItem add = new JMenuItem("Add Calendar");
    add.setActionCommand("add calendar");
    add.addActionListener(this);
    JMenuItem save = new JMenuItem("Save Calendars");
    save.setActionCommand("save calendar");
    save.addActionListener(this);
    file.add(save);
    file.add(add);
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(file);
    this.setJMenuBar(menuBar);

    users = new JComboBox<>(model.getUsers().toArray(new IUser[model.getUsers().size()]));
    users.setSelectedItem(currentUser);
    users.setActionCommand("switch user");
    users.addActionListener(this);

    this.add(users);

    create = new JButton("Create Event");
    create.setActionCommand("create");
    create.addActionListener(this);
    this.add(create);


    schedule = new JButton("Schedule Event");
    schedule.setActionCommand("schedule");
    schedule.addActionListener(this);
    this.add(schedule);
    JPanel mainPanel = new JPanel(new BorderLayout());
    panel1 = new JSchedulePanel(users.getSelectedIndex(), model);
    mainPanel.add(panel1, BorderLayout.CENTER);


    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(users);
    buttonPanel.add(create);
    buttonPanel.add(schedule);

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    add(mainPanel);
    this.pack();
    this.setSize(770, 820);

    this.setVisible(true);

  }


  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "switch user":
        this.currentUser = users.getItemAt(users.getSelectedIndex());
        this.panel1.changeSelectedUser(this.currentUser);
        panel1.repaint();
        this.repaint();
        break;
      case "add calendar":
      case "save calendar":
        new JFileChooserFrame();
        break;
      case "create":
        eventFrame = new JEventFrameView(model, currentUser);
        eventFrame.drawLayout();
        break;
      case "schedule":
        EventFrame scheduleView = new JScheduleEventFrameView(model, currentUser);
        scheduleView.drawLayout();
        repaint();
        break;
      default:
        throw new UnsupportedOperationException("operation not supported");
    }
  }

  @Override
  public IUser getCurrentUser() {
    return currentUser;
  }

  @Override
  public SchedulePanel getPanel() {
    return this.panel1;
  }

  @Override
  public void repaintAll() {
    panel1.repaint();
    this.repaint();
  }
  @Override
  public void setListener(ActionListener listener) {
    //creating new event

  }

  @Override
  public void drawLayout() {
    this.repaint();
  }
}
