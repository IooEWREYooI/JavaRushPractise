package com.game.repository;

import com.game.entity.PlayerEntity;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface PlayerRepo extends CrudRepository<PlayerEntity, Long> {
    Integer countByName(String name);
    Integer countByBannedIsFalse();
    Integer countByTitleContains(String word);
    Integer countByExperienceIsAfterAndLevelIsAfter(Integer exp, Integer lvl);
    Integer countByRaceIsAndProfessionIsAndBannedIsTrue(String race, String profession);
    Integer countByRaceIsAndProfessionIsAndExperienceIsBefore(String race, String profession, Integer exp);
    Integer countByRaceIsAndProfessionIsAndBirthdayIsBefore(String race, String profession, Date date);
    Integer countByNameContainsAndBirthdayIsAfterAndLevelIsBefore(String word, Date date, Integer lvl);
    Iterable<PlayerEntity> findDistinctByNameContains(String name);
    Iterable<PlayerEntity> findAllByTitleContains(String title);
    Iterable<PlayerEntity> findAllByBannedIsFalseAndLevelIsBefore(Integer lvl);
    Iterable<PlayerEntity> findAllByBirthdayIsBetweenAndExperienceIsBetween(Date date1, Date date2, Integer exp1, Integer exp2);
    Iterable<PlayerEntity> findAllByRaceIsAndProfessionIsAndExperienceBetween(String race, String profession, Integer exp1, Integer exp2);
    Iterable<PlayerEntity> findAllByRaceIsAndProfessionIsAndBirthdayBetween(String race, String profession, Date date1, Date date2);
}
