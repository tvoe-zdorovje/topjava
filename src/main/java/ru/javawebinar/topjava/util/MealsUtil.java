package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;

public class MealsUtil {
    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static final int CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {}

    // FIXME evaluate the complexity of this algorithm. Reduce to O(n)
    public static List<MealTo> mealToList(Collection<Meal> meals, int caloriesPerDay) {
        return meals.stream()
                .map(meal -> createTo(meal, filteredByStreams(meals).get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    // FIXME why the Meal is sorted and the MealTo is returned?
    //  Why the Collection is sorted and the List is returned?
    //  Why does the sort modify the data?
    public static List<MealTo> sorted(Collection<Meal> meals, int caloriesPerDay) {
        List<MealTo> list = mealToList(meals, caloriesPerDay);
        list.sort(Comparator.comparing(MealTo::getDateTime).reversed());
        return list;
    }

    // FIXME This method collects, but doesn't filter
    public static Map<LocalDate, Integer> filteredByStreams(Collection<Meal> meals) {
        return meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
