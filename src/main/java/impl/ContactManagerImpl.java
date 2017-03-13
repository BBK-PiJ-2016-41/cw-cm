package impl;
import spec.Contact;
import spec.ContactManager;
import spec.Meeting;
import spec.FutureMeeting;
import spec.PastMeeting;
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
* Citations used: For method reference implementation of Comparator.
* http://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property.
* @author kathryn.buckley
*/
public class ContactManagerImpl implements ContactManager, Serializable {
  /**
  * Holds today's date for use in comparisons.
  */
  private final Calendar TODAY = Calendar.getInstance();;
  /**
  * A list of all the meetings held in this Contact Manager.
  */
  private final List<Meeting> meetings;
  /**
  * A set of all the contacts held in this Contact Manager.
  */
  private final Set<Contact> contacts;
  /**
  * An enum of different object types used to
  * identify objects being read in and out.
  */
  private enum ObjectTypes {
    PASTMEETING, FUTUREMEETING, CONTACT
  }
  /**
  * serial version UID.
  */
  private static final long serialVersionUID = 1L;
  /**
  * Constructor reads in information from contacts.txt.
  * @throws FileNotFoundException when the input file can't be found.
  * @throws EOFException when the file is empty.
  * @throws IOException when there is an error reading in from the
  * file.
  * @throws SecurityException if the user does not have correct access.
  * @throws NullPointerException if the input file is null.
  * @throws ClassNotFoundException if an object read in cannot be found.
  */
  public ContactManagerImpl() {
    this.meetings = new ArrayList<Meeting>();
    this.contacts = new HashSet<Contact>();
    String filename = "contacts.txt";
    File file = new File(filename);
    if (file.exists()) {
      FileInputStream input = null;
      ObjectInputStream ois = null;
      try {
        input = new FileInputStream(filename);
        if (input != null && input.available() > 0) {
          ois = new ObjectInputStream(input);
          while (ois.available() > 0) {
            int objectType = ois.read();
            Object readIn = ois.readObject();
            if (objectType == ObjectTypes.PASTMEETING.ordinal()) {
              PastMeeting pastMeeting = (PastMeeting) readIn;
              this.meetings.add(pastMeeting);
            } else if (objectType == ObjectTypes.FUTUREMEETING.ordinal()) {
              FutureMeeting meeting = (FutureMeeting) readIn;
              if (meeting.getDate().compareTo(this.TODAY) < 0) {
                PastMeeting newPastMeeting = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), "Meeting date has passed");
                this.meetings.add(newPastMeeting);
              } else {
                this.meetings.add(meeting);
              }
            } else if (objectType == ObjectTypes.CONTACT.ordinal()) {
              Contact contact = (Contact) readIn;
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
    } else {
      try {
        boolean created = file.createNewFile();
        if (!created) {
          System.out.println("Unable to create contacts file");
        }
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("Unable to create file");
      }
    }
  }
  /**
  * {@inheritDoc}
  */
  public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
    if (date == null || contacts == null) {
      throw new NullPointerException("Arguments cannot be null");
    }
    if (date.compareTo(TODAY) < 0) {
      throw new IllegalArgumentException("Meeting cannot be in the past");
    }
    Iterator<Contact> contactIterator = contacts.iterator();
    while (contactIterator.hasNext()) {
      Contact contact = contactIterator.next();
      if (!(contacts.contains(contact))) {
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
  * {@inheritDoc}
  */
  public PastMeeting getPastMeeting(int id) {
    PastMeeting pastMeeting = null;
    try {
      pastMeeting = (PastMeeting) this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Meeting is in the future");
    }
    if (pastMeeting.getDate().compareTo(TODAY) > 0) {
      throw new IllegalStateException("Meeting is in the future");
    }
    return pastMeeting;
  }
  /**
  * {@inheritDoc}
  */
  public FutureMeeting getFutureMeeting(int id) {
    FutureMeeting futureMeeting = null;
    try {
      futureMeeting = (FutureMeeting) this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Meeting is in the past");
    }
    if (futureMeeting.getDate().compareTo(TODAY) < 0) {
      throw new IllegalStateException("Meeting is in the past");
    }
    return futureMeeting;
  }
  /**
  * {@inheritDoc}
  */
  public Meeting getMeeting(int id) {
    Meeting returnMeeting = null;
    try {
      returnMeeting = this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      ex.printStackTrace();
    }
    return returnMeeting;
  }
  /**
  * {@inheritDoc}
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
      if (meetingContacts.contains(contact) && meetingDate.compareTo(TODAY) > 0 && (!returnMeetings.contains(current))) {
        returnMeetings.add(current);
      }
    }
    // sort meetings in date order
    returnMeetings.sort(Comparator.comparing(Meeting::getDate));
    return returnMeetings;
  }
  /**
  * {@inheritDoc}
  */
  public List<Meeting> getMeetingListOn(Calendar date) {
    if (date == null) {
      throw new NullPointerException("Date cannot be null");
    }
    Calendar zeroDate = this.setZeroTime(date);
    List<Meeting> meetings = new ArrayList<Meeting>();
    Iterator<Meeting> meetingIterator = this.meetings.iterator();
    while (meetingIterator.hasNext()) {
      Meeting current = meetingIterator.next();
      Calendar currentDate = this.setZeroTime(current.getDate());
      if (currentDate.compareTo(zeroDate) == 0 && (!meetings.contains(current))) {
        meetings.add(current);
      }
    }
    // sort meetings in date order
    meetings.sort(Comparator.comparing(Meeting::getDate));
    return meetings;
  }
  /**
  * {@inheritDoc}
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
          pastMeetings.add((PastMeeting) current);
        }
      }
    }
    // sort meetings in date order
    pastMeetings.sort(Comparator.comparing(Meeting::getDate));
    return pastMeetings;
  }
  /**
  * {@inheritDoc}
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
    if (date.compareTo(TODAY) > 0) {
      throw new IllegalArgumentException("Date must be in the past");
    }
    int meetingId = this.meetings.size();
    PastMeeting newPastMeeting = new PastMeetingImpl(meetingId, date, contacts, text);
    this.meetings.add(newPastMeeting);
    this.flush();
    return meetingId;
  }
  /**
  * {@inheritDoc}
  */
  public PastMeeting addMeetingNotes(int id, String text) {
    if (text == null) {
      throw new NullPointerException("Notes cannot be null");
    }
    PastMeetingImpl meeting;
    try {
      meeting = (PastMeetingImpl) this.meetings.get(id);
    } catch (IndexOutOfBoundsException ex) {
      throw new IllegalArgumentException("Meeting does not exist");
    } catch (ClassCastException ex) {
      throw new IllegalStateException("Meeting is set for a date in the future");
    }
    if (meeting == null) {
      throw new IllegalArgumentException("Meeting does not exist");
    }
    if (TODAY.compareTo(meeting.getDate()) < 0) {
      throw new IllegalStateException("Meeting is set for a date in the future");
    }
    String existingNotes = meeting.getNotes();
    String newNotes = existingNotes + "; " + text;
    meeting.setNotes(newNotes);
    this.flush();
    return meeting;
  }
  /**
  * {@inheritDoc}
  */
  public int addNewContact(String name, String notes) {
    if (name == null || notes == null) {
      throw new NullPointerException("Arguments cannot be null");
    }
    if ("".equals(name) || "".equals(notes)) {
      throw new IllegalArgumentException("Arguments cannot be empty");
    }
    int contactId = this.contacts.size() + 1;
    ContactImpl newContact = new ContactImpl(contactId, name, notes);
    this.contacts.add(newContact);
    return contactId;
  }
  /**
  * {@inheritDoc}
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
  * {@inheritDoc}
  */
  public Set<Contact> getContacts(int... ids) {
    int noIds = ids.length;
    Integer[] complexIds = new Integer[noIds];
    for (int i = 0; i < noIds; i++) {
      complexIds[i] = (Integer) ids[i];
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
  * Sets the time of a Date object to zero for comparison purposes.
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
  * {@inheritDoc}
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
          oos.write(ObjectTypes.PASTMEETING.ordinal());
        } else if (meeting instanceof FutureMeeting) {
          oos.write(ObjectTypes.FUTUREMEETING.ordinal());
        }
        oos.writeObject(meeting);
      }
      Iterator<Contact> contactIterator = this.contacts.iterator();
      while (contactIterator.hasNext()) {
        Contact contact = contactIterator.next();
        oos.write(ObjectTypes.CONTACT.ordinal());
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
        if (oos != null) {
          oos.close();
        }
        if (output != null) {
          output.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
        System.out.println("I/O exception closing file");
      }
    }
    return;
  }
}
