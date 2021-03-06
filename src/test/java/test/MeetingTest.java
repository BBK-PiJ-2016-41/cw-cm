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
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

public class MeetingTest {
  Set<Contact> meetingContacts;
  Calendar xmas;
  Calendar nextXmas;
  PastMeeting testMeeting;
  FutureMeeting testFutureMeeting;
  @BeforeClass
  public void buildup() {
    meetingContacts = new HashSet<Contact>();
    Contact one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    Contact two = new ContactImpl(3, "Alan");
    meetingContacts.add(two);
    Contact three = new ContactImpl(2, "Mary");
    meetingContacts.add(three);
    xmas = Calendar.getInstance();
    xmas.set(2016, 11, 25);
    nextXmas = Calendar.getInstance();
    nextXmas.set(2017, 11, 25);
    testMeeting = new PastMeetingImpl(5, xmas, meetingContacts, "Here are some notes");
    testFutureMeeting = new FutureMeetingImpl(8, nextXmas, meetingContacts);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testsPMConstructor() {
    String notes = "Some random notes";
    Calendar date = Calendar.getInstance();
    date.set(2016, 11, 25);
    Set<Contact> contacts = new HashSet<Contact>();
    PastMeeting oldMeeting = new PastMeetingImpl(1, date, contacts, notes);
  }
  @Test(expected = NullPointerException.class)
  public void testsPMConstructorNull() {
    PastMeeting badMeeting = new PastMeetingImpl(-1, xmas, meetingContacts, "blah");
  }
  @Test(expected = NullPointerException.class)
  public void testsPMConstructorNull2() {
    PastMeeting badMeeting2 = new PastMeetingImpl(1, null, meetingContacts, "blah");
  }
  @Test(expected = NullPointerException.class)
  public void testsPMConstructorNull3() {
    PastMeeting badMeeting3 = new PastMeetingImpl(1, xmas, null, "blah");
  }
  @Test(expected = NullPointerException.class)
  public void testsPMConstructorNull4() {
    PastMeeting badMeeting4 = new PastMeetingImpl(1, xmas, meetingContacts, null);
  }
  @Test
  public void testsgetId() {
    assertEquals(testMeeting.getId(), 5);
  }
  @Test
  public void testsgetDate() {
    assertEquals(testMeeting.getDate(), xmas);
  }
  @Test
  public void testsgetContacts() {
    assertEquals(testMeeting.getContacts(), meetingContacts);
  }
  @Test
  public void testsgetNotes() {
    assertEquals(testMeeting.getNotes(), "Here are some notes");
  }
  @Test(expected = IllegalArgumentException.class)
  public void testsFMConstructor() {
    Calendar date = Calendar.getInstance();
    date.set(2017, 11, 25);
    Set<Contact> contacts = new HashSet<Contact>();
    FutureMeeting newMeeting = new FutureMeetingImpl(1, date, contacts);
  }
  @Test(expected = NullPointerException.class)
  public void testsFMConstructorNull() {
    FutureMeeting fMeeting = new FutureMeetingImpl(-1, nextXmas, meetingContacts);
  }
  @Test(expected = NullPointerException.class)
  public void testsFMConstructorNull2() {
    FutureMeeting fMeeting2 = new FutureMeetingImpl(1, null, meetingContacts);
  }
  @Test(expected = NullPointerException.class)
  public void testsFMConstructorNull3() {
    FutureMeeting fMeeting3 = new FutureMeetingImpl(1, nextXmas, null);
  }
  @Test
  public void testFutureMeetingFunctions() {
    assertEquals(8, testFutureMeeting.getId());
    assertEquals(nextXmas, testFutureMeeting.getDate());
    assertEquals(meetingContacts, testFutureMeeting.getContacts());
  }
}
