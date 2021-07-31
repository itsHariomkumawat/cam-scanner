package com.templatemela.camscanner.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.scanlibrary.ScanActivity;
import com.templatemela.camscanner.R;
import com.templatemela.camscanner.db.DBHelper;
import com.templatemela.camscanner.main_utils.AdjustUtil;
import com.templatemela.camscanner.main_utils.BitmapUtils;
import com.templatemela.camscanner.main_utils.Constant;
import com.templatemela.camscanner.models.DBModel;
import com.templatemela.camscanner.utils.AdsUtils;
import com.templatemela.camscanner.utils.VerticalSeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.pqpo.smartcropperlib.view.CropImageView;

public class CropDocumentActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "CropDocumentActivity";
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constant.IdentifyActivity.equals("DocumentEditorActivity_Crop")) {
                Intent intent2 = new Intent(CropDocumentActivity.this, DocumentEditorActivity.class);
                intent2.putExtra("TAG", "SavedDocumentActivity");
                intent2.putExtra("scan_doc_group_name", selected_group_name);
                intent2.putExtra("current_doc_name", current_docs_name);
                startActivity(intent2);
                Constant.IdentifyActivity = "";
                finish();
            } else if (Constant.IdentifyActivity.equals("CurrentFilterActivity")) {
                startActivity(new Intent(CropDocumentActivity.this, CurrentFilterActivity.class));
                Constant.IdentifyActivity = "";
                finish();
            } else if (Constant.IdentifyActivity.equals("ScannerActivity_Retake")) {
                startActivity(new Intent(CropDocumentActivity.this, ScannerActivity.class));
                Constant.IdentifyActivity = "";
                finish();
            }
        }
    };

    public String current_docs_name;

    public DBHelper dbHelper;
    protected ImageView iv_back;
    protected ImageView iv_Rotate_Doc;
    protected ImageView iv_edit;
    protected TextView iv_done;
    protected ImageView iv_full_crop;
    private CropImageView iv_preview_crop;
    protected TextView iv_retake;
    protected LinearLayout ly_current_filter;
    protected LinearLayout ly_rotate_doc;

    public String selected_group_name;
    private AdView adView;


    private ImageView iv_ocv_black;
    private ImageView iv_original;
    private ImageView iv_color;
    private ImageView iv_sharp_black;
    private Bitmap tempBitmap;
    public Bitmap original;
    private ProgressDialog progressDialog;

    private ImageView iv_add_new_scan;
    private SeekBar seekBarBrightness;

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(getPackageName() + ".DocumentEditorActivity_Crop"));
        registerReceiver(broadcastReceiver, new IntentFilter(getPackageName() + ".CurrentFilterActivity"));
        registerReceiver(broadcastReceiver, new IntentFilter(getPackageName() + ".ScannerActivity_Retake"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_crop_document);
        dbHelper = new DBHelper(this);
        init();


       /* Intent intent = getIntent();
        String uriString = intent.getStringExtra("Data");
        Uri uri = Uri.parse(uriString);
        iv_preview_crop.setImageURI(uri);*/



    }

    private void init() {
        adView = findViewById(R.id.adView);
        iv_add_new_scan = (ImageView) findViewById(R.id.iv_add_new_scan);
        seekBarBrightness = (SeekBar) findViewById(R.id.seekBarBrightness);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_edit = (ImageView) findViewById(R.id.iv_edit);
        iv_done = (TextView) findViewById(R.id.iv_done);
        iv_preview_crop = (CropImageView) findViewById(R.id.iv_preview_crop);
        iv_full_crop = (ImageView) findViewById(R.id.iv_full_crop);
        ly_rotate_doc = (LinearLayout) findViewById(R.id.ly_rotate_doc);
        ly_current_filter = (LinearLayout) findViewById(R.id.ly_current_filter);
        iv_retake = (TextView) findViewById(R.id.iv_retake);
        iv_Rotate_Doc = (ImageView) findViewById(R.id.iv_Rotate_Doc);
        iv_original = (ImageView) findViewById(R.id.iv_original);
        iv_color = (ImageView) findViewById(R.id.iv_color);
        iv_sharp_black = (ImageView) findViewById(R.id.iv_sharp_black);
        iv_ocv_black = (ImageView) findViewById(R.id.iv_ocv_black);



        if (Constant.original != null) {
            iv_preview_crop.setImageToCrop(Constant.original);
            original = Constant.original;
            changeBrightness(20);
        }
        AdsUtils.loadGoogleInterstitialAd(this, CropDocumentActivity.this);
        AdsUtils.showGoogleBannerAd(this, adView);

        seekBarBrightness.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_new_scan:
                return;

                /*Original*/
            case R.id.iv_original:

                iv_original.setImageResource(R.drawable.originalgreen);
                // iv_original.setTextColor(getResources().getColor(R.color.white));
                iv_color.setImageResource(R.drawable.colorwhite);
                // iv_color.setTextColor(getResources().getColor(R.color.black));

                iv_sharp_black.setImageResource(R.drawable.sharpwhite);
                // iv_sharp_black.setTextColor(getResources().getColor(R.color.black));

                iv_ocv_black.setImageResource(R.drawable.originalwhite);
                // iv_ocv_black.setTextColor(getResources().getColor(R.color.black));

                try {
                    showProgressDialog();
                    tempBitmap = original;
                    iv_preview_crop.setImageBitmap(original);

                    dismissProgressDialog();
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    dismissProgressDialog();
                }


                return;

            /*OCVBlack*/
            case R.id.iv_ocv_black:

                iv_ocv_black.setImageResource(R.drawable.originalgreen);

                iv_original.setImageResource(R.drawable.originalwhite);
                // iv_original.setTextColor(getResources().getColor(R.color.black));

                iv_color.setImageResource(R.drawable.colorwhite);
//                iv_color.setTextColor(getResources().getColor(R.color.black));

                iv_sharp_black.setImageResource(R.drawable.sharpwhite);
                //  iv_sharp_black.setTextColor(getResources().getColor(R.color.black));

                // iv_ocv_black.setTextColor(getResources().getColor(R.color.white));

                showProgressDialog();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tempBitmap = ScanActivity.getBWBitmap(original);
                        } catch (OutOfMemoryError e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tempBitmap = original;
                                    iv_preview_crop.setImageBitmap(original);
                                    e.printStackTrace();
                                    dismissProgressDialog();
                                }
                            });
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_preview_crop.setImageBitmap(tempBitmap);
                                dismissProgressDialog();
                            }
                        });
                    }
                });


                return;

            /*Color*/
            case R.id.iv_color:

                iv_color.setImageResource(R.drawable.colorgreen);

                iv_original.setImageResource(R.drawable.originalwhite);
                //iv_original.setTextColor(getResources().getColor(R.color.black));

                iv_sharp_black.setImageResource(R.drawable.sharpwhite);
                // iv_sharp_black.setTextColor(getResources().getColor(R.color.black));

                iv_ocv_black.setImageResource(R.drawable.originalwhite);
                // iv_ocv_black.setTextColor(getResources().getColor(R.color.black));

                showProgressDialog();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tempBitmap = ScanActivity.getMagicColorBitmap(original);
                        } catch (OutOfMemoryError e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tempBitmap = original;
                                    iv_preview_crop.setImageBitmap(original);
                                    e.printStackTrace();
                                    dismissProgressDialog();
                                }
                            });
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_preview_crop.setImageBitmap(tempBitmap);
                                dismissProgressDialog();
                            }
                        });
                    }
                });



                return;

            /*SharpBlack*/

            case R.id.iv_sharp_black:
                showProgressDialog();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tempBitmap = ScanActivity.getGrayBitmap(original);
                        } catch (OutOfMemoryError e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tempBitmap = original;
                                    iv_preview_crop.setImageBitmap(original);
                                    e.printStackTrace();
                                    dismissProgressDialog();
                                }
                            });
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_preview_crop.setImageBitmap(tempBitmap);
                                dismissProgressDialog();
                            }
                        });
                    }
                });

                iv_sharp_black.setImageResource(R.drawable.sharpgreen);

                iv_original.setImageResource(R.drawable.originalwhite);
               // iv_original.setTextColor(getResources().getColor(R.color.black));

                iv_color.setImageResource(R.drawable.colorwhite);
               // iv_color.setTextColor(getResources().getColor(R.color.black));


               // iv_sharp_black.setTextColor(getResources().getColor(R.color.white));

                iv_ocv_black.setBackgroundResource(R.drawable.originalwhite);
                //iv_ocv_black.setTextColor(getResources().getColor(R.color.black));
                return;

            case R.id.iv_done:
                if (iv_preview_crop.canRightCrop()) {
                    Constant.original = iv_preview_crop.crop();
                    new addDocGroup().execute(new Bitmap[]{Constant.original});
                    return;
                }
                return;
            case R.id.iv_back:
                onBackPressed();
                return;
            case R.id.iv_Rotate_Doc:
                Bitmap bitmap = Constant.original;
                Matrix matrix = new Matrix();
                matrix.postRotate(90.0f);
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                Constant.original.recycle();
                System.gc();
                Constant.original = createBitmap;
                original = createBitmap;
                iv_preview_crop.setImageToCrop(Constant.original);
                iv_preview_crop.setFullImgCrop();
                Log.e(TAG, "onClick: Rotate");
                /*if (iv_preview_crop.canRightCrop()) {
                    Constant.original = iv_preview_crop.crop();
                    Constant.IdentifyActivity = "CurrentFilterActivity";
                    AdsUtils.showGoogleInterstitialAd(CropDocumentActivity.this, true);
                    return;
                }*/
                return;
            case R.id.iv_edit:
                if (iv_preview_crop.canRightCrop()) {
                    Constant.original = iv_preview_crop.crop();
                    new addGroup().execute(new Bitmap[]{Constant.original});
                    return;
                }
                return;
            case R.id.iv_full_crop:
                iv_preview_crop.setFullImgCrop();
                return;
            case R.id.iv_retake:
                Constant.IdentifyActivity = "ScannerActivity_Retake";
                AdsUtils.showGoogleInterstitialAd(CropDocumentActivity.this, true);
                return;
            case R.id.ly_current_filter:
                if (iv_preview_crop.canRightCrop()) {
                    Constant.original = iv_preview_crop.crop();
                    Constant.IdentifyActivity = "CurrentFilterActivity";
                    AdsUtils.showGoogleInterstitialAd(CropDocumentActivity.this, true);
                    return;
                }
                return;
          /*  case R.id.ly_rotate_doc:
                Bitmap bitmap = Constant.original;
                Matrix matrix = new Matrix();
                matrix.postRotate(90.0f);
                Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                Constant.original.recycle();
                System.gc();
                Constant.original = createBitmap;
                iv_preview_crop.setImageToCrop(Constant.original);
                iv_preview_crop.setFullImgCrop();
                return;*/
            default:
                return;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBarBrightness:
                changeBrightness(progress);
                break;
        }
    }

    private void changeBrightness(float brightness) {
        iv_preview_crop.setImageBitmap(AdjustUtil.changeBitmapContrastBrightness(original, 1.0f, brightness));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class addGroup extends AsyncTask<Bitmap, Void, Bitmap> {
        String current_doc_name;
        String group_date;
        String group_name;
        ProgressDialog progressDialog;

        private addGroup() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CropDocumentActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        public Bitmap doInBackground(Bitmap... bitmapArr) {
            Bitmap bitmap = Constant.original;
            if (bitmap == null) {
                return null;
            }
            byte[] bytes = BitmapUtils.getBytes(bitmap);
            File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(externalFilesDir, System.currentTimeMillis() + ".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Constant.inputType.equals("Group")) {
                group_name = "CamScanner" + Constant.getDateTime("_ddMMHHmmss");
                group_date = Constant.getDateTime("yyyy-MM-dd  hh:mm a");
                current_doc_name = "Doc_" + System.currentTimeMillis();
                dbHelper.createDocTable(group_name);
                dbHelper.addGroup(new DBModel(group_name, group_date, file.getPath(), Constant.current_tag));
                dbHelper.addGroupDoc(group_name, file.getPath(), current_doc_name, "Insert text here...");
                return null;
            }
            group_name = GroupDocumentActivity.current_group;
            current_doc_name = "Doc_" + System.currentTimeMillis();
            dbHelper.addGroupDoc(group_name, file.getPath(), current_doc_name, "Insert text here...");
            return null;
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            selected_group_name = group_name;
            current_docs_name = current_doc_name;
            Constant.IdentifyActivity = "DocumentEditorActivity_Crop";
            AdsUtils.showGoogleInterstitialAd(CropDocumentActivity.this, true);
        }
    }

    private class addDocGroup extends AsyncTask<Bitmap, Void, Bitmap> {
        String current_doc_name;
        String group_date;
        String group_name;
        ProgressDialog progressDialog;

        private addDocGroup() {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CropDocumentActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
        }

        @Override
        public Bitmap doInBackground(Bitmap... bitmapArr) {
            Bitmap bitmap = Constant.original;
            if (bitmap == null) {
                return null;
            }
            byte[] bytes = BitmapUtils.getBytes(bitmap);
            File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(externalFilesDir, System.currentTimeMillis() + ".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Constant.inputType.equals("Group")) {
                group_name = "CamScanner" + Constant.getDateTime("_ddMMHHmmss");
                group_date = Constant.getDateTime("yyyy-MM-dd  hh:mm a");
                current_doc_name = "Doc_" + System.currentTimeMillis();
                dbHelper.createDocTable(group_name);
                dbHelper.addGroup(new DBModel(group_name, group_date, file.getPath(), Constant.current_tag));
                dbHelper.addGroupDoc(group_name, file.getPath(), current_doc_name, "Insert text here...");
                return null;
            }
            group_name = GroupDocumentActivity.current_group;
            current_doc_name = "Doc_" + System.currentTimeMillis();
            dbHelper.addGroupDoc(group_name, file.getPath(), current_doc_name, "Insert text here...");
            return null;
        }

        @Override
        public void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            selected_group_name = group_name;
            current_docs_name = current_doc_name;

            Intent intent2 = new Intent(CropDocumentActivity.this, GroupDocumentActivity.class);
            intent2.putExtra("current_group", selected_group_name);
            startActivity(intent2);
            Constant.IdentifyActivity = "";
            finish();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Applying Filter...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }


    public void dismissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
