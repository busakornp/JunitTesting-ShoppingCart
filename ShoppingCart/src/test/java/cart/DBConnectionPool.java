package cart;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class DBConnectionPool {
    @BeforeAll
    static void connectDBConnectionPool(){
        System.out.println("<=DB Connection Established =>");
    }
    @AfterAll
    static void closeDBConnectionPool(){
        System.out.println("<=DB Connection Closed =>");
    }
}
