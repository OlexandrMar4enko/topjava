package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.getUpdated;

@ContextConfiguration({
		"classpath:spring/spring-app.xml",
		"classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

	static {
		// Only for postgres driver logging
		// It uses java.util.logging and logged via jul-to-slf4j bridge
		SLF4JBridgeHandler.install();
	}

	@Autowired
	private MealService mealService;

	@Test
	public void get() {
		Meal meal = mealService.get(MEAL1_ID, SecurityUtil.authUserId());
		assertMatch(meal, meal1);
	}

	@Test
	public void getNotFound() {
		assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, NOT_FOUND));
	}

	@Test
	public void delete() {
		mealService.delete(MEAL1_ID, SecurityUtil.authUserId());
		assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, SecurityUtil.authUserId()));
	}

	@Test
	public void deleteNotFound() {
		assertThrows(NotFoundException.class, () -> mealService.delete(MEAL1_ID, NOT_FOUND));
	}

	@Test
	public void getBetweenInclusive() {
		List<Meal> betweenInclusive = mealService.getBetweenInclusive(LocalDate.of(2023, 8, 25),
				LocalDate.of(2023, 8, 27), SecurityUtil.authUserId());
		assertMatch(betweenInclusive, meal3, meal1);
	}

	@Test
	public void getAll() {
		List<Meal> meals = mealService.getAll(SecurityUtil.authUserId());
		assertMatch(meals, meal1, meal2, meal3);
	}

	@Test
	public void update() {
		Meal updated = getUpdated();
		mealService.update(updated, SecurityUtil.authUserId());
		assertMatch(mealService.get(MEAL1_ID, SecurityUtil.authUserId()), getUpdated());
	}

/*	@Test
	public void updateNotFound() {
		Meal updated = getUpdated();
		assertThrows(NotFoundException.class, () -> mealService.update(updated, NOT_FOUND));
	}*/

	@Test
	public void create() {
		Meal createdMeal = mealService.create(getNew(), SecurityUtil.authUserId());
		Integer newId = createdMeal.getId();
		Meal newMeal = getNew();
		newMeal.setId(newId);
		assertMatch(createdMeal, newMeal);
	}

	@Test
	public void duplicateDateTimeCreate() {
		assertThrows(DataAccessException.class, () ->
		{
			Meal duplicateMeal = new Meal(null, LocalDateTime.of(2023, 8, 26, 12, 0), "DuplicateMeal", 3000);
			mealService.create(duplicateMeal, SecurityUtil.authUserId());
		});
	}
}