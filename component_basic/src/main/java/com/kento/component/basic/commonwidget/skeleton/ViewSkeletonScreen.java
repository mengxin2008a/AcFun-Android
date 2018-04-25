package com.kento.component.basic.commonwidget.skeleton;

import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kento.component.basic.R;


/**
 * Created by ethanhua on 2017/7/29.
 */

public class ViewSkeletonScreen implements SkeletonScreen {
	private static final String TAG = ViewSkeletonScreen.class.getName();
	private final ViewReplacer mViewReplacer;
	private final View mActualView;
	private final int mSkeletonResID;
	private final int mShimmerColor;
	private final boolean mShimmer;
	private final int mShimmerDuration;
	private final int mShimmerAngle;
	private boolean isShow = false;
	private ViewSkeletonScreen( Builder builder ) {
		mActualView = builder.mView;
		mSkeletonResID = builder.mSkeletonLayoutResID;
		mShimmer = builder.mShimmer;
		mShimmerDuration = builder.mShimmerDuration;
		mShimmerAngle = builder.mShimmerAngle;
		mShimmerColor = builder.mShimmerColor;
		mViewReplacer = new ViewReplacer( builder.mView );
	}

	private ShimmerLayout generateShimmerContainerLayout( ViewGroup parentView ) {
		ShimmerLayout shimmerLayout = ( ShimmerLayout ) LayoutInflater.from( mActualView.getContext() )
																	  .inflate( R.layout.layout_shimmer, null );
		shimmerLayout.setShimmerColor( mShimmerColor );
		shimmerLayout.setShimmerAngle( mShimmerAngle );
		shimmerLayout.setShimmerAnimationDuration( mShimmerDuration );
		View innerView = LayoutInflater.from( shimmerLayout.getContext() )
									   .inflate( mSkeletonResID, null );
		shimmerLayout.addView( innerView );
		shimmerLayout.startShimmerAnimation();
		return shimmerLayout;
	}

	private View generateSkeletonLoadingView() {
		ViewParent viewParent = mActualView.getParent();
		if ( viewParent == null ) {
			Log.e( TAG, "the source view have not attach to any view" );
			return null;
		}
		ViewGroup parentView = ( ViewGroup ) viewParent;
		if ( mShimmer ) {
			return generateShimmerContainerLayout( parentView );
		}
		return LayoutInflater.from( mActualView.getContext() )
							 .inflate( mSkeletonResID, parentView, false );
	}

	@Override
	public void show() {
		isShow = true;
		View skeletonLoadingView = generateSkeletonLoadingView();
		if ( skeletonLoadingView != null ) {
			mViewReplacer.replace( skeletonLoadingView );
		}
	}

	@Override
	public void hide() {
		isShow = false;
		mViewReplacer.restore();
	}
	@Override
	public boolean isShowing() {
		return isShow;
	}
	public static class Builder {
		private final View mView;
		private int mSkeletonLayoutResID;
		private boolean mShimmer = true;
		private int mShimmerColor;
		private int mShimmerDuration = 1000;
		private int mShimmerAngle = 20;

		public Builder( View view ) {
			this.mView = view;
			this.mShimmerColor = ContextCompat.getColor( mView.getContext(), R.color.shimmer_color );
		}

		/**
		 * @param skeletonLayoutResID the loading skeleton layoutResID
		 */
		public Builder load( @LayoutRes int skeletonLayoutResID ) {
			this.mSkeletonLayoutResID = skeletonLayoutResID;
			return this;
		}

		/**
		 * @param shimmerColor the shimmer color
		 */
		public Builder color( @ColorRes int shimmerColor ) {
			this.mShimmerColor = ContextCompat.getColor( mView.getContext(), shimmerColor );
			return this;
		}

		/**
		 * @param shimmer whether show shimmer animation
		 */
		public ViewSkeletonScreen.Builder shimmer( boolean shimmer ) {
			this.mShimmer = shimmer;
			return this;
		}

		/**
		 * the duration of the animation , the time it will take for the highlight to move from one end of the layout
		 * to the other.
		 *
		 * @param shimmerDuration Duration of the shimmer animation, in milliseconds
		 */
		public ViewSkeletonScreen.Builder duration( int shimmerDuration ) {
			this.mShimmerDuration = shimmerDuration;
			return this;
		}

		/**
		 * @param shimmerAngle the angle of the shimmer effect in clockwise direction in degrees.
		 */
		public ViewSkeletonScreen.Builder angle( @IntRange( from = 0, to = 30 ) int shimmerAngle ) {
			this.mShimmerAngle = shimmerAngle;
			return this;
		}

		public ViewSkeletonScreen show() {
			ViewSkeletonScreen skeletonScreen = new ViewSkeletonScreen( this );
			skeletonScreen.show();
			return skeletonScreen;
		}

	}
}
