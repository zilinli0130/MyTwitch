//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of GameService class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.service;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.entity.response.Game;

// Framework includes
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

// System includes
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class GameService {

//**********************************************************************************************************************
// * Public methods: search game/video/clip/stream
//**********************************************************************************************************************

    // Integrate search() and getGameList() together, returns the top x popular games from Twitch.
    public List<Game> GetTopGames(int limit) {
        if (limit <= 0) {
            limit = DEFAULT_GAME_LIMIT;
        }
        return GetGameList(SearchByTwitchAPIs(BuildGameURL(TOP_GAME_URL, "", limit)));
    }

    // Integrate search() and getGameList() together, returns the dedicated game based on the game name.
    public Game SearchGame(String gameName) {
        List<Game> gameList = GetGameList(SearchByTwitchAPIs(BuildGameURL(GAME_SEARCH_URL_TEMPLATE, gameName, 0)));
        if (gameList.size() != 0) {
            return gameList.get(0);
        }
        return null;
    }

    public Map<String, List<Item>> SearchItems(String gameId) throws TwitchException {
        Map<String, List<Item>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), SearchByItemType(gameId, type, DEFAULT_SEARCH_LIMIT));
        }
        return itemMap;
    }

//**********************************************************************************************************************
// * Private methods: build URLs
//**********************************************************************************************************************

    // Build the request URL which will be used when calling Twitch APIs,
    // e.g. https://api.twitch.tv/helix/games/top when trying to get top games.
    private String BuildGameURL(String url, String gameName, int limit) {
        if (gameName.equals("")) {
            return String.format(url, limit);
        } else {
            try {
                // Encode special characters in URL, e.g. Rick Sun -> Rick%20Sun
                gameName = URLEncoder.encode(gameName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return String.format(url, gameName);
        }
    }

    // Similar to buildGameURL, build item URL that will be used when calling Twitch API.
    // e.g. https://api.twitch.tv/helix/clips?game_id=12924.
    private String BuildItemURL(String url, String gameId, int limit) {
        try {
            gameId = URLEncoder.encode(gameId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(url, gameId, limit);
    }

//**********************************************************************************************************************
// * Private methods: search through switch APIs
//**********************************************************************************************************************

    // Send HTTP request to Twitch Backend based on the given URL, and returns the body of the HTTP response
    // returned from Twitch backend.
    private String SearchByTwitchAPIs(String url) throws TwitchException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // Define the response handler to parse and return HTTP response body returned from Twitch
        ResponseHandler<String> responseHandler = response -> {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {
                System.out.println("Response status: " + response.getStatusLine().getReasonPhrase());
                throw new TwitchException("Failed to get result from Twitch API");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new TwitchException("Failed to get result from Twitch API")   ;
            }
            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            return obj.getJSONArray("data").toString();
        };

        try {
            // Define the HTTP request, TOKEN and CLIENT_ID are used for user authentication on Twitch backend
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", TOKEN);
            request.setHeader("Client-Id", CLIENT_ID);
            return httpclient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TwitchException("Failed to get result from Twitch API");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//**********************************************************************************************************************
// * Private methods: parse the search results
//**********************************************************************************************************************

    // Convert JSON format data returned from Twitch to an Arraylist of Game objects
    private List<Game> GetGameList(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(data, Game[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse game data from Twitch API");
        }
    }

    // Similar to getGameList, convert the json data returned from Twitch to a list of Item objects.
    private List<Item> GetItemList(String data) throws TwitchException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(data, Item[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new TwitchException("Failed to parse item data from Twitch API");
        }
    }

//**********************************************************************************************************************
// * Private methods: search helpers
//**********************************************************************************************************************

    // Returns the top x streams based on game ID.
    private List<Item> SearchStreams(String gameId, int limit) throws TwitchException {
        List<Item> streams = GetItemList(SearchByTwitchAPIs(BuildItemURL(STREAM_SEARCH_URL_TEMPLATE, gameId, limit)));
        for (Item item : streams) {
            item.setType(ItemType.STREAM);
            item.setUrl(TWITCH_BASE_URL + item.getBroadcasterName());
        }
        return streams;
    }

    // Returns the top x clips based on game ID.
    private List<Item> SearchClips(String gameId, int limit) throws TwitchException {
        List<Item> clips = GetItemList(SearchByTwitchAPIs(BuildItemURL(CLIP_SEARCH_URL_TEMPLATE, gameId, limit)));
        for (Item item : clips) {
            item.setType(ItemType.CLIP);
        }
        return clips;
    }

    // Returns the top x videos based on game ID.
    private List<Item> SearchVideos(String gameId, int limit) throws TwitchException {
        List<Item> videos = GetItemList(SearchByTwitchAPIs(BuildItemURL(VIDEO_SEARCH_URL_TEMPLATE, gameId, limit)));
        for (Item item : videos) {
            item.setType(ItemType.VIDEO);
        }
        return videos;
    }

    public List<Item> SearchByItemType(String gameId, ItemType type, int limit) throws TwitchException {

        List<Item> items = Collections.emptyList();
        switch (type) {
            case STREAM:
                items = SearchStreams(gameId, limit);
                break;
            case VIDEO:
                items = SearchVideos(gameId, limit);
                break;
            case CLIP:
                items = SearchClips(gameId, limit);
                break;
        }

        // Update gameId for all items. GameId is used by recommendation function
        for (Item item : items) {
            item.setGameId(gameId);
        }
        return items;
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    private static final int DEFAULT_GAME_LIMIT = 20;
    private static final int DEFAULT_SEARCH_LIMIT = 20;
    private static final String TOKEN = "Bearer qr9xa03hsso466cs7jt3vw2yln4dbn";
    private static final String CLIENT_ID = "odgp5sp63c87qtvjqdeb06bq0i8950";
    private static final String TWITCH_BASE_URL = "https://www.twitch.tv/";
    private static final String TOP_GAME_URL = "https://api.twitch.tv/helix/games/top?first=%s";
    private static final String GAME_SEARCH_URL_TEMPLATE = "https://api.twitch.tv/helix/games?name=%s";
    private static final String STREAM_SEARCH_URL_TEMPLATE = "https://api.twitch.tv/helix/streams?game_id=%s&first=%s";
    private static final String VIDEO_SEARCH_URL_TEMPLATE = "https://api.twitch.tv/helix/videos?game_id=%s&first=%s";
    private static final String CLIP_SEARCH_URL_TEMPLATE = "https://api.twitch.tv/helix/clips?game_id=%s&first=%s";
}
