package com.expleo.qe;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class dataAndFileProcessing {

    public static String myString = "";
    static int count = 0;

    String file = "C:\\Users\\7390\\IdeaProjects\\JhbGrads20104_ProjectDay2_Maserole\\Files\\Project Day2.txt";
    private static Connection conn = dbConnection.getConnection();
    private static ResultSet myResultSet =null;
    private static String dataLine = "";

    public dataAndFileProcessing() {
    }


    public static String getDataLine(){

        return dataLine;
    }
    public void fileManipulation() {

        File myFile = new File(file);
        Scanner myReader;

        try{

            myReader = new Scanner(new FileReader(myFile));
            myReader.nextLine();

            while (myReader.hasNextLine()) {

                this.myString = myReader.nextLine();
                String [] myArr = myString.split(",");

                try {
                    dataLine = dataLine + myArr[0] + " " + myArr[1] + " " + myArr[2] + " " + myArr[3] + " " + myArr[4] + " " + myArr[5];

                    //Creating a file with a database name
                    File dFile = new File("Files/"+myArr[0] + ".db");
                    if (dFile.exists() == true) {
                        if (myTable(myArr[1]).equalsIgnoreCase(myArr[1])) {
                            if (keyColumn1(myArr[2], myArr[1]) == true) {
                                if (getKeyColumn1(myArr[1], myArr[3], myArr[2]).equalsIgnoreCase(myArr[3])) {
                                    if (columnExist2(myArr[4], myArr[1]) == true) {
                                        if (columnValue2(myArr[4], myArr[1], myArr[5]).equalsIgnoreCase(myArr[5])) {
                                            dataLine += " OK\n";
                                        }else{

                                            try {

                                                assertThat(" Invalid Column Value", myArr[5], is(equalTo(myTable(myArr[1]))));
                                            } catch (AssertionError e) {

                                                dataLine += " Invalid Value " + "\n";
                                                // System.out.println(e.getMessage());
                                                continue;
                                            }
                                        }
                                    }else{

                                        try {

                                            assertThat(" Invalid Column", myArr[4], is(equalTo(myTable(myArr[1]))));
                                        } catch (AssertionError e) {

                                            dataLine += " Invalid Column " + "\n";
                                            continue;
                                        }

                                    }
                                }else{

                                    try {

                                        assertThat(" Invalid Primary Key Value", myArr[3], is(equalTo(myTable(myArr[1]))));

                                    } catch (AssertionError e) {

                                        dataLine += " Invalid Primary Key Value " + "\n";
                                        continue;
                                    }
                                }
                            }else{

                                try {

                                    assertThat(" Invalid Primary Key Name", myArr[2], is(equalTo(myTable(myArr[1]))));

                                } catch (AssertionError e) {

                                    dataLine += " Invalid Primary Key Name " + "\n";
                                    continue;
                                }
                            }

                        } else {
                            try {

                                assertThat(" Invalid Table", myArr[1], is(equalTo(myTable(myArr[1]))));
                            } catch (AssertionError e) {

                                dataLine += " Invalid Table "+ "\n";
                                // System.out.println(e.getMessage());
                                continue;
                            }
                        }

                    } else {
                        try {

                            assertThat(" Invalid Database", true, is(dFile.exists()));
                        } catch (AssertionError e) {
                            dataLine += " Invalid Database" + "\n";
                            continue;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    if (ex.getMessage().equals("0")) {
                        System.out.println("Error: Database can not be empty at index " + ex.getMessage());
                    } else if (ex.getMessage().equals("1")) {
                        System.out.println("Error: Table can not be empty at index " + ex.getMessage());
                    } else if (ex.getMessage().equals("2")) {
                        System.out.println("Error: Key Column can not be empty at index " + ex.getMessage());
                    } else if (ex.getMessage().equals("3")) {
                        System.out.println("Error: Key Value can not be empty at index " + ex.getMessage());
                    } else if (ex.getMessage().equals("4")) {
                        System.out.println("Error: Column can not be empty at index " + ex.getMessage());
                    } else if (ex.getMessage().equals("5")) {
                        System.out.println("Error: Column Value can not be empty at index " + ex.getMessage());
                    }
                }

            }

        }catch (FileNotFoundException e){

            assertThat("File Does Not Exist In Directory", myFile.exists());
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Checking if the table exist
    public static String myTable(String tableName) throws SQLException {

        String  sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"'";
        PreparedStatement myStatement;

        try {

            myStatement = conn.prepareStatement(sql);
            myResultSet = myStatement.executeQuery();

        } catch (SQLException e) {

            System.out.println("Sql Error, "+ e.getMessage());
        }
        return myResultSet.getString("name");
    }

    //Check if table column exist
    public static boolean keyColumn1(String name,String tableName){

        boolean isContained = false;

        String sql = "select count("+name+") 'column' FROM '" + tableName + "'";
        PreparedStatement myStatement = null;

        try {

            myStatement = conn.prepareStatement(sql);
            myResultSet = myStatement.executeQuery();

            if(Integer.valueOf(myResultSet.getString("column"))>0){

                isContained = true;
            }
        } catch (SQLException e) {
            System.out.println( name + " does not exist");
        }
        return isContained;
    }

    public static String columnValue2(String columnName,String tableName,String columnValue){

        String sql="select "+columnName+" 'value' from "+tableName+" where "+columnName+" = '"+columnValue+"'";
        PreparedStatement myStatement;
        String index = "";
        try {
            myStatement = conn.prepareStatement(sql);
            myResultSet = myStatement.executeQuery();
            index = myResultSet.getString("value");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return index;
    }
    public static boolean columnExist2(String name,String tableName){

        boolean isContained = false;
        String sql = "select count("+name+") 'column' FROM '" + tableName + "'";
        PreparedStatement myStatement = null;

        try {

            myStatement = conn.prepareStatement(sql);
            myResultSet = myStatement.executeQuery();

            if(Integer.valueOf(myResultSet.getString("column"))>0){
                isContained = true;
            }
        } catch (SQLException e) {
            System.out.println( name + " does not exist");
        }
        return isContained;
    }

    public static String getKeyColumn1(String tableName,String keyColumnValue,String keyColumn){

        String sql = "select " + keyColumnValue + " 'Value' from " + tableName + " where " + keyColumn + " = '" + keyColumnValue + "'";
        PreparedStatement myStatement = null;
        String index = "";

        try {

            myStatement = conn.prepareStatement(sql);
            myResultSet = myStatement.executeQuery();
            index = myResultSet.getString("Value");

        } catch (SQLException e) {
            System.out.println(keyColumnValue+ " Does not match");
        }
        return index;
    }

    public boolean myFileIsFound(String fName)
    {
        File myFile = new File(fName);
        boolean found = false;

        if(myFile.exists())
        {
            found = true;
        }

        return found;
    }

}

