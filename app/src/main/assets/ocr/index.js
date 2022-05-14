module.exports = function (plugin) {

    function Ocr(){
        this.api = plugin.createApi()
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

    return Ocr;
}