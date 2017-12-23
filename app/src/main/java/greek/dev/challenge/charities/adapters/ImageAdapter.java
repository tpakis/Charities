package greek.dev.challenge.charities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import greek.dev.challenge.charities.R;

/**
 * Created by eirinimitsopoulou on 18/12/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] web = {
            R.string.make_donation,
            R.string.add_donation,
            R.string.make_wish,
            R.string.info
    };

    public Integer[] Imageid = {
            R.drawable.ic_donate,
            R.drawable.ic_add,
            R.drawable.ic_wishes,
            R.drawable.ic_info,
    };

    public ImageAdapter(Context c )
    {
        mContext = c;
    }

    @Override
    public int getCount()
    {
        return Imageid.length;
    }
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup
            parent)
    {

        View gridView;
        if (convertView == null)
        {
            gridView =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_layout, parent, false);
            // set value into textview
            TextView textView = (TextView)
                    gridView.findViewById(R.id.grid_item_label);
            textView.setText(mContext.getString(web[position]));
            // set image based on selected text
            ImageView imageView = (ImageView)
                    gridView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(Imageid[position]);
        }
        else
        {
            gridView = (View) convertView;
        }
        return gridView;
    }


}
