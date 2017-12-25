package greek.dev.challenge.charities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.model.Charity;
import greek.dev.challenge.charities.views.CharitiesResultsActivity;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsHolder>{

    private ArrayList<Charity> charitiesResults;
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private final CharitiesResultsAdapterOnClickHandler mClickHandler;

    public interface CharitiesResultsAdapterOnClickHandler {
        void onClick(Charity selectedCharity);
    }

    public ResultsAdapter(CharitiesResultsAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }


    public class ResultsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rv_charity_name)
        public TextView tv_charity_name;

        @BindView(R.id.rv_charity_summary)
        public TextView tv_charity_info;

        @BindView(R.id.rv_charity_icon)
        public CircleImageView iv_charity_icon;

        @BindView(R.id.rv_charity_default_icon)
        public IconicsImageView iv_charity_default_icon;

        // the user can't send data to the firebase db yet, so it can't be implemented
        // @BindView(R.id.rating)
        // public RatingBar rb_rating;

        public ResultsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            map = CharitiesResultsActivity.getDrawablesMap();

        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAdapterPosition();
            Charity selectedCharity = charitiesResults.get(positionClicked);
            mClickHandler.onClick(selectedCharity);
        }
    }

    @Override
    public ResultsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultsHolder holder, int position) {
        Charity charityObject = charitiesResults.get(position);
        holder.tv_charity_name.setText(charityObject.getName());
        holder.tv_charity_info.setText(charityObject.getDescription());
        // If the charity's icon exist
        if(map.containsKey(charityObject.getIconlink()))
        {
            holder.iv_charity_icon.setImageResource(map.get(charityObject.getIconlink()));
            holder.iv_charity_default_icon.setVisibility(View.GONE);

            charitiesResults.get(position).setDrawableIconPosition(map.get(charityObject.getIconlink()));

        }
         /* NOTE. if the charity's image is available, then we should use:
            holder.iv_charity_default_icon.setVisibility(View.INVISIBLE);
            holder.iv_charity_icon.setImageResource(); (or a similar method to set the source)
            but if the charity's image in not available, then we should use:
            holder.iv_charity_default_icon.setVisibility(View.VISIBLE);
            holder.iv_charity_icon.setImageResource(R.drawable.no_charity_icon_bg);  */

        // has to be implemented the getIconlink provides link not image
        // holder.iv_charity_icon.setImageResource(charityObject.getIconLink());
        // The user can't send data to the firebase db yet, so it can't be implemented
        // holder.rb_rating.setNumStars(sampleDataObject.getStars());
    }

    @Override
    public int getItemCount() {
        if(charitiesResults == null) return 0;
        return charitiesResults.size();
    }

    public void setSeriesResults(ArrayList<Charity> charitiesResults) {
        this.charitiesResults = charitiesResults;
        notifyDataSetChanged();
    }

}
