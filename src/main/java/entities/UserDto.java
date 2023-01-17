package entities;

import dtos.GuestDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link User} entity
 */
public class UserDto implements Serializable {
    @NotNull
    private final String userName;
    @NotNull
    @Size(min = 1, max = 255)
    private final String userPass;
    private  GuestDto guestAccount;
    private  List<String> roleListRoleNames = new ArrayList<>();

    public  UserDto(User user){
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
        this.roleListRoleNames = user.getRolesAsStrings();
        this.guestAccount = new GuestDto(user.getGuestAccount());

    }

    public UserDto(String userName, String userPass){
        this.userName = userName;
        this.userPass = userPass;

    }


    public UserDto(String userName, String userPass, GuestDto guestAccount, List<String> roleListRoleNames) {
        this.userName = userName;
        this.userPass = userPass;
        this.guestAccount = guestAccount;
        this.roleListRoleNames = roleListRoleNames;
    }

    public User createEntity(){
        User user = new User();
        user.setUserName(this.userName);
        user.setUserPass(this.userPass);
        user.setGuestAccount(this.guestAccount.createEntity());

        for (String roleListRoleName : this.roleListRoleNames) {
            user.addRole( new Role(roleListRoleName));
        }
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public GuestDto getGuestAccount() {
        return guestAccount;
    }

    public List<String> getRoleListRoleNames() {
        return roleListRoleNames;
    }

    public void setGuestAccount(GuestDto guestAccount) {
        this.guestAccount = guestAccount;
    }

    public void setRoleListRoleNames(List<String> roleListRoleNames) {
        this.roleListRoleNames = roleListRoleNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.userName, entity.userName) &&
                Objects.equals(this.userPass, entity.userPass) &&
                Objects.equals(this.guestAccount, entity.guestAccount) &&
                Objects.equals(this.roleListRoleNames, entity.roleListRoleNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userPass, guestAccount, roleListRoleNames);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userName = " + userName + ", " +
                "userPass = " + userPass + ", " +
                "guestAccount = " + guestAccount + ", " +
                "roleListRoleNames = " + roleListRoleNames + ")";
    }
}