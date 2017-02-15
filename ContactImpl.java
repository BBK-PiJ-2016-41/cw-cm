import java.io.Serializable;
/**
* A contact is a person we are making business with or may do in the future.
*
* Contacts have an ID (unique, a non-zero positive integer),
* a name (not necessarily unique), and notes that the user
* may want to save about them.
*/
public class ContactImpl implements Contact, Serializable {
  private final int id;
  private final String name;
  private String notes;
  /*Constructor taking three arguments
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
  /*Constructor taking two arguments
  * @param int id
  * @param String name
  * @throws IllegalArgumentException if id < 1
  * @throws NullPointerException if either string params are null
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
  * Returns the ID of the contact.
  *
  * @return the ID of the contact.
  */
  public int getId() {
    return this.id;
  }
  /**
  * Returns the name of the contact.
  *
  * @return the name of the contact.
  */
  public String getName() {
    return this.name;
  }
  /**
  * Returns our notes about the contact, if any.
  *
  * If we have not written anything about the contact, the empty
  * string is returned.
  *
  * @return a string with notes about the contact, maybe empty.
  */
  public String getNotes() {
    return this.notes;
  }
  /**
  * Add notes about the contact.
  *
  * @param note the notes to be added
  */
  public void addNotes(String note) {
    this.notes = note;
  }
  /**
  * A method to override hashCode and facilitate comparison (maybe use apache library here)
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
    return (this.hashCode() == contact.hashCode()) ? true : false;
  }
}
