package com.baidu.paddle.lite.demo.ocr;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;

public class OCRApi {

    private final int useOpencl = 0;
    private final int cpuThreadNum = 1;
    private final String cpuPowerMode = "LITE_POWER_HIGH";
    private final int detLongSize = 960;
    private final float scoreThreshold = 0.1f;

    // 检测
    protected int run_det = 1;
    // 分类
    protected int run_cls = 1;
    // 识别
    protected int run_rec = 1;

    private final String assetModelDirPath = "ocr";
    //    private final String assetlabelFilePath = "ocr/ppocr_keys_v1.txt";
    private final String assetlabelFilePath = "ocr";

    private final Context mContext;
    private final Predictor mPredictor;

    public OCRApi(final Context mContext) {
        this.mPredictor = new Predictor();
        this.mContext = mContext;
        boolean flag = this.mPredictor.init(mContext, assetModelDirPath, assetlabelFilePath, useOpencl, cpuThreadNum,
                cpuPowerMode,
                detLongSize, scoreThreshold);
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
        Log.d("****************", imagePath);
        try {
            ExifInterface exif = null;
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap image = BitmapFactory.decodeFile(imagePath);
            image = Utils.rotateBitmap(image, orientation);

            this.mPredictor.setInputImage(image);
            boolean flag = this.mPredictor.runModel(run_det, run_cls, run_rec);
            if (!flag) {
                Log.d("****************", "无法运行！");
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
        boolean flag = this.mPredictor.runModel(run_det, run_cls, run_rec);
        if (!flag) {
            Log.d("****************", "无法运行！");
            return "";
        }
        return JSONObject.toJSONString(this.mPredictor.outputResultList);
    }

}