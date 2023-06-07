import java.sql.*;
import java.util.Scanner;

public class app {

  public static void main(String[] args) {

    try {
      Connection connection = ConnectionMySQL.getInstance();
      int selected=0;
      Scanner scanner = new Scanner(System.in);
      do{
        mostrarMenu();
        selected = scanner.nextInt();
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
          case 3:
            listClientClass(connection);
            selected = 0;
            selected = scanner.nextInt();
          break;
        }

      } while (selected != 4);
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
      scanner.close();
      try {
        Statement statement = c.createStatement();
        String query = "INSERT INTO Toma (DNI_Cliente, id_clase) VALUES (\""+DNI_Cliente+"\", \""+id_clase+"\");"; 
        
        int resultInsert = statement.executeUpdate(query);
        if(resultInsert==1) {
          System.out.print("Cliente " + DNI_Cliente + " inscripto correctamente a la clase " + id_clase + " \n");
        }
        else {
          System.out.print("Error: por favor intente de nuevo \n");
        }
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
        String query = "INSERT INTO Clase_Teorica (id_clase, nombre, cupo_max, descripcion, DNI_Secretaria, DNI_Instructor_responsable) VALUES (18, \""+nombre+"\", \""+cupo_max+"\", \""+descripcion+"\", \""+dni_secretaria+"\", \""+dni_instructor+"\");";
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
        String query1 = "CREATE TEMPORARY TABLE clasesTomadas AS SELECT Cliente.DNI_Cliente as dni_cliente, Cliente.nombre, Cliente.apellido, Toma.id_clase FROM Cliente LEFT JOIN Toma ON Cliente.DNI_Cliente = Toma.DNI_Cliente; " ;
        String query2 = "SELECT clasesTomadas.DNI_Cliente, clasesTomadas.nombre AS Nombre, clasesTomadas.apellido AS Apellido, Clase_Teorica.nombre AS 'Nombre Clase' FROM clasesTomadas INNER JOIN Clase_Teorica ON clasesTomadas.id_clase = Clase_Teorica.id_clase;";
        PreparedStatement statement1 = c.prepareStatement(query1);
        statement1.executeUpdate();
        PreparedStatement statement2 = c.prepareStatement(query2);
        ResultSet resultSet = statement2.executeQuery();
        
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
      catch (SQLException sqle) {
        sqle.printStackTrace();
        System.err.println("Error connecting: " + sqle);
      } catch (Exception sqle) {
        sqle.printStackTrace();
        System.err.println("Error connecting: " + sqle);
      }
    }
    
    private static void mostrarMenu (){
      System.out.print("1) Insertar clase.\n");
      System.out.print("2) Inscribir cliente a una clase.\n");
      System.out.print("3) Listar los cliente y las clases que tomaron.\n");
      System.out.print("4) Salir.\n");
    }
  }

/*
  comandos para compilar en Ubuntu
  sudo chmod 777 .
  javac ConnectionMySQL.java
  java -classpath ".:mysql-connector-java-8.0.20.jar" ConnectionMySQL
  java -classpath ".:mysql-connector-java-8.0.20.jar" app.java
*/