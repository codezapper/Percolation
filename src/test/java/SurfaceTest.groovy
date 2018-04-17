import org.junit.Test;
import static org.junit.Assert.assertEquals;

class SurfaceTest extends GroovyTestCase {
    void testGetAdjacents() {
    }

    void testOpen() {
    }

    @Test
    void testGetIdFromCoords() {
        Surface surface = new Surface();

        int id = surface.getIdFromCoords(2, 2);
        assertEquals(22, id);
        id = surface.getIdFromCoords(2, 5);
        assertEquals(52, id);
    }

    @Test
    void testGetCoordsFromId() {
        Surface surface = new Surface();

        int[] coords = surface.getCoordsFromId(52);
        assertEquals(2, coords[0]);
        assertEquals(5, coords[1]);
        coords = surface.getCoordsFromId(Site.VIRTUAL_TOP);
        assertEquals(0, coords[0]);
        assertEquals(10, coords[1]);
        coords = surface.getCoordsFromId(Site.VIRTUAL_BOTTOM);
        assertEquals(1, coords[0]);
        assertEquals(10, coords[1]);
    }

    @Test
    void testFindRoot() {
        Surface surface = new Surface();

        surface.open(5, 5);
        assertEquals(surface.getIdFromCoords(5, 5), surface.findRoot(surface.getIdFromCoords(5, 5)))
        surface.open(6, 5);
        assertEquals(surface.findRoot(surface.getIdFromCoords(6, 5)), surface.findRoot(surface.getIdFromCoords(5, 5)))
    }
}
