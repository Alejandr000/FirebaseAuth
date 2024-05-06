package net.lrivas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView tvbienvenida;
    Button btnsalir, btncambiar;
    private FirebaseAuth objfirebase;
    private  FirebaseAuth.AuthStateListener objFirebaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        tvbienvenida = findViewById(R.id.textView2);
        btnsalir = findViewById(R.id.button);
        btncambiar = findViewById(R.id.button6);
        objfirebase = FirebaseAuth.getInstance();
        objFirebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser objusuario = firebaseAuth.getCurrentUser();
                if (objusuario !=null){
                    tvbienvenida.setText("Bienvenido/a :"+objusuario.getEmail());
                }
            }
        };
        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });
        btncambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objLogin = new Intent(MainActivity.this, cambiarcontra.class);
                startActivity(objLogin);

            }
        });

    }
    @Override
    protected void onStart(){

        super.onStart();
        objfirebase.addAuthStateListener(objFirebaseListener);
    }
    @Override
    protected  void onStop(){

        super.onStop();
        if (objFirebaseListener !=null){

            objfirebase.removeAuthStateListener(objFirebaseListener);
        }
    }
    private void salir() {
        objfirebase.signOut();
        Intent objLogin = new Intent(MainActivity.this, Login.class);
        startActivity(objLogin);
        this.finish();
    }
}