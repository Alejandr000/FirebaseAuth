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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class cambiarcontra extends AppCompatActivity {
    EditText txtcontraactual, txtcontranueva;
    Button btnCambiarContra;
    private FirebaseAuth objFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cambiarcontra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    txtcontraactual = findViewById(R.id.txtactual);
    txtcontranueva = findViewById(R.id.txtnueva);
    btnCambiarContra = findViewById(R.id.button7);
        objFirebase = FirebaseAuth.getInstance();


        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contraactual = txtcontraactual.getText().toString().trim();
                final String contranueva = txtcontranueva.getText().toString().trim();

                if (contraactual.isEmpty()) {
                    Toast.makeText(cambiarcontra.this, "Complete todos los campos", Toast.LENGTH_LONG).show();

                }

                if (contranueva.isEmpty()) {
                    Toast.makeText(cambiarcontra.this, "Complete todos los campos", Toast.LENGTH_LONG).show();

                }
                final FirebaseUser user = objFirebase.getCurrentUser();
                if (user != null) {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), contraactual);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(contranueva).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(cambiarcontra.this, "Contraseña actualizada", Toast.LENGTH_LONG).show();
                                                    finish();
                                                } else {
                                                    Toast.makeText(cambiarcontra.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(cambiarcontra.this, "Error de autenticación " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}