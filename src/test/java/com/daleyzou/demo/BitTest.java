package com.daleyzou.demo;

import org.junit.jupiter.api.Test;

/**
 * BitTest
 * @description TODO
 * @author daleyozu
 * @date 2020年09月10日 9:22
 * @version 1.3.1
 */
public class BitTest  extends LearnDemoApplicationTests {

    @Test
    public void testBit(){
        System.out.println("1<<12: " + (1<<12));
        System.out.println("1<<1: " + (1<<1));
    }

}
