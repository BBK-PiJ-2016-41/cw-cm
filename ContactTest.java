import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.*;
public class ContactTest {
  @Test
  public void testsId() {
    //Test with first constructor
    Contact contact = new ContactImpl(1, "Steve");
    assertEquals(1, contact.getId());
    //Test with second constructor
    Contact c2 = new ContactImpl(2, "Alan", "Partridge");
    assertEquals(2, c2.getId());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testBadId() {
    //Tests with invalid ID
    Contact badC = new ContactImpl(0, "Oops I");
    Contact bad2 = new ContactImpl(-1, "Did It Again", "I played with your heart");
  }

}
