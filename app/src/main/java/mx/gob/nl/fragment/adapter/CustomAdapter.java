package mx.gob.nl.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.gob.nl.fragment.R;
import mx.gob.nl.fragment.model.ModelList;

/**
 * Created by Ramses on 26/10/14.
 */
public class CustomAdapter extends BaseAdapter implements Filterable {

    private List<ModelList> mDataset;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder {

        public int id;
        public ImageView logo;
        public TextView name;
        public TextView precentacion;
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
            holder.id = position;
            holder.logo = (ImageView) convertView.findViewById(R.id.imageView__simple_category_list_item_logo);
            holder.name = (TextView) convertView.findViewById(R.id.textView__simple_category_list_item_title);
            holder.precentacion = (TextView) convertView.findViewById(R.id.textView__simple_category_list_item_detail);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder)convertView.getTag();
        }

        if(!getItem(position).getUrlFoto().isEmpty())
         Picasso.with(mContext).load(getItem(position).getUrlFoto()).into(holder.logo);

        holder.name.setText(getItem(position).getName());
        holder.precentacion.setText(getItem(position).getPresentacion());

        return convertView;
    }

}
