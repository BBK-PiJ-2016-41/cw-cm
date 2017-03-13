package test;
import impl.ContactManager;
import impl.Meeting;
import impl.PastMeeting;
import impl.FutureMeeting;
import impl.Contact;
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

public class ThoroughTest {
  static ContactManager cMan;
  static Set<Contact> evenContacts;
  static Set<Contact> oddContacts;
  @BeforeClass
  public static void buildup() {
    cMan = new ContactManagerImpl();
    evenContacts = new HashSet<Contact>();
    oddContacts = new HashSet<Contact>();
    //add contacts
    for (int i = 0; i < 20; i++) {
      String name = "Contact " + i;
      System.out.println(name);
      int id = cMan.addNewContact(name, "notes");
      Contact contact = new ContactImpl(id, "Contact " + i);
      if (i%2 == 0) {
        evenContacts.add(contact);
      } else {
        oddContacts.add(contact);
      }
    }
    //add some past meetings
    for (int i = 0; i < 12; i++) {
      Calendar date = Calendar.getInstance();
      date.set(2016, i, 15);
      cMan.addNewPastMeeting(evenContacts, date, "Some random notes");
    }
    //add some future meetings
    for (int i = 0; i < 12; i++) {
      Calendar date = Calendar.getInstance();
      date.set(2018, i, 15);
      cMan.addFutureMeeting(oddContacts, date);
    }
  }
  @Test
  // Test that there are no meetings with ID > 23
  public void testOne() {
    assertNull(cMan.getMeeting(24));
  }
  @Test
  // Assert that there was a meeting on 15th Dec 2016
  public void testTwo() {
    Calendar testDate = Calendar.getInstance();
    testDate.set(2016, 11, 15);
    assertNotNull(cMan.getMeetingListOn(testDate));
  }
  @Test
  // Assert that there is a past meeting with ID 7
  public void testThree() {
    assertNotNull(cMan.getPastMeeting(7));
  }
  @Test
  // Assert that there is a future meeting with ID 14
  public void testFour() {
    assertNotNull(cMan.getFutureMeeting(14));
  }
  @Test
  // Assert that there is a meeting of any type with ID 12
  public void testFive() {
    assertNotNull(cMan.getMeeting(12));
  }
  @Test
  // Assert that there are 12 meetings with contact "Contact 7"
  public void testSix() {
    Iterator<Contact> oddContactIterator = oddContacts.iterator();
    while (oddContactIterator.hasNext()) {
      Contact testContact = oddContactIterator.next();
      assertEquals(12, cMan.getFutureMeetingList(testContact).size());
    }
  }
  @Test
  // Assert that there are 12 meetings with contact "Contact 10"
  public void testSeven() {
    Iterator<Contact> evenContactIterator = evenContacts.iterator();
    while (evenContactIterator.hasNext()) {
      Contact evenContact = evenContactIterator.next();
      assertEquals(12, cMan.getPastMeetingListFor(evenContact).size());
    }
  }
  @Test
  // Assert that there are 3 contacts with IDs 4, 5, 6
  public void testEight() {
    assertEquals(3, cMan.getContacts(4, 5, 6).size());
  }
  @Test
  // Assert that there is 1 contact with the name "Contact 2"
  public void testNine() {
    assertEquals(1, cMan.getContacts("Contact 2").size());
  }
}
