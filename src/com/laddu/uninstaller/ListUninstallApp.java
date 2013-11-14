package com.laddu.uninstaller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author chokkar
 * 
 */

public class ListUninstallApp extends ListActivity {

	List<String> appNameList;
	Map<String, String> appPackageNames;
	ListView listView;
	private static final String LOG_TAG = ListUninstallApp.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_display, installedApps());
		setListAdapter(arrayAdapter);
		listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				String appName = (String) ((TextView) view).getText();
				String packageName = appPackageNames.get(appName);
				unInstallApp(packageName);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_uninstall_main, menu);
		return true;
	}

	public List<String> installedApps() {
		appNameList = new ArrayList<String>();
		appPackageNames = new HashMap<String, String>();
		List<PackageInfo> PackList = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (int i = 0; i < PackList.size(); i++) {
			PackageInfo packInfo = PackList.get(i);
			if (((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true) {
				String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
				String packageName = packInfo.packageName;
				appPackageNames.put(appName, packageName);
				appNameList.add(appName);
			}
		}
		return appNameList;
	}

	public void unInstallApp(String packageName) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		startActivity(uninstallIntent);
	}

}
