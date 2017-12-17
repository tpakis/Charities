package greek.dev.challenge.charities.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.SampleData;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsHolder>{

    // using sample data until real ones are available
    private ArrayList<SampleData> charitiesResults;

    public class ResultsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.charity_name)
        public TextView tv_charity_name;

        @BindView(R.id.charity_info)
        public TextView tv_charity_info;

        @BindView(R.id.charity_icon)
        public ImageView iv_charity_logo;

        @BindView(R.id.rating)
        public RatingBar rb_rating;

        public ResultsHolder(View view) {
            super(view);
        }
    }

    public ResultsAdapter() {
    }


    @Override
    public ResultsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultsHolder holder, int position) {
        //TODO implement with the true data
        SampleData sampleDataObject = charitiesResults.get(position);
        holder.tv_charity_name.setText(sampleDataObject.getName());
        holder.tv_charity_info.setText(sampleDataObject.getInfo());
        holder.iv_charity_logo.setImageResource(sampleDataObject.getLogo());
        holder.rb_rating.setNumStars(sampleDataObject.getStars());
    }

    @Override
    public int getItemCount() {
        if(charitiesResults == null) return 0;
        return charitiesResults.size();
    }

    public void setSeriesResults(ArrayList<SampleData> charitiesResults) {
        this.charitiesResults = charitiesResults;
        notifyDataSetChanged();
    }
}
