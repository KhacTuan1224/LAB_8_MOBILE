package thanhdnh.ueh.edu.article_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(view -> finish());

        int userId = (int) getIntent().getLongExtra(MainActivity.EXTRA_USER_ID, -1);
        UserProfile user = MainActivity.getUserById(userId);
        if (user == null) {
            finish();
            return;
        }

        ImageView avatar = findViewById(R.id.iv_detail_avatar);
        TextView userName = findViewById(R.id.tv_detail_user_name);
        TextView id = findViewById(R.id.tv_detail_id);
        TextView email = findViewById(R.id.tv_detail_email);
        TextView hobbies = findViewById(R.id.tv_detail_hobbies);
        TextView description = findViewById(R.id.tv_detail_description);

        Picasso.get()
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .fit()
                .centerCrop()
                .into(avatar);
        userName.setText(user.getUserName());
        id.setText(getString(R.string.user_id_value, user.getId()));
        email.setText(getString(R.string.email_value, user.getEmail()));
        hobbies.setText(getString(R.string.hobbies_value, user.getHobbies()));
        description.setText(user.getDescription());
    }
}
