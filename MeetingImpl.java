import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
*/
public abstract class MeetingImpl implements Meeting {
  /**
  * Returns the id of the meeting.
  *
  * @return the id of the meeting.
  */
  public int getId() {
    return 0;
  }
  /**
  * Return the date of the meeting.
  *
  * @return the date of the meeting.
  */
  public Calendar getDate() {
    Calendar date = Calendar.getInstance();
    date.set(2016, 12, 25);
    return date;
  }
  /**
  * Return the details of people that attended the meeting.
  *
  6
  * The list contains a minimum of one contact (if there were
  * just two people: the user and the contact) and may contain an
  * arbitrary number of them.
  *
  * @return the details of people that attended the meeting.
  */
  public Set<Contact> getContacts() {
    Set<Contact> contacts = new HashSet<Contact>();
    return contacts;
  }
}
