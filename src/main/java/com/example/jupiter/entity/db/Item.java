//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of Item class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.entity.db;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// System includes
import java.io.Serializable;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {


//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    public String getBroadcasterName() {
        return broadcasterName;
    }

    public void setBroadcasterName(String broadcasterName) {
        this.broadcasterName = broadcasterName;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public ItemType getType() {
        return type;
    }
    public void setType(ItemType type) {
        this.type = type;
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("broadcaster_name")
    @JsonAlias({ "user_name" })
    private String broadcasterName;
    @JsonProperty("game_id")
    private String gameId;
    @JsonProperty("item_type")
    private ItemType type;
}
