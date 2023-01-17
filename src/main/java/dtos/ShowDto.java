package dtos;

import entities.Show;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link Show} entity
 */
public class ShowDto implements Serializable {
    private final int id;
    private final String name;
    private final float duration;
    private final String startDateAndTime;
    private final List<GuestDto> guestList;

    public ShowDto(int id, String name, float duration, String startDateAndTime, List<GuestDto> guestList) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.startDateAndTime = startDateAndTime;
        this.guestList = guestList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getDuration() {
        return duration;
    }

    public String getStartDateAndTime() {
        return startDateAndTime;
    }

    public List<GuestDto> getGuestList() {
        return guestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowDto entity = (ShowDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.duration, entity.duration) &&
                Objects.equals(this.startDateAndTime, entity.startDateAndTime) &&
                Objects.equals(this.guestList, entity.guestList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, startDateAndTime, guestList);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "duration = " + duration + ", " +
                "startDateAndTime = " + startDateAndTime + ", " +
                "guestList = " + guestList + ")";
    }
}