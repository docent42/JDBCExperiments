import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main
{
    public static void main(String[] args)
    {
        String url = "jdbc:mysql://localhost:3306/sk2"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";

        String user ="root";
       String pass = "еуыееуые";
       try
       {
           Connection connection = DriverManager.getConnection(url, user, pass);
           Statement statement = connection.createStatement();
           String sql = "SELECT course_name, AVG(subs_count) AS avg_purchaseCount FROM " +
                   "(SELECT courses.name AS course_name, COUNT(MONTH(Subscriptions.Subscription_date)) " +
                   "AS subs_count FROM courses JOIN subscriptions ON subscriptions.course_id = courses.id " +
                   "GROUP BY courses.id, MONTH(Subscriptions.Subscription_date)) as Result " +
                   "GROUP BY course_name;";
           ResultSet resultSet = statement.executeQuery(sql);
           System.out.printf("%-36s%s%n","Название курса","Среднее кол-во покупок в месяц");
           while (resultSet.next())
           {
               String courseName = resultSet.getString("course_name");
               double avg = resultSet.getDouble("avg_purchaseCount");
               System.out.printf("%-35s - %.4f%n",courseName,avg);
           }
           resultSet.close();statement.close();connection.close();

       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
    }
}
