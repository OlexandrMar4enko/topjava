package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(em.getReference(User.class, userId));
            em.persist(meal);
            return meal;
        } else {
            Meal existedMeal = em.getReference(Meal.class, meal.getId());
            User user = em.getReference(User.class, userId);  // need to find user belonged to meal
            if (existedMeal != null && existedMeal.getUser().getId() == userId) {
                existedMeal.setDescription(meal.getDescription());
                existedMeal.setCalories(meal.getCalories());
                existedMeal.setDateTime(meal.getDateTime());
                existedMeal.setUser(user);
                em.merge(existedMeal);
                meal = existedMeal;
            } else {
                return null;
            }
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.getReference(User.class, userId);
        Query query = em.createQuery("DELETE from Meal m where m.id =: id and m.user =: user");
        query.setParameter("id", id);
        query.setParameter("user", user);
        return query.executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User user = em.getReference(User.class, userId);
        TypedQuery<Meal> query = em.createQuery("select m from Meal m where m.id =: id and m.user =: user", Meal.class);
        query.setParameter("id", id);
        query.setParameter("user", user);
        List<Meal> meals = query.getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
/*        User user = em.getReference(User.class, userId);
        TypedQuery<Meal> query = em.createQuery("select m from Meal m where m.user =: user order by m.dateTime desc", Meal.class);
        query.setParameter("user", user);
        return query.getResultList(); */

        // Named query
        return em.createNamedQuery(Meal.ALL)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
/*        User user = em.getReference(User.class, userId);
        TypedQuery<Meal> query = em.createQuery("select m from Meal m where m.user =: user and m.dateTime >=: " +
                "startDateTime and m.dateTime <: endDateTime order by m.dateTime desc ", Meal.class);
        query.setParameter("user", user);
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);
        return query.getResultList();*/

        return em.createNamedQuery(Meal.ALL_BETWEEN_HALF_OPEN)
                .setParameter("userId", userId)
                .setParameter("startDate", startDateTime)
                .setParameter("endDate", endDateTime)
                .getResultList();

    }
}