package net.lrivas.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button btniniciar, btnagregar, btncambiar;
    EditText txtcoreo, txtpass;
    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;
    private ProgressDialog objDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btniniciar = findViewById(R.id.button2);
        txtcoreo = findViewById(R.id.txtCorre);
        txtpass = findViewById(R.id.txtPass);
        btnagregar = findViewById(R.id.button3);
        btncambiar = findViewById(R.id.button4);
        objFirebase = FirebaseAuth.getInstance();
        objFirebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser objUsuario = firebaseAuth.getCurrentUser();
                if (objUsuario != null)
                {

                    cargarPrincipal();
                }
            }
        };
        objDialog = new ProgressDialog(this);
        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtcoreo.getText().toString().equals("") || txtpass.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Complete los campos", Toast.LENGTH_LONG).show();
                }else{
                    iniciarSesion();
                }

            }
        });
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objVentana = new Intent(Login.this, Agregar.class);
                startActivity(objVentana);

            }
        });
        btncambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objVentana = new Intent(Login.this, cambiar.class);
                startActivity(objVentana);
            }
        });
    }
    @Override
    protected void onStart(){


        super.onStart();
        objFirebase.addAuthStateListener(objFirebaseListener);
    }
    @Override
    protected  void onStop(){


        super.onStop();
        if (objFirebaseListener != null){
            objFirebase.removeAuthStateListener(objFirebaseListener);

        }

    }
    private void iniciarSesion() {
        objDialog.setMessage("Iniciando sesión...");
        objDialog.show();
        objFirebase.signInWithEmailAndPassword(txtcoreo.getText().toString(), txtpass.getText().toString()).addOnCompleteListener(
                Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = objFirebase.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {



                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else {
                                FirebaseUser users = objFirebase.getCurrentUser();

                                users.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Login.this, "Verifica tu correo electronico", Toast.LENGTH_LONG).show();

                                            }
                                        });
                            }

                        }else{

                            objDialog.dismiss();
                            Toast.makeText(Login.this,"Usuario o password incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }
                }


        );


    }

    private void cargarPrincipal() {
        Intent objVentana = new Intent(Login.this, MainActivity.class);
        startActivity(objVentana);
        this.finish();
    }
}