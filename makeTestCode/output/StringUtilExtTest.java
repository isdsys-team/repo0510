package common.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;


public class StringUtilExtTest {
    static public final String EMPTY = "";

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testIsAscii_ng_1004(){
        
        String param1="ＡＢＣＤＥ";

        boolean rslt = StringUtilExt.isAscii(param1);
            boolean expected = false;
        assertEquals(expected , rslt);
    }

    @Test
    public void testIsAscii_ng_855(){
        
        String param1="";

        try {
            boolean rslt = StringUtilExt.isAscii(param1);{
        catch(IllegalArgumentException e) {
            boolean expected = false;
            assertEquals("パラメータがnullまたは空文字です。", e.getMessage());
        }
    }

    @Test
    public void testIsAscii_ok_525(){
        
        String param1="abcde";

        boolean rslt = StringUtilExt.isAscii(param1);
            boolean expected = true;
        assertEquals(expected , rslt);
    }

    @Test
    public void testCnvSeirekiToWareki_ok_889(){
        Calendar cal = Calendar.getInstance();
cal.set(1868 ,9, 12);;

        Date param1=cal.getTime();

        String rslt = StringUtilExt.cnvSeirekiToWareki(param1);
            String expected = "明治01年10月12日";
        assertEquals(expected , rslt);
    }


}
