package com.locuslabs.notiphisample.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.locuslabs.notiphisample.R;
import com.notikum.notifypassive.utils.ClientUtility;
import com.notikum.notifypassive.utils.NotiphiPromotion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotiphiListFragment extends  Fragment{

	private final String TAG = NotiphiListFragment.class.getSimpleName();
	private Context mContext;
	private ListView mListView;
	private NotiphiListAdapter mNotiphiListAdapter;
	private ArrayList<NotiphiPromotion> promotionList;
	private boolean moreResult = false;
	private View mView;
	private int notificationIdentifier;
	
	
	public NotiphiListFragment(Context context,int notificationIdentifier) {
		mContext = context;
		this.notificationIdentifier = notificationIdentifier;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.notiphi_notification_center_main_layout, null);
		promotionList = new ArrayList<NotiphiPromotion>();
		mListView = (ListView)mView.findViewById(R.id.notification_center_list_view);
		mNotiphiListAdapter = new NotiphiListAdapter();
		NotiphiAsyncTask notiphiAsyncTask = new NotiphiAsyncTask();
		notiphiAsyncTask.execute("NotificationCenter");
		return mView;
	}
	
	
	

	private class NotiphiListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return promotionList.size();
		}

		@Override
		public NotiphiPromotion getItem(int position) {
			return promotionList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 
			if(convertView == null){
				LayoutInflater inflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflator.inflate(R.layout.notiphi_notification_list_row_layout, null);
			}
			ViewHolder holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.notification_center_title);
			holder.content = (TextView)convertView.findViewById(R.id.notification_center_content);
			NotiphiPromotion promo = getItem(position);
			holder.title.setVisibility(View.VISIBLE);
			holder.content.setVisibility(View.VISIBLE);
			holder.title.setText(promo.getPromoTitle());
			holder.content.setText(promo.getPromoContent());
			return convertView;
		}
	}

	static class ViewHolder{
		TextView title;
		TextView content;
	}
	
	private class NotiphiAsyncTask extends AsyncTask<String, Integer, Boolean> {

		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String result = ClientUtility.notificationCenterResult(mContext, 0,notificationIdentifier); // 0 for all notification and 1 for saved one
			Log.d(TAG, " Result received from server is = " + result);
			try{
				JSONObject promoJsonObject = new JSONObject(result);
				if(promoJsonObject.get("status").toString().equalsIgnoreCase("more_data")){
					moreResult = true;
				}
				JSONArray promoJsonArray = promoJsonObject.getJSONArray("promos");
				JSONObject finalPromoObject=null;
				for(int count = 0; count<promoJsonArray.length();count++){
					finalPromoObject = promoJsonArray.getJSONObject(count);
					promotionList.add(new NotiphiPromotion(finalPromoObject.getInt("id"), finalPromoObject.getString("title"), 
							finalPromoObject.getString("url"), finalPromoObject.getString("outlet_address"), finalPromoObject.getString("companylogo"),
							finalPromoObject.getString("content"), finalPromoObject.getBoolean("enabled")));
				}
				
			}catch(JSONException jsex){
				jsex.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				mListView.setAdapter(mNotiphiListAdapter);
			}
		}

	}
}
