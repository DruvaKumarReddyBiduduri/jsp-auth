package db;


public interface UserDAO {
    User findByName(String name);
    boolean insert(User user);
    User update(User user);
    void delete(String name);
    User findById(int id);
}
