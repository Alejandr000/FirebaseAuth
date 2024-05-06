package net.lrivas.myapplication;

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
import com.google.firebase.auth.FirebaseAuth;

public class cambiar extends AppCompatActivity {
    EditText txtEmailModific;
    Button btnModific;
    private FirebaseAuth objFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cambiar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        txtEmailModific= findViewById(R.id.txtemail);
        btnModific = findViewById(R.id.button5);



        objFirebase = FirebaseAuth.getInstance();
        btnModific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtEmailModific.getText().toString().trim();

                if (correo.isEmpty()){

                    txtEmailModific.setError("Complete este campo");
                    txtEmailModific.requestFocus();

                }
                objFirebase.sendPasswordResetEmail(correo)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(cambiar.this, "Contraseña cambiada, revise su correo", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(cambiar.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });



    }




}