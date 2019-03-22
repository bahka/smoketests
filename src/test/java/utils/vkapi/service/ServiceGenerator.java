package utils.vkapi.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ServiceGenerator {
    private static final String VK_ENDPOINT = System.getProperty("endpoint", "https://api.vk.com/");
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
    private static final int DEFAULT_TIMEOUT = 10;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(VK_ENDPOINT)
            .addConverterFactory(JacksonConverterFactory.create());

    public static <S> S createService(Class<S> service) {
        httpClient.retryOnConnectionFailure(true);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(service);
    }
}
