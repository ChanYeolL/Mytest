import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet(name = "HandServlet" , urlPatterns = "/api/hero/*")
public class HandServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = ConnectJdbc.getInstance().getConnection() ;
        ResultSet rs = null ;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = ConnectJdbc.getInstance().getConnection() ;
        ResultSet rs = null ;
        List<HeroBean> list = new ArrayList<>() ;
        String ids[] = request.getRequestURI().split("/") ;
        int len = ids.length - 1 ;
        Gson gson = new Gson() ;
        String json = null;
        if(isNumeric(ids[len])){
            int id = Integer.parseInt(ids[len]);
            try {
                rs = connection.prepareStatement("SELECT * FROM hero WHERE id = '"+id+"'").executeQuery() ;
               if(rs.next()){
                   HeroBean hero = new HeroBean() ;
                   hero.setId(rs.getInt("id"));
                   hero.setName(rs.getString("name"));
                   json = gson.toJson(hero) ;
               }else {
                   json = "[]";
               }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                rs = connection.prepareStatement("SELECT * FROM hero").executeQuery();
                while (rs.next()) {
                    HeroBean hero = new HeroBean();
                    hero.setId(rs.getInt("id"));
                    hero.setName(rs.getString("name"));
                    list.add(hero);
                }
                json = gson.toJson(list) ;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //跨域
        response.addHeader("Access-Control-Allow-Origin","*");

        response.addHeader("Accept", "application/json");
        response.addHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().print(json);
    }

        public  boolean isNumeric(String str){
            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(str).matches();
        }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
