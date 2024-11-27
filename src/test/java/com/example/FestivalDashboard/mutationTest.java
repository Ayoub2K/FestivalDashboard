package com.example.FestivalDashboard;

import com.example.FestivalDashboard.domain.Palindrome;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class mutationTest {

    @Test
    public void whenPalindrom_thenAccept() {
        Palindrome palindromeTester = new Palindrome();
        assertTrue(palindromeTester.isPalindrome("noon"));
    }

}

