package utils.vkapi.queries;

import java.util.HashMap;
import java.util.Map;

public class QueryGetLeaderboard {
    private String version;
    private String accessToken;
    private String type = "score";

    public Map<String, String> getQueryMap() {
        Map<String, String> queryMap = new HashMap<>();

        if (version != null)
            queryMap.put("v", version);

        if (accessToken != null)
            queryMap.put("access_token", accessToken);

        queryMap.put("type", type);

        return queryMap;
    }

    public static Builder newBuilder() {
        return new QueryGetLeaderboard().new Builder();
    }

    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setVersion(String version) {
            QueryGetLeaderboard.this.version = version;
            return this;
        }

        public Builder setAccessToken(String token) {
            QueryGetLeaderboard.this.accessToken = token;
            return this;
        }

        public QueryGetLeaderboard build() {
            return QueryGetLeaderboard.this;
        }
    }
}
