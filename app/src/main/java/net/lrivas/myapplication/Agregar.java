package net.lrivas.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class Agregar extends AppCompatActivity {

    Button btniniciar, btnagregar;
    EditText txtcoreo, txtpass;
    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;
    private ProgressDialog objDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btniniciar = findViewById(R.id.button2);
        txtcoreo = findViewById(R.id.txtCorre);
        txtpass = findViewById(R.id.txtPass);
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
                iniciarSesion();
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
        objFirebase.createUserWithEmailAndPassword(txtcoreo.getText().toString(), txtpass.getText().toString()).addOnCompleteListener(
                Agregar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            objDialog.dismiss();
                            cargarPrincipal();
                        }else{

                            objDialog.dismiss();
                            Toast.makeText(Agregar.this,"Usuario o password incorrecto",Toast.LENGTH_SHORT).show();
                        }
                    }
                }


        );
    }

    private void cargarPrincipal() {
        Intent objVentana = new Intent(Agregar.this, MainActivity.class);
        startActivity(objVentana);
        this.finish();
    }
}