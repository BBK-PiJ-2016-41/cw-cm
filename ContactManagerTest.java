import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ContactManagerTest {
  ContactManager cMan;
  Set<Contact> meetingContacts;
  Calendar xmas;
  @Before
  public void buildup() {
    cMan = new ContactManagerImpl();
    meetingContacts = new HashSet<Contact>();
    Contact one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    xmas = Calendar.getInstance();
    xmas.set(2017, 11, 25);
  }
  @Test
  public void testAddFutureMeeting() {
    //There is one meeting added at index 0 so we are looking for 1
    assertEquals(1, cMan.addFutureMeeting(meetingContacts, xmas));
  }
  @Test(expected = IllegalArgumentException.class)
  public void testBadDate() {
    Calendar badDate = Calendar.getInstance();
    badDate.set(2017, 1, 1);
    int badId1 = cMan.addFutureMeeting(meetingContacts, badDate);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testBadContact() {
    Contact rogue = new ContactImpl(17, "Creepy Dave", "Avoid");
    Set<Contact> badContact = new HashSet<Contact>();
    badContact.add(rogue);
    cMan.addFutureMeeting(badContact, xmas);
  }
  @Test(expected = NullPointerException.class)
  public void testContactNull() {
    cMan.addFutureMeeting(null, xmas);
  }
  @Test(expected = NullPointerException.class)
  public void testDateNull() {
    cMan.addFutureMeeting(meetingContacts, null);
  }
  @Test
  public void testGetPastMeeting() {
    PastMeeting pastMeeting = cMan.getPastMeeting(0);
    assertEquals(pastMeeting.getId(), 0);
  }
  @Test
  public void testPMBadId() {
    assertNull(cMan.getPastMeeting(77));
  }
  @Test(expected = IllegalStateException.class)
  public void testPMBadDate() {
    int futureId = cMan.addFutureMeeting(meetingContacts, xmas);
    cMan.getPastMeeting(futureId);
  }
}
