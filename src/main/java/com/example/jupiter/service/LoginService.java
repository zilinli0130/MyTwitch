//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of LoginService class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.service;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.util.Util;
import com.example.jupiter.dao.LoginDao;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// System includes
import java.io.IOException;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class LoginService {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public String verifyLogin(String userId, String password) throws IOException {
        password = Util.encryptPassword(userId, password);
        return loginDao.verifyLogin(userId, password);
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    @Autowired
    private LoginDao loginDao;
}

