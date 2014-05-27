JavaReddAPIWrapperREST
======================

GET and POST data from/to the ReddAPI using REST

You can test the wrapper by creating a class similar this one:

```java
    import java.io.IOException;
    
    public class testAPI {

     public static void main(String[] args) throws IOException {
        API ReddAPI = new API("somekeyGET", "somekeyPOST");
        System.out.println(ReddAPI.getUserBalance("someusername"));
        System.out.println(ReddAPI.getUserBalanceDetail("someusername"));
        System.out.println(ReddAPI.getUserList());
        System.out.println(ReddAPI.getUserInfo("someusername"));
        System.out.println(ReddAPI.sendToAddress("someusername", "someRDDaddress", 1.0));
        System.out.println(ReddAPI.moveToUser("someusername", "someusername", 1.0));
        System.out.println(ReddAPI.createNewUser("someusername"));
     }
    }
```

