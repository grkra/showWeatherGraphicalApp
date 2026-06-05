package krawczyk.grzegorz.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    void setNameShouldChangeFirstLetterOfEveryWordToCapital() {
        //  given
        Location location = new Location();

        // when
        location.setName("three words cityname");

        // then
        assertThat(location.getName()).isEqualTo("Three Words Cityname");
    }

}