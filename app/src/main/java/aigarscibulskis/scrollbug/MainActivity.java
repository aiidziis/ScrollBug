package aigarscibulskis.scrollbug;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static String PARAMS_LEAGUELIST = "leagueList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String leagueData = readFile();

        try {
            JSONObject leagueObj = new JSONObject(leagueData);
            JSONArray itemArray = leagueObj.getJSONArray("items");

            List<League> leagueList = new ArrayList<>(itemArray.length());
            JSONObject leagueObject;
            League league;

            for (int i = 0; i < itemArray.length(); i++) {
                leagueObject = itemArray.getJSONObject(i);
                league = new Gson().fromJson(leagueObject.toString(), League.class);
                leagueList.add(league);
            }

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            Bundle args = new Bundle();
            args.putParcelableArrayList(PARAMS_LEAGUELIST, (ArrayList<? extends Parcelable>) leagueList);

            ContentFragmentTablet contentFragmentTablet = new ContentFragmentTablet();
            contentFragmentTablet.setArguments(args);
            transaction.replace(R.id.container, contentFragmentTablet).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  String readFile() {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.leagues)));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
