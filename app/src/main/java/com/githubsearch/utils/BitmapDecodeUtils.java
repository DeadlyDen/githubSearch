package com.githubsearch.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;

/**
 * Created by den on 28.08.17.
 */

public class BitmapDecodeUtils {

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);
        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;
        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bm = BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
        Bitmap bitmap = bm;
        try {
            ExifInterface exif = new ExifInterface(getRealPathFromURI(c, uri));

            int orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);

                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);

                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);

                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String getRealPathFromURI(Context context,Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static File createFile(Context context, Bitmap bitmapRes) {
        if (bitmapRes != null) {
            File f = new File(context.getCacheDir(), "image.jpg");
            try {
                f.createNewFile();

                Bitmap bitmap = bitmapRes;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = null;
                fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        } else
            return null;
    }

    public static Observable<File> obsCreateFile(Context context, Bitmap bitmapRes) {
        if (bitmapRes != null) {

         return  Observable.fromCallable(() -> {
             File f = new File(context.getCacheDir(), "image.jpg");
             try {
                 f.createNewFile();

                 Bitmap bitmap = bitmapRes;
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                 byte[] bitmapdata = bos.toByteArray();

                 FileOutputStream fos = null;
                 fos = new FileOutputStream(f);
                 fos.write(bitmapdata);
                 fos.flush();
                 fos.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return f;
            });

        } else {
            return null;
        }
    }

}
