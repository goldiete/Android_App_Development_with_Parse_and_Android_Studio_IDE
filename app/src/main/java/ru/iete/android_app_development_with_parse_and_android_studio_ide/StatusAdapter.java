package ru.iete.android_app_development_with_parse_and_android_studio_ide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

public class StatusAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> statusList;

    public StatusAdapter(Context context, List<ParseObject> objects) {
        super(context, R.layout.homepage_list_item, objects);
        mContext = context;
        statusList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.homepage_list_item, null);
            holder = new ViewHolder();
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject statusObject = statusList.get(position);

        String username = statusObject.getString("username");
        holder.username.setText(username);

        String status = statusObject.getString("body");
        holder.status.setText(status);

        return convertView;
    }

    public static class ViewHolder {
        TextView username;
        TextView status;
    }
}
