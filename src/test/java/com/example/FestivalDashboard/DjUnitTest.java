package com.example.FestivalDashboard;

import com.example.FestivalDashboard.domain.DJ;
import com.example.FestivalDashboard.domain.Performance;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

class DjUnitTest {

    @Test
    void testDJConstructorAndGetters() {
        // Given
        DJ dj = new DJ("Martin Garrix", "EDM", 27);

        // Then
        assertThat(dj.getName()).isEqualTo("Martin Garrix");
        assertThat(dj.getGenre()).isEqualTo("EDM");
        assertThat(dj.getAge()).isEqualTo(27);
    }

    @Test
    void testSetters() {
        // Given
        DJ dj = new DJ();

        // When
        dj.setName("Armin van Buuren");
        dj.setGenre("Trance");
        dj.setAge(44);

        // Then
        assertThat(dj.getName()).isEqualTo("Armin van Buuren");
        assertThat(dj.getGenre()).isEqualTo("Trance");
        assertThat(dj.getAge()).isEqualTo(44);
    }


    @Test
    void testNoArgsConstructor() {
        // Given
        DJ dj = new DJ();

        // Then
        assertThat(dj.getName()).isNull();
        assertThat(dj.getGenre()).isNull();
        assertThat(dj.getAge()).isEqualTo(0);
        assertThat(dj.getPerformances()).isEmpty();
    }

}
