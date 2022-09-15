package com.game.controller;

import com.game.entity.PlayerEntity;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.Player;
import com.game.service.PlayerJson;
import com.game.service.PlayerService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.game.service.Player.toEntity;
import static java.util.stream.Collectors.toList;

@RestController
public class MainController {
    @Autowired
    private PlayerService playerService = new PlayerService();

    @Autowired
    public MainController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Get players list OK
    @GetMapping (value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Player> getPlayers(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                        @RequestParam(name = "order", required = false) PlayerOrder order,
                                        @RequestParam(name = "name", required = false) String name,
                                        @RequestParam(name = "title" , required = false) String title,
                                        @RequestParam(name = "race", required = false) Race race,
                                        @RequestParam(name = "profession", required = false) Profession profession,
                                        @RequestParam(name = "after", required = false) Long after,
                                        @RequestParam(name = "before", required = false) Long before,
                                        @RequestParam(name = "banned", required = false) Boolean banned,
                                        @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                        @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                        @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                        @RequestParam(name = "maxLevel", required = false) Integer maxLevel){

        System.out.println("\nПоступил запрос rest/players с атрибутами: " +"\npageNumber = "+pageNumber +"\npageSize = "+pageSize +"\norder = "+order +"\nname = "+name +"\ntitle = "+title +"\nrace = "+race +"\nprofession = "+profession +"\nafter = "+after +"\nbefore = "+before +"\nbanned = "+banned +"\nminExp = "+minExperience +"\nmaxExp = "+maxExperience +"\nminLvl = "+minLevel +"\nmaxLvl = "+maxLevel);

        ArrayList<Player> playerList = playerService.getAll();
        ArrayList<Player> refPlayerList = new ArrayList<>();

        if (pageNumber == null && pageSize == null && race != null && profession != null && after != null && before != null){
            playerList = playerService.getAllByRaceIsAndProfessionIsAndBirthdayBetween(race.toString(), profession.toString(), new Date(after), new Date(before));
            for (int i = 0; i < 3; i++) {
                if (i + (0 * 3) < playerList.size())
                    refPlayerList.add(playerList.get(i + (0 * 3)));
                else break;
            }
            List<Player> list = refPlayerList.stream().sorted((x,y) -> (int) (x.getId() - y.getId())).collect(toList());
            refPlayerList.clear();
            list.forEach(x -> refPlayerList.add(x));
        }

        else if (pageNumber == null && pageSize == null && race != null && profession != null && minExperience != null && maxExperience != null){
            playerList = playerService.getAllByRaceIsAndProfessionIsAndExperienceBetween(race.toString(), profession.toString(), minExperience, maxExperience);
            for (int i = 0; i < 3; i++) {
                if (i + (0 * 3) < playerList.size())
                    refPlayerList.add(playerList.get(i + (0 * 3)));
                else break;
            }
            List<Player> list = refPlayerList.stream().sorted((x,y) -> (int) (x.getId() - y.getId())).collect(toList());
            refPlayerList.clear();
            list.forEach(x -> refPlayerList.add(x));
        }

        else if (pageNumber != null && pageSize == null && after != null && before != null & minExperience != null && maxExperience != null){
            playerList = playerService.getAllByBirthdayIsBetweenAndExperienceIsBetween(new Date(after), new Date(before), minExperience, maxExperience);
            for (int i = 0; i < 3; i++) {
                if (i + (pageNumber * 3) < playerList.size())
                    refPlayerList.add(playerList.get(i + (pageNumber * 3)));
                else break;
            }
            List<Player> list = refPlayerList.stream().sorted((x,y) -> (int) (x.getId() - y.getId())).collect(toList());
            refPlayerList.clear();
            list.forEach(x -> refPlayerList.add(x));
        }

        else if (pageNumber == null && pageSize == null && banned != null && maxLevel != null){
            playerList = playerService.getAllByBannedIsFalseAndLevelIsBefore(maxLevel);
            for (int i = 0; i < 3; i++) {
                if (i < playerList.size())
                    refPlayerList.add(playerList.get(i));
                else break;
            }
            List<Player> list = refPlayerList.stream().sorted((x,y) -> (int) (x.getId() - y.getId())).collect(toList());
            refPlayerList.clear();
            list.forEach(x -> refPlayerList.add(x));
        }

        else if (pageNumber != null && name != null && pageSize == null){
            playerList = playerService.getAllPlayersWhereNameContains(name);
            for (int i = 0; i < 3; i++) {
                if (i + (pageNumber * 3) < playerList.size())
                    refPlayerList.add(playerList.get(i + (pageNumber * 3)));
                else break;
            }
            List<Player> list = refPlayerList.stream().sorted((x,y) -> (int) (x.getId() - y.getId())).collect(toList());
            refPlayerList.clear();
            list.forEach(x -> refPlayerList.add(x));
        }

        else if (pageSize != null && title != null && pageNumber == null){
            playerList = playerService.getAllPlayersWhereTitleContains(title);
            for (int i = 0; i < pageSize; i++) {
                if (i < playerList.size())
                    refPlayerList.add(playerList.get(i));
                else break;
            }
            List<Player> list = refPlayerList.stream().sorted((x,y) -> (int) (x.getId() - y.getId())).collect(toList());
            refPlayerList.clear();
            list.forEach(x -> refPlayerList.add(x));
        }

        //Количество элементов на странице дефолт
        else if (pageSize == null && pageNumber == null){
            for (int i = 0; i < 3; i++) {
                refPlayerList.add(playerList.get(i));
            }
        }
        //Количество элементов на странице не дефолт
        else if (pageSize != null && pageNumber != null) {
            refPlayerList.clear();
            for (int i = 0; i < pageSize; i++) {
                if (i+(pageNumber*pageSize) < playerList.size())
                refPlayerList.add(playerList.get(i+(pageNumber*pageSize)));
                else break;
            }
        }

        System.out.println("\nОтправлен лист -> \n"+refPlayerList.toString());
        return refPlayerList;
    }

    // Get players count OK
    @GetMapping (value = "/rest/players/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getPlayersCount(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                   @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                   @RequestParam(name = "order", required = false) PlayerOrder order,
                                   @RequestParam(name = "name", required = false) String name,
                                   @RequestParam(name = "title" , required = false) String title,
                                   @RequestParam(name = "race", required = false) Race race,
                                   @RequestParam(name = "profession", required = false) Profession profession,
                                   @RequestParam(name = "after", required = false) Long after,
                                   @RequestParam(name = "before", required = false) Long before,
                                   @RequestParam(name = "banned", required = false) Boolean banned,
                                   @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                   @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                   @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                   @RequestParam(name = "maxLevel", required = false) Integer maxLevel) {

        System.out.println(
                 "\nПоступил запрос /rest/players/count с атрибутами: "
                +"\npageNumber = "+pageNumber
                +"\npageSize = "+pageSize
                +"\norder = "+order
                +"\nname = "+name
                +"\ntitle = "+title
                +"\nrace = "+race
                +"\nprofession = "+profession
                +"\nafter = "+after
                +"\nbefore = "+before
                +"\nbanned = "+banned
                +"\nminExp = "+minExperience
                +"\nmaxExp = "+maxExperience
                +"\nminLvl = "+minLevel
                +"\nmaxLvl = "+maxLevel);

        if (name != null && maxLevel != null && after != null)
            return  playerService.getCountByNameContainsAndBirthdayIsAfterAndLevelIsBefore(name, new Date(after), maxLevel);

        if (race != null && profession != null && before != null)
            return playerService.getCountByRaceIsAndProfessionIsAndBirthdayIsBefore(race.toString(), profession.toString(), new Date(before));

        if (race != null && profession != null && banned != null && banned == true)
            return playerService.getCountByRaceIsAndProfessionIsAndBannedIsTrue(race.toString(), profession.toString());

        if (race != null && profession != null && maxExperience != null)
            return playerService.getCountByRaceIsAndProfessionIsAndExperienceIsBefore(race.toString(), profession.toString(), maxExperience);

        if (minExperience != null && minExperience != null)
            return  playerService.getCountByMinLvLIsAndMinExpIs(minExperience,minLevel);

        if (name != null)
            return playerService.getCountByName(name);

        if (banned != null && banned == false)
            return playerService.getCountByBannedIsFalse();

        if (title != null)
            return playerService.getCountByTitleContains(title);

        return playerService.PlayerCount().intValue();
    }

    // Get player OK
    @RequestMapping (value = "/rest/players/{id}",method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPlayerOneById(@PathVariable Long id) {
        if(id > 0) {
            if (id instanceof Long) {
                try {
                    Player player = playerService.getPlayerOne(id);
                    return ResponseEntity.ok(playerService.getPlayerOne(id));
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);//404
                }
            }
        }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
    }

    // Delete player OK
    @RequestMapping(value = "/rest/players/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePlayerOneById(@PathVariable Long id){
        if(id > 0) {
            if (id instanceof Long) {
                try {
                    playerService.deletePlayerOne(id);
                    System.out.println("Пользователь с id - " + id + " удален");
                    return ResponseEntity.ok("Пользователь с id - " + id + " удален");
                } catch (NoSuchElementException e) {
                    System.out.println("Пользователь с id - " + id + " не найден");
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);//404
                }
            }
        }
        System.out.println("Плохой запрос при удалении пользователя");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
    }

    // Update player
    @RequestMapping(value = "/rest/players/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePlayerOneById(@PathVariable Long id, @RequestBody PlayerJson playerJson){
        if(id > 0) {
            if (id instanceof Long) {
                try {
                    System.out.println("\nПользователь получен из запроса JSON ->\n" + playerJson.toString());

                    Player player = playerService.getPlayerOne(id);

                    System.out.println("\nplayerService.getPlayerOne(id) Пользователь получен из БД \n" + player.toString());
                    if (playerJson.getName() == null
                            && playerJson.getTitle() == null
                            && playerJson.getRace() == null
                            && playerJson.getProfession() == null
                            && playerJson.getBirthday() == null
                            && playerJson.getBanned() == null
                            && playerJson.getExperience() == null){
                        System.out.println("\nПользователь пуст");
                        return ResponseEntity.ok(player);
                    }

                    Player playerFromJson = Player.fromJsonToModel(player, playerJson);
                    System.out.println("\nPlayer.fromJsonToModel(player, playerJson) Пользователь из json преобразован в модель \n" + playerFromJson.toString());

                    System.out.println(" \nПользовательские данные обновлены с \n" + player.toString());
                    player.update(player, playerFromJson);
                    System.out.println(" \nПользовательские данные обновлены на \n" + playerFromJson.toString());

                    Long Birthday = playerJson.getBirthday();
                    Integer Experience = playerJson.getExperience();

                    if(playerJson.getBirthday() == null)
                        Birthday = player.getBirthday().getTime();

                    if (playerJson.getExperience() == null)
                        Experience = player.getExperience();

                    if (Experience > 10000000 || Experience < 0 || Birthday < 0) {
                        System.out.println("\nПользователь имеет неверные данные -> \n" + playerJson.toString());
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
                    }
                        System.out.println("\n----НАЧАЛО ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯ----\n" + playerFromJson.toString());
                    if (playerFromJson.getLevel() != null && playerFromJson.getExperience() != null && playerFromJson.getUntilNextLevel() != null) {
                        Integer lvl = playerFromJson.getLevel();
                        Integer exp = playerFromJson.getExperience();
                        Integer untilNextLvl = playerFromJson.getUntilNextLevel();
                        playerService.add(id, lvl, untilNextLvl, playerFromJson);
                    }
                        else playerService.add(id, playerFromJson);
                        System.out.println("\nplayerService.add(id, playerFromJson) Пользователь \n" + playerFromJson.toString() + " обновлен");

                        return ResponseEntity.ok(playerFromJson);


                    } catch(NullPointerException ee){
                        ee.printStackTrace();
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
                    } catch(NoSuchElementException e){
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND);//404
                    }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
    }

    // Create player OK
    @RequestMapping(value = "/rest/players", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPlayer (@RequestBody PlayerJson playerJson){
        System.out.println("\nПолучен пользователь :\n"+playerJson.toString());

        if(playerJson.getName() == null
                || playerJson.getTitle() == null
                || playerJson.getRace() == null
                || playerJson.getProfession() == null
                || playerJson.getBirthday() == null
                || playerJson.getBanned() == null
                || playerJson.getExperience() == null) {
            System.out.println("Ошибка 400 BAD_REQUEST! что-то = null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
        }
        else if (playerJson.getName().length() > 12 || playerJson.getTitle().length() > 30
                || playerJson.getName().trim().equals("") || playerJson.getBirthday() < 0
                || playerJson.getExperience() > 10000000) {
            System.out.println("Ошибка 400 BAD_REQUEST! что-то за пределами допустимых значений");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST);//400
        }

        Player playerFromJson = Player.fromJsonToModel(playerJson);
        System.out.println("\nPlayer сконвертирован в Model -> \n"+playerFromJson.toString());

        if (playerJson.getBanned() == null) {
            System.out.println("\nПрисвоено значение banned = false");
            playerFromJson.setBanned(false);
        }

        try {
            System.out.println("НАЧАЛО ДОБАВЛЕНИЯ ПОЛЬЗОВАТЕЛЯ \n"+playerFromJson.toString());
            PlayerEntity entity = playerService.add(playerFromJson);

            System.out.println("\nПользователь добавлен \n"+playerJson.toString());
            return ResponseEntity.ok(entity);

        } catch (NullPointerException e) {
            System.out.println("\nОшибка 400 BAD_REQUEST!-> \n"+playerFromJson.toString());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());//400
        }
    }
}
