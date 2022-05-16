package com.baidu.paddle.lite.demo.ocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
//import androidx.exifinterface.media.ExifInterface;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

public class OCRApi {
    protected final String modelPath = "models/ocr_v2_for_cpu";
    protected final String labelPath = "labels/ppocr_keys_v1.txt";

    public int cpuThreadNum = 1;
    public final String cpuPowerMode = "LITE_POWER_HIGH";
    protected String inputColorFormat = "BGR";
    protected long[] inputShape = new long[]{1, 3, 960};
    protected float[] inputMean = new float[]{0.485f, 0.456f, 0.406f};
    protected float[] inputStd = new float[]{0.229f, 0.224f, 0.225f};
    protected float scoreThreshold = 0.1f;

    protected Predictor mPredictor = new Predictor();

    public OCRApi(final Context mContext) {
        if (this.mPredictor.isLoaded()) {
            this.mPredictor.releaseModel();
        }
        boolean flag = this.mPredictor.init(mContext, modelPath, labelPath, cpuThreadNum,
                cpuPowerMode,
                inputColorFormat,
                inputShape, inputMean,
                inputStd, scoreThreshold);

        if (!flag) {
            Log.d("*************", "初始化失败");
        } else {
            Log.d("*************", "初始化成功");
        }
    }

    public void end() {
        this.mPredictor.releaseModel();
    }

    public String ocrFile(final String imagePath) {
        File file = new File(imagePath);
        Log.d("*************", imagePath);
        try {
            ExifInterface exif = null;
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap image = BitmapFactory.decodeFile(imagePath);
            image = Utils.rotateBitmap(image, orientation);

            this.mPredictor.setInputImage(image);
            boolean flag = this.mPredictor.runModel();
            if (!flag) {
                Log.d("*************", "无法运行！");
                return "";
            }
            return JSONObject.toJSONString(this.mPredictor.outputResultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String ocrBitmap(Bitmap bitmap) {
        this.mPredictor.setInputImage(bitmap);
        boolean flag = this.mPredictor.runModel();
        if (!flag) {
            Log.d("*************", "无法运行！");
            return "";
        }
        return JSONObject.toJSONString(this.mPredictor.outputResultList);
    }

}
