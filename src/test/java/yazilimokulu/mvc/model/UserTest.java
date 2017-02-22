package yazilimokulu.mvc.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.yazilimokulu.mvc.entities.Role;
import com.yazilimokulu.mvc.entities.User;

public class UserTest {

    @Test
    public void testHasRole() throws Exception {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user = new User();

        assertThat(user.hasRole("ROLE_ADMIN"), is(equalTo(false)));

        user.getRoles().add(role);

        assertThat(user.hasRole("ROLE_ADMIN"), is(equalTo(true)));
        assertThat(user.hasRole("admin"), is(equalTo(true)));
    }
}