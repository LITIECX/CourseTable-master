package example.com.universitytimetable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.universitytimetable.OkHttp.IHttpClient;
import example.com.universitytimetable.OkHttp.IRequest;
import example.com.universitytimetable.OkHttp.IResponse;
import example.com.universitytimetable.OkHttp.MyOkHttpClient;
import example.com.universitytimetable.OkHttp.MyRequest;


public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView usernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    public static enum handler_key {

        /** 登录 */
        LOGIN,

        /** 自动登录 */
        AUTO_LOGIN,

        /** 第三方登录 */
        THRED_LOGIN,

    }



    /******************************************************************************************111*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        usernameView = (AutoCompleteTextView) findViewById(R.id.email);
//        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                // 点击软键盘上的回车键才会触发
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();  //登录操作
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoLogin();

    }

    /****************************************登录跳转**********************************************/

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        usernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = usernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;  //展示插件

        // 如果用户输入了密码，请检查有效密码
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 检查有效的电子邮件地址。
        if (TextUtils.isEmpty(name)) {
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        }

        if (cancel) {
            //有一个错误;不要尝试登录并将第一个表单字段与焦点对齐。
            focusView.requestFocus();//指向错误框
        } else {
            // 显示进度微调器，然后启动后台任务以执行用户登录尝试
            showProgress(true);
            mAuthTask = new UserLoginTask(name, password);
            mAuthTask.execute((Void) null);  //执行登录任务
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

/*********************************************************************************************3333*****/
    /**
     * 显示进度UI并隐藏登录表单。
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /****************************************************************************44444444444444444***/
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                //首先显示主电子邮件地址。请注意，如果用户未指定主电子邮件地址，则不会有//主电子邮件地址。
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ///创建适配器告诉AutoCompleteTextView在其下拉列表中显示什么。
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        usernameView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
/************************************************************************************************/


    /**
     * 表示用于对用户进行身份验证的异步登录/注册任务.
     */                                        //参数   进度  结果
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String userId;
        private final String mPassword;


        UserLoginTask(String username, String password) {
            userId = username;
            mPassword = password;

        }

        @Override
        protected Boolean doInBackground(Void... params) {  //执行耗时任务  子线程
            // 尝试对网络服务进行身份验证。

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) { //收尾工作  主线程执行
            mAuthTask = null;
            showProgress(false);
            if (success) {  //登录成功，保存数据，跳转到首页。
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE)
                        .edit();
                editor.putString("userId", userId);
                editor.putString("password", mPassword);
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));//密码错误
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {  //此函数表示任务关闭  主线程
            mAuthTask = null;
            showProgress(false);
        }
    }


    private void autoLogin() {

        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        if (TextUtils.isEmpty(pref.getString("userId", "")) || TextUtils.isEmpty(pref.getString("password", ""))) {
            return;
        }
        showProgress(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                IRequest request = new MyRequest("http://47.100.13.155:8080/TimeTable/autoLogin");
                request.setBody("userId", pref.getString("userId", ""));
                request.setBody("password",pref.getString("password", ""));
                IHttpClient mHttpClient = new MyOkHttpClient();
                IResponse response1 = mHttpClient.post(request);
                if (response1.getData().equals("ok")) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           showProgress(false);
                           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   });
                }
            }
        }).start();

    }



}