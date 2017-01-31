import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

/**
* A meeting that was held in the past.
*
* It includes your notes about what happened and what was agreed.
*/
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

  public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("Set of contacts cannot be empty.");
    }
    if (id < 1 || date == null || contacts == null || notes == null) {
      throw new NullPointerException("No null parameters accepted.");
    }
    this.id = id;
    this.date = date;
    this.contacts = contacts;
    this.notes = notes;
  }

/**
* Returns the notes from the meeting.
*
* If there are no notes, the empty string is returned.
*
* @return the notes from the meeting.
*/
  public String getNotes() {
    return this.notes;
  }
}
