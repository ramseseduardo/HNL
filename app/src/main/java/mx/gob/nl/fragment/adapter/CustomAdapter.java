package mx.gob.nl.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.gob.nl.fragment.R;
import mx.gob.nl.fragment.model.ModelList;

/**
 * Created by Ramses on 26/10/14.
 */
public class CustomAdapter extends BaseAdapter {

    private List<ModelList> mDataset;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public class ViewHolder {

        public ImageView logo;
        public TextView title;
        public TextView body;
    }

    public CustomAdapter(Context context, List<ModelList> items) {

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDataset = items;
    }

    public void setDataset(List<ModelList> newDataset) {

        mDataset = newDataset;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public ModelList getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.simple_category_list_item, null);

            holder = new ViewHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.imageView__simple_category_list_item_logo);
            holder.title = (TextView) convertView.findViewById(R.id.textView__simple_category_list_item_title);
            holder.body = (TextView) convertView.findViewById(R.id.textView__simple_category_list_item_detail);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder)convertView.getTag();
        }

        Picasso.with(mContext).load(getItem(position).getUrl()).into(holder.logo);

        holder.title.setText(getItem(position).getTitle());
        holder.body.setText(getItem(position).getTitle());

        return convertView;
    }

}
