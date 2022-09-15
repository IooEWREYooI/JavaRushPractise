package com.game.service;

import com.game.entity.PlayerEntity;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepo;
import javassist.NotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.game.service.Player.toEntity;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepo repo;

    public ArrayList<Player> getAllByRaceIsAndProfessionIsAndBirthdayBetween(String race, String profession, Date date1, Date date2){
        ArrayList<Player> list = new ArrayList<>();
        repo.findAllByRaceIsAndProfessionIsAndBirthdayBetween(race, profession, date1, date2).forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public ArrayList<Player> getAllByRaceIsAndProfessionIsAndExperienceBetween(String race, String profession, Integer exp1, Integer exp2){
        ArrayList<Player> list = new ArrayList<>();
        repo.findAllByRaceIsAndProfessionIsAndExperienceBetween(race, profession, exp1, exp2).forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public ArrayList<Player> getAllByBirthdayIsBetweenAndExperienceIsBetween(Date date1, Date date2, Integer exp1, Integer exp2){
        ArrayList<Player> list = new ArrayList<>();
        repo.findAllByBirthdayIsBetweenAndExperienceIsBetween(date1, date2, exp1, exp2).forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public ArrayList<Player> getAllByBannedIsFalseAndLevelIsBefore(Integer lvl){
        ArrayList<Player> list = new ArrayList<>();
        repo.findAllByBannedIsFalseAndLevelIsBefore(lvl).forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public ArrayList<Player> getAllPlayersWhereNameContains(String word){
        ArrayList<Player> list = new ArrayList<>();
        repo.findDistinctByNameContains(word).forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public ArrayList<Player> getAllPlayersWhereTitleContains(String word){
        ArrayList<Player> list = new ArrayList<>();
        repo.findAllByTitleContains(word).forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public Player getPlayerOne(Long id){
        PlayerEntity entity = repo.findById(id).get();
        return Player.toModel(entity);
    }

    public ArrayList<Player> getAll(){
        ArrayList<Player> list = new ArrayList<Player>();
        repo.findAll().forEach(x -> list.add(Player.toModel(x)));
        return list;
    }

    public void deletePlayerOne(Long id){
        repo.delete(repo.findById(id).get());
    }

    public Long PlayerCount(){
        return repo.count();
    }

    public PlayerEntity add (Player player){
        PlayerEntity entity = toEntity(player);
        repo.save(entity);
        System.out.println("\nrepo.save(toEntity(player))\n"+entity.toString()+"\n-> Добавлен");
        return entity;
    }

    public void add (Long id, Player player){
        player.setId(id);
        PlayerEntity entity = toEntity(player);
        entity.setId(id);
        repo.save(entity);
        System.out.println("\nadd (Long id, Player player) player.setId(id)\n"+player.toString()+"" +
                            "\nPlayerEntity entity = toEntity(player) entity.setId(id) = "+entity.getId()+"\nentity.toString() - \n"+entity.toString());
    }

    public void add (Long id,Integer lvl, Integer untilNextLevel, Player player){
        player.setId(id);
        PlayerEntity entity = toEntity(player);
        entity.setId(id);
        entity.setUntilNextLevel(untilNextLevel);
        entity.setLevel(lvl);
        repo.save(entity);
        System.out.println("\nadd (Long id, Player player) player.setId(id)\n"+player.toString()+"" +
                "\nPlayerEntity entity = toEntity(player) entity.setId(id) = "+entity.getId()+"\nentity.toString() - \n"+entity.toString());
    }

    public Integer getCountByName(String name){
        return repo.countByName(name);
    }

    public Integer getCountByBannedIsFalse(){
        return repo.countByBannedIsFalse();
    }

    public Integer getCountByTitleContains(String word){
        return repo.countByTitleContains(word);
    }

    public Integer getCountByMinLvLIsAndMinExpIs(Integer exp, Integer lvl){
        return repo.countByExperienceIsAfterAndLevelIsAfter(exp,lvl);
    }

    public Integer getCountByRaceIsAndProfessionIsAndBannedIsTrue(String race, String profession){
        return repo.countByRaceIsAndProfessionIsAndBannedIsTrue(race, profession);
    }

    public Integer getCountByRaceIsAndProfessionIsAndBirthdayIsBefore(String race, String profession, Date date){
        return repo.countByRaceIsAndProfessionIsAndBirthdayIsBefore(race, profession, date);
    }

    public Integer getCountByNameContainsAndBirthdayIsAfterAndLevelIsBefore(String word, Date date, Integer lvl){
        return repo.countByNameContainsAndBirthdayIsAfterAndLevelIsBefore(word, date, lvl);
    }

    public Integer getCountByRaceIsAndProfessionIsAndExperienceIsBefore(String race, String profession, Integer exp){
        return repo.countByRaceIsAndProfessionIsAndExperienceIsBefore(race, profession, exp);
    }
}
