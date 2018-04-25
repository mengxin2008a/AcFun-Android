package com.kento.component.basic.commonwidget.filter.typeview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kento.component.basic.commonwidget.filter.adapter.BaseBaseAdapter;
import com.kento.component.basic.commonwidget.filter.interfaces.OnFilterItemClickListener;
import com.kento.component.basic.commonwidget.filter.util.CommonUtil;

import java.util.List;


/**
 * 单列ListView
 */
public class SingleListView< DATA > extends ListView implements AdapterView.OnItemClickListener {

	private BaseBaseAdapter< DATA > mAdapter;
	private OnFilterItemClickListener< DATA > mOnItemClickListener;

	public SingleListView( Context context ) {
		this( context, null );
	}

	public SingleListView( Context context, AttributeSet attrs ) {
		super( context, attrs );
		init( context );
	}

	public SingleListView( Context context, AttributeSet attrs, int defStyleAttr ) {
		super( context, attrs, defStyleAttr );
		init( context );
	}

	//    android:divider="@color/text_color_grayeb"
//    android:dividerHeight="1dp"
//    android:scrollbars="vertical"/>
	private void init( Context context ) {
		setChoiceMode( ListView.CHOICE_MODE_SINGLE );
		setDivider( null );
		setDividerHeight( 0 );
		setSelector( new ColorDrawable( Color.TRANSPARENT ) );
		setVerticalScrollBarEnabled( true );
		setOnItemClickListener( this );
	}


	public SingleListView< DATA > adapter( BaseBaseAdapter< DATA > adapter ) {
		this.mAdapter = adapter;
		setAdapter( adapter );
		return this;
	}

	public SingleListView< DATA > onItemClick( OnFilterItemClickListener< DATA > onItemClickListener ) {
		this.mOnItemClickListener = onItemClickListener;
		return this;
	}

	public void setList( List< DATA > list, int checkedPositoin ) {
		mAdapter.setList( list );

		if ( checkedPositoin != -1 ) {
			setItemChecked( checkedPositoin, true );
		}
	}


	@Override
	public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
		if ( CommonUtil.isFastDoubleClick() ) {
			return;
		}

		DATA item = mAdapter.getItem( position );
		if ( mOnItemClickListener != null ) {
			mOnItemClickListener.onItemClick( item );
		}
	}


}
