package tests.deleteAppRequests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;
import utils.dataprovider.TestData;
import utils.vkapi.queries.Query;
import utils.vkapi.service.ServiceGenerator;
import utils.vkapi.service.Tokens;
import utils.vkapi.service.VKService;
import utils.vkapi.responses.VKError;
import utils.vkapi.responses.VKResponse;

import java.io.IOException;


public class DeleteAppRequests {
    private VKService vkapi;

    @BeforeClass
    private void tearUp() {
        vkapi = ServiceGenerator.createService(VKService.class);
    }

    @DataProvider
    private Object[][] apiVersions() {
        return new Object[][]{
                {"5.92"},
                {"5.63"},
                {"3.0"}
        };
    }

    @Test(dataProvider = "apiVersions")
    public void successRemoveAppRequestsWithUserToken(String version) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(version).build();

        Response<VKResponse> response = vkapi.deleteAppRequests(query.getQueryMap()).execute();

        // user token for admin says 'Access denied'  :(
        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().responseBody, 1);
    }

    @Test
    public void cannotRemoveAppRequestsWithServiceToken() throws IOException {
        Query query = Query.newBuilder()
                .setAccessToken(Tokens.serviceToken)
                .setVersion("5.92")
                .build();

        Response<VKError> response = vkapi.tryDeleteAppRequests(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 28);
    }


    @Test
    public void cannotRemoveAppRequestsWithCodeFlowToken() throws IOException {
        Query query = Query.newBuilder()
                .setAccessToken(Tokens.codeFlowToken)
                .setVersion("5.92")
                .build();

        Response<VKError> response = vkapi.tryDeleteAppRequests(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 15);
    }

    @Test(dataProvider = "incorrectTokens", dataProviderClass = TestData.class)
    public void cannotRemoveAppRequestsWithoutToken(String token) throws IOException {
        Query query = Query.newBuilder()
                .setAccessToken(token)
                .setVersion("5.92")
                .build();

        Response<VKError> response = vkapi.tryDeleteAppRequests(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotRemoveAppRequestsWithoutVersion() throws IOException {
        Query query = Query.newBuilder()
                .setAccessToken(Tokens.implicitFlowToken)
                .build();

        Response<VKError> response = vkapi.tryDeleteAppRequests(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }
}
