package com.rateworld.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.rateworld.R;

public class ImageSlideAdapter extends PagerAdapter {
	ImageLoader imageLoader = ImageLoader.getInstance();
	
	DisplayImageOptions options;
	private ImageLoadingListener imageListener;
	//Activity activity;
	Context context;
	ArrayList<String> products;
	//HomeFragment homeFragment;
	
	public ImageSlideAdapter(Context context, ArrayList<String> products) {
		this.context = context;
		//this.homeFragment = homeFragment;
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		this.products = products;
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.pictureone)
				.showStubImage(R.drawable.pictureone)
				.showImageForEmptyUri(R.drawable.pictureone).cacheInMemory()
				.cacheOnDisc().build();

		imageListener = new ImageDisplayListener();
	}

	@Override
	public int getCount() {
		return products.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		
		//System.out.println("in adapter");
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.vp_image, container, false);
		
		ImageView mImageView = (ImageView) view
				.findViewById(R.id.image_display);
		
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
		
		imageLoader.displayImage(
				(products.get(position)), mImageView,
				options, imageListener);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	private static class ImageDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
