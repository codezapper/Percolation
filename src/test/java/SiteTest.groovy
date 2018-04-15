import org.junit.Test;
import static org.junit.Assert.assertEquals;

class SiteTest {
    @Test
    void testSetId() {
        Site site = new Site(-1, 1, 1, 30, 30);
        assertEquals(false, site.isOpen());
        site.setId(1);
        assertEquals(true, site.isOpen());
    }

    void testGetRoot() {
    }

    void testIsOpen() {
    }
}
