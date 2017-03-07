import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
* @author kathryn.buckley
*/
public abstract class MeetingImpl implements Meeting, Serializable {
  /**
  * The ID of the meeting
  */
  protected int id;
  /**
  * The date of the meeting
  */
  protected Calendar date;
  /**
  * The contacts attending the meeting
  */
  protected Set<Contact> contacts;
  /**
  * Notes about the meeting
  */
  protected String notes;
  /**
  * Constructor takes an int to denote meeting ID, the date of the meeting, and a set of contacts attending
  * @param int id, the ID of the meeting
  * @param Calendar date, the date of the meeting
  * @param Set<Contact> contacts, the contacts who are attending the meeting
  * @throws IllegalArgumentException if the set of contacts is empty
  * @throws NullPointerException if any of the parameters are null
  */
  public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("Set of contacts cannot be empty.");
    }
    if (id < 0 || date == null || contacts == null) {
      throw new NullPointerException("No null parameters accepted.");
    }
    this.id = id;
    this.date = date;
    this.contacts = contacts;
  }
  /**
  * {@inheritDoc}
  */
  public int getId() {
    return id;
  }
  /**
  * {@inheritDoc}
  */
  public Calendar getDate() {
    return date;
  }
  /**
  * {@inheritDoc}
  */
  public Set<Contact> getContacts() {
    return contacts;
  }
}
