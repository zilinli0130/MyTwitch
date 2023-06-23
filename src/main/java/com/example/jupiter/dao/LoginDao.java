//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of LoginDao class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.dao;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.entity.db.User;

// Framework includes
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Repository
public class LoginDao {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    // Verify if the given user Id and password are correct. Returns the user name when it passes
    public String verifyLogin(String userId, String password) {
        String name = "";

        //https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userId);
            if(user != null && user.getPassword().equals((password))) {
                name = user.getFirstName();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    @Autowired
    private SessionFactory sessionFactory;
}

