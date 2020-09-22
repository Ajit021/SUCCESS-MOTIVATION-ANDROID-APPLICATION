package com.ajit.android.posting;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.getDrawable;


/**
 * Created by this pc on 3/19/2020.
 */

public class ViewHolder extends RecyclerView.ViewHolder  {

    View mView;
    View msave;
    View mshare;
    TextView mtitleview;
    ImageView mImageview;
    private static final int REQUEST = 112;
   private  Context context;
//    public static final int  WRITE_EXTRENAL_STORAGE_CODE=1;
//    Bitmap bitmap;
    public ViewHolder(View itemView){
        super(itemView);
        mView = itemView;
    }


    public void setDetails(final Context ctx, final String title , final String image){

         mtitleview=mView.findViewById(R.id.rTitleTv);
         mImageview=mView.findViewById(R.id.rImageView);
         msave= mView.findViewById(R.id.saveBtn);
         mshare=mView.findViewById(R.id.shareBtn);


       msave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
//               Drawable highlight = getDrawable(ctx,R.drawable.highlight);
//               msave.setBackground(highlight);
               if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//
                   if (ContextCompat.checkSelfPermission(ctx,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                           PackageManager.PERMISSION_GRANTED){
                       String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                       requestPermissions((Activity)ctx,permission,REQUEST);

                   }
                   else{

                       Picasso.get()
                               .load(image)
                               .into(getTarget("firebase"));
                       Toast.makeText(ctx,"IMAGE SAVED",Toast.LENGTH_SHORT).show();
                   }
               }
               else{

               Picasso.get()
                       .load(image)
                       .into(getTarget("firebase"));

               }
                   }



       });


       mshare.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               shareImage(ctx);
           }
       });

        final int[] flag = {0};


            mtitleview.setText(title);

        //mtitleview.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_COMPACT));

        Picasso.get().load(image).into(mImageview);





    }

    private void shareImage(Context ctx) {
        try{
        String s =mtitleview.getText().toString();
        File file =new File(ctx.getExternalCacheDir(),"sample.png");
        FileOutputStream fout= new FileOutputStream(file);
        Bitmap bitmap=((BitmapDrawable)mImageview.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
        fout.flush();
        fout.close();
        file.setReadable(true,false);

        //intent to share the image
            Intent intent= new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT,s);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            ctx.startActivity(Intent.createChooser(intent,"Share via"));





        }

        catch(Exception e){
            e.printStackTrace();

        }


    }


    //target to save
    private static Target getTarget(final String url){
        final Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(System.currentTimeMillis());
                       // File file = new File(Environment.getExternalStorageDirectory().getPath() + "/firebasew/");
                        File path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File dir= new File(path+"/Firebase/");
                        dir.mkdir();
                        String imagename= timestamp+ ".PNG";
                        File file= new File(dir,imagename);
                        OutputStream out;

                        try {
                            //file.createNewFile();
                            out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.flush();
                            out.close();
                            Log.v("save","save kar diya");


                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());

                        }
                    }
                }).start();


            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }



            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
    }




