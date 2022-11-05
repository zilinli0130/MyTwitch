//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of FavoriteService class.
// * Note: <Info>
//**********************************************************************************************************************

package com.example.jupiter.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************
// Project includes
import com.example.jupiter.dao.FavoriteDao;
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// System includes
import java.util.*;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Service
public class FavoriteService {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public void setFavoriteItem(String userId, Item item) {
        favoriteDao.setFavoriteItem(userId, item);
    }

    public void unsetFavoriteItem(String userId, String itemId) {
        favoriteDao.unsetFavoriteItem(userId, itemId);
    }

    public Map<String, List<Item>> getFavoriteItems(String userId) {
        Map<String, List<Item>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }
        Set<Item> favorites = favoriteDao.getFavoriteItems(userId);
        for(Item item : favorites) {
            itemMap.get(item.getType().toString()).add(item);
        }
        return itemMap;
    }


//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @Autowired
    private FavoriteDao favoriteDao;
}
