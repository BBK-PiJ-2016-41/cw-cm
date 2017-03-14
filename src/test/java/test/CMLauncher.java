package test;

import impl.ContactManagerImpl;
import impl.MeetingImpl;
import impl.FutureMeetingImpl;
import impl.ContactImpl;
import impl.PastMeetingImpl;

import spec.ContactManager;
import spec.Meeting;
import spec.PastMeeting;
import spec.FutureMeeting;
import spec.Contact;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CMLauncher {
  public static void main (String[] args) {
    ContactManager cm = new ContactManagerImpl();
    Calendar tomorrow = Calendar.getInstance();
    tomorrow.set(2017, 1, 27);
    System.out.println(cm.getContacts("Steve").isEmpty());
    System.out.println(cm.getMeetingListOn(tomorrow).isEmpty());
    cm.addNewContact("Steve", "some notes");
    cm.addFutureMeeting(cm.getContacts("Steve"), tomorrow);
    cm.flush();
    ContactManager cm2 = new ContactManagerImpl();
    System.out.println(cm2.getContacts("Steve").size());
    System.out.println(cm2.getMeetingListOn(tomorrow).size());
  }
}
