package com.example.aplikasisqlite;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasisqlite.helper.DbHelper;

public class AddEditActivity extends AppCompatActivity {
    EditText edtId, edtName, edtAdress;
    Button btnSubmit, btnCancel;
    DbHelper dbHelper = new DbHelper(this);
    String id, name, adress;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();
        edtId = findViewById(R.id.txt_id);
        edtName = findViewById(R.id.txt_name);
        edtAdress = findViewById(R.id.txt_adress);
        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);

        if (getIntent().hasExtra(MainActivity.TAG_ID)&&getIntent().hasExtra(MainActivity.TAG_NAME)&&getIntent().hasExtra(MainActivity.TAG_ADRESS)){
            id = bundle.getString(MainActivity.TAG_ID);
            name = bundle.getString(MainActivity.TAG_NAME);
            adress = bundle.getString(MainActivity.TAG_ADRESS);
            setTitle("Edit Data");
            edtId.setText(id);
            edtName.setText(name);
            edtAdress.setText(adress);

        }else{
            setTitle("Add Data");
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (edtId.getText().toString().equals("")){
                        save();
                    }else{
                        edit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    private void blank(){
        edtName.requestFocus();
        edtId.setText(null);
        edtName.setText(null);
        edtAdress.setText(null);
    }

    public void save(){
        if (TextUtils.isEmpty(edtName.getText()) || TextUtils.isEmpty(edtAdress.getText())){
            Toast.makeText(this, "Input Nama dan Alamat, Masih Kosong", Toast.LENGTH_SHORT).show();

        }else{
            dbHelper.insert(edtName.getText().toString().trim(), edtAdress.getText().toString().trim());
            blank();
            finish();
        }
    }

    public void edit(){
        if (TextUtils.isEmpty(edtName.getText()) || TextUtils.isEmpty(edtAdress.getText())){
            Toast.makeText(this, "Input Nama dan Alamat, Masih Kosong", Toast.LENGTH_SHORT).show();

        }else{
            dbHelper.update(Integer.parseInt(edtId.getText().toString().trim()), edtName.getText().toString().trim(), edtAdress.getText().toString().trim());
            blank();
            finish();
        }
    }
}
