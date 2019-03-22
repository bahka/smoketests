package tests.getFriendsList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;
import utils.dataprovider.TestData;
import utils.vkapi.queries.Query;
import utils.vkapi.responses.VKError;
import utils.vkapi.responses.VKResponse;
import utils.vkapi.service.ServiceGenerator;
import utils.vkapi.service.Tokens;
import utils.vkapi.service.VKService;

import java.io.IOException;

public class GetFriendList {
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
    public void canGetFriendListWithUserImplicitCodeFlow(String version) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(version).build();

        Response<VKResponse> response = vkapi.getFriendsList(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void cannotGetFriendListWithUserCodeFlowToken(String version) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.codeFlowToken).setVersion(version).build();

        Response<VKError> response = vkapi.tryGetFriendsList(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 15);
    }

    @Test(dataProvider = "methodVersions")
    public void cannotGetFriendListWithServiceToken(String version) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.serviceToken).setVersion(version).build();

        Response<VKError> response = vkapi.tryGetFriendsList(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 28);
    }

    @Test(dataProvider = "incorrectTokens", dataProviderClass = TestData.class)
    public void cannotGetFriendListWithoutToken(String token) throws IOException {
        Query query = Query.newBuilder().setVersion("5.92").setAccessToken(token).build();

        Response<VKError> response = vkapi.tryGetFriendsList(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotGetFriendListWithoutVersion() throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.implicitFlowToken).build();

        Response<VKError> response = vkapi.tryGetFriendsList(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }
}
