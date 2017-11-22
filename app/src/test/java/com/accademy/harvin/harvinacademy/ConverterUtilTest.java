package com.accademy.harvin.harvinacademy;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by ishank on 20/11/17.
 */

public class ConverterUtilTest {

    @Test
    public void testConvertF2C(){
        float actual=ConverterUtil.convertC2F(100);
        float expected=100;
        assertEquals(actual,20,20);
    }


}
