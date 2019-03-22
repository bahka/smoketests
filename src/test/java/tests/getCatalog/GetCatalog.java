package tests.getCatalog;

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

public class GetCatalog {
    private VKService vkapi;

    @BeforeClass
    private void tearUp() {
        vkapi = ServiceGenerator.createService(VKService.class);
    }

    @DataProvider
    private Object[][] methodVersions() {
        return new Object[][]{
                {"5.92"},
                {"5.57"},
                {"5.38"},
                {"3.0"}
        };
    }

    @Test(dataProvider = "methodVersions")
    public void canGetCatalogWithServiceToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.serviceToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.getCatalog(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetCatalogWithUserCodeFlowToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.codeFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.getCatalog(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetCatalogWithUserImplicitFlowToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.getCatalog(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "incorrectTokens", dataProviderClass = TestData.class)
    public void canGetCatalogWithoutToken(String token) throws IOException {
        Query query = Query.newBuilder().setVersion("5.92").setAccessToken(token).build();

        Response<VKError> response = vkapi.tryGetCatalog(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotGetAppInfoWithoutVersion() throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.serviceToken).build();

        Response<VKError> response = vkapi.tryGetCatalog(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }
}
