import org.junit.*;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

public class MeetingTest {
  
  @Before
  public void buildup() {
    Set<Contact> meetingContacts = new HashSet<Contact>();
    Contact one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    Contact two = new ContactImpl(3, "Alan");
    meetingContacts.add(two);
    Contact three = new ContactImpl(2, "Mary");
    meetingContacts.add(three);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testsPMConstructor() {
    String notes = "Some random notes";
    Calendar date = Calendar.getInstance();
    date.set(2016, 12, 25);
    Set<Contact> contacts = new HashSet<Contact>();
    PastMeeting oldMeeting = new PastMeetingImpl(1, date, contacts, notes);
  }

}
