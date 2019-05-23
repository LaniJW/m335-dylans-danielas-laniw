package com.example.m335_dylans_danielas_laniw;

import com.example.m335_dylans_danielas_laniw.persistence.Comic;

import org.junit.Assert;
import org.junit.Test;

import okhttp3.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MainActvityTest {
    MainActivity mainActivity = new MainActivity();

    @Test
    public void randomNumberIsRight(){
        assertTrue(mainActivity.getRandomNum(1000) > 0 && mainActivity.getRandomNum(1000) < 1000);
    }
}
