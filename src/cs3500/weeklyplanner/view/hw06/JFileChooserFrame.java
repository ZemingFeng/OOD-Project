package cs3500.weeklyplanner.view.hw06;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


/**
 * The frame that represents the file chooser. It shows a window
 * that looks like a file explorer, which allows the user to select
 * an XML file to upload, or to save the system to an XML file.
 */
public class JFileChooserFrame extends JFrame {

  /**
   * constructor of the file chooser class.
   */
  public JFileChooserFrame() {
    JFileChooser fileChooser = new JFileChooser();
    this.add(fileChooser);
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
    } else {
      System.out.println("File selection was canceled. ");
    }
    this.setSize(700, 700);
    this.setVisible(true);
  }

}
