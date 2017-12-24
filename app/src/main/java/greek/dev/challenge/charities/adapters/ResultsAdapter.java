package greek.dev.challenge.charities.adapters;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.view.IconicsImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import greek.dev.challenge.charities.R;
import greek.dev.challenge.charities.model.Charity;

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
            map.put("actionaid", R.drawable.actionaid);
            map.put("antikarkiniki", R.drawable.antikarkiniki);
            map.put("anima", R.drawable.anima);
            map.put("edke", R.drawable.edke);
            map.put("elepap", R.drawable.elepap);
            map.put("elpida", R.drawable.elpida);
            map.put("grammis_sos", R.drawable.grammis_sos);
            map.put("i_pisti", R.drawable.i_pisti);
            map.put("iagkalia", R.drawable.iagkalia);

            map.put("kibotos", R.drawable.kibotos);
            map.put("theofilos", R.drawable.theofilos);
            map.put("unicef", R.drawable.unicef);
            map.put("xamogelo_paidiou", R.drawable.xamogelo_paidiou);
            map.put("xwria_sos", R.drawable.xwria_sos);

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
        //TODO implement with the true data
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
