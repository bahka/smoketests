package tests.getScopes;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import retrofit2.Response;
import utils.vkapi.queries.Query;
import utils.vkapi.responses.VKError;
import utils.vkapi.responses.VKResponse;
import utils.vkapi.service.ServiceGenerator;
import utils.vkapi.service.Tokens;
import utils.vkapi.service.VKService;

import java.io.IOException;

public class GetScopes {
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
    public void cannotGetScopesByServiceToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.serviceToken).setVersion(methodVersion).build();

        Response<VKError> response = vkapi.tryGetScopes(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 28);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetScopesByCodeFlowToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.codeFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.getScopes(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test(dataProvider = "methodVersions")
    public void canGetScopesWithImplicitFlowToken(String methodVersion) throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.implicitFlowToken).setVersion(methodVersion).build();

        Response<VKResponse> response = vkapi.getScopes(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void cannotGetScopesWithoutToken() throws IOException {
        Query query = Query.newBuilder().setVersion("5.92").build();

        Response<VKError> response = vkapi.tryGetScopes(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 5);
    }

    @Test
    public void cannotGetScopesWithoutVersion() throws IOException {
        Query query = Query.newBuilder().setAccessToken(Tokens.codeFlowToken).build();

        Response<VKError> response = vkapi.tryGetScopes(query.getQueryMap()).execute();

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(response.body().error.errorCode, 8);
    }
}
