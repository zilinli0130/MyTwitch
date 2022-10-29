//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of Game class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.entity.response;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Game.Builder.class)

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
public class Game {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getBoxArtUrl() {
        return boxArtUrl;
    }

//**********************************************************************************************************************
// * Private class constructors
//**********************************************************************************************************************
    private Game(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.boxArtUrl = builder.boxArtUrl;
    }

//**********************************************************************************************************************
// * Inner Class definition
//**********************************************************************************************************************
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Builder {

//**********************************************************************************************************************
// * Private attributes/methods
//**********************************************************************************************************************
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("box_art_url")
        private String boxArtUrl;
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder boxArtUrl(String boxArtUrl) {
            this.boxArtUrl = boxArtUrl;
            return this;
        }

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
        public Game build() {
            return new Game(this);
        }
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @JsonProperty("id")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("box_art_url")
    private final String boxArtUrl;
}



