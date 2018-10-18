package com.ore.vicse.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.ore.vicse.firebase.activities.HomeActivity;
import com.ore.vicse.firebase.activities.RegisterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText TextEmail;
    private TextView btnRegistrar;
    private EditText TextPassword;
    private CardView btnLogin;
    private ProgressDialog progressDialog;


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail= (EditText) findViewById(R.id.editText3);
        TextPassword= (EditText) findViewById(R.id.editText2);
        btnRegistrar = (TextView) findViewById(R.id.textRegistrar) ;
        btnLogin= (CardView) findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }



    private void loguearUsuario(){

        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Falta ingresar contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Ingresando");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Bienvenido"+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(getApplication(),HomeActivity.class);
                            intent.putExtra(HomeActivity.user, email);
                            startActivity(intent);

                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this, "Ese usuario ya existe",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "No se pudo registrar el usuario",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view){

        switch (view.getId()){
            case R.id.textRegistrar:
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonLogin:
                loguearUsuario();
        }
    }


}
