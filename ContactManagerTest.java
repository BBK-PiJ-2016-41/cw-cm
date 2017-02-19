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
  Contact one;
  @Before
  public void buildup() {
    cMan = new ContactManagerImpl();
    meetingContacts = new HashSet<Contact>();
    one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    xmas = Calendar.getInstance();
    xmas.set(2017, 11, 25);
    cMan.addFutureMeeting(meetingContacts, xmas);
  }
  /*
  @Test
  public void testAddFutureMeeting() {
    //There is one meeting added at index 0 so we are looking for 1
    assertEquals(1, cMan.addFutureMeeting(meetingContacts, xmas));
  }*/
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
  @Test
  public void testGetFutureMeeting() {
    FutureMeeting futureMeeting = cMan.getFutureMeeting(1);
    assertEquals(futureMeeting.getId(), 1);
  }
  @Test
  public void testFMBadId() {
    assertNull(cMan.getFutureMeeting(45));
  }
  @Test(expected = IllegalStateException.class)
  public void testFMBadDate() {
    cMan.getFutureMeeting(0);
  }
  @Test
  public void testGetMeeting() {
    assertNotNull(cMan.getMeeting(1));
  }
  @Test
  public void testNullMeeting() {
    assertNull(cMan.getMeeting(35));
  }
  @Test
  public void testFMList() {
    ArrayList<Meeting> futureMeetings = (ArrayList<Meeting>)cMan.getFutureMeetingList(one);
    assertEquals(1, futureMeetings.size());
    Meeting meeting = futureMeetings.get(0);
    Set<Contact> contacts = meeting.getContacts();
    assertTrue(contacts.contains(one));
  }
  @Test (expected = NullPointerException.class)
  public void testFMNullContact() {
    cMan.getFutureMeetingList(null);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testFMBadContact() {
    Contact badContact = new ContactImpl(21, "Eileen");
    cMan.getFutureMeetingList(badContact);
  }
  @Test
  public void testGetMeetingOnDate() {
    ArrayList<Meeting> meetings = (ArrayList<Meeting>)cMan.getMeetingListOn(xmas);
    assertEquals(1, meetings.size());
  }
  @Test (expected = NullPointerException.class)
  public void testBadDateNull() {
    cMan.getMeetingListOn(null);
  }
  @Test
  public void testEmptyDate() {
    Calendar randomDate = Calendar.getInstance();
    randomDate.set(2017, 1, 14);
    ArrayList<Meeting> noMeetings = (ArrayList<Meeting>)cMan.getMeetingListOn(randomDate);
    assertEquals(0, noMeetings.size());
  }
  @Test
  public void testPMList() {
    ArrayList<PastMeeting> pastMeetings = (ArrayList<PastMeeting>)cMan.getPastMeetingListFor(one);
    assertEquals(1, pastMeetings.size());
    Meeting meeting = pastMeetings.get(0);
    Set<Contact> contacts = meeting.getContacts();
    assertTrue(contacts.contains(one));
  }
  @Test (expected = NullPointerException.class)
  public void testPMNullContact() {
    cMan.getPastMeetingListFor(null);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testPMBadContact() {
    Contact badContact = new ContactImpl(21, "Eileen");
    cMan.getPastMeetingListFor(badContact);
  }
  @Test
  public void testAddPastMeeting() {
    Calendar past = Calendar.getInstance();
    past.set(2017, 1, 2);
    assertEquals(2, cMan.addNewPastMeeting(meetingContacts, past, "Some notes here"));
  }
  @Test (expected = NullPointerException.class)
  public void testNullPM1() {
    Calendar past = Calendar.getInstance();
    past.set(2017, 1, 2);
    cMan.addNewPastMeeting(null, past, "notes");
  }
  @Test (expected = NullPointerException.class)
  public void testNullPM2() {
    cMan.addNewPastMeeting(meetingContacts, null, "more notes");

  }
  @Test (expected = NullPointerException.class)
  public void testNullPM3() {
    Calendar past = Calendar.getInstance();
    past.set(2017, 1, 2);
    cMan.addNewPastMeeting(meetingContacts, past, null);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testIllArg1() {
    Set<Contact> contacts = new HashSet<Contact>();
    cMan.addNewPastMeeting(contacts, xmas, "notes");
  }
  @Test (expected = IllegalArgumentException.class)
  public void testIllArg2() {
    Set<Contact> contacts = new HashSet<Contact>();
    Contact badContact = new ContactImpl(21, "Eileen");
    contacts.add(badContact);
    cMan.addNewPastMeeting(contacts, xmas, "notes");
  }
  @Test (expected = IllegalArgumentException.class)
  public void testIllArg3() {
    Calendar future = Calendar.getInstance();
    future.set(2018, 0, 1);
    cMan.addNewPastMeeting(meetingContacts, future, "notes");
  }
}
