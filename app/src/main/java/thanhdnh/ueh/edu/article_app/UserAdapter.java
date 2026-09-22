package thanhdnh.ueh.edu.article_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private final ArrayList<UserProfile> users;
    private final Context context;

    public UserAdapter(ArrayList<UserProfile> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public UserProfile getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_disp_tpl, parent, false);
            holder = new ViewHolder();
            holder.avatar = convertView.findViewById(R.id.iv_user_avatar);
            holder.userName = convertView.findViewById(R.id.tv_user_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserProfile user = getItem(position);
        Picasso.get()
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .fit()
                .centerCrop()
                .into(holder.avatar);
        holder.userName.setText(user.getUserName());
        return convertView;
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView userName;
    }
}
