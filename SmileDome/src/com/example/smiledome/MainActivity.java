package com.example.smiledome;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private List<Smile> smiles = null; 
	private GridView emoj;
	private SmiliesEditText messageContent;
	private Button face;
	private boolean flag = false; // 表情框显示状态
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		emoj = (GridView) findViewById(R.id.emoj);
		// 监听表情被点击了哪一项
		emoj.setOnItemClickListener(gridViewFaceItemClickListener);
		messageContent = (SmiliesEditText) findViewById(R.id.EditText1);
		//messageContent.insertIcon(R.drawable.emo_001);
		
		face = (Button) findViewById(R.id.face);
		face.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!flag) {
					// 插入表情
					flag = true;
					emoj.setVisibility(View.VISIBLE);
					face.setText("关闭");
					messageContent.setVisibility(View.VISIBLE);
				} else {
					flag = false;
					emoj.setVisibility(View.GONE);
					messageContent.setVisibility(View.VISIBLE);
					face.setText("表情");
				}
			}
		});
		
		initGridView();
	}
	
	private void initGridView() {
		try {
			InputStream inputStream = this.getResources().getAssets().open("brow.xml"); // 取得assets中的borw.xml文件
			smiles = ParserBrowXml.getInfo(inputStream); // 解析borw.xml
			addexpression(this, smiles, emoj);// 调用生情表情的方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addexpression(Context context, List<Smile> smiles, GridView gridView) throws Exception {
		// 通过反射把资源文件中的图片取出来放在GridView上
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < smiles.size(); i++) {
			Smile smile = smiles.get(i);
			if (smile != null) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				Field f = (Field) R.drawable.class.getDeclaredField(smile.getName());
				int j = f.getInt(R.drawable.class);
				map.put("ItemImage", j);// 添加图像资源的ID
				lstImageItem.add(map);
			}
		}

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(context, lstImageItem,// 数据来源
				R.layout.brow_item,
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage" },
				// ImageItem的XML文件里面的一个ImageView
				new int[] { R.id.iv_brow });
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));// 解决点击GridView背景变黑的情况
		gridView.setAdapter(saImageItems);
	}
	
	private OnItemClickListener gridViewFaceItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			// 首先得到当前用户点击的表情的信息
			Smile smile = smiles.get(position);
			// 得到当前CURSOR位置
			int cursor = messageContent.getSelectionStart();
			Field f;
			try {
				// 根据资源名字得到Resource和对应的Drawable
				f = (Field) R.drawable.class.getDeclaredField(smile.getName());
				int j = f.getInt(R.drawable.class);
				Drawable d = getResources().getDrawable(j);
				d.setBounds(0, 0, 30, 30);// 设置表情图片的显示大小
				// 显示在EditText中
				String str = "img";
				SpannableString ss = new SpannableString(str);
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
				ss.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				messageContent.getText().insert(cursor, ss);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
