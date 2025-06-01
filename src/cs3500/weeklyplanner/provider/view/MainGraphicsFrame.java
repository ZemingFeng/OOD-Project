package cs3500.weeklyplanner.provider.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.function.Consumer;

import javax.swing.JFrame;

import planner.model.ReadOnlyNUPlanner;

/**
 * Main frame to contain and handle panels to work with schedule. Has file menu, week panel, and
 * buttons to create, remove, and modify events.
 */
public class MainGraphicsFrame extends JFrame implements MainView {

  private final MainPanel panel;

  /**
   * Main frame to contain and handle panels to work with schedule. Has file menu, week panel, and
   * buttons to create, remove, and modify events.
   */
  public MainGraphicsFrame(ReadOnlyNUPlanner planner) {
    super();
    this.setTitle("NUPlanner");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize((int)(screenSize.width * 0.6),
            (int)(screenSize.height * 0.75));
    this.setMinimumSize(new Dimension((int)(screenSize.width * 0.3),
            (int)(screenSize.height * 0.4)));
    this.setMaximumSize(new Dimension((int)(screenSize.width * 0.95),
            (int)(screenSize.height * 0.95)));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new MainPanel(planner);
    EventView eventView = null;
    this.setResizable(true);
    this.add(this.panel);
  }

  @Override
  public void refresh() {
    this.repaint();
    this.panel.repaint();
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    this.panel.setCommandCallback(callback);
  }
}
