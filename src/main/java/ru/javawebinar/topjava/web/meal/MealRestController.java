package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MealService service;

	public Meal save(Meal meal) {
		checkNew(meal);
		int userId = SecurityUtil.authUserId();
		log.info("create {} for user {}", meal, userId);
		return service.save(meal, userId);
	}

	public void update(Meal meal, int id) {
		log.info("update {} with id={}", meal, meal.getId());
		assureIdConsistent(meal, id);
		int userId = SecurityUtil.authUserId();
		service.update(meal, userId);
	}

	// false if meal does not belong to userId
	public void delete(int id) {
		int userId = SecurityUtil.authUserId();
		log.info("delete meal {} for user {}", id, userId);
		service.delete(id, userId);
	}

	// null if meal does not belong to userId
	public Meal get(int id) {
		int userId = SecurityUtil.authUserId();
		log.info("get meal {} for user {}", id, userId);
		return service.get(id, userId);
	}

	public List<Meal> getAll() {
		int userId = SecurityUtil.authUserId();
		log.info("getAll");
		return service.getAll(userId);
	}

}