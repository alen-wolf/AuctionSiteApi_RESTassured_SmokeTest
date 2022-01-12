import com.github.javafaker.Faker;

import java.util.Random;

public class Utils {
    public final static String HOME_URL = "https://abh-auction.herokuapp.com/";
    public final static String BASE_URL = "https://abh-auction-api.herokuapp.com";

    public final static String signInMessage = "User successfully registered";
    public final static String categoryMatch = "Category of item doesn't match!";
    public final static String highestBidder = "Congrats! You are the highest bidder";
    public final static String logOutMessage = "Log out successful!";
    public final static String bidPrice = "Current bid lower than starting price!";
    public final static String bidCountMatch = "Bid count did not increase";

    public static String errorBadResponse(String expected, String actual){return "Bad response expected: " + expected + " got: " + actual;}

    public static String randomEmail(){
        Random rand = new Random();
        int length = rand.nextInt(11-5)+5;
        String CHARSEQ = "abcdefghijklmnopqrstuvwxyz-._1234567890";
        StringBuilder name = new StringBuilder();

        for(int i=0; i<length; i++){
            Character r = CHARSEQ.charAt(rand.nextInt(39));
            if(i==0 || i==length-1){
                while(r == '-' || r == '.' || r == '_'){
                    r = CHARSEQ.charAt(rand.nextInt(39));
                }
            }
            name.append(r);
        }
        return name + "@mail.com";
    }

    public static String randomFirstName(){
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public static String randomLastName(){
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    public static String randomPhoneNumber(){
        Faker faker = new Faker();
        return faker.phoneNumber().cellPhone();
    }
}
