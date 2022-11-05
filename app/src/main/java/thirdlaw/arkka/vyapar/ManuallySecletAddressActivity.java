package thirdlaw.arkka.vyapar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import thirdlaw.arkka.vyapar.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ManuallySecletAddressActivity extends AppCompatActivity {

    Button btn_go_home_page;
    EditText addressLine, city, pincode, contry;
    String user_success="Registration successful";
    ImageView imageViewAdhar;
    String encodedImage;
    String encodeImageString,encodeImageString1;
    Bitmap bitmap, bitmap1;
    ImageView openCamera;
    String userPPhoto, userNName, userPPhone, userEEmail, userPPasword, userType;

    //for user pic
    private static final int CAMERA_PERMISSION_CODE=100;
    private static final int STORAGE_PERMISSION_CODE=200;
    private static final int IMAGE_FROM_GALLERY_CODE=300;
    private static final int IMAGE_FROM_CAMERA_CODE=400;

    private String [] cameraPermission;
    private String [] storagePermission;

    //image uri var
    Uri imageUri;

    //for user aadhar
    private static final int CAMERA1_PERMISSION_CODE=500;
    private static final int STORAGE1_PERMISSION_CODE=600;
    private static final int IMAGE1_FROM_GALLERY_CODE=700;
    private static final int IMAGE1_FROM_CAMERA_CODE=800;

    private String [] cameraPermission1;
    private String [] storagePermission1;

    //image uri var
    Uri imageUri1;

    public static final String url="http://14.139.158.99/arkvyaper/api/register.php";
    public static final String url2="http://14.139.158.99/arkvyaper/api/register2.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_seclet_address);

        //for image display which come from signup details page from backened
/*        Bundle ex=getIntent().getExtras();
        byte[] bytearray=ex.getByteArray("cameraimage");
        Bitmap bmp= BitmapFactory.decodeByteArray(bytearray,0,bytearray.length);*/
        /*        encodedImage=android.util.Base64.encodeToString(bytearray, Base64.DEFAULT);*/

        cameraPermission=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        cameraPermission1=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission1=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imageViewAdhar=findViewById(R.id.imageView26);

        imageViewAdhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog1();
            }
        });

        openCamera=findViewById(R.id.imageView7);

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });


        btn_go_home_page=findViewById(R.id.btn_go_to_home_page);


        addressLine=findViewById(R.id.address_location);
        city=findViewById(R.id.city_location);
        pincode=findViewById(R.id.pincode_location);
        contry=findViewById(R.id.state_location);




        String add=getIntent().getStringExtra("addre");
        String ci= getIntent().getStringExtra("city");
        String pin=getIntent().getStringExtra("pincode");
        String con= getIntent().getStringExtra("contry");


        addressLine.setText(add);
        city.setText(ci);
        pincode.setText(pin);
        contry.setText(con);




        btn_go_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bitmap==null) {
                  Toast.makeText(getApplicationContext(),"Please Upload a Pic", Toast.LENGTH_SHORT).show();
                }if (bitmap!=null){
                    insertData();
                }if (bitmap1==null){
                    insertData1();
                }
            }
        });
    }


    private void insertData()
    {

        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        userPPhoto=sp.getString("user_photo","");
        userNName=sp.getString("user_name","");
        userEEmail=sp.getString("user_email","");
        userPPhone=sp.getString("user_phone","");
        userPPasword=sp.getString("user_password","");
        userType=sp.getString("user_type","");



        Double lonngi=getIntent().getDoubleExtra("longi",0.00);
        Double latti=getIntent().getDoubleExtra("lati",0.00);
        String add=getIntent().getStringExtra("addre");
        String ci= getIntent().getStringExtra("city");
        String pin=getIntent().getStringExtra("pincode");
        String con= getIntent().getStringExtra("contry");




        String longiStr = String.valueOf(lonngi);
        String lattiStr = String.valueOf(latti);

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject resObject=new JSONObject(response);
                        String responseStr= resObject.getString("ResponseMsg");
                        Log.d("REGISTER", responseStr);

                        Toast.makeText(getApplicationContext(),user_success, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

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
                    params.put("uname",userNName);
                    params.put("uemail",userEEmail);
                    params.put("uphone",userPPhone);
                    params.put("upassword",userPPasword);
                    params.put("u_img", encodeImageString);
                    params.put("uid_img",encodeImageString1);

                    params.put("buyer_seller",userType);

                    params.put("uaddress1",add);
                    params.put("uaddress2", ci);
                    params.put("upin", pin);
                    params.put("ustate", con);
                    params.put("llong",longiStr);
                    params.put("lat",lattiStr);
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }

    private void insertData1()
    {

        SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
        userPPhoto=sp.getString("user_photo","");
        userNName=sp.getString("user_name","");
        userEEmail=sp.getString("user_email","");
        userPPhone=sp.getString("user_phone","");
        userPPasword=sp.getString("user_password","");
        userType=sp.getString("user_type","");



        Double lonngi=getIntent().getDoubleExtra("longi",0.00);
        Double latti=getIntent().getDoubleExtra("lati",0.00);
        String add=getIntent().getStringExtra("addre");
        String ci= getIntent().getStringExtra("city");
        String pin=getIntent().getStringExtra("pincode");
        String con= getIntent().getStringExtra("contry");




        String longiStr = String.valueOf(lonngi);
        String lattiStr = String.valueOf(latti);

        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resObject=new JSONObject(response);
                    String responseStr= resObject.getString("ResponseMsg");
                    Log.d("REGISTER", responseStr);

                    Toast.makeText(getApplicationContext(),user_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

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
                params.put("uname",userNName);
                params.put("uemail",userEEmail);
                params.put("uphone",userPPhone);
                params.put("upassword",userPPasword);
                params.put("u_img", encodeImageString);

                params.put("buyer_seller",userType);

                params.put("uaddress1",add);
                params.put("uaddress2", ci);
                params.put("upin", pin);
                params.put("ustate", con);
                params.put("llong",longiStr);
                params.put("lat",lattiStr);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

   //for Image of User
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


    private void encodeBitmapImage(Bitmap bitmap)

    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    //for Aadhar Image of User

    private void showImagePickerDialog1() {
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
                    if (!checkCameraPermission1()){
                        //request camera permission
                        requestCameraPermission1();
                    }else {
                        pickFromCamera1();
                    }
                }else if(which==1){
                    //storage selected
                    if (!checkStoragePermission1()){
                        //request camera permission
                        requestStoragePermission1();
                    }else {
                        pickFromGallery1();
                    }
                }

            }
        }).create().show();
    }

    private void pickFromGallery1() {
        //intent for taking image from gallary
        Intent gallaryIntent= new Intent(Intent.ACTION_PICK);
        gallaryIntent.setType("image/*"); //only image

        startActivityForResult(gallaryIntent, IMAGE1_FROM_GALLERY_CODE);

    }

    private void pickFromCamera1() {

        //content value for image info
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION,"IMAGE_DETAIL");


        //save image uri
        Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri1);

        startActivityForResult(cameraIntent, IMAGE1_FROM_CAMERA_CODE);
    }

    //check camera permission
    private boolean checkCameraPermission1(){
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result && result1;
    }

    //request for camera permission
    private void requestCameraPermission1(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA1_PERMISSION_CODE);
    }

    //check storage permission
    private boolean checkStoragePermission1(){
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result1;
    }

    //request for storage permission
    private void requestStoragePermission1(){
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
            case CAMERA1_PERMISSION_CODE:
                if (grantResults.length>0){
                    //if all permission allowed return true, otherwise false
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageaAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageaAccepted){
                        //both permission granted
                        pickFromCamera1();
                    }else {
                        Toast.makeText(getApplicationContext(),"Camera and Storage Permission needed...",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE1_PERMISSION_CODE:
                if (grantResults.length>0){
                    //if all permission allowed return true, otherwise false
                    boolean storageaAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if (storageaAccepted){
                        //permission granted
                        pickFromGallery1();
                    }else {
                        Toast.makeText(getApplicationContext(),"Storage Permission needed...",Toast.LENGTH_SHORT).show();
                    }

                }
                break;

        }
    }



    private void encodeBitmapImage1(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString1=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMAGE_FROM_GALLERY_CODE){
            try {
                InputStream inputStream =getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                openCamera.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else if (requestCode==IMAGE_FROM_CAMERA_CODE){
            bitmap=(Bitmap)data.getExtras().get("data");
            openCamera.setImageBitmap(bitmap);
            encodeBitmapImage(bitmap);
        }

        if (requestCode==IMAGE1_FROM_GALLERY_CODE){
            try {
                InputStream inputStream =getContentResolver().openInputStream(data.getData());
                bitmap1 = BitmapFactory.decodeStream(inputStream);
                imageViewAdhar.setImageBitmap(bitmap1);
                encodeBitmapImage1(bitmap1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else if (requestCode==IMAGE1_FROM_CAMERA_CODE){
            bitmap1=(Bitmap)data.getExtras().get("data");
            imageViewAdhar.setImageBitmap(bitmap1);
            encodeBitmapImage1(bitmap1);
        }



    }

    @Override
    public void onBackPressed() {

    }
}