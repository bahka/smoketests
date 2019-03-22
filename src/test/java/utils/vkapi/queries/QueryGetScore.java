package utils.vkapi.queries;

import java.util.HashMap;
import java.util.Map;

public class QueryGetScore {
    private String version;
    private String accessToken;
    private String userId = "898685";

    public Map<String, String> getQueryMap() {
        Map<String, String> queryMap = new HashMap<>();

        if (version != null)
            queryMap.put("v", version);

        if (accessToken != null)
            queryMap.put("access_token", accessToken);

        queryMap.put("user_id", userId);

        return queryMap;
    }

    public static Builder newBuilder() {
        return new QueryGetScore().new Builder();
    }

    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setVersion(String version) {
            QueryGetScore.this.version = version;
            return this;
        }

        public Builder setAccessToken(String token) {
            QueryGetScore.this.accessToken = token;
            return this;
        }

        public QueryGetScore build() {
            return QueryGetScore.this;
        }
    }
}
