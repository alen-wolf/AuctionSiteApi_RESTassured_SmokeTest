import io.restassured.response.Response;

public class CatalogSOM {

    public static boolean categoryMatch(Response response,String category,String subcategory){
        for (int i = 0; i < response.jsonPath().getList("$").size(); i++){
            String categoryPath = "[x].category";
            String subcategoryPath = "[x].subcategory";

            String new1 =response.jsonPath().get(categoryPath.replace("x",Integer.toString(i)));
            String new2 =response.jsonPath().get(subcategoryPath.replace("x",Integer.toString(i)));

            if(!new1.equals(category))
                return false;
            if(!new2.equals(subcategory))
                return false;
        }
        return true;
    }
}
