package tests.getLeaderboard;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;
import utils.dataprovider.TestData;
import utils.vkapi.queries.QueryGetLeaderboard;
import utils.vkapi.responses.VKError;
import utils.vkapi.responses.VKResponse;
import utils.vkapi.service.ServiceGenerator;
import utils.vkapi.service.Tokens;
import utils.vkapi.service.VKService;

import java.io.IOException;

public class GetLeaderBoard {
    private VKService vkapi;

    @BeforeClass
    private void tearUp() {
        vkapi = ServiceGenerator.createService(VKService.class);
    }

    @DataProvider
    private Object[][] methodVersions() {
        return new Object[][]{
                {"5.92"},
                {"3.0"}
        };
    }

    @Test(dataProvider = "methodVersions")
    public void canGetLeaderBoardWithImplicitCodeFlow(String version) throws IOException {
        QueryGetLeaderboard query = QueryGetLeaderboard.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(version).build();

        Response<VKResponse> response = vkapi.getLeaderboard(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetLeaderBoardWithCodeFlowToken(String version) throws IOException {
        QueryGetLeaderboard query = QueryGetLeaderboard.newBuilder().setAccessToken(Tokens.codeFlowToken).setVersion(version).build();

        Response<VKResponse> response = vkapi.getLeaderboard(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200); // oops untrasted app
    }

    @Test(dataProvider = "methodVersions")
    public void cannotGetFriendListWithServiceToken(String version) throws IOException {
        QueryGetLeaderboard query = QueryGetLeaderboard.newBuilder().setAccessToken(Tokens.serviceToken).setVersion(version).build();

        Response<VKError> response = vkapi.tryGetLeaderboard(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 28);
    }

    @Test(dataProvider = "incorrectTokens", dataProviderClass = TestData.class)
    public void cannotGetFriendListWithoutToken(String token) throws IOException {
        QueryGetLeaderboard query = QueryGetLeaderboard.newBuilder().setVersion("5.92").setAccessToken(token).build();

        Response<VKError> response = vkapi.tryGetLeaderboard(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotGetFriendListWithoutVersion() throws IOException {
        QueryGetLeaderboard query = QueryGetLeaderboard.newBuilder().setAccessToken(Tokens.implicitFlowToken).build();

        Response<VKError> response = vkapi.tryGetLeaderboard(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }
}
