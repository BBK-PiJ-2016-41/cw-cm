package test;

import impl.ContactManagerImpl;
import impl.MeetingImpl;
import impl.FutureMeetingImpl;
import impl.ContactImpl;
import impl.PastMeetingImpl;

import spec.ContactManager;
import spec.Meeting;
import spec.PastMeeting;
import spec.FutureMeeting;
import spec.Contact;

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

public class ContactManagerTest {
  ContactManager cMan;
  Set<Contact> meetingContacts;
  Calendar xmas;
  Contact one;
  @BeforeClass
  public void buildup() {
    cMan = new ContactManagerImpl();
    meetingContacts = new HashSet<Contact>();
    one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    xmas = Calendar.getInstance();
    xmas.set(2017, 11, 25);
    cMan.addFutureMeeting(meetingContacts, xmas);
  }
  // Test creation of future meeting with past date
  @Test(expected = IllegalArgumentException.class)
  public void testBadDate() {
    Calendar badDate = Calendar.getInstance();
    badDate.set(2017, 1, 1);
    int badId1 = cMan.addFutureMeeting(meetingContacts, badDate);
  }
  // Test creation of new meeting with contact which doesn't exist
  @Test(expected = IllegalArgumentException.class)
  public void testBadContact() {
    Contact rogue = new ContactImpl(17, "Creepy Dave", "Avoid");
    Set<Contact> badContact = new HashSet<Contact>();
    badContact.add(rogue);
    cMan.addFutureMeeting(badContact, xmas);
  }
  // Test creation of meeting with null contacts
  @Test(expected = NullPointerException.class)
  public void testContactNull() {
    cMan.addFutureMeeting(null, xmas);
  }
  // Test creation of meeting with null date
  @Test(expected = NullPointerException.class)
  public void testDateNull() {
    cMan.addFutureMeeting(meetingContacts, null);
  }
  // Test getting a past meeting
  @Test
  public void testGetPastMeeting() {
    PastMeeting pastMeeting = cMan.getPastMeeting(0);
    assertEquals(pastMeeting.getId(), 0);
  }
  // Test getting a meeting that doesn't exist
  @Test
  public void testPMBadId() {
    assertNull(cMan.getPastMeeting(77));
  }
  // Test getting a past meeting with a future date
  @Test(expected = IllegalStateException.class)
  public void testPMBadDate() {
    int futureId = cMan.addFutureMeeting(meetingContacts, xmas);
    cMan.getPastMeeting(futureId);
  }
  //Test getting a future meeting
  @Test
  public void testGetFutureMeeting() {
    FutureMeeting futureMeeting = cMan.getFutureMeeting(1);
    assertEquals(futureMeeting.getId(), 1);
  }
  // Test getting a future meeting which doesn't exist
  @Test
  public void testFMBadId() {
    assertNull(cMan.getFutureMeeting(45));
  }
  // Test getting a future meeting with a date in the past
  @Test(expected = IllegalStateException.class)
  public void testFMBadDate() {
    cMan.getFutureMeeting(0);
  }
  // Test getting any meeting
  @Test
  public void testGetMeeting() {
    assertNotNull(cMan.getMeeting(1));
  }
  // Test getting a meeting that results in null
  @Test
  public void testNullMeeting() {
    assertNull(cMan.getMeeting(35));
  }
  // Test that the right meeting returned contains the correct contact
  @Test
  public void testFMList() {
    ArrayList<Meeting> futureMeetings = (ArrayList<Meeting>)cMan.getFutureMeetingList(one);
    assertEquals(1, futureMeetings.size());
    Meeting meeting = futureMeetings.get(0);
    Set<Contact> contacts = meeting.getContacts();
    assertTrue(contacts.contains(one));
  }
  // Test getting a list of meetings with null as contact parameter
  @Test (expected = NullPointerException.class)
  public void testFMNullContact() {
    cMan.getFutureMeetingList(null);
  }
  // Test getting meetings with a contact that doesn't exist
  @Test (expected = IllegalArgumentException.class)
  public void testFMBadContact() {
    Contact badContact = new ContactImpl(21, "Eileen");
    cMan.getFutureMeetingList(badContact);
  }
  // Test getting a meeting on a particular date
  @Test
  public void testGetMeetingOnDate() {
    ArrayList<Meeting> meetings = (ArrayList<Meeting>)cMan.getMeetingListOn(xmas);
    assertEquals(1, meetings.size());
  }
  // Test getting a meeting with a null date
  @Test (expected = NullPointerException.class)
  public void testBadDateNull() {
    cMan.getMeetingListOn(null);
  }
  // Test getting a meeting on a date with no meetings assigned
  @Test
  public void testEmptyDate() {
    Calendar randomDate = Calendar.getInstance();
    randomDate.set(2017, 1, 14);
    ArrayList<Meeting> noMeetings = (ArrayList<Meeting>)cMan.getMeetingListOn(randomDate);
    assertEquals(0, noMeetings.size());
  }
  // Test getting past meetings for a particular contact
  @Test
  public void testPMList() {
    ArrayList<PastMeeting> pastMeetings = (ArrayList<PastMeeting>)cMan.getPastMeetingListFor(one);
    assertEquals(1, pastMeetings.size());
    Meeting meeting = pastMeetings.get(0);
    Set<Contact> contacts = meeting.getContacts();
    assertTrue(contacts.contains(one));
  }
  // Test getting the meeting list for a null contact
  @Test (expected = NullPointerException.class)
  public void testPMNullContact() {
    cMan.getPastMeetingListFor(null);
  }
  // test getting past meetings for a contact that doesn't exist
  @Test (expected = IllegalArgumentException.class)
  public void testPMBadContact() {
    Contact badContact = new ContactImpl(21, "Eileen");
    cMan.getPastMeetingListFor(badContact);
  }
  // Test adding a past meeting
  @Test
  public void testAddPastMeeting() {
    Calendar past = Calendar.getInstance();
    past.set(2017, 1, 2);
    assertEquals(2, cMan.addNewPastMeeting(meetingContacts, past, "Some notes here"));
  }
  // Test adding a past meeting with null contacts
  @Test (expected = NullPointerException.class)
  public void testNullPM1() {
    Calendar past = Calendar.getInstance();
    past.set(2017, 1, 2);
    cMan.addNewPastMeeting(null, past, "notes");
  }
  // Test adding a past meeting with null date
  @Test (expected = NullPointerException.class)
  public void testNullPM2() {
    cMan.addNewPastMeeting(meetingContacts, null, "more notes");

  }
  // Test adding a past meeting with null notes
  @Test (expected = NullPointerException.class)
  public void testNullPM3() {
    Calendar past = Calendar.getInstance();
    past.set(2017, 1, 2);
    cMan.addNewPastMeeting(meetingContacts, past, null);
  }
  // Test adding meeting with empty set of contacts
  @Test (expected = IllegalArgumentException.class)
  public void testIllArg1() {
    Set<Contact> contacts = new HashSet<Contact>();
    cMan.addNewPastMeeting(contacts, xmas, "notes");
  }
  // Test adding meeting with a set containing a bad contact
  @Test (expected = IllegalArgumentException.class)
  public void testIllArg2() {
    Set<Contact> contacts = new HashSet<Contact>();
    Contact badContact = new ContactImpl(21, "Eileen");
    contacts.add(badContact);
    cMan.addNewPastMeeting(contacts, xmas, "notes");
  }
  // Test adding a past meeting with a date in the future
  @Test (expected = IllegalArgumentException.class)
  public void testIllArg3() {
    Calendar future = Calendar.getInstance();
    future.set(2018, 0, 1);
    cMan.addNewPastMeeting(meetingContacts, future, "notes");
  }
  // Test adding notes to a meeting that doesn't exist
  @Test (expected = IllegalArgumentException.class)
  public void addMysteryMeetingNotes() {
    cMan.addMeetingNotes(13, "This meeting definitely doesn't exist");
  }
  // Test adding notes to a meeting that hasn't happened
  @Test (expected = IllegalStateException.class)
  public void addNotesFuture() {
    cMan.addMeetingNotes(1, "This meeting is definitely in the future");
  }
  // Test adding ntoes with a null string
  @Test (expected = NullPointerException.class)
  public void addNotesNull() {
    cMan.addMeetingNotes(0, null);
  }
  // Test adding a contact
  @Test
  public void testaddContact() {
    assertEquals(4, cMan.addNewContact("Phili", "sister"));
  }
  // Test adding a contact with no name
  @Test (expected = IllegalArgumentException.class)
  public void testEmptyName() {
    cMan.addNewContact("", "notes");
  }
  // Test adding a contact with no notes
  @Test (expected = IllegalArgumentException.class)
  public void testEmptyNotes() {
    cMan.addNewContact("Random", "");
  }
  // Test adding a contact with null name
  @Test (expected = NullPointerException.class)
  public void testNullName() {
    cMan.addNewContact(null, "notes");
  }
  // Test adding a contact with null notes
  @Test (expected = NullPointerException.class)
  public void testNullNotes() {
    cMan.addNewContact("Name", null);
  }
  // Test getting contacts named Steve
  @Test
  public void testGetContacts() {
    Set<Contact> steves = cMan.getContacts("Steve");
    assertEquals(1, steves.size());
    Iterator<Contact> iterator = steves.iterator();
    while (iterator.hasNext()) {
      Contact contact = iterator.next();
      assertTrue(contact.getName().contains("Steve"));
    }
  }
  // Test getting contacts with a null string
  @Test (expected = NullPointerException.class)
  public void testNullContName() {
    String nullString = null;
    cMan.getContacts(nullString);
  }
  // Test getting contacts with IDs
  @Test
  public void getContactsInts() {
    Set<Contact> moreContacts = cMan.getContacts(1, 2);
    assertEquals(2, moreContacts.size());
    Object[] contactArray = moreContacts.toArray();
    assertTrue(Arrays.asList(contactArray).contains(one));
  }
  // Test getting contacts with one illegal int
  @Test (expected = IllegalArgumentException.class)
  public void getContactsBadInts() {
    cMan.getContacts(1, 2, 35);
  }
  // Test getting contacts with an empty array of ints
  @Test (expected = IllegalArgumentException.class)
  public void getContactsNoInts() {
    int[] blank = new int[3];
    cMan.getContacts(blank);
  }
}
