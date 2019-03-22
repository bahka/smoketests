package tests.sendRequest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;
import utils.vkapi.queries.QueryGetScore;
import utils.vkapi.responses.VKError;
import utils.vkapi.responses.VKResponse;
import utils.vkapi.service.ServiceGenerator;
import utils.vkapi.service.Tokens;
import utils.vkapi.service.VKService;

import java.io.IOException;

public class SendRequest {
    private VKService vkapi;

    @BeforeClass
    private void tearUp() {
        vkapi = ServiceGenerator.createService(VKService.class);
    }

    @DataProvider
    private Object[][] methodVersions() {
        return new Object[][] {
                {"5.92"},
                {"5.25"},
                {"3.0"}
        };
    }

    @Test(dataProvider = "methodVersions")
    public void cannotSendRequestWithServiceToken(String methodVersion) throws IOException {
        QueryGetScore query = QueryGetScore.newBuilder().setAccessToken(Tokens.serviceToken).setVersion(methodVersion).build();

        Response<VKError> response = vkapi.trySendRequest(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 28);
    }

    @Test(dataProvider = "methodVersions")
    public void cannotSendRequestCodeFlowToken(String methodVersion) throws IOException {
        QueryGetScore query = QueryGetScore.newBuilder().setAccessToken(Tokens.codeFlowToken).setVersion(methodVersion).build();

        Response<VKError> response = vkapi.trySendRequest(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 15); // if this method allowed only with implicit token i got error_code = 15 ?
    }

    @Test(dataProvider = "methodVersions")
    public void canSendRequestWithImplicitFlowToken(String methodVersion) throws IOException {
        QueryGetScore query = QueryGetScore.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.sendRequest(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void cannotSendRequestWithoutToken() throws IOException {
        QueryGetScore query = QueryGetScore.newBuilder().setVersion("5.92").build();

        Response<VKError> response = vkapi.trySendRequest(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotSendRequestWithoutVersion() throws IOException {
        QueryGetScore query = QueryGetScore.newBuilder().setAccessToken(Tokens.codeFlowToken).build();

        Response<VKError> response = vkapi.trySendRequest(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }

    // mandatory field user_id - is functional check, i think
}
