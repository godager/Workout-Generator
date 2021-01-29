import com.areyes1.jgc.junit.integ.IntegrationTest;
import main.db.DBConnection;
import org.junit.experimental.categories.Category;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Category(IntegrationTest.class)
public class DBConnectionIT {

    @org.junit.jupiter.api.Test
    void connectSQLEx() {
        assertDoesNotThrow(DBConnection::connect);
    }

    @org.junit.jupiter.api.Test
    void close() {
    assertDoesNotThrow(DBConnection::close);
    }
}
