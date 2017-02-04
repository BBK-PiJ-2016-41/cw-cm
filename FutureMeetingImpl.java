import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

/**
* A meeting to be held in the future
*/
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
// No methods here, this is just a naming interface
// (i.e. only necessary for type checking and/or downcasting)
  public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
    super(id, date, contacts);
  }
}
