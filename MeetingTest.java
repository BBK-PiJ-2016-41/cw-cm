import org.junit.*;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

public class MeetingTest {
  Set<Contact> meetingContacts;
  Calendar xmas;
  @Before
  public void buildup() {
    meetingContacts = new HashSet<Contact>();
    Contact one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    Contact two = new ContactImpl(3, "Alan");
    meetingContacts.add(two);
    Contact three = new ContactImpl(2, "Mary");
    meetingContacts.add(three);
    xmas = Calendar.getInstance();
    xmas.set(2016, 12, 25);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testsPMConstructor() {
    String notes = "Some random notes";
    Calendar date = Calendar.getInstance();
    date.set(2016, 12, 25);
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

}
