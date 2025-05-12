package common.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MathUtilExtTest {
    static public final String EMPTY = "";

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testRoundHalfUp_ok_908(){
        MathUtilExt obj = new MathUtilExt();
        
        double param1=1.56;
        int param2=2;

        double rslt = obj.roundHalfUp(param1,param2);
        double expected = 1.6;
        assertEquals(expected , rslt, 0.0);
    }


}
