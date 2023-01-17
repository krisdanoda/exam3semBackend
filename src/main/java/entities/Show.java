package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shows")
@NamedQuery(name = "Show.deleteAllRows", query = "DELETE FROM Show ")
public class Show {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private float duration;

    @Column(name = "startDateAndTime")
    private String startDateAndTime;

    @Column(name = "location")
    private String location;

    @ManyToMany
    @JoinTable(name = "shows_guest",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private List<Guest> guestList = new ArrayList<>();

    public Show(String name, float duration, String startDateAndTime, String location) {
        this.name = name;
        this.duration = duration;
        this.startDateAndTime = startDateAndTime;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Float.compare(show.duration, duration) == 0 && Objects.equals(name, show.name) && Objects.equals(startDateAndTime, show.startDateAndTime) && Objects.equals(location, show.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, startDateAndTime, location);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(String startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Show(){}
}
