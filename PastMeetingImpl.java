import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import java.io.Serializable;

/**
* A meeting that was held in the past.
*
* It includes your notes about what happened and what was agreed.
*/
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

  public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
    super(id, date, contacts);
    if (notes == null) {
      throw new NullPointerException("No null parameters accepted.");
    }
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
  /**
  * Sets the notes to the value passed to the method.
  * @param notes a String containing the notes to set
  */
  public void setNotes(String notes) {
    this.notes = notes;
  }
}
