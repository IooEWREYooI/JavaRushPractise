package com.game.service;

import com.game.entity.Profession;
import com.game.entity.Race;

public class PlayerJson {

    @Override
    public String toString(){
        PlayerJson json = this;
        return  "“name”:"+json.getName()+"\n" +
                "“title”:"+json.getTitle()+"\n" +
                "“race”:"+json.getRace()+"\n" +
                "“profession”:"+json.getProfession()+"\n" +
                "“birthday”:"+json.getBirthday()+"\n" +
                "“banned”:"+json.getBanned()+"\n" +
                "“experience”:"+json.getExperience()+"\n";
    }
    /*
        {
         “name”:[String], --optional
         “title”:[String], --optional
         “race”:[Race], --optional
         “profession”:[Profession], --optional
         “birthday”:[Long], --optional
         “banned”:[Boolean], --optional
         “experience”:[Integer] --optional
        }
         */
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long birthday;
    private Boolean banned;
    private Integer experience;

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

    public void setRace(Race race) {
        this.race = race;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Integer getExperience() {
        return experience;
    }

    public Long getBirthday() {
        return birthday;
    }

    public Profession getProfession() {
        return profession;
    }

    public Race getRace() {
        return race;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
