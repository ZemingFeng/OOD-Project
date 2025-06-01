package cs3500.weeklyplanner.xmlutils;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Utilities that deal with XML files.
 */
public interface XMLUtils {
  /**
   * uploads an XML file specified in the path to the system.
   *
   * @param path is the path to the file.
   * @throws ParserConfigurationException if we are unable to parse the file.
   * @throws IOException                  if there's an error with the Input or Output.
   * @throws SAXException                 if there's an error with the XML file.
   */
  void uploadXML(String path) throws ParserConfigurationException, IOException, SAXException;

  /**
   * save the schedule to an XML file.
   *
   * @throws ParserConfigurationException if we can't parse the schedule.
   * @throws TransformerException         if there's an exceptional
   *                                      condition that occurred during the transformation process
   */
  void saveSchedule() throws ParserConfigurationException, TransformerException;
}
