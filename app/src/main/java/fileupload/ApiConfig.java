package fileupload;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by commando3 on 5/16/2017.
 */

public interface ApiConfig {
    /*@Multipart
    @POST("/upload")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);*/
    @Multipart
    @POST("/upload")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);
}

