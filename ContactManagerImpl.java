import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Comparator;
/**
* A class to manage your contacts and meetings.
*/
public class ContactManagerImpl implements ContactManager, Serializable {
  private List<Meeting> meetings;
  private Set<Contact> contacts;
  public ContactManagerImpl() {
    //should open contacts.txt file here
    //using dummy details for now
    //when file is loaded, should run through meeting list and cast through future meetings to past meetings
    Set<Contact> meetingContacts = new HashSet<Contact>();
    Contact one = new ContactImpl(1, "Steve");
    meetingContacts.add(one);
    Contact two = new ContactImpl(3, "Alan");
    meetingContacts.add(two);
    Contact three = new ContactImpl(2, "Mary");
    meetingContacts.add(three);
    Calendar xmas = Calendar.getInstance();
    xmas.set(2016, 11, 25);
    List<Meeting> myMeetings = new ArrayList<Meeting>();
    PastMeetingImpl pastMeeting = new PastMeetingImpl(0, xmas, meetingContacts, "Here are some notes");
    this.meetings = myMeetings;
    this.meetings.add(0, pastMeeting);
    this.contacts = meetingContacts;
  }
  /**
  * Add a new meeting to be held in the future.
  *
  * An ID is returned when the meeting is put into the system. This
  * ID must be positive and non-zero.
  *
  * @param contacts a set of contacts that will participate in the meeting
  * @param date the date on which the meeting will take place
  * @return the ID for the meeting
  * @throws IllegalArgumentException if the meeting is set for a time
  * in the past, of if any contact is unknown / non-existent.
  * @throws NullPointerException if the meeting or the date are null
  */
  public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
    Calendar today = Calendar.getInstance();
    if (date == null || contacts == null) {
      throw new NullPointerException("Arguments cannot be null");
    }
    if (date.compareTo(today) < 0) {
      throw new IllegalArgumentException("Meeting cannot be in the past");
    }
    Iterator<Contact> contactIterator = contacts.iterator();
    while (contactIterator.hasNext()) {
      Contact contact = contactIterator.next();
      if (!(this.contacts.contains(contact))) {
        throw new IllegalArgumentException("Contact not recognised");
      }
    }
    int meetingId = this.meetings.size();
    FutureMeeting newMeeting = new FutureMeetingImpl(meetingId, date, contacts);
    this.meetings.add(newMeeting);
    return meetingId;
  }
  /**
  * Returns the PAST meeting with the requested ID, or null if it there is none.
  *
  * The meeting must have happened at a past date.
  *
  * @param id the ID for the meeting
  * @return the meeting with the requested ID, or null if it there is none.
  * @throws IllegalStateException if there is a meeting with that ID happening
  * in the future
  */
  public PastMeeting getPastMeeting(int id) {
    PastMeeting pastMeeting;
    try {
      pastMeeting = (PastMeeting)this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      return null;
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Meeting is in the future");
    }
    Calendar today = Calendar.getInstance();
    if (pastMeeting.getDate().compareTo(today) > 0) {
      throw new IllegalStateException("Meeting is in the future");
    }
    return pastMeeting;
  }
  /**
  * Returns the FUTURE meeting with the requested ID, or null if there is none.
  *
  * @param id the ID for the meeting
  * @return the meeting with the requested ID, or null if it there is none.
  2
  * @throws IllegalStateException if there is a meeting with that ID happening
  * in the past
  */
  public FutureMeeting getFutureMeeting(int id) {
    FutureMeeting futureMeeting;
    try {
      futureMeeting = (FutureMeeting)this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      return null;
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Meeting is in the past");
    }
    Calendar today = Calendar.getInstance();
    if (futureMeeting.getDate().compareTo(today) < 0) {
      throw new IllegalStateException("Meeting is in the past");
    }
    return futureMeeting;
  }
  /**
  * Returns the meeting with the requested ID, or null if it there is none.
  *
  * @param id the ID for the meeting
  * @return the meeting with the requested ID, or null if it there is none.
  */
  public Meeting getMeeting(int id) {
    Meeting returnMeeting;
    try {
      returnMeeting = this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      return null;
    }
    return returnMeeting;
  }
  /**
  * Returns the list of future meetings scheduled with this contact.
  *
  * If there are none, the returned list will be empty. Otherwise,
  * the list will be chronologically sorted and will not contain any
  * duplicates.
  *
  * @param contact one of the users contacts
  * @return the list of future meeting(s) scheduled with this contact (maybe empty).
  * @throws IllegalArgumentException if the contact does not exist
  * @throws NullPointerException if the contact is null
  */
  public List<Meeting> getFutureMeetingList(Contact contact) {
    if (contact == null) {
      throw new NullPointerException("Contact cannot be null");
    }
    if (!this.contacts.contains(contact)) {
      throw new IllegalArgumentException("This contact does not exist");
    }
    List<Meeting> returnMeetings = new ArrayList<Meeting>();
    Iterator<Meeting> meetingIterator = this.meetings.iterator();
    Calendar today = Calendar.getInstance();
    while (meetingIterator.hasNext()) {
      Meeting current = meetingIterator.next();
      Set<Contact> meetingContacts = current.getContacts();
      Calendar meetingDate = current.getDate();
      if (meetingContacts.contains(contact) && meetingDate.compareTo(today) > 0 && (!returnMeetings.contains(current))) {
        returnMeetings.add(current);
      }
    }
    //make sure sort works
    returnMeetings.sort(Comparator.comparing(Meeting::getDate));
    return returnMeetings;
  }
  /**
  * Returns the list of meetings that are scheduled for, or that took
  * place on, the specified date
  *
  * If there are none, the returned list will be empty. Otherwise,
  * the list will be chronologically sorted and will not contain any
  * duplicates.
  *
  * @param date the date
  * @return the list of meetings
  * @throws NullPointerException if the date are null
  */
  public List<Meeting> getMeetingListOn(Calendar date) {
    if (date == null) {
      throw new NullPointerException("Date cannot be null");
    }
    List<Meeting> meetings = new ArrayList<Meeting>();
    Iterator<Meeting> meetingIterator = this.meetings.iterator();
    while (meetingIterator.hasNext()) {
      Meeting current = meetingIterator.next();
      if (current.getDate().compareTo(date) == 0 && (!meetings.contains(current))) {
        meetings.add(current);
      }
    }
    //Make sure sort works
    meetings.sort(Comparator.comparing(Meeting::getDate));
    return meetings;
  }
  /**
  * Returns the list of past meetings in which this contact has participated.
  *
  * If there are none, the returned list will be empty. Otherwise,
  * the list will be chronologically sorted and will not contain any
  * duplicates.
  *
  * @param contact one of the users contacts
  * @return the list of future meeting(s) scheduled with this contact (maybe empty). ?? Should be past?
  * @throws IllegalArgumentException if the contact does not exist
  * @throws NullPointerException if the contact is null
  */
  public List<PastMeeting> getPastMeetingListFor(Contact contact) {
    if (contact == null) {
      throw new NullPointerException("Contact cannot be null");
    }
    if (!this.contacts.contains(contact)) {
      throw new IllegalArgumentException("Contact does not exist");
    }
    List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();
    Iterator<Meeting> meetingIterator = this.meetings.iterator();
    while (meetingIterator.hasNext()) {
      Meeting current = meetingIterator.next();
      if (current instanceof PastMeeting) {
        Set<Contact> contacts = current.getContacts();
        if (contacts.contains(contact) && (!pastMeetings.contains(current))) {
          pastMeetings.add((PastMeeting)current);
        }
      }
    }
    pastMeetings.sort(Comparator.comparing(Meeting::getDate));
    return pastMeetings;
  }
  /**
  * Create a new record for a meeting that took place in the past.
  *
  * @param contacts a set of participants
  * @param date the date on which the meeting took place
  * @param text messages to be added about the meeting.
  * @return the ID for the meeting
  * @throws IllegalArgumentException if the list of contacts is
  * empty, if any of the contacts does not exist, or if
  * the date provided is in the future
  * @throws NullPointerException if any of the arguments is null
  */
  public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
    if (contacts == null || date == null || text == null) {
      throw new NullPointerException("Arguments cannot be null");
    }
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("Set of contacts cannot be empty");
    }
    Iterator<Contact> contactIterator = contacts.iterator();
    while (contactIterator.hasNext()) {
      Contact current = contactIterator.next();
      if (!this.contacts.contains(current)) {
        throw new IllegalArgumentException("One or more contacts does not exist");
      }
    }
    Calendar today = Calendar.getInstance();
    if (date.compareTo(today) > 0) {
      throw new IllegalArgumentException("Date must be in the past");
    }
    int meetingId = this.meetings.size();
    PastMeeting newPastMeeting = new PastMeetingImpl(meetingId, date, contacts, text);
    this.meetings.add(newPastMeeting);
    return meetingId;
  }
  /**
  * Add notes to a meeting.
  *
  * This method is used when a future meeting takes place, and is
  * then converted to a past meeting (with notes) and returned.
  *
  * It can be also used to add notes to a past meeting at a later date.
  *
  * @param id the ID of the meeting
  * @param text messages to be added about the meeting.
  * @throws IllegalArgumentException if the meeting does not exist
  * @throws IllegalStateException if the meeting is set for a date in the future
  * @throws NullPointerException if the notes are null
  */
  public PastMeeting addMeetingNotes(int id, String text) {
    if (text == null) {
      throw new NullPointerException("Notes cannot be null");
    }
    PastMeetingImpl meeting;
    try {
      meeting = (PastMeetingImpl)this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      throw new IllegalArgumentException("Meeting does not exist");
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Meeting is set for a date in the future");
    }
    if (meeting == null) {
      throw new IllegalArgumentException("Meeting does not exist");
    }
    Calendar today = Calendar.getInstance();
    if (today.compareTo(meeting.getDate()) < 0) {
      throw new IllegalStateException("Meeting is set for a date in the future");
    }
    String existingNotes = meeting.getNotes();
    String newNotes = existingNotes + " " + text;
    meeting.setNotes(newNotes);
    return meeting;
  }
  /**
  * Create a new contact with the specified name and notes.
  *
  * @param name the name of the contact.
  * @param notes notes to be added about the contact.
  * @return the ID for the new contact
  * @throws IllegalArgumentException if the name or the notes are empty strings
  * @throws NullPointerException if the name or the notes are null
  */
  public int addNewContact(String name, String notes) {
    return 1;
  }
  /**
  * Returns a set with the contacts whose name contains that string.
  *
  * If the string is the empty string, this methods returns the set
  * that contains all current contacts.
  *
  * @param name the string to search for
  * @return a set with the contacts whose name contains that string.
  * @throws NullPointerException if the parameter is null
  */
  public Set<Contact> getContacts(String name) {
    return null;
  }
  /**
  * Returns a set containing the contacts that correspond to the IDs.
  * Note that this method can be used to retrieve just one contact by passing only one ID.
  *
  * @param ids an arbitrary number of contact IDs
  * @return a set containing the contacts that correspond to the IDs.
  * @throws IllegalArgumentException if no IDs are provided or if
  * any of the provided IDs does not correspond to a real contact
  */
  public Set<Contact> getContacts(int... ids) {
    return null;
  }
  /**
  * Save all data to disk.
  *
  * This method must be executed when the program is
  * closed and when/if the user requests it.
  */
  public void flush() {
    return;
  }
}
