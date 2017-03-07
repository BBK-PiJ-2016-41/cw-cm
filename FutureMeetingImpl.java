import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import java.io.Serializable;
/**
* A meeting to be held in the future
* @author kathryn.buckley
*/
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
// No methods here, this is just a naming interface
// (i.e. only necessary for type checking and/or downcasting)
/**
* Constructor
* @see MeetingImpl#MeetingImpl(int, Calendar, Set<Contact>)
*/
  public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
    super(id, date, contacts);
  }
}
