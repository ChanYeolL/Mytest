import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJdbc {
    private static ConnectJdbc connectJdbc;
    private Connection connection ;
    private static final String userName = "root" ;
    private static final String passWord = "199729" ;
    private static final String url = "jdbc:mysql://10.0.0.26:3306/web?useUnicode=true&characterEncoding=utf-8" ;

    public static ConnectJdbc getInstance(){
        if(connectJdbc == null){
            try {
                connectJdbc = new ConnectJdbc() ;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connectJdbc ;
    }

    private ConnectJdbc() throws ClassNotFoundException,SQLException{
        Class.forName("com.mysql.jdbc.Driver") ;
        connection= DriverManager.getConnection(url ,userName ,passWord) ;
    }

    public Connection getConnection() {
        return connection;
    }
}
