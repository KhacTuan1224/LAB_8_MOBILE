package thanhdnh.ueh.edu.article_app;

import android.app.Activity;
import android.content.Context;
import android.widget.GridView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserData {
    public static UserList data;

    private final Context context;
    private final GridView gridView;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public UserData(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
    }

    public static UserProfile getUserById(int id) {
        if (data == null || data.getUsers() == null) {
            return null;
        }
        return data.getUserById(id);
    }

    public void loadData(String url, Activity activity) {
        executor.execute(() -> {
            File file = DownloadWithProgress.downloadFile(url, context.getCacheDir());
            if (file == null) {
                return;
            }

            String json = readText(file);
            if (json.isEmpty()) {
                return;
            }

            UserList downloadedData = new Gson().fromJson(json, UserList.class);
            if (downloadedData == null || downloadedData.getUsers() == null) {
                return;
            }

            data = downloadedData;
            activity.runOnUiThread(() ->
                    gridView.setAdapter(new UserAdapter(data.getUsers(), context)));
        });
    }

    private String readText(File file) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
