package fileupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class AppConfig {
    private static String BASE_URL = "http://14.141.60.217";
    static  public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
