package project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Event ev = new Event("Concert", "testEvent", "7am", "8am", "Spektrum");
        assertTrue( true );
    }

    @Test
    public void shouldAnswerWithTrue2()
    {
        assertTrue( true );
    }
}
