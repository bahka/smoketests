package utils.vkapi.queries;

import java.util.HashMap;
import java.util.Map;

public class Query {
    private String version;
    private String accessToken;

    public Map<String, String> getQueryMap() {
        Map<String, String> queryMap = new HashMap<>();

        if (version != null)
            queryMap.put("v", version);

        if (accessToken != null)
            queryMap.put("access_token", accessToken);

        return queryMap;
    }

    public static Builder newBuilder() {
        return new Query().new Builder();
    }

    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setVersion(String version) {
            Query.this.version = version;
            return this;
        }

        public Builder setAccessToken(String token) {
            Query.this.accessToken = token;
            return this;
        }

        public Query build() {
            return Query.this;
        }
    }
}
