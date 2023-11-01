package com.example.firebase_teste;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText mEdtId, mEdtNome, mEdtTelefone;
    Button mBtnSalvar, mBtnPesquisar, mBtnAtualizar, mBtnRemover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtId = findViewById(R.id.edtId);
        mEdtNome = findViewById(R.id.edtNome);
        mEdtTelefone = findViewById(R.id.edtTelefone);
        mBtnSalvar = findViewById(R.id.btnSalvar);
        mBtnPesquisar = findViewById(R.id.btnPesquisar);
        mBtnAtualizar = findViewById(R.id.btnAtualizar);
        mBtnRemover = findViewById(R.id.btnRemover);

        mBtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarContato();
            }
        });

        mBtnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesquisarDados();
            }
        });

        mBtnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarDados();
            }
        });

        /*mBtnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerDados();
            }
        });*/


        mBtnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = getLayoutInflater();
                //inflamos o layout xml na view
                View view2 = li.inflate(R.layout.box_escolha, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Escolha o id:");
                builder.setView(view2);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText mEdt = view2.findViewById(R.id.EdtBox);
                        int id = Integer.parseInt(mEdt.getText().toString());
                        String t = mEdt.getText().toString();
                        //Toast.makeText(MainActivity.this, t, Toast.LENGTH_SHORT).show();
                        removerDadosTeste(id);
                    }
                });

                AlertDialog box = builder.create();
                box.show();


                //removerDados();
            }
        });

    }

    public void pesquisarDados() {

        // Adicionamos o ChildEventListener para a referência do Realtime Database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        int id = Integer.parseInt(mEdtId.getText().toString());

        database.child("agenda").orderByChild("id").equalTo(id).addChildEventListener(childEventListener);


    }

    public void atualizarDados() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        int id = Integer.parseInt(mEdtId.getText().toString());

        database.child("agenda").orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Verificamos todas as referências existentes através do método getChildren()
                for (DataSnapshot data : snapshot.getChildren()) {
                    // Objeto com a estrutura chave-valor
                    // String é a chave
                    // Object é o valor
                    Map<String, Object> valoresAtualizados = new HashMap<>();

                    // Informamos qual atributo desejamos atualizar e o valor atualizado
                    valoresAtualizados.put("telefone","111");
                    data.getRef().updateChildren(valoresAtualizados);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removerDados() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        int id = Integer.parseInt(mEdtId.getText().toString());

        database.child("agenda").orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Percorremos todas as referências existentes através do método getChildren()
                for (DataSnapshot data : snapshot.getChildren()) {

                    // Removemos o produto "1" do Realtime Database
                    data.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void removerDadosTeste(int id) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("agenda").orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Percorremos todas as referências existentes através do método getChildren()
                for (DataSnapshot data : snapshot.getChildren()) {

                    // Removemos o produto "1" do Realtime Database
                    data.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Agenda agenda = snapshot.getValue(Agenda.class);
            Log.i("FIREBASE", snapshot.getValue().toString());
            mEdtNome.setText(agenda.getNome());
            mEdtTelefone.setText(agenda.getTelefone());


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    public void salvarContato(){

        String idStr = mEdtId.getText().toString();
        int id = Integer.parseInt(idStr);
        Agenda agenda = new Agenda(id,mEdtNome.getText().toString(), mEdtTelefone.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("agenda").push().setValue(agenda);

        mEdtId.setText("");
        mEdtNome.setText("");
        mEdtTelefone.setText("");

    }






}