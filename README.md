JavaReddAPIWrapperREST
======================

GET and POST data from/to the ReddAPI using REST

You can test the wrapper by creating a class similar this one:

    import java.io.IOException;

    ```java
    public class testAPI {

     public static void main(String[] args) throws IOException {
     API ReddAPI = new API("5M8xfAQAOBRjej2B9eloWK7BH0VEee9XByjnwbMvH8u5Ja2Wd9MeT6UVo6qRpK7j", "HeKyMT38Dvp34sSh0YrQTZP47jE8ZdE08b0APkBMt5CWcA4P8Y7HmQzr3q5aO4H0");
     System.out.println(ReddAPI.getUserBalance("someusername"));
     System.out.println(ReddAPI.getUserList());
     System.out.println(ReddAPI.getUserInfo("someusername"));
     System.out.println(ReddAPI.sendToAddress("someusername", "someRDDaddress", 1.0));
     System.out.println(ReddAPI.moveToUser("someusername", "someusername", 1.0));
     System.out.println(ReddAPI.createNewUser("someusername"));
     }
    }
    ```

