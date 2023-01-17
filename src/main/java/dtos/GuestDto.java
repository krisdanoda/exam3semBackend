package dtos;

import entities.Guest;
import entities.Show;
import entities.UserDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link Guest} entity
 */
public class GuestDto implements Serializable {
    private final int id;
    private final String name;
    private List<ShowDto> shows = new ArrayList<>();
    private final Long phoneNumber;
    private final FestivalDto festival;

    private final String status;

    private final String email;

    private UserDto user;

    public GuestDto(int id, String name, List<ShowDto> shows, Long phoneNumber, FestivalDto festival, String status, String email, UserDto user) {
        this.id = id;
        this.name = name;
        this.shows = shows;
        this.phoneNumber = phoneNumber;
        this.festival = festival;
        this.status = status;
        this.email = email;
        this.user = user;
    }

    public GuestDto(Guest guest) {
        this.id = Math.toIntExact(guest.getId());
        this.name = guest.getName();
        this.festival = new FestivalDto(guest.getFestival());
        this.phoneNumber = guest.getPhoneNumber();
        this.status = guest.getStatus();
        this.email = guest.getEmail();

        for (Show show : guest.getShows()) {
            this.shows.add(new ShowDto(show));
        }
    }

    public Guest createEntity() {
        Guest guest = new Guest();
        if (this.id != 0)
            guest.setId((long) this.id);
        guest.setName(this.name);
        guest.setEmail(this.email);
        guest.setStatus(this.status);
        guest.setPhoneNumber(this.phoneNumber);
        if (this.festival != null)
            guest.setFestival(this.festival.createEntity());
        for (ShowDto show : this.shows) {
            guest.getShows().add(show.creatEntity());
        }
        return guest;
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
    public static class InnerShowDto implements Serializable {
        private final Long id;
        private final String name;
        private final float duration;
        private final String startDateAndTime;

        public InnerShowDto(Long id, String name, float duration, String startDateAndTime) {
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