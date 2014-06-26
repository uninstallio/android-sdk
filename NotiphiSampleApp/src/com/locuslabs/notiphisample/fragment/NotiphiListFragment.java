package com.locuslabs.notiphisample.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.locuslabs.notiphisample.NotificationDetailsActivity;
import com.locuslabs.notiphisample.R;
import com.notikum.notifypassive.utils.ClientUtility;
import com.notikum.notifypassive.utils.Constants;
import com.notikum.notifypassive.utils.NotiphiPromotion;

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
		mListView.setOnItemClickListener(new OnListItemClick());
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
			holder.imageView = (ImageView)convertView.findViewById(R.id.notification_center_image);
			NotiphiPromotion promo = getItem(position);
			holder.title.setVisibility(View.VISIBLE);
			holder.content.setVisibility(View.VISIBLE);
			holder.imageView.setVisibility(View.VISIBLE);
			holder.title.setText(promo.getPromoTitle());
			holder.content.setText(promo.getPromoContent());
			Log.d(TAG, " Server address = " + Constants.IMAGE_SERVER_ADDRESS+promo.getPromoCompanyLogoLink());
			//holder.imageView.setImageDrawable(Constants.IMAGE_SERVER_ADDRESS+"offer_images" + promo.getPromoCompanyLogoLink());
			return convertView;
		}
	}

	static class ViewHolder{
		TextView title;
		TextView content;
		ImageView imageView;
	}
	
	private class NotiphiAsyncTask extends AsyncTask<String, Integer, Boolean> {
		
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
	
	private class OnListItemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			NotiphiPromotion promotion = promotionList.get(position);
			Intent  notificationIntent = new Intent(mContext, NotificationDetailsActivity.class);
			notificationIntent.putExtra("promotion", promotion);
			startActivity(notificationIntent);
			
		}
		
	}
}
