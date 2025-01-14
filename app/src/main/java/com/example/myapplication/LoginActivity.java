package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private EditText txtusername,txtpassword;
    private Button btnlogin,btnsignUp;
    private TextView tv_forgotpassword, lgntxt, lgnb, ee, ep, dhacc;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ProgressDialog for loading
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setTitle("Loading");
        loading.setMessage("Please wait...");

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtusername = (EditText) findViewById(R.id.editTextTextEmailAddress);
        txtpassword = (EditText) findViewById(R.id.editTextTextPassword);
        tv_forgotpassword = (TextView) findViewById(R.id.textView_fgtpass);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnsignUp = (Button) findViewById(R.id.btn_signUp);
        lgntxt = findViewById(R.id.lgntxt);
        lgnb = findViewById(R.id.plz_lgn);
        ee = findViewById(R.id.enteremail);
        ep = findViewById(R.id.enterpass);
        dhacc = findViewById(R.id.dhacc);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // calculate NO of days between 2 dates
//        firebaseDatabase.getReference().child("Complaints").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try {
//                    for (DataSnapshot ds:snapshot.getChildren()){
//                        String dateofComplaint = ds.child("dateofComplaint").getValue(String.class);
//                        calendar = Calendar.getInstance();
//                        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                        String today = dateFormat.format(calendar.getTime());
//
//                        Date date1 = dateFormat.parse(dateofComplaint);
//                        Date date2 = dateFormat.parse(today);
//
//                        long diff = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
//                        String nodays = Long.toString(diff);
//                        Toast.makeText(LoginActivity.this, nodays, Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        
        lgnb.setTranslationY(300);
        lgntxt.setTranslationY(300);
        ee.setTranslationY(300);
        ep.setTranslationY(300);
        txtpassword.setTranslationY(300);
        txtusername.setTranslationY(300);
        tv_forgotpassword.setTranslationY(300);
        dhacc.setTranslationY(300);
        btnsignUp.setTranslationY(300);
        btnlogin.setTranslationY(300);


        lgntxt.setAlpha(v);
        lgnb.setAlpha(v);
        ee.setAlpha(v);
        ep.setAlpha(v);
        txtusername.setAlpha(v);
        txtpassword.setAlpha(v);
        tv_forgotpassword.setAlpha(v);
        dhacc.setAlpha(v);
        btnlogin.setAlpha(v);
        btnsignUp.setAlpha(v);


        lgntxt.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        lgnb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        ee.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        txtusername.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        ep.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        txtpassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        dhacc.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        tv_forgotpassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        btnlogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        btnsignUp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(it);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show();
                String username = txtusername.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();

                if(!username.isEmpty() && !password.isEmpty()){
                    mAuth.signInWithEmailAndPassword(username,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //loading.hide();
                                    if (task.isSuccessful()){
                                        String uid = task.getResult().getUser().getUid();

                                        checkUserCategory(uid);
                                        //loading.hide();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loading.hide();
                            if (e instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(LoginActivity.this,"Incorrect Email Address",Toast.LENGTH_SHORT).show();
                                txtusername.setError("Invalid Email");
                                txtusername.requestFocus();
                            }else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(LoginActivity.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                                txtpassword.setError("Invalid Password");
                                txtpassword.requestFocus();
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Enter valid email and password",Toast.LENGTH_SHORT).show();
                }
            }


        });

        tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }

    private void sendEmailVerifcation() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Verification link has been sent.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkUserCategory(String uid) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Users").child(uid).child("category")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String usertype = snapshot.getValue(String.class);
                        if(usertype.equals("Citizen")){
                            Toast.makeText(LoginActivity.this,"Citizen",Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(LoginActivity.this,CitizenHomeActivity.class);
                            it.putExtra("fragmentId",1);
                            startActivity(it);
                            finish();

                        }else if(usertype.equals("Admin")){
                            Toast.makeText(LoginActivity.this,"Admin",Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(LoginActivity.this, AdminHomeActivity.class);
                            it.putExtra("fragmentId",1);
                            startActivity(it);
                            finish();

                        }else if(usertype.equals("Driver")){
                            Intent it = new Intent(LoginActivity.this, DriverHomeActivity.class);
                            startActivity(it);
                            Toast.makeText(LoginActivity.this,"Driver",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}
