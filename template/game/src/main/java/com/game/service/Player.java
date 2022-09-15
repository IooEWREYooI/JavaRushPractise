package com.game.service;

import com.game.entity.PlayerEntity;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.ArrayList;
import java.util.Date;

public class Player {

    @Override
    public String toString(){
        Player player = this;
        return  "\nid:"+player.getId()+"\n" +
                "name:"+player.name+"\n" +
                "title:"+player.title+"\n" +
                "race:"+player.getRace()+"\n" +
                "profession:"+player.getProfession()+"\n" +
                "birthday:"+player.getBirthday()+"\n" +
                "banned:"+player.getBanned()+"\n" +
                "lvl:"+player.getLevel()+"\n" +
                "exp:"+player.getExperience()+"\n"+
                "untilNextLevel:"+player.getUntilNextLevel()+"\n";
    }

    public static PlayerEntity toEntity (Player player){
        PlayerEntity entity = new PlayerEntity();
        entity.setName(player.getName());
        entity.setBanned(player.getBanned());
        System.out.println("toEntity (Player player)\n entity.setBanned(player.getBanned()) = "+player.getBanned());
        entity.setLevel(player.getLevel());
        entity.setBirthday(player.getBirthday());
        entity.setProfession(player.getProfession().toString());
        entity.setExperience(player.getExperience());
        entity.setTitle(player.getTitle());
        entity.setRace(player.getRace().toString());
        entity.setUntilNextLevel(player.getUntilNextLevel());
        return entity;
    }

    public static Player update (Player playerFromSQL, Player playerFromJson){
        Player player = playerFromSQL;
        if (playerFromJson.getName() != null)
            player.setName(playerFromJson.getName());
        if (playerFromJson.getTitle() != null)
            player.setTitle(playerFromJson.getTitle());
        if (playerFromJson.getRace() != null)
            player.setRace(playerFromJson.getRace().toString());
        if (playerFromJson.getBanned() != null)
            player.setBanned(playerFromJson.getBanned());
        if (playerFromJson.getBirthday() != null)
            player.setBirthday(playerFromJson.getBirthday());
        if (playerFromJson.getExperience() != null)
            player.setExperience(playerFromJson.getExperience());
        if (playerFromJson.getProfession() != null)
            player.setProfession(playerFromJson.getProfession().toString());
        return player;
    }

    public static Player toModel(PlayerEntity entity){
        Player model = new Player();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setTitle(entity.getTitle());
        model.setRace(entity.getRace());
        model.setProfession(entity.getProfession());
        model.setBirthday(entity.getBirthday());
        model.setBanned(entity.getBanned());
        model.setExperience(entity.getExperience());
        model.setLevel(entity.getLevel());
        model.setUntilNextLevel(entity.getUntilNextLevel());
        return model;
    }

    public static Player fromJsonToModel(Long id, PlayerJson json){
        int exp = json.getExperience();
        int lvl = ((int)(Math.sqrt(2500 + 200 * exp - 50))/100);

        Player model = new Player();

        model.setName(json.getName());
        model.setTitle(json.getTitle());
        model.setProfession(json.getProfession().toString());
        model.setBirthday(new Date(json.getBirthday()));
        model.setId(id);
        model.setBanned(json.getBanned());
        model.setExperience(json.getExperience());
        model.setUntilNextLevel(50 * (lvl + 1) * (lvl + 2) - exp);
        model.setLevel(lvl);
        model.setRace(json.getRace().toString());
        return  model;
    }

    public static Player fromJsonToModel(Player player, PlayerJson json){
        Player model = new Player();
        int exp = 0;

        if (json.getExperience() != null) {
            model.setExperience(json.getExperience());
            exp = json.getExperience();
        } else {
            model.setExperience(player.getExperience());
            exp = player.getExperience();
        }

        //int lvl = ((int)(Math.sqrt(2500 + 200 * exp - 50))/100);
        int lvl = ((int)(Math.sqrt(2500 + 200 * exp) - 50)/100);

        if (json.getName() != null)
        model.setName(json.getName());
        else model.setName(player.getName());

        if (json.getTitle() != null)
             model.setTitle(json.getTitle());
        else model.setTitle(player.getTitle());

        if (json.getProfession() != null)
        model.setProfession(json.getProfession().toString());
        else model.setProfession(player.getProfession().toString());

        if (json.getBirthday() != null)
        model.setBirthday(new Date(json.getBirthday()));
        else model.setBirthday(player.getBirthday());

        model.setId(player.getId());

        if (json.getBanned() != null)
        model.setBanned(json.getBanned());
        else model.setBanned(player.getBanned());

        model.setUntilNextLevel(50 * (lvl + 1) * (lvl + 2) - exp);
        model.setLevel(lvl);

        if (json.getRace() != null)
        model.setRace(json.getRace().toString());
        else model.setRace(player.getRace().toString());

        return  model;
    }

    public static Player fromJsonToModel(PlayerJson json){
        int exp = json.getExperience();
        //int lvl = ((int)(Math.sqrt(2500 + 200 * exp - 50))/100);
        int lvl = ((int)(Math.sqrt(2500 + 200 * exp) - 50)/100);

        Player model = new Player();

        model.setName(json.getName());
        model.setTitle(json.getTitle());
        model.setProfession(json.getProfession().toString());
        model.setBirthday(new Date(json.getBirthday()));
        model.setBanned(json.getBanned());
        System.out.println("fromJsonToModel(PlayerJson json)\n model.setBanned(json.getBanned()) = "+json.getBanned());
        model.setExperience(json.getExperience());
        model.setUntilNextLevel(50 * (lvl + 1) * (lvl + 2) - exp);
        model.setLevel(lvl);
        model.setRace(json.getRace().toString());
        return  model;
    }

    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Date birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profession getProfession(){
        return profession;
    }

    public void setProfession(String profession) {
        if (profession.equals("PALADIN"))
            this.profession = Profession.PALADIN;
        else if (profession.equals("WARLOCK"))
            this.profession = Profession.WARLOCK;
        else if (profession.equals("WARRIOR"))
            this.profession = Profession.WARRIOR;
        else if (profession.equals("ROGUE"))
            this.profession = Profession.ROGUE;
        else if (profession.equals("CLERIC"))
            this.profession = Profession.CLERIC;
        else if (profession.equals("NAZGUL"))
            this.profession = Profession.NAZGUL;
        else if (profession.equals("DRUID"))
            this.profession = Profession.DRUID;
        else if (profession.equals("SORCERER"))
            this.profession = Profession.SORCERER;
    }

    public Date getBirthday(){
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Race getRace(){
        return race;
    }

    public void setRace(String race) {
        if (race.equals("HUMAN"))
            this.race = Race.HUMAN;
        else if (race.equals("ELF"))
            this.race = Race.ELF;
        else if (race.equals("ORC"))
            this.race = Race.ORC;
        else if (race.equals("DWARF"))
            this.race = Race.DWARF;
        else if (race.equals("GIANT"))
            this.race = Race.GIANT;
        else if (race.equals("TROLL"))
            this.race = Race.TROLL;
        else if (race.equals("HOBBIT"))
            this.race = Race.HOBBIT;
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
