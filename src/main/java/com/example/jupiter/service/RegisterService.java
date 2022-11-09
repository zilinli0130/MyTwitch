//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of RegisterService class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.service;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.dao.RegisterDao;
import com.example.jupiter.entity.db.User;
import com.example.jupiter.util.Util;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// System includes
import java.io.IOException;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class RegisterService {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public boolean register(User user) throws IOException {
        user.setPassword(Util.encryptPassword(user.getUserId(), user.getPassword()));
        return registerDao.register(user);
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    @Autowired
    private RegisterDao registerDao;
}



