//package com.locuslabs.notiphisample;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.notikum.notifypassive.utils.Constants;
//import com.notikum.notifypassive.utils.NotiphiPromotion;
//import com.notikum.notifypassive.utils.UrlImageView;
//
//public class NotificationDetailsActivity extends Activity{
//
//	private static final String TAG = NotificationDetailsActivity.class.getSimpleName();
//	@Override
//	public void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.notification_details_layout);
//		UrlImageView notificationImage = (UrlImageView)findViewById(R.id.notification_image);
//		TextView notificationTitle = (TextView)findViewById(R.id.notification_title);
//		TextView notificationContent = (TextView)findViewById(R.id.notification_content);
//		NotiphiPromotion promotion = getIntent().getParcelableExtra("promotion");
//		Log.d(TAG, "Promotion id is = " + promotion.getPromotionId());
//		Log.d(TAG, " promo title = " + promotion.getPromoTitle());
//		Log.d(TAG, " promo content = " + promotion.getPromoContent());
//		notificationImage.setImageDrawable(Constants.IMAGE_SERVER_ADDRESS+promotion.getPromoCompanyLogoLink());
//		notificationTitle.setText(promotion.getPromoTitle());
//		notificationContent.setText(promotion.getPromoContent());
//	}
//	
//	@Override
//	public void onStart(){
//		super.onStart();
//	}
//	
//	
//	@Override
//	public void onStop(){
//		super.onStop();
//	}
//	
//	@Override
//	public void onDestroy(){
//		super.onDestroy();
//	}
//}
