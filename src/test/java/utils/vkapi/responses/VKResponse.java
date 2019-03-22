package utils.vkapi.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VKResponse {
    @JsonProperty("response")
    public Object responseBody;
}
