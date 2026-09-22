package thanhdnh.ueh.edu.article_app;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class DownloadWithProgress {
    public static String cachedFilePath = "";

    private DownloadWithProgress() {
    }

    public static File downloadFile(String url, File cacheDirectory) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }

            String extension = getExtensionFromMimeType(response.header("Content-Type", ""));
            File file = File.createTempFile("downloaded_file", extension, cacheDirectory);
            try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
                sink.writeAll(response.body().source());
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void downloadWithProgress(String inputUrl, Handler mainHandler, Context context,
                                            File directory, ProgressBar progressBar,
                                            ImageView imageView) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(inputUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> progressBar.setVisibility(View.INVISIBLE));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (Response closeableResponse = response) {
                    if (!closeableResponse.isSuccessful() || closeableResponse.body() == null) {
                        mainHandler.post(() -> progressBar.setVisibility(View.INVISIBLE));
                        return;
                    }

                    long totalBytes = closeableResponse.body().contentLength();
                    String extension = getExtensionFromMimeType(
                            closeableResponse.header("Content-Type", ""));
                    File outputFile = new File(directory, "downloaded_file" + extension);

                    try (InputStream inputStream = closeableResponse.body().byteStream();
                         OutputStream outputStream = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[8192];
                        long downloadedBytes = 0;
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            downloadedBytes += bytesRead;
                            if (totalBytes > 0) {
                                int progress = (int) (downloadedBytes * 100 / totalBytes);
                                mainHandler.post(() -> progressBar.setProgress(progress));
                            }
                        }
                    }

                    mainHandler.post(() -> {
                        cachedFilePath = outputFile.getAbsolutePath();
                        imageView.setImageURI(Uri.fromFile(outputFile));
                        progressBar.setVisibility(View.INVISIBLE);
                    });
                } catch (IOException e) {
                    mainHandler.post(() -> progressBar.setVisibility(View.INVISIBLE));
                }
            }
        });
    }

    private static String getExtensionFromMimeType(String mimeType) {
        Map<String, String> mimeMap = new HashMap<>();
        mimeMap.put("image/jpeg", ".jpg");
        mimeMap.put("image/png", ".png");
        mimeMap.put("application/json", ".json");
        return mimeMap.getOrDefault(mimeType, "");
    }
}
