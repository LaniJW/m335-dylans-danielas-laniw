package com.example.m335_dylans_danielas_laniw;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainActivityTest {
    final private MainActivity mainActivity = new MainActivity();

    @Test
    public void randomNumberIsRight(){
        assertTrue(mainActivity.getRandomNum(1000) > 0 && mainActivity.getRandomNum(1000) < 1000);
    }
}
