package utils.vkapi.queries;

import java.util.HashMap;
import java.util.Map;

public class QuerySendRequest {
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
        return new QuerySendRequest().new Builder();
    }

    public class Builder {

        private Builder() {
            // private constructor
        }

        public Builder setVersion(String version) {
            QuerySendRequest.this.version = version;
            return this;
        }

        public Builder setAccessToken(String token) {
            QuerySendRequest.this.accessToken = token;
            return this;
        }

        public QuerySendRequest build() {
            return QuerySendRequest.this;
        }
    }
}
