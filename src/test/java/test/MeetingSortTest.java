
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.io.*;

public class MeetingSortTest {
    public static void main(String[] args) {
    Set<Contact> meetingContacts;
    Calendar xmas;
    Calendar nextXmas;
    PastMeeting testMeeting;
    FutureMeeting testFutureMeeting;
      meetingContacts = new HashSet<Contact>();
      Contact one = new ContactImpl(1, "Steve");
      meetingContacts.add(one);
      Contact two = new ContactImpl(3, "Alan");
      meetingContacts.add(two);
      Contact three = new ContactImpl(2, "Mary");
      meetingContacts.add(three);
      xmas = Calendar.getInstance();
      xmas.set(2016, 11, 25);
      nextXmas = Calendar.getInstance();
      nextXmas.set(2017, 11, 25);
      testMeeting = new PastMeetingImpl(5, xmas, meetingContacts, "Here are some notes");
      testFutureMeeting = new FutureMeetingImpl(8, nextXmas, meetingContacts);
      Calendar march = Calendar.getInstance();
      march.set(2017, 2, 27);
      FutureMeeting marchMeeting = new FutureMeetingImpl(1, march, meetingContacts);
      List<Meeting> meetingList = new ArrayList<Meeting>();
      meetingList.add(testFutureMeeting);
      meetingList.add(testMeeting);
      meetingList.add(marchMeeting);
      meetingList.sort(Comparator.comparing(Meeting::getDate));
      System.out.println(meetingList.get(0).getId());
      meetingList.sort(Comparator.comparing(Meeting::getId));
      System.out.println(meetingList.get(0).getId());
  }
}
