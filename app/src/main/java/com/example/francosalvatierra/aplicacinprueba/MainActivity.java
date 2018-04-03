package com.example.francosalvatierra.aplicacinprueba;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        TextView txtView = (TextView)findViewById(R.id.txt);

        Intent receivedIntent = getIntent();

        if(receivedIntent.getAction().equals(Intent.ACTION_SEND))
        {
            ClipData receivedAction = receivedIntent.getClipData();

            txtView.setText(receivedAction.toString());
        }

        ((Button)findViewById(R.id.commit_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlert("Simulación de Upload");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mShare:
                /*Intent i = new Intent(
                        Intent.ACTION_ALL_APPS);
                i.setType("text/plain");
                i.putExtra(
                        android.content.Intent.EXTRA_TEXT, "My new app https://play.google.com/store/search?q=TECHHUBINDIAN");
                startActivity(Intent.createChooser(
                        i,
                        "Share Via"));*/
                new MaterialFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(1)
                        .withFilter(Pattern.compile(".*\\.pdf\\.xlsx$")) // Filtering files and directories by file name using regexp
                        /*.withFilterDirectories(true) // Set directories filterable (false by default)*/
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
        }

        Toast.makeText(getApplicationContext(), "You click on menu share", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    public void ShowAlert(String text){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Simulación de Upload")
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1001:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
}
