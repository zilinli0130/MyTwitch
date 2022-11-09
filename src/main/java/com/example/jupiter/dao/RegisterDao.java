//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of RegisterDao class.
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

// System includes
import javax.persistence.PersistenceException;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Repository
public class RegisterDao {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public boolean register(User user) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (PersistenceException | IllegalStateException ex) {
            // if hibernate throws this exception, it means the user already be register
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
        return true;
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    @Autowired
    private SessionFactory sessionFactory;
}



