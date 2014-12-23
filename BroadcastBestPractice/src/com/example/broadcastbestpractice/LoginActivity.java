package com.example.broadcastbestpractice;

import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private EditText account;
	private EditText passwd;
	private Button login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		account = (EditText) findViewById(R.id.account);
		passwd = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		String acct = account.getText().toString();
		String pswd = passwd.getText().toString();
		if (acct.equals("admin") && pswd.equals("123456")) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(LoginActivity.this, "account or password is invalid",  Toast.LENGTH_SHORT).show();
		}
	}
}
