package t0l1k.ttt;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    @Test
    public void testInitBoard() {
        Board b = new Board();
        assertEquals("         ", b.toString());
        assertEquals('X', b.getTurn());
    }

    @Test
    public void testMove() {
        Board b = new Board().move(1);
        assertEquals(" X       ", b.toString());
        assertEquals('O', b.getTurn());
    }

    @Test
    public void testPossibleMoves() {
        Board b = new Board().move(1).move(3).move(4);
        assertArrayEquals(new Integer[]{0, 2, 5, 6, 7, 8}, b.possibleMoves());
    }

    @Test
    public void testWin() {
        assertFalse(new Board().win('X'));
        assertTrue(new Board("XXX      ").win('X'));
        assertTrue(new Board("   OOO   ").win('O'));
        assertTrue(new Board("X  X  X  ").win('X'));
        assertTrue(new Board("  X X X  ").win('X'));
        assertTrue(new Board("X   X   X").win('X'));
    }

}