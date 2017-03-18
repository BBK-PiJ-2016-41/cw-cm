package impl;

import impl.ContactImpl;
import java.io.Serializable;
import spec.Contact;

/**
* A contact is a person we are making business with or may do in the future.
*
* <p>Contacts have an ID (unique, a non-zero positive integer),
* a name (not necessarily unique), and notes that the user
* may want to save about them.
* Citations used:
* 1. For implementation of hash code
* Joshua Bloch, Effective Java 2nd Edition, p 48</p>
*
* @author kathryn.buckley
*/

public class ContactImpl implements Contact, Serializable {
  /**
  * The ID of the contact.
  */
  private final int id;
  /**
  * The contact name.
  */
  private final String name;
  /**
  * Notes about this contact.
  */
  private String notes;
  /**
  * serial version UID.
  */
  private static final long serialVersionUID = 1L;
  /** Constructor taking three arguments.
  * @param id - the contact ID
  * @param name - the contact name
  * @param notes - notes about the contact
  * @throws IllegalArgumentException if id < 1
  * @throws NullPointerException if either string params are null
  */

  public ContactImpl(final int id, final String name, final String notes) {
    if (name == null) {
      throw new NullPointerException("Name cannot be null.");
    }
    if (notes == null) {
      throw new NullPointerException("Notes cannot be null.");
    }
    if (id < 1) {
      throw new IllegalArgumentException("ID must be a positive integer.");
    }
    this.id = id;
    this.name = name;
    this.notes = notes;
  }
  /** Constructor taking two arguments, used when a contact has no notes.
  * {@code} notes defaults to null
  * @see #ContactImpl(int, String, String)
  */

  public ContactImpl(final int id, final String name) {
    if (name == null) {
      throw new NullPointerException("Name cannot be null.");
    }
    if (id < 1) {
      throw new IllegalArgumentException("ID must be a positive integer.");
    }
    this.id = id;
    this.name = name;
  }
  /**
  * {@inheritDoc}.
  */

  public int getId() {
    return this.id;
  }
  /**
  * {@inheritDoc}.
  */

  public String getName() {
    return this.name;
  }
  /**
  * {@inheritDoc}.
  */

  public String getNotes() {
    return this.notes;
  }
  /**
  * {@inheritDoc}.
  */

  public void addNotes(final String note) {
    this.notes = note;
  }
  /**
  * A method to override hashCode and facilitate comparison.
  * @return an int, which is a unique hash code for the id/name combination
  */

  @Override
  public int hashCode() {
    int hash = 29;
    Integer id = (Integer) this.id;
    hash = hash * 31 + id.hashCode();
    hash = hash * 31 + this.name.hashCode();
    return hash;
  }
  /**
  * A method to override equals and facilitate comparison.
  * @param contact the contact to compare with this contact
  * @return a boolean depending on whether the two objects are equal
  */

  @Override
  public boolean equals(final Object contact) {
    return (this.hashCode() == contact.hashCode());
  }
}
