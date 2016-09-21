package ru.fineplaces.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import ru.fineplaces.App;
import ru.fineplaces.R;
import ru.fineplaces.service.AuthenticationService;
import ru.fineplaces.utils.ViewUtils;

public class RegisterActivity extends AppCompatActivity {

    private UserRegisterTask registerTask = null;

    // UI references.
    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private EditText orgNameView;
    private View progressView;
    private View registerFormView;
    private Button registerButton;
    private Activity thisActivity;

    @Inject
    AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        emailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        passwordView = (EditText) findViewById(R.id.register_password);
        orgNameView = (EditText) findViewById(R.id.register_name);
        registerFormView = findViewById(R.id.register_form_scroll_view);
        progressView = findViewById(R.id.register_progress);
        thisActivity = this;

        App.getComponent().inject(this);


        registerButton = (Button) findViewById(R.id.register_register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });


    }

    private void attemptRegister() {
        if (registerTask != null) {
            return;
        }

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);
        orgNameView.setError(null);

        // Store values at the time of the login attempt.
        String email = this.emailView.getText().toString();
        String password = this.passwordView.getText().toString();
        String orgName = this.orgNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid emailView address.
        if (TextUtils.isEmpty(email)) {
            this.emailView.setError(getString(R.string.error_field_required));
            focusView = this.emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            this.emailView.setError(getString(R.string.error_invalid_email));
            focusView = this.emailView;
            cancel = true;
        }

        // Check for a valid organisation name.
        if (TextUtils.isEmpty(orgName)) {
            this.orgNameView.setError(getString(R.string.error_field_required));
            focusView = this.orgNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            registerTask = new UserRegisterTask(email, password, orgName);
            registerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void) null);
//            registerTask.execute((Void) null);
            Toast.makeText(this, getString(R.string.authentication_wait), Toast.LENGTH_SHORT).show();


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;
        private final String orgName;

        UserRegisterTask(String email, String password, String orgName) {
            this.email = email;
            this.password = password;
            this.orgName = orgName;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return authenticationService.register(orgName, email, password);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            registerTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(thisActivity, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(thisActivity, getString(R.string.register_error), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            registerTask = null;
            showProgress(false);
        }
    }
}

