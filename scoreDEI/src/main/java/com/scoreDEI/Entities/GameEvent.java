package com.scoreDEI.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "events")
@JsonIgnoreProperties({"game_events"})
@XmlRootElement
public class GameEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "event_gen")
    @Column(name = "gameEventId", nullable = false)
    private int gameEventId;
    @Column(name = "eventdate", nullable = false)
    private Time eventDate;
    @ManyToOne
    @JoinColumn(name = "gameId", nullable = false)
    private Game game;

    public GameEvent() {
    }

    public GameEvent(Time eventDate, Game game) {
        this.eventDate = eventDate;
        this.game = game;
    }

    public int getGameEventId() {
        return gameEventId;
    }

    public void setGameEventId(int gameEventId) {
        this.gameEventId = gameEventId;
    }

    public Time getEventDate() {
        return eventDate;
    }

    public void setEventDate(Time eventDate) {
        this.eventDate = eventDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getTypeEvent()
    {
        return 0;
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "eventDate=" + eventDate +
                ", game=" + game +
                '}';
    }
}
