package Run;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alex.runtrak.R;

/**
 * Created by Alex on 11/3/2015.
 */
public class RunListAdapter extends ArrayAdapter<Run> {

    private int layoutResourceId;

    private static final String LOG_TAG = "MemoListAdapter";

    public RunListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        layoutResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Run item = getItem(position);
            View v = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = inflater.inflate(layoutResourceId, null);
               // v = inflater.inflate(R.layout.list_layout,null);

            } else {
                v = convertView;
            }

            TextView header = (TextView) v.findViewById(R.id.runInfo);

            header.setText(item.toString());

            return v;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }
}
