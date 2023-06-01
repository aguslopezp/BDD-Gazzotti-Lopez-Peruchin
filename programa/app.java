import java.sql.*;
import java.util.Scanner;

public class app {

  public static void main(String[] args) {

    try {
      String driver = "com.mysql.cj.jdbc.Driver";
      String url = "jdbc:mysql://localhost:3306/prueba?serverTimezone=UTC";
      String username = "root";
      String password = "root";

      // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection = DriverManager.getConnection(url, username, password);

      System.out.print("1) Select persona\n");
      System.out.print("2) Insertar cliente\n");
      System.out.print("3) Salir\n");
      Scanner scanner = new Scanner(System.in);
      int selected = scanner.nextInt();
      while(selected != 3){
        System.out.print("1) Select persona\n");
        System.out.print("2) Insertar cliente\n");
        System.out.print("3) Salir\n");
        switch (selected){
          case 1: 
            selectPersona(connection);
            selected = 0;
            selected = scanner.nextInt();
          break;
          case 2: 
            insertClass(connection);
            selected = 0;
            selected = scanner.nextInt();
          break;
        }
      }
      scanner.close();
    }catch(Exception e){ 
      System.out.println(e);
    }  

  }

    public static void selectPersona(Connection c) {
      try{
        String query = "SELECT * FROM persona ";
        PreparedStatement statement = c.prepareStatement(query); // statement.setString(1, "2");
        ResultSet resultSet = statement.executeQuery(); // Send query to database and store results.
  
        // Print results.
        while (resultSet.next()) {
          System.out.print(" DNI: " + resultSet.getString("DNI"));
          System.out.print("; Nombre: " + resultSet.getString("nombre"));
          System.out.print("; Email: " + resultSet.getString("email"));
          System.out.print("\n   ");
          System.out.print("\n   ");
        }
      }
      // catch (ClassNotFoundException cnfe) {
      //   System.err.println("Error loading driver: " + cnfe);
      //   cnfe.printStackTrace();
      // } 
      catch (SQLException sqle) {
        sqle.printStackTrace();
        System.err.println("Error connecting: " + sqle);
      } catch (Exception sqle) {
        sqle.printStackTrace();
        System.err.println("Error connecting: " + sqle);
      }
    }
      

    public static void insertClass(Connection c){
      Scanner scanner = new Scanner(System.in);
      System.out.print("Inserte nombre de la clase\n");
      String nombre = scanner.nextLine();
      System.out.print("Inserte cupo máximo\n");
      String cupo_max = scanner.nextLine();
      System.out.print("Inserte descripción\n");
      String descripcion = scanner.nextLine();
      System.out.print("Inserte dni de la secretaria a cargo\n");
      String dni_secretaria = scanner.nextLine();
      System.out.print("Inserte dni del instructor responsable \n");
      String dni_instructor = scanner.nextLine();
      scanner.close();
      try{ 
        Statement statement = c.createStatement();
        String query = "INSERT INTO Clase-Teorica (id_clase, nombre, cupo_max, descripción, DNI_Secretaria, DNI_Instructor_responsable) VALUES (18, \""+nombre+"\", \""+cupo_max+"\", \""+descripcion+"\", \""+dni_secretaria+"\", \""+dni_instructor+"\");";
        int resultInsert = statement.executeUpdate(query);
        if(resultInsert==1) {
          System.out.print("Insertado correctamente \n");
        }
        else {
          System.out.print("Error: por favor intente de nuevo \n");
        }
      }
      catch(Exception e){ System.out.println(e);} 
    }
}
