package com.java8.basics;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Unit Test for Java 8 Optional Test
 */
public class Java8OptionalTest {


    @Test
    public void whenCreateEmptyOptional_thenCorrect() {
        Optional<String> optional = Optional.empty();
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void givenNonNull_WhenCreateOptional_thenCorrect() {
        String str = "Java 8 Optional";
        Optional<String> optional = Optional.of(str);
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isEqualTo("Java 8 Optional");
    }

    @Test(expected = NullPointerException.class)
    public void givenNull_WhenThrowErrorOnCreate_thenCorrect() {
        Optional.of(null);
    }

    @Test
    public void givenNonNull_WhenCreatesNullable_ThenCorrect() {
        String str = "Java8 Optionals";
        Optional<String> optional = Optional.ofNullable(str);
        assertThat("Optional[Java8 Optionals]").isEqualTo(optional.toString());
    }

    @Test
    public void givenNull_WhenCreatesNullable_ThenCorrect() {
        Optional<String> emptyOptional = Optional.ofNullable(null);
        assertThat(emptyOptional).isEqualTo(Optional.empty());
        assertThat(emptyOptional.toString()).isEqualTo("Optional.empty");
    }

    @Test
    public void givenNonNUll_WhenIsPresent_ThenCorrect() {
        String str = "Jain";
        assertThat(Optional.of(str).isPresent()).isTrue();

        // Given null and When OfNullable then optional is not present
        assertThat(Optional.ofNullable(null).isPresent()).isFalse();
    }

    @Test
    public void givenOptional_WhenIFPresentWorks_ThenCorrect() {
        Optional<String> optional = Optional.of("Hello World");
        optional.ifPresent(name -> System.out.println(name));
    }

    @Test
    public void whenOrElseWorks_ThenCorrect() {
        String nullName = null;
        String name = Optional.ofNullable(nullName).orElse("OrElse with Optional.ofNullable()");

        assertThat(name).isEqualTo("OrElse with Optional.ofNullable()");
    }

    /**
     * orElseGet allows you to pass in a supplier function and perform more activities
     */
    @Test
    public void whenOrElseGetWorks_thenCorrect() {
        String nullString = null;
        assertThat(Optional.ofNullable(nullString).orElseGet(() -> "Neeraj")).isEqualTo("Neeraj");
    }

    @Test
    public void whenOrElseAndOrElseGetOverlap_thenCorrect() {
        String text = null;

        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertThat(defaultText).isEqualTo("Default Value");

        System.out.println("Using orElse");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertThat("Default Value").isEqualTo(defaultText);
    }

    @Test
    public void whenOrElseAndOrElseGetDiffer_thenCorrect() {
        String text = "something";

        System.out.println("Using orElseGet:");
        String defaultText = Optional.ofNullable(text).orElseGet(this::getMyDefault);
        assertThat(defaultText).isEqualTo("something");

        System.out.println("Using orElse");
        defaultText = Optional.ofNullable(text).orElse(getMyDefault());
        assertThat("something").isEqualTo(defaultText);
    }

    @Test(expected = IllegalStateException.class)
    public void whenOrElseThrowWorks_thenCorrect() {
        String text = null;
        Optional.ofNullable(text).orElseThrow(IllegalStateException::new);
    }

    @Test(expected = NoSuchElementException.class)
    public void givenOptionalWithNull_whenGetThrowsException_thenCorrect() {
        Optional.ofNullable(null).get();
        System.out.println("This line will not be executed as get of Optional class throw exception when encounter null");
    }

    /**
     * 10. Conditional Return with filter()
     * The filter API is used to run an inline test on the wrapped value. It takes a predicate as an argument and returns an Optional object.
     * If the wrapped value passes testing by the predicate, then the Optional is returned as is.
     * <p>
     * However, if the predicate returns false, then an empty Optional is returned:
     */
    @Test
    public void whenOptionalFilterWorks_thenCorrect() {
        Integer year = 2018;

        assertThat(Optional.of(year).filter((y) -> y == 2018).isPresent()).isTrue();
        assertThat(Optional.of(year).filter((y) -> y == 2019).isPresent()).isFalse();
    }

    @Test
    public void whenOptionalFilterWorksWithModel_thenCorrect() {
        assertThat(modemPriceIsInRange(new Modem(10.0))).isTrue();
        assertThat(modemPriceIsInRange(new Modem(9.9))).isFalse();
        assertThat(modemPriceIsInRange(new Modem(null))).isFalse();
        assertThat(modemPriceIsInRange(new Modem(15.5))).isFalse();
        assertThat(modemPriceIsInRange(null)).isFalse();
    }

    /**
     * 11. Transforming Value with map()
     */
    @Test
    public void givenOptional_whenMapWorks_thenCorrect() {
        String password = " password ";
        Optional<String> passwordOptional = Optional.of(password);
        assertThat(passwordOptional.filter(p -> p.equals("password")).isPresent()).isFalse();
        assertThat(passwordOptional.map(String::trim).filter(p -> p.equals("password")).isPresent()).isTrue();
    }


    public boolean modemPriceIsInRange(Modem modem) {
        return Optional.ofNullable(modem).
                map(Modem::getPrice).
                filter(price -> price >= 10).
                filter(price -> price <= 15).
                isPresent();
    }

    public String getMyDefault() {
        System.out.println("Getting Default Value");
        return "Default Value";
    }
}

class Modem {
    private Double price;

    public Modem(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
