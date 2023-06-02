import java.sql.*;
import java.util.Scanner;

public class app {

  public static void main(String[] args) {

    try {
      ConnectionMySQL conectMySQL = new Connect();

      // Load database driver if not already loaded.
      Class.forName(driver);
      // Establish network connection to database.
      Connection connection = DriverManager.getConnection(conectMySQL.url, conectMySQL.username, conectMySQL.password);

      System.out.print("1) Select persona\n");
      System.out.print("2) Insertar cliente\n");
      System.out.print("3) Salir\n");
      Scanner scanner = new Scanner(System.in);
      int selected = scanner.nextInt();
      while(selected != 3){
        System.out.print("1) Insertar cliente.\n");
        System.out.print("2) Inscribir cliente a una clase.\n");
        System.out.print("3) Salir.\n");
        switch (selected){
          case 1: 
            insertClass(connection);
            selected = 0;
            selected = scanner.nextInt();
          break;
          case 2:
            insertClientClass(connection);
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

    public static void insertClientClass(Connection c) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Inserte el dni del cliente\n");
      String DNI_Cliente = scanner.nextLine();
      System.out.print("Inserte identificador de la clase\n");
      String id_clase = scanner.nextLine();
      try {
        String query = "INSERT INTO Toma (DNI_Cliente, id_clasee) VALUES (\""+DNI_Cliente+"\", \""+id_clase+"\");";
        int resultInsert = statement.executeUpdate(query);
        if(resultInsert==1) {
          System.out.print("Cliente " + DNI_Cliente + " inscripto correctamente a la clase " + id_clase + " \n");
        }
        else {
          System.out.print("Error: por favor intente de nuevo \n");
        }
      }
      catch (ClassNotFoundException cnfe) {
        System.err.println("Error loading driver: " + cnfe);
        cnfe.printStackTrace();
      } 
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

    public static void listClientClass(Connection c) {
      try {
        String query = "CREATE TEMPORARY TABLE clasesTomadas AS SELECT Cliente.DNI_Cliente as dni_cliente, Cliente.nombre, Cliente.apellido, Toma.id_clase FROM Cliente LEFT JOIN Toma ON Cliente.DNI_Cliente = Toma.DNI_Cliente;     SELECT clasesTomadas.DNI_Cliente, clasesTomadas.nombre AS Nombre, clasesTomadas.apellido AS Apellido, Clase_Teorica.nombre AS 'Nombre Clase' FROM clasesTomadas INNER JOIN Clase_Teorica ON clasesTomadas.id_clase = Clase_Teorica.id_clase;" ;
        PreparedStatement statement = c.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        
        // Print results.
        while (resultSet.next()) {
          System.out.print(" DNI Cliente: " + resultSet.getString("DNI_Cliente"));
          System.out.print("; Nombre cliente: " + resultSet.getString("Nombre"));
          System.out.print("; Apellido cliente: " + resultSet.getString("Apellido"));
          System.out.print("; Clase que toma: " + resultSet.getString("Nombre Clase"));
          System.out.print("\n   ");
          System.out.print("\n   ");
        }
      }
      catch (ClassNotFoundException cnfe) {
        System.err.println("Error loading driver: " + cnfe);
        cnfe.printStackTrace();
      } 
      catch (SQLException sqle) {
        sqle.printStackTrace();
        System.err.println("Error connecting: " + sqle);
      } catch (Exception sqle) {
        sqle.printStackTrace();
        System.err.println("Error connecting: " + sqle);
      }
    }
  }
