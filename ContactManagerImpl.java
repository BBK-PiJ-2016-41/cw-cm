import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Arrays;
import java.io.*;
/**
* A class to manage your contacts and meetings.
* @author kathryn.buckley
*/
public class ContactManagerImpl implements ContactManager, Serializable {
  private final Calendar today;
  private List<Meeting> meetings;
  private Set<Contact> contacts;
/**
* Constructor reads in information from contacts.txt
* @throws FileNotFoundException when the input file can't be found
* @throws EOFException when the file is empty
* @throws IOException when there is an error reading in from the file
* @throws SecurityException if the user does not have correct access
* @throws NullPointerException if the input file is null
* @throws ClassNotFoundException if an object read in cannot be found
*/
  public ContactManagerImpl() {
    this.today = Calendar.getInstance();
    this.meetings = new ArrayList<Meeting>();
    this.contacts = new HashSet<Contact>();
    FileInputStream input = null;
    ObjectInputStream ois = null;
    try {
      input = new FileInputStream("contacts.txt");
      if (input != null && input.available() > 0) {
        ois = new ObjectInputStream(input);
        while (ois.available() > 0) {
          int objectType = ois.read();
          Object readIn = ois.readObject();
          if (objectType == 1) {
            PastMeeting pastMeeting = (PastMeeting)readIn;
            this.meetings.add(pastMeeting);
          } else if (objectType == 2) {
            FutureMeeting meeting = (FutureMeeting)readIn;
            if (meeting.getDate().compareTo(today) < 0) {
              PastMeeting newPastMeeting = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), "Meeting date has passed");
              this.meetings.add(newPastMeeting);
            } else {
              this.meetings.add(meeting);
            }
          } else if (objectType == 3) {
            Contact contact = (Contact)readIn;
            this.contacts.add(contact);
          }
        }
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
      System.out.println("Contacts file not found");
    } catch (EOFException ex) {
      ex.printStackTrace();
      System.out.println("File is empty, proceed with empty Contact Manager");
    } catch (IOException ex) {
      ex.printStackTrace();
      System.out.println("I/O Exception opening file");
    } catch (SecurityException ex) {
      ex.printStackTrace();
      System.out.println("You aren't authorised to access this file");
    } catch (NullPointerException ex) {
      ex.printStackTrace();
      System.out.println("Input file cannot be null");
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
      System.out.println("Class of object not found");
    } finally {
      try {
        if (ois != null) {
          ois.close();
        }
        if (input != null) {
          input.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("I/O Exception closing file");
      } catch (NullPointerException ex) {
        ex.printStackTrace();
        System.out.println("Input stream is empty");
      }
    }
    this.meetings.sort(Comparator.comparing(Meeting::getId));
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
    if (date == null || contacts == null) {
      throw new NullPointerException("Arguments cannot be null");
    }
    if (date.compareTo(this.today) < 0) {
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
    this.flush();
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
    if (pastMeeting.getDate().compareTo(this.today) > 0) {
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
    if (futureMeeting.getDate().compareTo(this.today) < 0) {
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
    while (meetingIterator.hasNext()) {
      Meeting current = meetingIterator.next();
      Set<Contact> meetingContacts = current.getContacts();
      Calendar meetingDate = current.getDate();
      if (meetingContacts.contains(contact) && meetingDate.compareTo(this.today) > 0 && (!returnMeetings.contains(current))) {
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
    date = this.setZeroTime(date);
    List<Meeting> meetings = new ArrayList<Meeting>();
    Iterator<Meeting> meetingIterator = this.meetings.iterator();
    while (meetingIterator.hasNext()) {
      Meeting current = meetingIterator.next();
      Calendar currentDate = this.setZeroTime(current.getDate());
      if (currentDate.compareTo(date) == 0 && (!meetings.contains(current))) {
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
    if (date.compareTo(this.today) > 0) {
      throw new IllegalArgumentException("Date must be in the past");
    }
    int meetingId = this.meetings.size();
    PastMeeting newPastMeeting = new PastMeetingImpl(meetingId, date, contacts, text);
    this.meetings.add(newPastMeeting);
    this.flush();
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
    if (this.today.compareTo(meeting.getDate()) < 0) {
      throw new IllegalStateException("Meeting is set for a date in the future");
    }
    String existingNotes = meeting.getNotes();
    String newNotes = existingNotes + " " + text;
    meeting.setNotes(newNotes);
    this.flush();
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
    if (name == null || notes == null) {
      throw new NullPointerException("Arguments cannot be null");
    }
    if (name.equals("") || notes.equals("")) {
      throw new IllegalArgumentException("Arguments cannot be empty");
    }
    int contactId = this.contacts.size() + 1;
    ContactImpl newContact = new ContactImpl(contactId, name, notes);
    this.contacts.add(newContact);
    return contactId;
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
    if (name == null) {
      throw new NullPointerException("Name cannot be null");
    }
    Set<Contact> returnContacts = new HashSet<Contact>();
    Iterator<Contact> contactIterator = this.contacts.iterator();
    while (contactIterator.hasNext()) {
      Contact contact = contactIterator.next();
      if (contact.getName().contains(name) || contact.getName().equals(name)) {
        returnContacts.add(contact);
      }
    }
    return returnContacts;
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
    int noIds = ids.length;
    Integer[] complexIds = new Integer[noIds];
    for (int i = 0; i < noIds; i++) {
      complexIds[i] = (Integer)ids[i];
    }
    if (noIds == 0) {
      throw new IllegalArgumentException("Please provide some IDs");
    }
    Set<Contact> returnContacts = new HashSet<Contact>();
    Iterator<Contact> contactIterator = this.contacts.iterator();
    while (contactIterator.hasNext() && returnContacts.size() <= noIds) {
      Contact contact = contactIterator.next();
      if (Arrays.asList(complexIds).contains(contact.getId())) {
        returnContacts.add(contact);
      }
    }
    if (noIds != returnContacts.size()) {
      throw new IllegalArgumentException(noIds - returnContacts.size() + " ids are not present in the contact set");
    }
    return returnContacts;
  }
  /**
  * Sets the time of a Date object to zero for comparison purposes
  * @param date an instance of Calendar that needs modifying
  * @return the date modified to contain no timestamps
  */
  private Calendar setZeroTime(Calendar date) {
    Calendar result = date;
    result.set(Calendar.HOUR_OF_DAY, 0);
    result.set(Calendar.MINUTE, 0);
    result.set(Calendar.SECOND, 0);
    result.set(Calendar.MILLISECOND, 0);
    return result;
  }

  /**
  * Save all data to disk.
  *
  * This method must be executed when the program is
  * closed and when/if the user requests it.
  */
  public void flush() {
    FileOutputStream output = null;
    ObjectOutputStream oos = null;
    try {
      output = new FileOutputStream("contacts.txt");
      oos = new ObjectOutputStream(output);
      Iterator<Meeting> meetingIterator = this.meetings.iterator();
      while (meetingIterator.hasNext()) {
        Meeting meeting = meetingIterator.next();
        if (meeting instanceof PastMeeting) {
          oos.write(1);
        } else if (meeting instanceof FutureMeeting) {
          oos.write(2);
        }
        oos.writeObject(meeting);
      }
      Iterator<Contact> contactIterator = this.contacts.iterator();
      while (contactIterator.hasNext()) {
        Contact contact = contactIterator.next();
        oos.write(3);
        oos.writeObject(contact);
      }
      oos.flush();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
      System.out.println("Contacts file not found");
    } catch (IOException ex) {
      ex.printStackTrace();
      System.out.println("I/O exception opening file");
    } catch (SecurityException ex) {
      ex.printStackTrace();
      System.out.println("You are not authorised to access this file");
    } catch (NullPointerException ex) {
      ex.printStackTrace();
      System.out.println("Output file cannot be null");
    } finally {
      try {
        oos.close();
        output.close();
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("I/O exception closing file");
      }
    }
    return;
  }
}
