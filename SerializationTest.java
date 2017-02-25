import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Arrays;

public class SerializationTest {
  ContactManager cMan;
  Calendar today;
  Calendar tomorrow;
  public void buildup() {
    cMan = new ContactManagerImpl();
    today = Calendar.getInstance();
    tomorrow = Calendar.getInstance();
    tomorrow.set(2017, 1, 26);
  }
  //Test that getContacts and getMeetings both return sets/lists of length 0 initially
  @Test
  public void testEmpty() {
    assertEquals(0, cMan.getContacts("Steve").size());
    assertEquals(0, cMan.getMeetingListOn(tomorrow).size());
  }
  //Add some contacts and meetings
  public void modify() {
    cMan.addNewContact("Steve", "Steve is the first contact");
    cMan.addNewContact("Mary", "Mary can be a bit quiet");
    cMan.addNewContact("John", "John is a complete arsehole");
    Set<Contact> contacts = cMan.getContacts(1, 2);
    cMan.addFutureMeeting(contacts, tomorrow);
    cMan.flush();
  }
  //Create a new instance and check that getContacts and getMeetings both return sets/lists of length > 0
  @Test
  public void testFull() {
    ContactManager cMan2 = new ContactManagerImpl();
    assertEquals(1, cMan2.getContacts("Steve").size());
    assertEquals(1, cMan2.getMeetingListOn(tomorrow).size());
  }
}
