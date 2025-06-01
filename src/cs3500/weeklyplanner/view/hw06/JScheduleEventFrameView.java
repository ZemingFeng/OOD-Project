package cs3500.weeklyplanner.view.hw06;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.weeklyplanner.model.IUser;
import cs3500.weeklyplanner.model.ReadOnlyPlannerModel;

public class JScheduleEventFrameView extends JEventFrameView {
  private JLabel durationLabel;
  private JTextField durationText;
  private JLabel workDayLabel;
  private JComboBox<String> onlineComboBox;
  private JComboBox<String> workDayComboBox;
  private JButton scheduleEventButton;

  public JScheduleEventFrameView(ReadOnlyPlannerModel model, IUser currentUser) {
    super(model, currentUser);
    this.setup();


    pack();
    setSize(500, 600);
    setMinimumSize(new Dimension(600, 600));
    setLocation(200, 200);

    this.setVisible(true);


  }

  protected void setup() {
    durationLabel = new JLabel("Duration in minutes: ");
    workDayLabel = new JLabel("Any Time / Work Day");
    durationText = new JTextField();
    onlineComboBox = new JComboBox<>(new String[]{"Not online", "Online"});
    workDayComboBox = new JComboBox<>(new String[]{"Any time", "Work day"});
    scheduleEventButton = new JButton("Schedule event");

    scheduleEventButton.setActionCommand("scheduleEvent");
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
                    .addComponent(workDayLabel)
                    .addComponent(durationLabel)
                    .addComponent(availableUsersLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(eventNameField)
                    .addComponent(locationField)
                    .addComponent(onlineComboBox)
                    .addComponent(workDayComboBox)
                    .addComponent(durationText)
                    .addComponent(userList)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(addUserButton)
                            .addComponent(removeUserButton)
                            .addComponent(scheduleEventButton)))
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
                    .addComponent(onlineComboBox))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(workDayLabel)
                    .addComponent(workDayComboBox))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(durationLabel)
                    .addComponent(durationText))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(availableUsersLabel)
                    .addComponent(userList))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addUserButton)
                    .addComponent(removeUserButton)
                    .addComponent(scheduleEventButton))
    );
  }
  @Override
  public void setListener(ActionListener listener) {
    scheduleEventButton.addActionListener(listener);
  }

  @Override
  public int getDuration() {
    return stringToInt(durationText.getText());
  }

  @Override
  public boolean getAnyTime() {
    return stringToBool(workDayComboBox.getItemAt(workDayComboBox.getSelectedIndex()));
  }


}
