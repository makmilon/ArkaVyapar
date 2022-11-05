package thirdlaw.arkka.vyapar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import thirdlaw.arkka.vyapar.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView arrow_back, take_pic;
    Bitmap bitmap;
    String setTime;
    TextView timetext;
    String choice;
    Button produtBtn;
    String encodeImageString;

    EditText uploadSubcat, quantityUpload;

    Spinner spinner;
    ArrayList<String> countryList = new ArrayList<>();
    ArrayAdapter<String> countryAdapter;
    RequestQueue requestQueue;
    String user_id;

    String userStrSubcat,userStrquan;

    String user_success="Product Upload successful";

    private static final int CAMERA_PERMISSION_CODE=100;
    private static final int STORAGE_PERMISSION_CODE=200;
    private static final int IMAGE_FROM_GALLERY_CODE=300;
    private static final int IMAGE_FROM_CAMERA_CODE=400;

    private String [] cameraPermission;
    private String [] storagePermission;

    //image uri var
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        cameraPermission=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        spinner=findViewById(R.id.spinner_add_product);
        produtBtn=findViewById(R.id.productUploadBtn);
        uploadSubcat=findViewById(R.id.uploadSubcat);
        quantityUpload=findViewById(R.id.quantityUpload);



        //take pic
        take_pic=findViewById(R.id.imageView19);


        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        //take pic


        //time picker
        timetext=findViewById(R.id.timeText);

        MaterialDatePicker materialDatePicker=MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds())).build();


        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        timetext.setText(materialDatePicker.getHeaderText());
                        setTime=materialDatePicker.getHeaderText();


                    }
                });

            }
        });
        //time picker

        arrow_back=findViewById(R.id.arrow_back_add_product);

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadProduct.this,MainActivity.class));
            }
        });



        //search list

        requestQueue = Volley.newRequestQueue(this);

        spinner = findViewById(R.id.spinner_add_product);

        String url = "http://14.139.158.99/arkvyaper/api/cat.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String countryName = jsonObject.optString("cname");
                        countryList.add(countryName);
                        countryAdapter = new ArrayAdapter<>(UploadProduct.this,
                                android.R.layout.simple_spinner_item, countryList);
                        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(countryAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinner.setOnItemSelectedListener(this);

        //search list


        //product upload

        produtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadProduct();
                startActivity(new Intent(UploadProduct.this,MoreProductUploadSeller.class));
            }
        });

        //product upload

    }

    private void uploadProduct() {

        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        user_id=sp.getString("user_id","");


        String producturl="http://14.139.158.99/arkvyaper/api/add_product.php?uid="+user_id;
        userStrSubcat=uploadSubcat.getText().toString();
        userStrquan=quantityUpload.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, producturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resObject=new JSONObject(response);
                    String responseStr= resObject.getString("ResponseMsg");
                    Log.d("REGISTER", responseStr);
                    uploadSubcat.setText("");
                    quantityUpload.setText("");
                    Toast.makeText(getApplicationContext(),user_success, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("uid",user_id);
                params.put("cid",choice);
                params.put("sname",userStrSubcat);
                params.put("pqty",userStrquan);
                params.put("pdate",setTime);
                params.put("pimg",encodeImageString);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);



    }


    private void showImagePickerDialog() {
        //option for dialog
        String options[]={"Camera", "Gallery"};

        //alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //set title
        builder.setTitle("Choose an Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle item click
                if (which==0){//start from zero index
                    //camera selected
                    if (!checkCameraPermission()){
                        //request camera permission
                        requestCameraPermission();
                    }else {
                        pickFromCamera();
                    }
                }else if(which==1){
                    //storage selected
                    if (!checkStoragePermission()){
                        //request camera permission
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }
                }

            }
        }).create().show();
    }

    private void pickFromGallery() {
        //intent for taking image from gallary
        Intent gallaryIntent= new Intent(Intent.ACTION_PICK);
        gallaryIntent.setType("image/*"); //only image

        startActivityForResult(gallaryIntent, IMAGE_FROM_GALLERY_CODE);

    }

    private void pickFromCamera() {

        //content value for image info
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION,"IMAGE_DETAIL");


        //save image uri
        Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

        startActivityForResult(cameraIntent, IMAGE_FROM_CAMERA_CODE);
    }


    //check camera permission
    private boolean checkCameraPermission(){
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result && result1;
    }

    //request for camera permission
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_PERMISSION_CODE);
    }


    //check storage permission
    private boolean checkStoragePermission(){
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result1;
    }

    //request for storage permission
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_PERMISSION_CODE);
    }


    //handle request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length>0){
                    //if all permission allowed return true, otherwise false
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageaAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageaAccepted){
                        //both permission granted
                        pickFromCamera();
                    }else {
                        Toast.makeText(getApplicationContext(),"Camera and Storage Permission needed...",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length>0){
                    //if all permission allowed return true, otherwise false
                    boolean storageaAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if (storageaAccepted){
                        //permission granted
                        pickFromGallery();
                    }else {
                        Toast.makeText(getApplicationContext(),"Storage Permission needed...",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }




    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMAGE_FROM_GALLERY_CODE){
            try {
                InputStream inputStream =getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                take_pic.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else if (requestCode==IMAGE_FROM_CAMERA_CODE){
            bitmap=(Bitmap)data.getExtras().get("data");
            take_pic.setImageBitmap(bitmap);
            encodeBitmapImage(bitmap);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choice= parent.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}