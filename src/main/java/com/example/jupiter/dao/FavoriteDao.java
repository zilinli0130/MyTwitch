//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10.22
// * Definition: Implementation of FavoriteDao class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.dao;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;
import com.example.jupiter.entity.db.User;

// Framework includes
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// System includes
import java.util.*;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Repository
public class FavoriteDao {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    // Insert a favorite record to the database
    public void setFavoriteItem(String userId, Item item) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            user.getItemSet().add(item);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    // Remove a favorite record from the database
    public void unsetFavoriteItem(String userId, String itemId) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    public Set<Item> getFavoriteItems(String userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId).getItemSet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new HashSet<>();
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @Autowired
    private SessionFactory sessionFactory;
}

