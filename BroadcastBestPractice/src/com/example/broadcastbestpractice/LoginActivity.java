package com.example.broadcastbestpractice;

import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
	private EditText account;
	private EditText passwd;
	private CheckBox rememberPass;
	private Button login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		account = (EditText) findViewById(R.id.account);
		passwd = (EditText) findViewById(R.id.password);
		rememberPass = (CheckBox) findViewById(R.id.remember_pass);
		login = (Button) findViewById(R.id.login);
		boolean isRemember = pref.getBoolean("remember_password", false);
		if (isRemember) {
			String acc = pref.getString("account", "");
			String pswd = pref.getString("password", "");
			account.setText(acc);
			passwd.setText(pswd);
			rememberPass.setChecked(true);
		}
		login.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		String acct = account.getText().toString();
		String pswd = passwd.getText().toString();
		if (acct.equals("admin") && pswd.equals("123456")) {
			editor = pref.edit();
			if (rememberPass.isChecked()) {
				editor.putBoolean("remember_password", true);
				editor.putString("account", acct);
				editor.putString("password", pswd);
			} else {
				editor.clear();
			}
			editor.commit();
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(LoginActivity.this, "account or password is invalid",  Toast.LENGTH_SHORT).show();
		}
	}
}
