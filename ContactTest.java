import org.junit.*;
import static org.junit.Assert.*;
public class ContactTest {
  @Test
  public void testsId() {
    Contact contact = new ContactImpl(1, "Steve");
    assertEquals(1, contact.getId());
  }
}
