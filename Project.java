import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

interface library {
    public void add_Deatail(String Name, String Father_name, int child_age,
            String Book_Name, String date, String location) throws SQLException;

    public void See_Detail() throws SQLException;

    public void DeleteData(String cname, String rnumber);

    public void Indivdual_Selection(String name, String rnumber);
}

class fetch extends Thread {
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    public void run() {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_detail", "root", "")) {
            try {
                java.sql.Statement stml = con.createStatement();
                ResultSet rs = stml.executeQuery("select * from detail");

                while (rs.next() == true) {
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "
                            + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                } 
                con.close();
                con.close();

            } catch (SQLException e) {
                System.out.println("Probram occured" + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();

        }
    }

}

class Lib {

    public void add_Deatail(String Name, String Roll_Number, String Department,
            String Book_Name, String date, String Time_Duration) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_detail",
                "root", "");
        try (Connection conn = SQLConnection.makeConnection()) {
            String sqlQuery = "insert into detail values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setString(1, Name);
            pst.setString(2, Roll_Number);
            pst.setString(3, Department);
            pst.setString(4, Book_Name);
            pst.setString(5, date);
            pst.setString(6, Time_Duration);
            if (pst.executeUpdate() == 1)
                System.out.println("Record Saved To SQl Database");
            else
                System.out.println("Record Saved To SQl Database Error");
        } catch (Exception e) {
            System.out.println("Exception is occured");
            e.printStackTrace();
        }

    }

    fetch f = new fetch();

    public void See_Detail() throws SQLException {
        f.start();
        try{
         f.join();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void Indivdual_Selection(String name, String rnumber) {
        try {
            // con =
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_detail",
                    "root", "");

            String sqlQuery1 = "select * from detail where Name = ? and Roll_Number= ?";
            PreparedStatement pst = con.prepareStatement(sqlQuery1);
            pst.setString(1, name);
            pst.setString(2, rnumber);
            ResultSet rs1 = pst.executeQuery();
            if (rs1.next() == false) {
                System.out.println("Sorry record not found");
            } else {
                System.out.println("**************************");
                System.out.println("Name" + " " + rs1.getString(1));
                System.out.println("Roll Number" + " " + rs1.getString(2));
                System.out.println("Age" + " " + rs1.getString(3));
                System.out.println("Book Name" + " " + rs1.getString(4));
                System.out.println("Date" + " " + rs1.getString(5));
                System.out.println("Time Duration" + " " + rs1.getString(6));
                System.out.println("**************************");

            }
        } catch (Exception e) {

            System.out.println("Exception occured");
            e.printStackTrace();

        }
    }

    public void DeleteData(String cname, String rnumber) {
        String sql = "delete  from detail where Name=? and Roll_number=?";
        try {
            // con =
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_detail",
                    "root", "");
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, cname);
            pst.setString(2, rnumber);
            pst.execute();
            System.out.println("Data Delected");
        } catch (SQLException e) {
            System.out.println("Data is not find in the data base ");
            e.printStackTrace();
        }

    }

}

public class Project {
    public static void main(String[] args) throws SQLException {

        Lib v = new Lib();

        Scanner sc = new Scanner(System.in);
        int cont;
        do {
            System.out.println("**************************Library Management System***************************");
            System.out.println("1 : Taking book process ");
            System.out.println("2 : All Result");
            System.out.println("3 : Search Book Result");
            System.out.println("4 : Delete Book Data");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Enter Student Name ");
                    String sname = sc.nextLine();
                    System.out.println("Enter Roll Number ");
                    String rnumber = sc.nextLine();
                    System.out.println("Enter Department");
                    String Department = sc.nextLine();
                    sc.nextLine();
                    System.out.println("Enter Book Name");
                    String book = sc.nextLine();

                    System.out.println("Enter  Date");
                    String date = sc.nextLine();
                    System.out.println("Enter Time Duration ");
                    String time = sc.nextLine();
                v.add_Deatail(sname, rnumber, Department, book, date, time);
                    break;
                case 2:
                    v.See_Detail();

                    break;
                case 3:
                    System.out.println("Enter Student Name ");
                    String name = sc.nextLine();
                    System.out.println("Enter Roll number ");
                    String rnum = sc.nextLine();
                    v.Indivdual_Selection(name, rnum);
                    break;
                case 4:
                    System.out.println("Enter Student Name ");
                    String cname = sc.nextLine();
                    System.out.println("Enter Roll number");
                    String rnum1= sc.nextLine();
                    v.DeleteData(cname, rnum1);
                    break;
                default:
                    System.out.println("Select above options");
                    break;
            }

            System.out.println("Press 0 to continue");

            cont = sc.nextInt();

        } while (cont == 0);

        sc.close();
    }
}
