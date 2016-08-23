package com.pudding.financeandroid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

        String aaa = "55.00";
        Double aa = Double.valueOf(aaa);
        String bbb = String.valueOf(aaa);
        if(aa > 1) {
            String b = bbb.substring(0, bbb.indexOf("."));
            System.out.print("最终的结果" + Integer.parseInt(b));
        }else {
            String b = bbb.substring(bbb.indexOf(".") + 1, bbb.length());
            System.out.print("最终的结果" + Integer.parseInt(b));
        }

    }
}