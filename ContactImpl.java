import java.io.Serializable;
/**
* A contact is a person we are making business with or may do in the future.
*
* Contacts have an ID (unique, a non-zero positive integer),
* a name (not necessarily unique), and notes that the user
* may want to save about them.
* Citations used:
* 1. For implementation of hash code
* Josh Bloch's Effective Java
* @author kathryn.buckley
*/
public class ContactImpl implements Contact, Serializable {
  /**
  * The ID of the contact
  */
  private final int id;
  /**
  * The contact name
  */
  private final String name;
  /**
  * Notes about this contact
  */
  private String notes;
  /** Constructor taking three arguments
  * @param int id
  * @param String name
  * @param String notes
  * @throws IllegalArgumentException if id < 1
  * @throws NullPointerException if either string params are null
  */
  public ContactImpl(int id, String name, String notes) {
    if (name == null) {
      throw new NullPointerException("Name cannot be null.");
    }
    if (notes == null) {
      throw new NullPointerException("Notes cannot be null.");
    }
    if (id < 1) {
      throw new IllegalArgumentException("ID must be a non zero positive integer.");
    }
    this.id = id;
    this.name = name;
    this.notes = notes;
  }
  /** Constructor taking two arguments, used when a contact has no notes
  * {@code} notes defaults to null
  * @see #ContactImpl(int, String, String)
  */
  public ContactImpl(int id, String name) {
    if (name == null) {
      throw new NullPointerException("Name cannot be null.");
    }
    if (id < 1) {
      throw new IllegalArgumentException("ID must be a non zero positive integer.");
    }
    this.id = id;
    this.name = name;
  }
  /**
  * {@inheritDoc}
  */
  public int getId() {
    return this.id;
  }
  /**
  * {@inheritDoc}
  */
  public String getName() {
    return this.name;
  }
  /**
  * {@inheritDoc}
  */
  public String getNotes() {
    return this.notes;
  }
  /**
  * {@inheritDoc}
  */
  public void addNotes(String note) {
    this.notes = note;
  }
  /**
  * A method to override hashCode and facilitate comparison
  * @return an int, which is a unique hash code for the id/name combination
  */
  @Override
  public int hashCode() {
    int hash = 29;
    Integer id = (Integer)this.id;
    hash = hash * 31 + id.hashCode();
    hash = hash * 31 + this.name.hashCode();
    return hash;
  }
  /**
  * A method to override equals and facilitate comparison
  * @param contact the contact to compare with this contact
  * @return a boolean depending on whether the two objects are equal
  */
  @Override
  public boolean equals(Object contact) {
    return (this.hashCode() == contact.hashCode());
  }
}
