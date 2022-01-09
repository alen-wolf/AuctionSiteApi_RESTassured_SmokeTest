import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class ApiSmokeTest {

    @Test(testName = "End to end user flow")
    public static void endToEndApiFlow(){
        when().
                get(Utils.HOME_URL).
                then().
                statusCode(200);
        //TODO implement rest of the api calls
    }

}
