package Myfriendbook;

import org.fluentlenium.adapter.junit.FluentTest;
import Myfriendbook.Repositories.AccountRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author petri
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest extends FluentTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder encoder;

    @After
    public void after() {
        accountRepository.deleteAll();
    }

    @Test
    public void anyCanGetDefaultPage() {
        goTo("http://localhost:" + port + "/");

        assertTrue(pageSource().contains("Hello World!"));
    }

    @Test
    public void unAuthGoLoginWhenAccessHome() {
        goTo("http://localhost:" + port + "/home");

        assertTrue(pageSource().contains("Log in"));
    }

    @Test
    public void canSignUp() {
        goTo("http://localhost:" + port + "/login");

        find("#toSignin").click();
        assertThat(window().title()).isEqualTo("Registration page");

        find("input", withName("name")).fill().with("Tester Name");
        find("input", withName("username")).fill().with("Tester");
        find("input", withName("password")).fill().with("test");
        find("input", withName("profilename")).fill().with("Profile name");

        find("button").first().click();

        assertTrue(pageSource().contains("Tester"));
        find("form").first().submit();
    }
    
    @Test
    public void logInWithWrongCredentials() {
         goTo("http://localhost:" + port + "/login");
         
        find("input", withName("username")).fill().with("Wrong");
        find("input", withName("password")).fill().with("wrognpassword");
        
         find("button").first().click();
         assertTrue(pageSource().contains("Wrong username or password"));
    }
}
