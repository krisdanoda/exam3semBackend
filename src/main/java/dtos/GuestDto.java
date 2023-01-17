package dtos;

import entities.Festival;
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
    private  InnerFestivalDTO festival;
    private final String status;

    private final String email;

    private UserDto user;

    public GuestDto(int id, String name, List<ShowDto> shows, Long phoneNumber, Festival festival, String status, String email, UserDto user) {
        this.id = id;
        this.name = name;
        this.shows = shows;
        this.phoneNumber = phoneNumber;
        this.festival = new InnerFestivalDTO(festival);
        this.status = status;
        this.email = email;
        this.user = user;
    }

    public GuestDto(Guest guest) {
        this.id = Math.toIntExact(guest.getId());
        this.name = guest.getName();
        if (guest.getFestival() != null)
            this.festival = new InnerFestivalDTO(guest.getFestival());
        this.phoneNumber = guest.getPhoneNumber();
        this.status = guest.getStatus();
        this.email = guest.getEmail();
        this.shows = new ArrayList<>();
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
        if (this.festival != null){
            guest.setFestival(this.festival.createEntity());
            guest.getFestival().getGuestList().add(guest);
        }
        if (this.shows != null)
            for (ShowDto showDTO : this.shows) {
                Show show = showDTO.creatEntity();
                show.getGuestList().add(guest);
                guest.getShows().add(show);
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

    public InnerFestivalDTO getFestival() {
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

    private class InnerFestivalDTO implements  Serializable{

        private int id;
        private String name;
        private String city;
        private String startDate;
        private String duration;

        public InnerFestivalDTO(Festival festival) {
            this.id = Math.toIntExact(festival.getId());
            this.name = festival.getName();
            this.city = festival.getCity();
            this.startDate = festival.getStartDate();
            this.duration = festival.getDuration();
        }

        public Festival createEntity(){
            Festival festival = new Festival();
            if (this.id != 0)
                festival.setId((long) this.id);
            festival.setCity(this.city);
            festival.setDuration(this.duration);
            festival.setName(this.name);
            festival.setStartDate(this.startDate);
            return festival;
        }
    }

    public static class InnerShowDto implements Serializable {
        private final int id;
        private final String name;
        private final float duration;
        private final String startDateAndTime;

        public InnerShowDto(int id, String name, float duration, String startDateAndTime) {
            this.id = id;
            this.name = name;
            this.duration = duration;
            this.startDateAndTime = startDateAndTime;
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