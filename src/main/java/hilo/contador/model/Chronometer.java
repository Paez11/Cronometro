package hilo.contador.model;

import java.util.Objects;

public class Chronometer {
    protected int id;
    protected int hour;
    protected int minute;
    protected int second;
    protected int milisecond;

    public Chronometer(){

    }

    public Chronometer(int hour, int minute, int second, int milisecond) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.milisecond = milisecond;
    }

    public Chronometer(int id, int hour, int minute, int second, int milisecond) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.milisecond = milisecond;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMilisecond() {
        return milisecond;
    }

    public void setMilisecond(int milisecond) {
        this.milisecond = milisecond;
    }

    @Override
    public String toString() {
        return "Chronometer{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", milisecond=" + milisecond +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chronometer that = (Chronometer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
