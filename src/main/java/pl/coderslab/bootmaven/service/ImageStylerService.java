package pl.coderslab.bootmaven.service;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class ImageStylerService {

    private static final String API_URL = "https://api.deepai.org/api/deepart";

    public String styleImage(String imageUrl, String style) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("style", style)
                .addFormDataPart("image", imageUrl)
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // Parse the JSON response
                Gson gson = new Gson();
                ImageStylerResponse stylerResponse = gson.fromJson(responseBody, ImageStylerResponse.class);

                // Extract the URL of the styled image
                String styledImageUrl = stylerResponse.getUrl();

                // Return the URL to the calling code
                return styledImageUrl;
            } else {
                throw new IOException("Unexpected HTTP code: " + response.code());
            }
        }
    }

    // Inner class representing the response JSON structure
    private static class ImageStylerResponse {
        private String id;
        private String output_url;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return output_url;
        }
    }
}
