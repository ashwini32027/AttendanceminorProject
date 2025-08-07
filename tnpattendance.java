import java.util.*;
import java.sql.*;

public class TnpApp {
    private final static String url = "jdbc:mysql://127.0.0.1:3306/tnp";
    private final static String username = "root";
    private final static String password = "773939";

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully");
            Connection connection = DriverManager.getConnection(url, username, password);
            markattendance mark = new markattendance(connection, sc);
            viewAttendancereport view = new viewAttendancereport(connection);
            viewsortedstudentlist sort = new viewsortedstudentlist(connection);
            searchstudentattendace search = new searchstudentattendace(connection, sc);
            addstudent add = new addstudent(connection, sc);
            removestudent remove = new removestudent(connection, sc);

            while (true) {
                System.out.println("\n===== Student Attendance System Design By Ashwini Kumar B-tech(2023-26) =====");
                System.out.println("1. Mark Attendance");
                System.out.println("2. View Attendance Report");
                System.out.println("3. View Sorted Student List");
                System.out.println("4. Search Student Attendance by Roll No");
                System.out.println("5. Add New Student");
                System.out.println("6. Remove Student");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                if (!sc.hasNextInt()) {
                    System.out.println(" Please enter a valid number.");
                    sc.next(); // consume wrong input
                    continue;
                }
                int choice = sc.nextInt();

                switch (choice) {

                    case 1: mark.marks(); break;
                    case 2: view.report(); break;
                    case 3: sort.sorted(); break;
                    case 4: search.search(); break;
                    case 5: add.add(); break;
                    case 6: remove.delete(); break;
                    case 7:
                        System.out.println("Exiting system. Goodbye!");
                        sc.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

class markattendance
{
private final Connection connection;
private final Scanner sc;
public markattendance(Connection connection,Scanner sc)
{
    this.connection=connection;
    this.sc=sc;
}
void marks()
{

    System.out.println("Do you want to mark attedance type Yes/No");
    String value=sc.next().trim().toLowerCase();
    if(value.equals("no") || value.equals("n"))
    {
        System.out.println("Existing from System There was No class Today");
        return;
    }
    else {
        stu();
    }
}
void stu()
{
     final String query="Update  student set total_class=total_class+1";
     try {
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         int row=preparedStatement.executeUpdate();
         if(row>0)
         {
             System.out.println("Total class value increase");
         }
         else
         {
             System.out.println("Sorry Something went wrong Existing From System");
           return;
         }
     } catch (SQLException e) {
         throw new RuntimeException(e);
     }
     final String fetch="Select roll_no,stu_name from student";
     final String updateq="update student set attendance_count=attendance_count+1 where roll_no=?";
     try
     {
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(fetch);
         PreparedStatement preparedStatement = connection.prepareStatement(updateq);
         while (resultSet.next())
         {
             String roll = resultSet.getString("roll_no");
             String name=resultSet.getString("stu_name");
             String val;
             while (true) {
                 System.out.println("In today Class Does roll " + roll + "name " + name + "present? (Yes/No or Y/N):");
                 val = sc.next().trim().toLowerCase();
                 if (val.equals("yes") || val.equals("y") || val.equals("no") || val.equals("n")) {
                     break; // valid input
                 } else {
                     System.out.println(" Invalid input! Please type only Yes/Y or No/N.");
                 }
             }
             if (val.equals("yes") || val.equals("y"))
             {
                 preparedStatement.setString(1,roll);
                 int update=preparedStatement.executeUpdate();
                 if(update>0)
                 {
                     System.out.println("Attendance marked succefully for " +roll);
                 }
             }
             else
             {
                 System.out.println("marked Absent for "+roll);
             }


         }
         preparedStatement.close();
         statement.close();
         resultSet.close();
     }
     catch (SQLException e)
     {
         System.out.println("Sql Error response " +e.getMessage());
     }
}

}
class viewAttendancereport
{
    private final Connection connection;

    public viewAttendancereport(Connection connection) {
        this.connection = connection;
    }

    void  report()
    {
       final String query ="Select*from student";
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("stu_name");
                String roll = resultSet.getString("roll_no");
                int attended = resultSet.getInt("attendance_count");
                int total = resultSet.getInt("total_class");

                double percentage = (total == 0) ? 0.0 : (attended * 100.0) / total;

                System.out.println("========================");
                System.out.printf("%-17s: %s\n", "Name", name);
                System.out.printf("%-17s: %s\n", "Roll No", roll);
                System.out.printf("%-17s: %d/%d\n", "Classes Attended", attended, total);
                System.out.printf("%-17s: %.2f%%\n", "Attendance %", percentage);
                System.out.println("========================");
            }


        }
        catch(SQLException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }



}

class viewsortedstudentlist
{
    private final Connection connection;
  public  viewsortedstudentlist(Connection connection)
  {
      this.connection=connection;
  }

  void sorted()
  {
      final String query="SELECT * FROM student ORDER BY (attendance_count * 100.0 / total_class) DESC";
try {
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);
    while (resultSet.next()) {
        String name = resultSet.getString("stu_name");
        String roll = resultSet.getString("roll_no");
        int Totalclass = resultSet.getInt("attendance_count");
        int per = resultSet.getInt("total_class");
        double percentage = (per == 0) ? 0.0 : (Totalclass * 100.0) / per;
        System.out.println("========================");
        System.out.println("Name         : " + name);
        System.out.println("Roll No      : " + roll);
        System.out.println("Classes Attended : " + Totalclass);
        System.out.printf("Percentage   : %.2f%%\n", percentage);
        System.out.println("========================");

    }
}
catch (SQLException e)
{
    throw new RuntimeException(e.getMessage());
}
  }


}
class searchstudentattendace
{
private final Connection connection;
private final  Scanner sc;
public searchstudentattendace(Connection connection,Scanner sc)
{
    this.connection=connection;
    this.sc=sc;
}
void search()
{
    final String query ="Select stu_name,roll_no,attendance_count,total_class from student where roll_no=? ";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        System.out.println("Please Enter Roll Number of Student");
        String rolll=sc.next();
        preparedStatement.setString(1,rolll);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
            System.out.println(" No student found with Roll No: " + rolll);
            return;
        }
        while (resultSet.next())
        {
            String name = resultSet.getString("stu_name");
            String roll = resultSet.getString("roll_no");
            int Totalclass = resultSet.getInt("attendance_count");
            int per = resultSet.getInt("total_class");
            double percentage = (per == 0) ? 0.0 : (Totalclass * 100.0) / per;
            System.out.println("========================");
            System.out.println("Name         : " + name);
            System.out.println("Roll No      : " + roll);
            System.out.println("Classes Attended : " + Totalclass);
            System.out.printf("Percentage   : %.2f%%\n", percentage);
            System.out.println("========================");
        }
    }
    catch (SQLException e)
    {
        System.out.println("SQL Error: " + e.getMessage());

    }
}



}
class addstudent
{
    private final Connection connection;
    private final Scanner sc;
    public addstudent(Connection connection,Scanner sc)
    {
        this.connection=connection;
        this.sc=sc;
    }
    void add()
    {
        String totalclass = "SELECT MAX(total_class) AS total_class FROM student";
        System.out.println("Please Enter carefully Student Details");
        System.out.println("Please Enter Student name");
        String name=sc.next().trim();
        System.out.println("Please Enter Roll Number of Student");
        String roll=sc.next().trim();
        System.out.println("Please Enter attendance count if not Join anY Class Enter 0");
        int attendace=sc.nextInt();

        try {
             Statement statement=connection.createStatement();
             ResultSet resultSet = statement.executeQuery(totalclass);
             int total=0;
             if(resultSet.next())
             {
                 total=resultSet.getInt("total_class");
             }
             resultSet.close();
             statement.close();
            String query = "Insert into student(roll_no,stu_name,attendance_count,total_class) Values(?,?,?,?)";

            PreparedStatement preparedStatement =connection.prepareStatement(query);
            preparedStatement.setString(1,roll);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,attendace);
            preparedStatement.setInt(4,total);
            int row=preparedStatement.executeUpdate();
            if(row>0)
            {
                System.out.println("Student Succesfully Added in Database " +name +" "+roll);
            }
            else
            {
                System.out.println("Failed to Add Student in DataBase");
            }

        }
        catch (SQLException e)
        {
            System.out.println("Sql error " +e.getMessage());
        }


    }

}
class removestudent
{
    private final Connection connection;
    private final Scanner sc;
    public  removestudent(Connection connection,Scanner sc)
    {
        this.connection=connection;
        this.sc=sc;
    }
    void delete()
    {try {


        System.out.println("Please Enter roll number of Student you want to delete");
        String roll = sc.next().trim().toUpperCase();
        String query = "delete from student where roll_no=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,roll);
        int row= preparedStatement.executeUpdate();
        if (row>0)
        {
            System.out.println("Succefully Deleted Data from Database");
        }
        else
        {
            System.out.println("Sorry Something went Wrong Data not delete");
        }
    }
    catch (SQLException e)
    {
        System.out.println("Sql error " +e.getMessage());
    }
    }


}



