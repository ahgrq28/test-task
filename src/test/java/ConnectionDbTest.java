import com.haulmont.testtask.db.ConnectionDb;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.junit.Assert.*;

public class ConnectionDbTest {
    @Test
    public void getConnection() throws Exception {
        Connection connect = ConnectionDb.getConnection();
        assertNotNull(connect);
    }

}