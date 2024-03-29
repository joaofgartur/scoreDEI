/**
 * It's a class that represents a goal in a game
 */
package com.scoreDEI.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@Entity
@JsonIgnoreProperties({"goals"})
@XmlRootElement
public class Goal extends GameEvent{
    @ManyToOne
    @JoinColumn(name="playerId", nullable=false)
    private Player player;

    public Goal() {
    }

    public Goal(Time eventDate, Game game, Player player) {
        super(eventDate, game);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int getTypeEvent() {
        return 3;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "player=" + player +
                '}';
    }
}
