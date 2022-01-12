import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class User {
    private int userId;
    private String email;
    private final String password = "12345";
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private final String [] role = {"user","seller"};
    private String refreshToken;
    private String token;
    private int bidCount;
    private Float highestBid;
    private Float amount;
}
