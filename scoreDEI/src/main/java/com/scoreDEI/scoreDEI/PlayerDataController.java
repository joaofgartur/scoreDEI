package com.scoreDEI.scoreDEI;

import com.scoreDEI.Entities.Player;
import com.scoreDEI.Entities.Team;
import com.scoreDEI.Forms.PlayerForm;
import com.scoreDEI.Services.PlayerService;
import com.scoreDEI.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

@Controller
@RequestMapping("/player")
public class PlayerDataController {
    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @GetMapping("/register")
    public String registerPlayerForm(Model model) {
        model.addAttribute("teams", this.teamService.getAllTeams());
        model.addAttribute("PlayerForm", new PlayerForm());
        return "player/register";
    }

    @PostMapping("/register")
    public String registerPlayerSubmit(@ModelAttribute PlayerForm form, Model model){
        model.addAttribute("PlayerForm", form);

        String playerName = form.getName();
        String playerPosition = form.getPosition();
        Date playerBirthday = form.getBirthday();
        Optional<Team> playerTeam = this.teamService.getTeam(form.getTeamName());

        if (playerTeam.isPresent()) {
            Player dbPlayer = new Player(playerName, playerPosition, playerBirthday, playerTeam.get());
            this.playerService.addPlayer(dbPlayer);
        }
        else{
            System.out.println("Team not found");
        }

        return "redirect:/player/list";
    }

    @GetMapping("/list")
    public String listTeams(Model model) {
        model.addAttribute("teams", this.teamService.getAllTeams());

        return "/player/list";
    }

    @GetMapping("/profile")
    public String playerProfile(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Player> p = this.playerService.getPlayer(id);

        if(p.isPresent()) {
            model.addAttribute("player", p.get());
            return "/player/profile";
        }

        return "redirect:/player/list";
    }

    @GetMapping("/edit/name")
    public String updatePlayerName(@RequestParam(name="id", required = true) int id, Model model) {
        Optional<Player> p = this.playerService.getPlayer(id);

        if(p.isPresent()) {
            model.addAttribute("playerForm", new PlayerForm());
            model.addAttribute("player", p.get());

            return "player/updateName";
        }

        return "redirect:/player/list";
    }

    @PostMapping("/edit/name")
    public String updatePlayerName(@RequestParam(name="id", required = true) int id, @ModelAttribute PlayerForm form, Model model) throws IOException {
        model.addAttribute("playerForm", form);

        String name = form.getName();
        this.playerService.updateName(id, name);

        return "redirect:/player/list";
    }

    @GetMapping("/edit/position")
    public String updatePosition(@RequestParam(name="id", required = true) int id, Model model) {
        Optional<Player> p = this.playerService.getPlayer(id);

        if(p.isPresent()) {
            model.addAttribute("playerForm", new PlayerForm());
            model.addAttribute("player", p.get());

            return "player/updatePosition";
        }

        return "redirect:/player/list";
    }

    @PostMapping("/edit/position")
    public String updatePosition(@RequestParam(name="id", required = true) int id, @ModelAttribute PlayerForm form, Model model) throws IOException {
        model.addAttribute("playerForm", form);

        String position = form.getPosition();
        this.playerService.updatePosition(id, position);

        return "redirect:/player/list";
    }

    @GetMapping("/edit/birthday")
    public String updateBirthday(@RequestParam(name="id", required = true) int id, Model model) {
        Optional<Player> p = this.playerService.getPlayer(id);

        if(p.isPresent()) {
            model.addAttribute("playerForm", new PlayerForm());
            model.addAttribute("player", p.get());

            return "player/updateBirthday";
        }

        return "redirect:/player/list";
    }

    @PostMapping("/edit/birthday")
    public String updateBirthday(@RequestParam(name="id", required = true) int id, @ModelAttribute PlayerForm form, Model model) throws IOException {
        model.addAttribute("playerForm", form);

        Date birthday = form.getBirthday();
        this.playerService.updateBirthday(id, birthday);

        return "redirect:/player/list";
    }

    @GetMapping("/edit/team")
    public String updatePlayerTeam(@RequestParam(name="id", required = true) int id, Model model) {
        Optional<Player> p = this.playerService.getPlayer(id);

        if(p.isPresent()) {
            model.addAttribute("playerForm", new PlayerForm());
            model.addAttribute("player", p.get());
            model.addAttribute("teams", this.teamService.getAllTeams());

            return "player/updateTeam";
        }

        return "redirect:/player/list";
    }

    @PostMapping("/edit/team")
    public String updatePlayerTeam(@RequestParam(name="id", required = true) int id, @ModelAttribute PlayerForm form, Model model) throws IOException {
        model.addAttribute("playerForm", form);
        Optional<Team> playerTeam = this.teamService.getTeam(form.getTeamName());
        playerTeam.ifPresent(team -> this.playerService.updateTeam(id, team));

        return "redirect:/player/list";
    }
}