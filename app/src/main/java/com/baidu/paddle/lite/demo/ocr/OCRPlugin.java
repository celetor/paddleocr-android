package com.baidu.paddle.lite.demo.ocr;


import android.content.Context;
import android.util.Log;

import org.autojs.plugin.sdk.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OCRPlugin extends Plugin {

    public OCRPlugin(Context context, Context selfContext, Object runtime, Object topLevelScope) {
        super(context, selfContext, runtime, topLevelScope);
    }

    @Override
    public String getAssetsScriptDir() {
        return "ocr";
    }

    public OCRApi createApi() throws IOException {
        Log.d("OCR", getPathForDefaultData());
        return new OCRApi(this.getContext());
    }


    public String getPathForDefaultData() throws IOException {
        final Context context = this.getContext();
        final Context selfContext = this.getSelfContext();
        final File obbDir = context.getCacheDir();
        final File file = new File(obbDir, "ocr");
        final File file1 = new File(file, "ppocr_keys_v1.txt");
        final File file2 = new File(file, "det.nb");
        final File file3 = new File(file, "cls.nb");
        final File file4 = new File(file, "rec.nb");
        if (!file1.exists()) {
            file.mkdirs();
            copy(selfContext.getAssets().open("ppocr_keys_v1.txt"), new FileOutputStream(file1));
            copy(selfContext.getAssets().open("det.nb"), new FileOutputStream(file2));
            copy(selfContext.getAssets().open("cls.nb"), new FileOutputStream(file3));
            copy(selfContext.getAssets().open("rec.nb"), new FileOutputStream(file4));
        }
        return obbDir.getPath();
    }

    private static void copy(final InputStream inputStream, final FileOutputStream fileOutputStream) throws IOException {
        final byte[] array = new byte[4096];
        while (true) {
            final int read = inputStream.read(array);
            if (read <= 0) {
                break;
            }
            fileOutputStream.write(array, 0, read);
        }
        inputStream.close();
        fileOutputStream.close();
    }

}