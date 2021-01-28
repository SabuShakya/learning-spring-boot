**Dependency Injection and IoC**

*Dependency Injection* means passing a dependent object as a parameter to a method rather than having the method create 
the dependent object.

*IOC* is a principle by which the control of objects is transferred to a container or a framework. Objects donot create 
objects they rely on to do their work. Instead, they get them from an outside source.

Let's assume we have a __'User'__,and it needs to access *database* to persist data. What we do is create an instance of 
database and proceed to use its method to persist.
Our class `User` has a dependency on `MySqlDatabase`.

```java
    public class User {
        MySqlDatabase mySqlDatabase;

        public User() {
            mySqlDatabase = new MySqlDatabase();
        }

        public void add(String data) {
            mySqlDatabase.persist(data);
        }
    }
```
Inversion of control recognizes that instead of initializing the dependency in a dependent object,the job should be passed
to some other framework or somewhere higher up the dependency graph.
So in above example, instead of creating instance of `MySqlDatabase`, it is initialized by so other frameworks and is 
passed as a method parameter. This ensures loosely coupled architecture and flexible code.
 
```java
    public class User {
        MySqlDatabase mySqlDatabase;
        
        public User(MySqlDatabase mySqlDatabase) {
            this.mySqlDatabase = mySqlDatabase;
        }

        public void add(String data) {
            mySqlDatabase.persist(data);
        }
    }
```
Now let's assume we have another type of database(`Oracle`) and now we need to use this to persist User data using this.
The above code will break if we pass oracle data instance to user as 
```text
  User user = new User(new OracleDatabase()); // will show error
```
Above code will show error:
![Alt text](./error.jpg?raw=true "Title")

Hence, we follow Dependency inversion principle: Rely on abstraction rather than implementation.
So, we create an interface `Database` and create MySqlDatabase and Oracle Database as its implementations, and
inject Database interface to `User`.

```java
    public class User {
        Database database;
       
        public User(Database database){
            this.database = database;
        }
        
        public void add(String data) {
            database.persist(data);
        }
    }

    public interface Database {
        void persist(String data);
    }

    public class MySqlDatabase implements  Database{
        public void persist(String data) {
            System.out.println("MySQL has persisted:" + data);
        }
    }

    public class OracleDatabase implements Database {
        public void persist(String data) {
            System.out.println("Oracle has persisted:" + data);
        }
    }
``` 

Now when we pass either MySqlDatabase or OracleDatabase to User, it won't show any error.
```text
        User user = new User(new MySqlDatabase());
        User user1 = new User(new OracleDatabase());
        user.add("This is some data"); // outputs: MySQL has persisted:This is some data
        user1.add("This is some data"); // outputs: Oracle has persisted:This is some data
```

This concept will be implemented in Spring using global context(spring container) to initialize dependencies 
configured as bean. Single instance of objects are initialized in the spring container, and they are provided whenever 
needed.
Using Spring framework the above code can be simplified by simply using annotation `@Autowired`.
```java
    public class User {
        @Autowired
        Database database;
        
        public void add(String data) {
            database.persist(data);
        }
    }

    @Component
    public interface Database {
        void persist(String data);
    }
```
Reference: [Dependency Injection & Inversion of Control](https://youtu.be/EPv9-cHEmQw)


