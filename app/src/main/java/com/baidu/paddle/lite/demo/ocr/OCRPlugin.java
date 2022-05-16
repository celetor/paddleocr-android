package com.baidu.paddle.lite.demo.ocr;

import android.content.Context;

import java.io.IOException;

import org.autojs.plugin.sdk.Plugin;


public class OCRPlugin extends Plugin {

    public OCRPlugin(Context context, Context selfContext, Object runtime, Object topLevelScope) {
        super(context, selfContext, runtime, topLevelScope);
    }

    // 返回插件的JavaScript胶水层代码的assets目录路径
    @Override
    public String getAssetsScriptDir() {
        return "ocr";
    }

    // 插件public API，可被JavaScript代码调用
    public String getStringFromJava() {
        return "Hello, PaddleOcr Android !";
    }

    public OCRApi createApi() throws IOException {
        return new OCRApi(this.getContext());
    }

}
