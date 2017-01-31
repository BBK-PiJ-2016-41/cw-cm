import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

/**
* A meeting that was held in the past.
*
* It includes your notes about what happened and what was agreed.
*/
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
  private int id;
  private Calendar date;
  private Set<Contact> contacts;
  private String notes;

  public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("Set of contacts cannot be empty.");
    }
    this.id = id;
  }

/**
* Returns the notes from the meeting.
*
* If there are no notes, the empty string is returned.
*
* @return the notes from the meeting.
*/
  public String getNotes() {
    return "";
  }
}
