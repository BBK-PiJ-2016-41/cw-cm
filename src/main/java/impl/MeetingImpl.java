package impl;

import impl.ContactImpl;
import impl.MeetingImpl;

import spec.Contact;
import spec.Meeting;

import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;

/**
* A class to represent meetings.
* Meetings have unique IDs, scheduled date and a list of participating contacts
* @author kathryn.buckley
*/

public abstract class MeetingImpl implements Meeting, Serializable {
  /**
  * The ID of the meeting.
  */
  protected int id;
  /**
  * The date of the meeting.
  */
  protected Calendar date;
  /**
  * The contacts attending the meeting.
  */
  protected Set<Contact> contacts;
  /**
  * Notes about the meeting.
  */
  protected String notes;
  /**
  * serial version UID.
  */
  private static final long serialVersionUID = 1L;
  /**
  * Constructor takes an int to denote meeting ID, the date of the meeting,
  * and a set of contacts attending.
  * @param id - the ID of the meeting
  * @param date - the date of the meeting
  * @param contacts - the contacts who are attending the meeting
  * @throws IllegalArgumentException if the set of contacts is empty
  * @throws NullPointerException if any of the parameters are null
  */

  public MeetingImpl(final int id, final Calendar date, final Set<Contact> contacts) {
    if (id < 0 || date == null || contacts == null) {
      throw new NullPointerException("No null parameters accepted.");
    }
    if (contacts.isEmpty()) {
      throw new IllegalArgumentException("Set of contacts cannot be empty.");
    }
    this.id = id;
    this.date = date;
    this.contacts = contacts;
  }
  /**
  * {@inheritDoc}.
  */

  public int getId() {
    return id;
  }
  /**
  * {@inheritDoc}.
  */

  public Calendar getDate() {
    return date;
  }
  /**
  * {@inheritDoc}.
  */
  
  public Set<Contact> getContacts() {
    return contacts;
  }
}
