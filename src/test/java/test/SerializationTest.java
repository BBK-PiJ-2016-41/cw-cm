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

public class SerializationTest {
  Calendar tomorrow;
  @Before
  public void buildup() {
    tomorrow = Calendar.getInstance();
    tomorrow.set(2017, 2, 27);
  }
  //Test that getContacts and getMeetings both return sets/lists of length 0 initially
  @Test
  public void testEmpty() {
    ContactManager cMan = new ContactManagerImpl();
    assertTrue(cMan.getContacts("Steve").isEmpty());
    assertTrue(cMan.getMeetingListOn(tomorrow).isEmpty());
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
