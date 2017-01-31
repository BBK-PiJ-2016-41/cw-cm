/**
* A meeting that was held in the past.
*
* It includes your notes about what happened and what was agreed.
*/
public class PastMeetingImpl extends Meeting implements PastMeeting {
  private int id;

  public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {
    this.id;
  }

/**
* Returns the notes from the meeting.
*
* If there are no notes, the empty string is returned.
*
* @return the notes from the meeting.
*/
String getNotes();
}
