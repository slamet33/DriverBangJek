package com.iu33.driverbangjek;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.iu33.driverbangjek.ConfigRetrofit.InstanceRetrofit;
import com.iu33.driverbangjek.History.HistoryActivity;
import com.iu33.driverbangjek.ResponeServer.ResponseDaftar;
import com.iu33.driverbangjek.helper.HeroHelper;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "a";
    //    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 12;
    GoogleSignInClient mGoogleSignInClient;
    //    GoogleApiClient api;
    @BindView(R.id.btngmail)
    Button btngmail;
    @BindView(R.id.btnLoginFacebook)
    Button btnfacebook;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();
        sessionManager.cerateLoginSession(email);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.iu33.driverbangjek",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }


//        Button btnGmail = findViewById(R.id.btngmail);
//        btnGmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signGmail();
//            }
//        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @OnClick({R.id.btngmail, R.id.btnLoginFacebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btngmail:
                signGmail();
                break;
            case R.id.btnLoginFacebook:
                signFacebook();
                break;
        }
    }

    private void signFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken.getCurrentAccessToken().getPermissions();
                String userId = loginResult.getAccessToken().getUserId();
                String nama = loginResult.getAccessToken().getApplicationId();

                Log.d(TAG, "dapat nama " + userId + "|" + nama);

                HeroHelper.pesan(context, "hasil :" + loginResult.toString());
                startActivity(new Intent(context, MainMenuActivity.class));
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                HeroHelper.pesan(context, "hasil :" + error.toString());
            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));

        HeroHelper.pesan(context, "Fb");

    }

    private void signGmail() {

        try {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN); //StartActtivity Result merupakan Implicit Intent
        } catch (RuntimeExecutionException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            String email = task.getResult().getEmail();
            String name = task.getResult().getDisplayName();

            // TODO
            sessionManager.setEmail(email);
            sessionManager.setNama(name);
//        startActivity(new Intent(c,HpActivity.class));
//        finish();

//        String photo = task.getResult().getPhotoUrl().toString();

            Log.d("gmail", email + "," + name);

            InstanceRetrofit.api.request_email(email).enqueue(new Callback<ResponseDaftar>() {
                @Override
                public void onResponse(Call<ResponseDaftar> call, Response<ResponseDaftar> response) {
                    if (response.isSuccessful()) {
                        String result = response.body().getResult();
                        if (result.equals("true")) {
                            startActivity(new Intent(context, HistoryActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(context, HpActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseDaftar> call, Throwable t) {

                }
            });
        } catch (RuntimeExecutionException e) {
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
