package jdbcsuperstore;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test

    public void testReviewMaking() throws SQLException, ClassNotFoundException {
        try {
            SuperstoreServices sr = new SuperstoreServices("jdbc:oracle:thin:", "198.168.52.211", "1521", "A2232160",
                    "Iliketurtles0132");
            Connection conn = sr.getConnection();
            Review r = new Review(2, 4, -5, "amazing", conn);

            fail("not able to create negative star!!");
        } catch (IllegalArgumentException e) {

        }

    }

}
