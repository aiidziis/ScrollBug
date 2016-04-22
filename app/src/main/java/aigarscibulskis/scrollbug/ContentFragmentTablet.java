package aigarscibulskis.scrollbug;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aigars.cibulskis on 4/21/2016.
 */
public class ContentFragmentTablet extends Fragment implements OnLeagueClickListener {
    private static String PARAMS_LEAGUELIST = "leagueList";
    private static String PARAMS_LEAGUEOBJPOS = "leagueObjPost";

    private View view;
    private TextView mLeagueId;
    private TextView mLeagueName;
    private ImageView mLeagueImg;
    private RecyclerView mLeagueListView;
    private LinearLayout mLeagueDetails;

    private List<League> mLeagueList;

    private League mLeague;
    private int mLeaguePosition;
    private int mScrollToPostition;

    public void setLeaguesList(List<League> leagueList){
        mLeagueList = leagueList;
    }

    public List<League> getLeagueList(){
        return mLeagueList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        if(args != null){
            if(args.containsKey(PARAMS_LEAGUEOBJPOS)){
                mLeaguePosition = args.getInt(PARAMS_LEAGUEOBJPOS);
                mLeagueList = args.getParcelableArrayList(PARAMS_LEAGUELIST);
                mLeague = mLeagueList.get(mLeaguePosition);
                mScrollToPostition = mLeaguePosition;
            }else {
                mLeagueList = args.getParcelableArrayList(PARAMS_LEAGUELIST);
                mLeague = mLeagueList.get(0);
            }
        }

        view = inflater.inflate(R.layout.content_fragment_layout, container, false);

        mLeagueDetails = (LinearLayout)view.findViewById(R.id.details);

        mLeagueId = (TextView)mLeagueDetails.findViewById(R.id.league_id);
        mLeagueName = (TextView)mLeagueDetails.findViewById(R.id.league_name);
        mLeagueImg = (ImageView)mLeagueDetails.findViewById(R.id.league_image);

        mLeagueListView = (RecyclerView)view.findViewById(R.id.content_list);
        mLeagueDetails = (LinearLayout)view.findViewById(R.id.details);

        mLeagueId.setText(String.valueOf(mLeague.getId()));
        mLeagueName.setText(mLeague.getName());

        Glide.with(getContext())
                .load(mLeague.getIconUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(mLeagueImg);

        mLeagueListView = (RecyclerView)view.findViewById(R.id.content_list);
        mLeagueListView.setHasFixedSize(true);

        if(mScrollToPostition != 0){
            mLeagueListView.scrollToPosition(mScrollToPostition);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLeagueListView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mLeagueListView != null){
            LeagueListAdapter listAdapter = new LeagueListAdapter(mLeagueList, getContext(), mLeague.getId());
            mLeagueListView.setAdapter(listAdapter);
            listAdapter.setLeagueListClickListener(this);
        }
    }

    @Override
    public void onLeagueClicked(View view, int position) {
        League league = ((LeagueListAdapter.ViewHolder) mLeagueListView.getChildViewHolder(view)).league;
        ((LeagueListAdapter) mLeagueListView.getAdapter()).setSelectedLeagueId(league.getId());
        mLeagueListView.getAdapter().notifyDataSetChanged();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAMS_LEAGUELIST, (ArrayList<? extends Parcelable>) getLeagueList());
        args.putInt(PARAMS_LEAGUEOBJPOS, position);

        ContentFragmentTablet fragmentTablet = new ContentFragmentTablet();
        fragmentTablet.setArguments(args);
        transaction.replace(getId(), fragmentTablet).commit();
    }
}

