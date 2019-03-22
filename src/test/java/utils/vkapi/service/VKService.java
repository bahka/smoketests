package utils.vkapi.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import utils.vkapi.responses.VKError;
import utils.vkapi.responses.VKResponse;

import java.util.Map;

public interface VKService {

    @GET("method/apps.deleteAppRequests")
    Call<VKResponse> deleteAppRequests(@QueryMap Map<String, String> options);
    @GET("method/apps.deleteAppRequests")
    Call<VKError> tryDeleteAppRequests(@QueryMap Map<String, String> options);

    @GET("method/apps.get")
    Call<VKResponse> get(@QueryMap Map<String, String> options);
    @GET("method/apps.get")
    Call<VKError> tryGet(@QueryMap Map<String, String> options);

    @GET("method/apps.getCatalog")
    Call<VKResponse> getCatalog(@QueryMap Map<String, String> options);
    @GET("method/apps.getCatalog")
    Call<VKError> tryGetCatalog(@QueryMap Map<String, String> options);

    @GET("method/apps.getFriendsList")
    Call<VKResponse> getFriendsList(@QueryMap Map<String, String> options);
    @GET("method/apps.getFriendsList")
    Call<VKError> tryGetFriendsList(@QueryMap Map<String, String> options);

    @GET("method/apps.getLeaderboard")
    Call<VKResponse> getLeaderboard(@QueryMap Map<String, String> options);
    @GET("method/apps.getLeaderboard")
    Call<VKError> tryGetLeaderboard(@QueryMap Map<String, String> options);

    @GET("method/apps.getScopes")
    Call<VKResponse> getScopes(@QueryMap Map<String, String> options);
    @GET("method/apps.getScopes")
    Call<VKError> tryGetScopes(@QueryMap Map<String, String> options);

    @GET("method/apps.getScore")
    Call<VKResponse> getScore(@QueryMap Map<String, String> options);
    @GET("method/apps.getScore")
    Call<VKError> tryGetScore(@QueryMap Map<String, String> options);

    @GET("method/apps.sendRequest")
    Call<VKResponse> sendRequest(@QueryMap Map<String, String> options);
    @GET("method/apps.sendRequest")
    Call<VKError> trySendRequest(@QueryMap Map<String, String> options);

}
