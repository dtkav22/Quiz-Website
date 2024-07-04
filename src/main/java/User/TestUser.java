package User;

import junit.framework.TestCase;

public class TestUser extends TestCase {
    public void testUser() {
        User user1 = new User("duta", "duta", "example@mail", false);
        assertTrue(user1.isPasswordCorrect("duta"));
        assertFalse(user1.isPasswordCorrect("duta12312"));
        assertEquals("duta", user1.getUserName());
        assertEquals("example@mail", user1.getEmail());
        System.out.println(user1.getPassword());
    }

    public void testgetPassword(){
        User user1 = new User("Nino", "Niniko", "ninoza21@freeuni.edu.ge");
        System.out.println("Niniko " + user1.getPassword());

        User user2 = new User("Zuko", "Zvio", "zviadivardava@gmail.com");
        System.out.println("Zvio " + user1.getPassword());

        User user3 = new User("Data_Tutashkhia", "asea_es", "tutashkhiadata@firali.com");
        System.out.println("asea_es " + user1.getPassword());
    }

}
