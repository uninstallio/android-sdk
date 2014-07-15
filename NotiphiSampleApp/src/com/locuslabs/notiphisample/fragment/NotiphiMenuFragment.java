//package com.locuslabs.notiphisample.fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.locuslabs.notiphisample.NotificationCenter;
//import com.locuslabs.notiphisample.R;
//import com.notikum.notifypassive.utils.ClientUtility;
//
//public class NotiphiMenuFragment extends Fragment{
//	
//	private Context mContext;
//	
//	public NotiphiMenuFragment(Context context) { 
//		mContext = context;
//	}
//	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View mView = inflater.inflate(R.layout.notification_center_menu_layout, null);
//		init(mView);
//		return mView;
//	}
//	
//	private void init(View mView){
//		Button allNotification = (Button)mView.findViewById(R.id.notification_center_all_notification);
//		Button savedNotification = (Button)mView.findViewById(R.id.notification_center_saved_notification);
//		Button optOut = (Button)mView.findViewById(R.id.notification_center_optout);
//		
//		allNotification.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				createFragmentInstance(ClientUtility.ALL_NOTIFICATION);
//			}
//		});
//		
//		savedNotification.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				createFragmentInstance(ClientUtility.SAVED_NOTIFICATION);
//			}
//		});
//		
//		optOut.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// implement
//			}
//		});
//	}
//	
//	private void createFragmentInstance(int notificationIdentifier){
//		Fragment newContent = new NotiphiListFragment(mContext,notificationIdentifier);
//		if(newContent != null)
//			switchFragment(newContent);
//	}
//	
//	// the meat of switching the above fragment
//			private void switchFragment(Fragment fragment) {
//				if (getActivity() == null)
//					return;
//
//				if (getActivity() instanceof NotificationCenter) {
//					NotificationCenter notificationCenter = (NotificationCenter) getActivity();
//					notificationCenter.switchContent(fragment);
//				}
//			}
//}
