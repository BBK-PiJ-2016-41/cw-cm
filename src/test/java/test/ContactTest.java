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

public class ContactTest {
  @Test
  public void testsId() {
    //Test with first constructor
    Contact contact = new ContactImpl(1, "Steve");
    assertEquals(1, contact.getId());
    //Test with second constructor
    Contact c2 = new ContactImpl(2, "Alan", "Partridge");
    assertEquals(2, c2.getId());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testBadId() {
    //Tests with invalid ID
    Contact badC = new ContactImpl(0, "Oops I");
    Contact bad2 = new ContactImpl(-1, "Did It Again", "I played with your heart");
  }
  @Test
  public void testName() {
    //Test with first constructor
    Contact contact = new ContactImpl(1, "Steve");
    assertEquals("Steve", contact.getName());
    //Test with second constructor
    Contact contact2 = new ContactImpl(2, "George Clooney", "Likes Coffee");
    assertEquals("George Clooney", contact2.getName());
  }
  // Test contacts with bad names
  @Test(expected = NullPointerException.class)
  public void testBadName() {
    Contact badC = new ContactImpl(3, null);
    Contact bad2 = new ContactImpl(4, null, "International Man of Mystery");
  }
  // Test getting notes from contact
  @Test
  public void testNotes() {
    Contact cWithNotes = new ContactImpl(5, "Deirdre", "I have notes");
    assertEquals("I have notes", cWithNotes.getNotes());
  }
  // Test adding contacts with no notes
  @Test(expected = NullPointerException.class)
  public void testBadNotes() {
    Contact badNotes = new ContactImpl(6, "Sadly no notes", null);
  }
  // Test checking a contact's notes
  @Test
  public void checkNotes() {
    Contact withNotes = new ContactImpl(7, "Bella");
    withNotes.addNotes("Pitch Perfect");
    assertEquals("Pitch Perfect", withNotes.getNotes());
  }
  // Test to check the hash function works
  @Test
  public void checkHash() {
    Contact contact = new ContactImpl(1, "Steve");
    Contact c2 = new ContactImpl(2, "Alan", "Partridge");
    assertFalse(contact.hashCode() == c2.hashCode());
    Contact steve = new ContactImpl(1, "Steve");
    assertEquals(contact.hashCode(), steve.hashCode());
  }
  // Test to check the contains override works
  @Test
  public void checkEquals() {
    Contact contact = new ContactImpl(1, "Steve");
    Contact c2 = new ContactImpl(2, "Alan", "Partridge");
    assertFalse(contact.equals(c2));
    Contact steve = new ContactImpl(1, "Steve");
    assertTrue(contact.equals(steve));
  }
}
