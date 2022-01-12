import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ApiSmokeTest {

    @BeforeTest
    static void preCondition(){
        baseURI = Utils.BASE_URL;
    }

    public static User user = User.builder().email(Utils.randomEmail()).firstName(Utils.randomFirstName()).lastName(Utils.randomLastName()).phoneNumber(Utils.randomPhoneNumber()).build();

    @Test(testName = "E2E API Flow")
    public static void endToEndApiFlow(){
//Landing page
        when().
                get(Utils.HOME_URL).
                then().
                statusCode(200);
//Sign-up
        Response singInResponse =
                given().
                        contentType(ContentType.JSON).
                        body(user).
                        post("/api/auth/signup");

        Assert.assertEquals(singInResponse.getStatusCode(),200);
        Assert.assertEquals(singInResponse.jsonPath().get("message"),Utils.signInMessage,Utils.errorBadResponse(Utils.signInMessage,singInResponse.jsonPath().get("message")));
//Log-in
        Response logInResponse =
                given().
                        contentType("application/json\r\n").
                        body(user).
                        when().
                        post("/api/auth/signin");

        Assert.assertEquals(logInResponse.getStatusCode(),200);
        Assert.assertEquals(logInResponse.jsonPath().get("email"),user.getEmail(),Utils.errorBadResponse(user.getEmail(),logInResponse.jsonPath().get("email")));
        Assert.assertEquals(logInResponse.jsonPath().get("firstName"),user.getFirstName(),Utils.errorBadResponse(user.getFirstName(),logInResponse.jsonPath().get("firstName")));
        Assert.assertEquals(logInResponse.jsonPath().get("lastName"),user.getLastName(),Utils.errorBadResponse(user.getLastName(),logInResponse.jsonPath().get("lastName")));
        user.setRefreshToken(logInResponse.jsonPath().get("refreshToken"));
        user.setUserId(logInResponse.jsonPath().get("id"));
//Refresh token
        Response tokenResponse =
                given().
                        contentType(ContentType.JSON).
                        body(user).
                        when().
                        post("/api/auth/refreshtoken");

        Assert.assertEquals(tokenResponse.getStatusCode(),200);
        Assert.assertEquals(tokenResponse.jsonPath().get("refreshToken"),user.getRefreshToken(),Utils.errorBadResponse(user.getRefreshToken(),logInResponse.jsonPath().get("refreshToken")));
        user.setToken(tokenResponse.jsonPath().get("accessToken"));
//Get Items catalog
        Response catalogResponse =
                given().
                        contentType(ContentType.JSON).
                        with().
                        queryParam("search","").
                        queryParam("category","").
                        queryParam("subcategory","Sportswear/Female").
                        queryParam("minPrice","0").
                        queryParam("maxPrice","0").
                        queryParam("sortBy","PRICE_SORT").
                        queryParam("direction","ASC").
                        queryParam("pageNumber","0").
                        queryParam("pageSize","1000").
                        when().
                        get("/api/v1/item/search");

        Assert.assertEquals(catalogResponse.getStatusCode(),200);
        Assert.assertTrue(CatalogSOM.categoryMatch(catalogResponse,"Sportswear","Female"),Utils.categoryMatch);
//Get Single item
        Response itemResponse = given().
                contentType(ContentType.JSON).
                with().
                pathParam("itemId","8").
                when().
                get("/api/v1/item/{itemId}");

        Assert.assertEquals(itemResponse.getStatusCode(),200);
        Float startingPrice = itemResponse.jsonPath().get("startingPrice");
        Float highestBid = itemResponse.jsonPath().get("highestBid");
        Assert.assertTrue( startingPrice < highestBid ,Utils.bidPrice);
        user.setHighestBid(highestBid);
        user.setBidCount(itemResponse.jsonPath().get("numberOfBids"));
//Place Bid
        user.setAmount(user.getHighestBid()+2);
        Response bidResponse =  given().
                header("Authorization","Bearer " + user.getToken()).
                contentType(ContentType.JSON).
                body(user).
                with().
                pathParam("itemId","8").
                when().
                post("/api/v1/bid/place/{itemId}");

        Assert.assertEquals(bidResponse.getStatusCode(),200);
        Assert.assertEquals(bidResponse.jsonPath().get("message"),Utils.highestBidder,Utils.errorBadResponse(Utils.highestBidder,bidResponse.jsonPath().get("message")));
//Bid Count
        Response countResponse = given().
                contentType(ContentType.JSON).
                with().
                pathParam("itemId","8").
                when().
                post("/api/v1/bid/count/{itemId}");

        Assert.assertEquals(bidResponse.getStatusCode(),200);
        int newBidCount =Integer.parseInt(countResponse.body().asString());
        Assert.assertTrue(newBidCount > user.getBidCount(),Utils.bidCountMatch);
//Log-out
        Response logOutResponse = given().
                contentType(ContentType.JSON).
                body(user).
                when().
                post("/api/auth/logout");

        Assert.assertEquals(logOutResponse.getStatusCode(),200);
        Assert.assertEquals(logOutResponse.jsonPath().get("message"),Utils.logOutMessage,Utils.errorBadResponse(Utils.logOutMessage,logOutResponse.jsonPath().get("message")));
    }
}
