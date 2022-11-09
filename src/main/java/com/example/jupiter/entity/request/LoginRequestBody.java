//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of LoginRequestBody class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.entity.request;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

public class LoginRequestBody {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @JsonCreator
    public LoginRequestBody(@JsonProperty("user_id") String userId, @JsonProperty("password") String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    private final String userId;
    private final String password;
}


