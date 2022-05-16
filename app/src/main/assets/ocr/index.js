module.exports = function (plugin) {
    let runtime = plugin.runtime;
    let scope = plugin.topLevelScope;

    function Ocr(){
        this.api = plugin.createApi();
    }

    Ocr.prototype.ocrFile = function(path){
        return this.api.ocrFile(path);
    }

    Ocr.prototype.ocrBitmap = function(bitmap){
            return this.api.ocrBitmap(bitmap);
    }

    Ocr.prototype.end = function(){
        return this.api.end();
    }

    Ocr.prototype.getStringFromJava = function(path){
        return plugin.getStringFromJava();
    }

    return Ocr;
}