package com.scoreDEI.Repositories;

import com.scoreDEI.Entities.Game;
import com.scoreDEI.Entities.Player;
import com.scoreDEI.Entities.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    /**
     * Find all teams whose name equals the given string.
     *
     * @param chars The name of the team you want to find.
     * @return A list of teams with the name that matches the parameter.
     */
    @Query("SELECT t FROM Team t WHERE t.name = ?1")
    public List<Team> findTeamByName(String chars);

    /**
     * Delete all GameEvents where the game is equal to the game passed in.
     *
     * @param game The game to delete the events for
     */
    @Modifying
    @Query("DELETE FROM GameEvent c WHERE c.game = ?1")
    public void deleteGameEvents(Game game);

    /**
     * Delete all games where the given team is either the home or visitor team.
     *
     * @param team The team to delete games for.
     */
    @Modifying
    @Query("DELETE FROM Game g WHERE g.homeTeam = ?1 OR g.visitorTeam = ?1")
    public void deleteGames(Team team);

    /**
     * Delete all goals for a given player.
     *
     * @param p The player whose goals we want to delete.
     */
    @Modifying
    @Query("DELETE FROM Goal g WHERE g.player = ?1")
    public void deleteGoals(Player p);

    /**
     * Delete all cards that belong to a player.
     *
     * @param p The player whose cards we want to delete.
     */
    @Modifying
    @Query("DELETE FROM Card c WHERE c.player = ?1")
    public void deleteCards(Player p);

    /**
     * Delete all players from the given team.
     *
     * @param t The team to delete players from
     */
    @Modifying
    @Query("DELETE FROM Player p WHERE p.team = ?1")
    public void deletePlayers(Team t);

    /**
     * Get the number of goals scored by a team in a game.
     *
     * @param game The game we want to get the score for
     * @param team The team to get the score for
     * @return The number of goals scored by a team in a game.
     */
    @Query("SELECT COUNT(t) FROM Goal t WHERE t.game = ?1 AND t.player.team = ?2")
    public int getTeamScore(Game game, Team team);

    @Modifying
    @Query("UPDATE Team t SET t.numberWins = t.numberWins - 1, t.numberGames = t.numberGames - 1 WHERE t.teamId = ?1")
    public void deleteWin(int teamId);

    @Modifying
    @Query("UPDATE Team t SET t.numberLosses = t.numberLosses - 1, t.numberGames = t.numberGames - 1 WHERE t.teamId = ?1")
    public void deleteLoss(int teamId);

    @Modifying
    @Query("UPDATE Team t SET t.numberDraws = t.numberDraws - 1, t.numberGames = t.numberGames - 1 WHERE t.teamId = ?1")
    public void deleteDraw(int teamId);
}
