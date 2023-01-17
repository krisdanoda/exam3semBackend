package dtos;

import entities.Guest;
import entities.Show;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link Guest} entity
 */
public class GuestDto implements Serializable {
    private final int id;
    private final String name;
    private final List<ShowDto> shows;
    private final Long phoneNumber;
    private final FestivalDto festival;
    private final UserDto user;

    public GuestDto(int id, String name, List<ShowDto> shows, Long phoneNumber, FestivalDto festival, UserDto user) {
        this.id = id;
        this.name = name;
        this.shows = shows;
        this.phoneNumber = phoneNumber;
        this.festival = festival;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ShowDto> getShows() {
        return shows;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public FestivalDto getFestival() {
        return festival;
    }

    public UserDto getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuestDto entity = (GuestDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.shows, entity.shows) &&
                Objects.equals(this.phoneNumber, entity.phoneNumber) &&
                Objects.equals(this.festival, entity.festival) &&
                Objects.equals(this.user, entity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shows, phoneNumber, festival, user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "shows = " + shows + ", " +
                "phoneNumber = " + phoneNumber + ", " +
                "festival = " + festival + ", " +
                "user = " + user + ")";
    }

    /**
     * A DTO for the {@link Show} entity
     */
    public static class ShowDto implements Serializable {
        private final Long id;
        private final String name;
        private final float duration;
        private final String startDateAndTime;

        public ShowDto(Long id, String name, float duration, String startDateAndTime) {
            this.id = id;
            this.name = name;
            this.duration = duration;
            this.startDateAndTime = startDateAndTime;
        }

        public Long getId() {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ShowDto entity = (ShowDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.duration, entity.duration) &&
                    Objects.equals(this.startDateAndTime, entity.startDateAndTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, duration, startDateAndTime);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "duration = " + duration + ", " +
                    "startDateAndTime = " + startDateAndTime + ")";
        }
    }
}