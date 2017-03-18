package impl;

import impl.MeetingImpl;
import impl.ContactImpl;
import impl.FutureMeetingImpl;

import spec.Meeting;
import spec.Contact;
import spec.FutureMeeting;

import java.util.Set;
import java.util.Calendar;

/**
* A meeting to be held in the future.
*
* @author kathryn.buckley
*/
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
  // No methods here, this is just a naming interface.
  // (i.e. only necessary for type checking and/or downcasting)
  /**
  * serial version UID.
  */
  private static final long serialVersionUID = 1L;
  /**
  * Constructor.
  * @see MeetingImpl#MeetingImpl(int, Calendar, Set<Contact>)
  */

  public FutureMeetingImpl(final int id, final Calendar date, final Set<Contact> contacts) {
    super(id, date, contacts);
  }
}
