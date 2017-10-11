package com.example.tianzl.multilevelmenu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	 private Map<String, List<String>> dataset = new HashMap<String, List<String>>();
	 private List<String> parList=new ArrayList<String>();
	 private Context context;
	 private Map<Integer, Boolean> parentMap=new HashMap<Integer, Boolean>();
	 private int oldTag=0;
	 private int childTag=-1;
	 private boolean changeChild=false;
	 
	 public ExpandableListViewAdapter(Context context, Map<String, List<String>> dataset){
		 this.context=context;
		 this.dataset=dataset;
		 initParentMap();
		 initKey();
	 }
	 public void initParentMap(){
		 for (int i = 0; i < dataset.size(); i++) {
			 if (i==0) {
				 parentMap.put(i, true);	
			}else {
				parentMap.put(i, false);
			}
		}
	 }
	 //初始化key值
	 public void initMapKey(){
		 for(String key : dataset.keySet()){ 
			 parList.add(key);
		 }  
	 }
	public void initKey() {
		//使用迭代器，获取key;  
        Iterator<String> iter = dataset.keySet().iterator(); 
        while(iter.hasNext()){ 
            String key=iter.next(); 
            parList.add(key);
        } 
	}
    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
    	
        return dataset.get(parList.get(parentPos)).get(childPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        if (dataset == null) {
            return 0;
        }
        return dataset.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        if (dataset.get(parList.get(parentPos)) == null) {
            return 0;
        }
        return dataset.get(parList.get(parentPos)).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return dataset.get(parList.get(parentPos));
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(final int parentPos, final boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) 
            		context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_parent, null);
        }
        view.setTag(R.layout.item_parent, parentPos);
        view.setTag(R.layout.item_child, -1);
        TextView text =view.findViewById(R.id.item_venue_detail_parent_tv);
        final ImageView ivMore=view.findViewById(R.id.item_venue_detail_parent_img);
        
        text.setText(parList.get(parentPos));
        text.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!b) {
		    		ObjectAnimator animator = ObjectAnimator.ofFloat(ivMore,
							"rotation", 0f, -180f);
					animator.setDuration(200);
					animator.start();	
				}else {
					ObjectAnimator animator = ObjectAnimator.ofFloat(ivMore,
							"rotation", -180f, 0f);
					animator.setDuration(200);
					animator.start();
				}
				changeChild=false;
				listener.onItemParentClick(parentPos);
				return false;
			}
		});
        return view;
    }
    //选择父项
    public void changeParentMap(int pos){
    	if (oldTag!=pos) {
    		oldTag=pos;
		}
    }
    //选择子项
    public void  changeChildMap(int parent,int child,boolean b,View view){
    	changeParentMap(parent);
    	changeChild=true;
    	childTag=child;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(final int parentPos, final int childPos, final boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_child, null);
        }
        view.setTag(R.layout.item_parent, parentPos);
        view.setTag(R.layout.item_child, childPos);
        TextView text = (TextView) view.findViewById(R.id.item_venue_detail_child_tv);
        final ImageView ivPlay=(ImageView) view.findViewById(R.id.item_venue_detail_child_play);
        text.setText(dataset.get(parList.get(parentPos)).get(childPos));
        if (parentPos==oldTag && childPos == childTag ) {
        	text.setTextColor(0xffFF7B61);
        	ivPlay.setVisibility(View.VISIBLE);
		}else {
			text.setTextColor(Color.BLACK);
        	ivPlay.setVisibility(View.INVISIBLE);
		}
        text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeChildMap(parentPos,childPos,b,ivPlay);
				notifyDataSetChanged();
				listener.onItemChildClick(parentPos, childPos);
			}
		});
        return view;
    }
    
    //回调父item和子item的点击事件
    public interface OnItemClickListener{
    	void onItemParentClick(int parent);
    	void onItemChildClick(int parent, int child);
    }
    private OnItemClickListener listener;
    
    public void setOnItemClickListener(OnItemClickListener listener){
    	this.listener=listener;
    }
    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    @Override
    public void onGroupCollapsed(int groupPosition) {
    	super.onGroupCollapsed(groupPosition);
    }
}