package com.softpo.requestingasharedfile;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Button点击事件
    public void request(View view) {
        Intent requestIntent = new Intent(Intent.ACTION_PICK);
        requestIntent.setType("image/*");
        this.startActivityForResult(requestIntent, 101);
    }

    //获取数据的返回值处理
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent returnIntent) {
        // If the selection didn't work
        if (resultCode != RESULT_OK) {
            // Exit without doing anything else
            return;
        } else {
            // Get the file's content URI from the incoming Intent
            Uri returnUri = returnIntent.getData();
            ImageView showImg = (ImageView) findViewById(R.id.showImg);
            showImg.setImageURI(returnUri);

            Cursor returnCursor =
                    getContentResolver().query(returnUri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            String fileName = returnCursor.getString(nameIndex);
            long fileSize = returnCursor.getLong(sizeIndex);
            Log.d("flag", "----------------->onActivityResult: fileName: "+fileName+", fileSize: "+fileSize);

        }
    }
}
