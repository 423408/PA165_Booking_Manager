package cz.muni.fi.pa165.service.facade;

import static org.testng.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import java.util.List;
import cz.muni.fi.pa165.api.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.api.dto.UserDTO;
import cz.muni.fi.pa165.api.facade.UserFacade;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.utils.Security;

/**
 * @author Petr Valenta
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class UserFacadeImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private UserFacade userFacade = new UserFacadeImpl();

    private UserDTO userDTO1;
    private UserAuthenticateDTO userAuthDTO1;
    private UserDTO newUserDTO;
    private String password = "password";
    private String hash = Security.createHash(password);

    @BeforeMethod
    public void init(){
        userDTO1 = new UserDTO();
        newUserDTO = new UserDTO();

        userAuthDTO1 = new UserAuthenticateDTO();

        userDTO1.setId(1L);
        userDTO1.setEmail("user1@dto.com");
        userDTO1.setFirstName("John");
        userDTO1.setSurname("Doe");
        userDTO1.setPasswordHash(hash);
        userDTO1.setAdministrator(false);

        userAuthDTO1.setUserId(1L);
        userAuthDTO1.setPassword(password);

        newUserDTO.setEmail("new@user.com");
        newUserDTO.setFirstName("New");
        newUserDTO.setSurname("User");
    }

    @Test
    public void testRegister() throws Exception {
        userFacade.register(newUserDTO, password);
        long id = newUserDTO.getId();
        assertNotNull(id);
    }

    @Test
    public void testGetAll() throws Exception {
        userFacade.register(newUserDTO, password);
        List<UserDTO> tmp = userFacade.getAll();
        assertEquals(tmp.size(),1);
    }

    @Test
    public void testAuthenticate() throws Exception {
        userFacade.register(newUserDTO, password);
        long id = newUserDTO.getId();
        userAuthDTO1.setUserId(id);

        boolean ret = userFacade.authenticate(userAuthDTO1);
        assertTrue(ret);
    }

    @Test
    public void testFindById() throws Exception {
        userFacade.register(newUserDTO, password);
        long id = newUserDTO.getId();
        UserDTO tmp = userFacade.findById(id);
        assertNotNull(tmp);
    }

    @Test
    public void testIsAdmin() throws Exception {
        userFacade.register(newUserDTO, password);
        assertFalse(userFacade.isAdmin(newUserDTO));
    }

    @Test
    public void testFindByEmail() throws Exception {
        userFacade.register(newUserDTO, password);
        UserDTO tmp = userFacade.findByEmail("new@user.com");
        assertEquals(tmp.getEmail(), "new@user.com");
    }
}