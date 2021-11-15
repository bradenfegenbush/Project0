import java.sql.DriverManager;
//import java.sql.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.SQLException;
import scala.io.StdIn._


/*
    Features I'd liked to add:
        - Update a number
        - Check for duplicates
        - Add regular expressions in search

    Things to be done:
        -Fix terminal class path for JDBC driver
        -Make user menu to be recursive


*/




object ContactBook {

  def main(args: Array[String]) {
    // connect to the database named "phonebook" on the localhost


    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/phonebook"
    val username = "root"
    val password = "Chaser22"

    Class.forName(driver)
    var connection:Connection = DriverManager.getConnection(url, username, password)


    menu.printmenu(args)
    menu.getUserInput(args, connection)


    //println("Adding a name and number to database.")
    //addNameAndNumber.addAction(connection,"Scott","(737) 373-7387")

    //println("Display all contacts.")
   //isplayContacts.displayAll(connection)

    //println("Display contact with name, Conor McGregor")
    //displayContacts.displayNumber(connection,"Conor McGregor")

    //println("Deleting contact with name, Tom Brady")
    //deleteContact.removeNumber(connection, "Tom Brady")

    connection.close()
  }
}



object menu {
  def printmenu(args: Array[String]) {
    //Prints the options of commands.
    println("Hello! Welcome to the Phone Book. Sponsored by Scala.")
    println("Please type one of the following commands.")
    println("Add, Remove, Display, Search")

  }


  def getUserInput(args: Array[String], connection: Connection) {
    //Recieve the input from the user.
    println("What is your command? ")
    val actionInput = scala.io.StdIn.readLine()
    if (actionInput == "Add") {
      println("What is the name you would like to add? ")
      val nameInput = scala.io.StdIn.readLine()

      println("What is the number you would like to add? ")
      val numberInput = scala.io.StdIn.readLine()

      addNameAndNumber.addAction(connection,nameInput,numberInput)
    }
    else if (actionInput == "Remove") {
      println("What name would you like to delete? ")
      val deleteInput = scala.io.StdIn.readLine()

      deleteContact.removeNumber(connection,deleteInput)
    }
    else if (actionInput == "Display All") {
      displayContacts.displayAll(connection)
    }
    else if (actionInput == "Search") {
      println("Enter a name to find their number. ")
      val searchInput = scala.io.StdIn.readLine()

      displayContacts.displayNumber(connection,searchInput)
    }
    else {
      println("Goodbye! Changes are saved.")
    }
}



//Function to add a name and number as Strings to the database.
object addNameAndNumber  {
  def addAction(connection: Connection, name: String, number: String) {

    val statement = connection.createStatement()
    println("successful")

    val sql = "INSERT INTO people (name, number)  VALUES (?,?)";
    val pstmt: PreparedStatement = connection.prepareStatement(sql);
    pstmt.setString (1, name);
    pstmt.setString (2, number);
    pstmt.executeUpdate();
  }
}


//Function to show all contacts in database or display the number asscoiated with a given name.
object displayContacts {
  def displayAll(connection: Connection) {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM people;")
    while ( resultSet.next() ) {
      val name = resultSet.getString("name")
      val number = resultSet.getString("number")
      println("name, number = " + name + ", " + number)
    }
  }

  def displayNumber(connection: Connection, name: String) {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT number FROM people WHERE name = '"+name+"';")
    resultSet.next()
    val number = resultSet.getString("number")
    println(number)

  }

}

//Function to delete a contact from the database.
object deleteContact {
  def removeNumber(connection: Connection, name: String) {
    val statement = connection.createStatement()
    val sql = "DELETE FROM people WHERE name = '"+name+"';"
    val resultSet = statement.executeUpdate(sql)

    println("Contact Deleted.")
  }
}

}