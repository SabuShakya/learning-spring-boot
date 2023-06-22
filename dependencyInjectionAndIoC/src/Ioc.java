import javax.xml.crypto.Data;

public class Ioc {
    public static void main(String[] args) {
        Ioc container = new Ioc();
//        1.User user = container.new User();
        User user = container.new User(container.new MySqlDatabase());
        User user1 = container.new User(container.new OracleDatabase());
        user.add("This is some data");
        user1.add("This is some data");
    }

    /**
     * Business Layer Logic
     */
    public class User {
        Database database;

        /* 1.basically we have to do this*/
        /*public User() {
            mySqlDatabase = new MySqlDatabase();
        }*/

        /* 2.Instead we pass the instance as a parameter*/
//        public User(MySqlDatabase mySqlDatabase) {
//            this.mySqlDatabase = mySqlDatabase;
//        }

        // 3. We inject an interface
        public User(Database database) {
            this.database = database;
        }

        public void add(String data) {
            database.persist(data);
        }
    }

    /**
     * Database Access Layer simulation
     */

    public interface Database {
        void persist(String data);
    }

    public class MySqlDatabase implements Database {
        public void persist(String data) {
            System.out.println("MySQL has persisted:" + data);
        }
    }

    public class OracleDatabase implements Database {
        public void persist(String data) {
            System.out.println("Oracle has persisted:" + data);
        }
    }
}
