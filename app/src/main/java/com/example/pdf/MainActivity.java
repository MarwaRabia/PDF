package com.example.pdf;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_CODE=1000;
    EditText editText,filen;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.edit);
        filen=findViewById(R.id.editfilename);
        button=findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                            (PackageManager.PERMISSION_DENIED)){
                        String [] permissions={android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,STORAGE_CODE);
                    }
                    else{
                        savepdf();
                        }
                }
                else{
                   savepdf();

               }

//                createPdf(filen.getText().toString(),editText.getText().toString());


            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void savepdf() {
        Document document=new Document();
        String mfilename=filen.getText().toString();
        String mfilePath= Environment.getExternalStorageDirectory() + "/" + mfilename + ".pdf";
        try{
            PdfWriter.getInstance(document,new FileOutputStream(mfilePath));
            document.open();
            String m=editText.getText().toString();
            document.addAuthor("marwa");
            document.add(new Paragraph(m));
            document.close();
            Toast.makeText(this, mfilename+".pdf\n is saved to \n"+mfilePath, Toast.LENGTH_SHORT).show();
        }
   catch (Exception e){
    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
}
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode){
        case STORAGE_CODE:
           if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
             }
            else{
            Toast.makeText(this, "permession denied ....", Toast.LENGTH_SHORT).show();
            }
}
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setTextLocale (Locale locale){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
           editText.setTextLocale(locale);
    }




    //

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(String title,String description){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawText(title, 20, 40, paint);
        canvas.drawText(description, 20, 60, paint);
        // finish the page
        document.finishPage(page);
        // draw text on the graphics object of the page
        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+title+".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done"+filePath, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }







}