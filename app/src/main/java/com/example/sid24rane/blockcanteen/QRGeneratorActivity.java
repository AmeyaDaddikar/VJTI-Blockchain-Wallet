package com.example.sid24rane.blockcanteen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.sid24rane.blockcanteen.data.KeyInSharedPreferences;
import com.google.gson.Gson;
import com.example.sid24rane.blockcanteen.KeyGeneration.KeyGenerationActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.security.KeyPair;
import java.security.PublicKey;

public class QRGeneratorActivity extends AppCompatActivity {

    private final String PREFS_NAME = "KeyFile";

    // qr code generation
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    private ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        qrcode = (ImageView) findViewById(R.id.qrcode);
        try {
            generateQRCode();
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    private void generateQRCode() throws WriterException {

        //String publicKey = new String(android.util.Base64.encode(Key.getEncoded(), Base64.DEFAULT));
        String publicKey = "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEMKkj/B4LqqUWT6FEbZRoSLvGbfC93yD7Zit+GWmaY/UXUiL0LOwIPljBZ/16sFMwxgCO+nlYGFqcTmftaHKmgA==";

        //String publicKey = KeyInSharedPreferences.retrievingPublicKey(QRGeneratorActivity.this);
        Log.d("PUBLIC KEY", publicKey);
        Bitmap bitmap = encodeAsBitmap(publicKey);
        qrcode.setImageBitmap(bitmap);

    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
