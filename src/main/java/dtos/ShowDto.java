package dtos;

import entities.Festival;
import entities.Guest;
import entities.Show;
import entities.UserDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//when this class was created only 2 people knew how this worked. Now only god knows, let this be a reminder that a coffee fueled bender is not the healthiest
public class ShowDto implements Serializable {
    private int id;
    private final String name;
    private final float duration;

    private final String location;

    private final String startDateAndTime;
    private List<InnerGuestDto> guestList = new ArrayList<>();

    public ShowDto(int id, String name, float duration, String startDateAndTime, List<InnerGuestDto> guestList, String location) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.startDateAndTime = startDateAndTime;
        this.guestList = guestList;
        this.location = location;
    }

    public ShowDto(int id, String name, float duration, String location, String startDateAndTime) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDateAndTime = startDateAndTime;
    }

    public ShowDto(Show show) {
        if (show.getId() != null)
            this.id = Math.toIntExact(show.getId());

        for (Guest guest : show.getGuestList()) {
            guestList.add(new InnerGuestDto(guest));
        }
        this.name = show.getName();
        this.duration = show.getDuration();
        this.startDateAndTime = show.getStartDateAndTime();
        this.location = show.getLocation();
    }

    public Show creatEntity() {
        Show show = new Show();
        show.setId((long) this.id);
        show.setLocation(this.location);
        show.setName(this.name);
        show.setDuration(this.duration);
        show.setStartDateAndTime(this.startDateAndTime);

        ArrayList<Guest> guestList = new ArrayList<>();
        for (InnerGuestDto innerGuestDto : this.guestList) {
            Guest guest = innerGuestDto.createEntity();
            guest.getShows().add(show);
            guestList.add(guest);
        }

        show.setGuestList(guestList);

        System.out.println(show);
        return show;

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

    public List<InnerGuestDto> getGuestList() {
        return guestList;
    }

    public String getLocation() {
        return location;
    }

    public void setGuestList(List<InnerGuestDto> guestList) {
        this.guestList = guestList;
    }

    private class InnerGuestDto implements Serializable {
        private final int id;
        private final String name;

        private final Long phoneNumber;
        private InnerInnerFestivalDTO festival;
        private final String status;

        private final String email;

        private UserDto user;

        public InnerGuestDto(int id, String name, Long phoneNumber, Festival festival, String status, String email, UserDto user) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.festival = new InnerInnerFestivalDTO(festival);
            this.status = status;
            this.email = email;
            this.user = user;
        }

        public InnerGuestDto(Guest guest) {
            this.id = Math.toIntExact(guest.getId());
            this.name = guest.getName();
            if (guest.getFestival() != null)
                this.festival = new InnerInnerFestivalDTO(guest.getFestival());
            this.phoneNumber = guest.getPhoneNumber();
            this.status = guest.getStatus();
            this.email = guest.getEmail();

        }

        public Guest createEntity() {
            Guest guest = new Guest();
            if (this.id != 0)
                guest.setId((long) this.id);
            guest.setName(this.name);
            guest.setEmail(this.email);
            guest.setStatus(this.status);
            guest.setPhoneNumber(this.phoneNumber);
            if (this.festival != null) {
                guest.setFestival(this.festival.createEntity());
                guest.getFestival().getGuestList().add(guest);
            }

            return guest;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }


        public Long getPhoneNumber() {
            return phoneNumber;
        }

        public InnerInnerFestivalDTO getFestival() {
            return festival;
        }

        public UserDto getUser() {
            return user;
        }


        @Override
        public int hashCode() {
            return Objects.hash(id, name, phoneNumber, festival, user);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "phoneNumber = " + phoneNumber + ", " +
                    "festival = " + festival + ", " +
                    "user = " + user + ")";
        }

        private class InnerInnerFestivalDTO implements Serializable {

            private int id;
            private String name;
            private String city;
            private String startDate;
            private String duration;

            public InnerInnerFestivalDTO(Festival festival) {
                this.id = Math.toIntExact(festival.getId());
                this.name = festival.getName();
                this.city = festival.getCity();
                this.startDate = festival.getStartDate();
                this.duration = festival.getDuration();
            }

            public Festival createEntity() {
                Festival festival = new Festival();
                if (this.id != 0)
                    festival.setId((long) this.id);
                festival.setCity(this.city);
                festival.setDuration(this.duration);
                festival.setName(this.name);
                festival.setStartDate(this.startDate);
                festival.setGuestList(new ArrayList<>());
                return festival;
            }
        }

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
        return "ShowDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", location='" + location + '\'' +
                ", startDateAndTime='" + startDateAndTime + '\'' +
                ", guestList=" + guestList +
                '}';
    }
}