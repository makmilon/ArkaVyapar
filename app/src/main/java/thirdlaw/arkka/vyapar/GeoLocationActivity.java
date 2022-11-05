package thirdlaw.arkka.vyapar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import thirdlaw.arkka.vyapar.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocationActivity extends AppCompatActivity {

    Button select_address_manBtn;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 100;
    String address, city, pincode, contry;
    Double longi, lati;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_location);

        askpermission();
        select_address_manBtn=findViewById(R.id.select_address_manBtn);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        select_address_manBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentLocation();


                Intent intent = new Intent(GeoLocationActivity.this, ManuallySecletAddressActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CurrentLocation() {
        if (ContextCompat.checkSelfPermission(GeoLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                Geocoder geocoder = new Geocoder(GeoLocationActivity.this, Locale.getDefault());
                                List<Address> addresses = null;

                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    longi=addresses.get(0).getLongitude();
                                    lati=addresses.get(0).getLatitude();
                                    address =addresses.get(0).getAddressLine(0);
                                    city=addresses.get(0).getSubLocality();
                                    pincode=addresses.get(0).getPostalCode();
                                    contry=addresses.get(0).getAdminArea();


                                    Intent intent = new Intent(GeoLocationActivity.this, ManuallySecletAddressActivity.class);
                                    intent.putExtra("longi",longi);
                                    intent.putExtra("lati",lati);
                                    intent.putExtra("addre",address);
                                    intent.putExtra("city",city);
                                    intent.putExtra("pincode",pincode);
                                    intent.putExtra("contry",contry);

                                    startActivity(intent);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    });

        }else {
            askpermission();

        }
    }

    private void askpermission() {
        ActivityCompat.requestPermissions(GeoLocationActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CurrentLocation();
            }else {
                Toast.makeText(this, "Please provide Location permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {

    }
}