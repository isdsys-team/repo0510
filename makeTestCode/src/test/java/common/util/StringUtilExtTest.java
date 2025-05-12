package common.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class StringUtilExtTest {
    static public final String EMPTY = "";

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testIsAscii_ng_463(){
        
        String param1="ＡＢＣＤＥ";

        boolean rslt = StringUtilExt.isAscii(param1);
            boolean expected = false;
        assertEquals(expected , rslt);
    }

    @Test
    public void testIsAscii_ng_344(){
        
        String param1="";

        try {
            boolean rslt = StringUtilExt.isAscii(param1);
        }
        catch(IllegalArgumentException e) {
            boolean expected = false;
            assertEquals("パラメータがnullまたは空文字です。", e.getMessage());
        }
    }

    @Test
    public void testIsAscii_ok_800(){
        
        String param1="abcde";

        boolean rslt = StringUtilExt.isAscii(param1);
            boolean expected = true;
        assertEquals(expected , rslt);
    }

    @Test
    public void testCnvSeirekiToWareki_ok_152(){
        Calendar cal = Calendar.getInstance();
cal.set(1868 ,9, 12);;

        Date param1=cal.getTime();

        String rslt = StringUtilExt.cnvSeirekiToWareki(param1);
            String expected = "明治01年10月12日";
        assertEquals(expected , rslt);
    }


}
