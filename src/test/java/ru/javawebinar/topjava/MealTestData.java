package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

	public static final int MEAL1_ID = START_SEQ + 3;
	public static final int MEAL2_ID = START_SEQ + 4;
	public static final int MEAL3_ID = START_SEQ + 5;
	public static final int NOT_FOUND = 10;

	public static final Meal meal1 = new Meal(MEAL1_ID, LocalDateTime.of(2023, 8, 26, 12, 0), "Meal1", 1000);
	public static final Meal meal2 = new Meal(MEAL2_ID, LocalDateTime.of(2023, 8, 26, 12, 1), "Meal2", 2000);
	public static final Meal meal3 = new Meal(MEAL3_ID, LocalDateTime.of(2023, 8, 26, 12, 5), "Meal3", 500);

	public static Meal getNew() {
		return new Meal(null, LocalDateTime.of(2023, 7, 26, 12, 0), "NewMeal", 1000);
	}

	public static Meal getUpdated() {
		Meal updated = new Meal(meal1);
		updated.setDateTime(LocalDateTime.of(2022, 8, 26, 12, 0));
		updated.setDescription("updatedMeal");
		updated.setCalories(2000);
		return updated;
	}

	public static void assertMatch(Meal actual, Meal expected) {
		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}

	public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
		assertMatch(actual, Arrays.asList(expected));
	}

	public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
		assertThat(actual).isEqualTo(expected);
	}

}
