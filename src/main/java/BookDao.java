import java.sql.*;

public class BookDao {
    private static final String URL = "jdbc:mysql://localhost:3306/library?characterEncoding=utf8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    private Connection connection;

    public BookDao(){
        try{
          Class.forName("com.mysql.jdbc.Driver");
          connection = DriverManager.getConnection(URL,USER,PASS);
        } catch (ClassNotFoundException e){
            System.out.println("No driver found");
        } catch(SQLException e){
            System.out.println("Could not establish connection");
        }
    }

    public void close(){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void save(Book book){
        final String sql = "INSERT INTO books(title,author,`year`,isbn) VALUES (?,?,?,?)";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setInt(3,book.getYear());
            preparedStatement.setString(4,book.getIsbn());
            preparedStatement.executeUpdate();
            System.out.println("##Dodano pozycje do bazy");
        } catch(SQLException e){
            System.out.println("Could not save record");
        }
    }

    public Book read(String isbn){
        final String sql = "SELECT * FROM books WHERE isbn = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,isbn);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                Book book = new Book();
                book.setId(result.getLong("id"));
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setYear(result.getInt("year"));
                book.setIsbn(result.getString("isbn"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Book book){
        final String sql = "UPDATE books set title=?, author=?, year=?, isbn=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYear());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setLong(5, book.getId());
            preparedStatement.executeUpdate();
            System.out.println("##Dane zostały zaktualizowane");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete (Book book){
        final String sql = "DELETE FROM books WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, book.getId());
            preparedStatement.executeUpdate();
            System.out.println("##Dane zostały usunięte");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
