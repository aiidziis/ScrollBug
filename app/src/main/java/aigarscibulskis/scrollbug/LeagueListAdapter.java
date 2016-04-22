package aigarscibulskis.scrollbug;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by aigars.cibulskis on 4/21/2016.
 */
public class LeagueListAdapter extends RecyclerView.Adapter<LeagueListAdapter.ViewHolder> {
    private List<League> mLeagueList;
    private Context mContext;
    private int mSelectedLeagueId;

    private OnLeagueClickListener mLeagueListClickListener;

    public LeagueListAdapter(List<League> leagueList, Context context, int selectedLeagueId){
        mLeagueList = leagueList;
        mContext = context;
        mSelectedLeagueId = selectedLeagueId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.content_list_item, parent, false);


        return new ViewHolder(view, mLeagueListClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        League league = mLeagueList.get(position);

        Glide.with(mContext).load(league.getIconUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(holder.itemImage);

        holder.itemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        holder.itemId.setText(String.valueOf(league.getId()));
        holder.itemName.setText(league.getName());
        holder.league = league;

        if(league.getId() != 0 && league.getId() == mSelectedLeagueId){
            ((CardView) holder.itemView).setCardBackgroundColor(Color.parseColor("#0099CC"));
        }else{
            ((CardView) holder.itemView).setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return mLeagueList != null ? mLeagueList.size() : 0;
    }

    public void  setLeagueListClickListener(OnLeagueClickListener leagueListClickListener){
        mLeagueListClickListener = leagueListClickListener;
    }

    public OnLeagueClickListener getLeagueListClickListener(){
        return  mLeagueListClickListener;
    }

    public void setSelectedLeagueId(int leagueId){
        mSelectedLeagueId = leagueId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemId;
        public TextView itemName;
        public ImageView itemImage;
        public League league;

        public OnLeagueClickListener onLeagueClickListener;

        public ViewHolder(View itemView, OnLeagueClickListener listener) {
            super(itemView);
            itemId = (TextView) itemView.findViewById(R.id.league_id);
            itemName = (TextView) itemView.findViewById(R.id.league_name);
            itemImage = (ImageView) itemView.findViewById(R.id.league_image);

            onLeagueClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(this.onLeagueClickListener != null){
                onLeagueClickListener.onLeagueClicked(v, getPosition());
            }
        }
    }
}
