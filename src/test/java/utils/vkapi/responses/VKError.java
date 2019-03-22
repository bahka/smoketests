package utils.vkapi.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VKError {
    @JsonProperty("error")
    public ErrorBody error;

    public class ErrorBody {
        @JsonProperty("error_code")
        public int errorCode;
        @JsonProperty("error_msg")
        public String errorMessage;
        @JsonProperty("request_params")
        public List<Object> requestParameters;
    }
}
