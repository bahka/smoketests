package tests.get;

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

public class Get {
    private VKService vkapi;

    @BeforeClass
    private void tearUp() {
        vkapi = ServiceGenerator.createService(VKService.class);
    }

    @DataProvider
    private Object[][] methodVersions() {
        return new Object[][]{
                {"5.92"},
                {"5.85"},
                {"5.57"},
                {"5.38"},
                {"5.36"},
                {"3.0"}
                // there is another response format but is it smoke check or not ¯\_(ツ)_/¯
        };
    }

    @Test(dataProvider = "methodVersions")
    public void canGetAppInfoWithServiceToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.serviceToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.get(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetAppInfoWithCodeFlowToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.codeFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.get(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetAppInfoWithImplicitFlowToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.get(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "incorrectTokens", dataProviderClass = TestData.class)
    public void cannotGetAppInfoWithoutToken(String token) throws IOException {
        Query query = Query.newBuilder()
                .setAccessToken(token)
                .setVersion("5.92")
                .build();

        Response<VKError> response = vkapi.tryGet(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotGetAppInfoWithoutVersion() throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.serviceToken).build();

        Response<VKError> response = vkapi.tryGet(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }
}
