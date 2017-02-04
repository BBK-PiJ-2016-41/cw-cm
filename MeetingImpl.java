import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
*/
public abstract class MeetingImpl implements Meeting {
  protected int id;
  protected Calendar date;
  protected Set<Contact> contacts;
  protected String notes;

  public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("Set of contacts cannot be empty.");
    }
    if (id < 1 || date == null || contacts == null) {
      throw new NullPointerException("No null parameters accepted.");
    }
    this.id = id;
    this.date = date;
    this.contacts = contacts;
    this.notes = notes;
  }
  /**
  * Returns the id of the meeting.
  *
  * @return the id of the meeting.
  */
  public int getId() {
    return id;
  }
  /**
  * Return the date of the meeting.
  *
  * @return the date of the meeting.
  */
  public Calendar getDate() {
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
    return contacts;
  }
}
