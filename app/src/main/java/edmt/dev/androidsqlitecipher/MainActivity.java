package edmt.dev.androidsqlitecipher;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import net.sqlcipher.database.SQLiteDatabase;

import edmt.dev.androidsqlitecipher.Helper.DBHelper;

public class MainActivity extends AppCompatActivity {

    Button btnAdd,btnUpdate,btnDelete;
    EditText edtEmail;

    ListView lstEmails;



    String saveEmail =""; // Save current email to update / delete
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);



        //Init view
        lstEmails = (ListView)findViewById(R.id.lstEmails);
        lstEmails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)lstEmails.getItemAtPosition(i);
                edtEmail.setText(item);
                saveEmail = item;
            }
        });

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnUpdate= (Button)findViewById(R.id.btnUpdate);
        btnDelete= (Button)findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(MainActivity.this).insertNewEmail(edtEmail.getText().toString());
                reloadEmails();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(MainActivity.this)
                        .updateEmail(saveEmail,edtEmail.getText().toString());
                reloadEmails();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.getInstance(MainActivity.this)
                        .deleteEmail(edtEmail.getText().toString());
                reloadEmails();
            }
        });

        reloadEmails();
    }

    private void reloadEmails() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                DBHelper.getInstance(this).getAllEmail());
        lstEmails.setAdapter(adapter);

    }
}
