package com.ore.vicse.firebase.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.ore.vicse.firebase.MainActivity;
import com.ore.vicse.firebase.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView TxtVolver;
    private EditText TextEmail,TextPassword;
    private CardView btnRegistrar;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        TxtVolver= (TextView)findViewById(R.id.textVolver);
        TextEmail=(EditText)findViewById(R.id.editText3);
        TextPassword=(EditText) findViewById(R.id.editText2);
        btnRegistrar=(CardView) findViewById(R.id.buttonRegister) ;

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
        TxtVolver.setOnClickListener(this);
    }

    private void registrarUsuario(){
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Falta ingresar contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "Ese usuario ya existe",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario",
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
            case R.id.buttonRegister:
                registrarUsuario();
                break;
            case R.id.textVolver:
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
        }
    }
}
