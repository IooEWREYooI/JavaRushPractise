package com.game.entity;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.Player;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class PlayerEntity {

    public PlayerEntity(){}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    private String race;
    private String profession;
    private Date birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;

    public void setId (Long id){
        this.id = id;
    }

    @Override
    public String toString(){
        PlayerEntity player = this;
        return  "id:"+player.getId()+"\n" +
                "name:"+player.name+"\n" +
                "title:"+player.title+"\n" +
                "race:"+player.getRace()+"\n" +
                "profession:"+player.getProfession()+"\n" +
                "birthday:"+player.getBirthday()+"\n" +
                "banned:"+player.getBanned()+"\n" +
                "lvl:"+player.getLevel()+"\n" +
                "exp:"+player.getExperience()+"\n" +
                "untilNextLevel:"+player.getUntilNextLevel();
    }

    public Long getId(){
        return id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Date getBirthday(){
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRace(){
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

}
