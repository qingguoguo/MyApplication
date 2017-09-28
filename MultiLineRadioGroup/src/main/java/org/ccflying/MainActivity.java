package org.ccflying;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.ccflying.MultiLineRadioGroup.OnCheckedChangedListener;

public class MainActivity extends Activity implements OnClickListener,
		OnCheckedChangedListener {
	private MultiLineRadioGroup group;
	private EditText mInput;
	private int gravity = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.showinlist).setOnClickListener(this);
		mInput = (EditText) findViewById(R.id.insert_position);
		group = (MultiLineRadioGroup) findViewById(R.id.content);
		group.setOnCheckChangedListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int j = -1;
		int id = item.getItemId();
		switch (id) {
		case R.id.append:
			group.append("append " + group.getChildCount());
			break;
		case R.id.clearChecked:
			group.clearChecked();
			break;
		case R.id.getCheckedItems:
			int[] cs = group.getCheckedItems();
			if (cs == null || cs.length == 0) {
				Toast.makeText(this, "none checked current!",
						Toast.LENGTH_SHORT).show();
			} else {
				String str = "";
				for (int i : cs) {
					str += i + ",";
				}
				Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.remove:
			j = getFunkIndex();
			if (j >= 0) {
				boolean bl = group.remove(j);
				if (bl) {
					Toast.makeText(this, "Child " + j + " removed!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "Invalid Position!",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.insert:
			j = getFunkIndex();
			if (j >= 0) {
				boolean bl = group.insert(j, "insert " + group.getChildCount());
				if (bl) {
					Toast.makeText(this, "insert a child at " + j,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "Invalid Position!",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.toggleMode:
			if (group.isSingleChoice()) {
				group.setChoiceMode(false);
			} else {
				group.setChoiceMode(true);
			}
			break;
		case R.id.setChecked:
			j = getFunkIndex();
			if (j >= 0) {
				boolean bl = group.setItemChecked(j);
				if (bl) {

				} else {
					Toast.makeText(this, "Invalid position!",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.setgravity:
			if (gravity == 0) { // center
				gravity = 1;
			} else if (gravity == 2) { // right
				gravity = 0;
			} else { // left
				gravity = 2;
			}
			group.setGravity(gravity);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem mi = menu.findItem(R.id.setgravity);
		if (gravity == 0) { // center
			mi.setTitle("setGravity(Left)");
		} else if (gravity == 2) { // right
			mi.setTitle("setGravity(Center)");
		} else { // left
			mi.setTitle("setGravity(Right)");
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.showinlist:
			startActivity(new Intent(this, ListAty.class));
			break;
		}
	}

	protected int getFunkIndex() {
		try {
			return Integer.valueOf(mInput.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "Please input a number!", Toast.LENGTH_SHORT)
					.show();
			return -1;
		}
	}

	@Override
	public void onItemChecked(MultiLineRadioGroup group, int position,
			boolean checked) {
		Log.e("MainActivity", "position:" + position);
	}
}
