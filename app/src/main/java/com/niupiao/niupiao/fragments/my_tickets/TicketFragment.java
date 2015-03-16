package com.niupiao.niupiao.fragments.my_tickets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.niupiao.niupiao.R;

/**
 * Created by Inanity on 2/27/15.
 */
public class TicketFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ticket, container, false);
        ImageView qrcode = (ImageView) root.findViewById(R.id.iv_qrcode);

        Intent intent = getActivity().getIntent();
        String admitType = intent.getStringExtra("EXTRA_TICKET_ADMIT_TYPE");
        String quantity = intent.getStringExtra("EXTRA_TICKET_QUANTITY");
        String name = intent.getStringExtra("EXTRA_TICKET_BUYER_NAME");

        String embeddedInfo = admitType + "\n" + quantity + "\n" + name;

        try {
            generateQRCode_general(embeddedInfo, qrcode);
        } catch(WriterException e){
            Toast.makeText(getActivity().getApplicationContext(), "Error with QR Code Generation",
                    Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    public static TicketFragment newInstance() {
        return new TicketFragment();
    }

    //Code taken from:
    // http://stackoverflow.com/questions/22371626/android-generate-qr-code-and-barcode-using-zxing
    private void generateQRCode_general(String data, ImageView image) throws WriterException{
        int width = 500;
        int height = 500;
        com.google.zxing.Writer writer = new QRCodeWriter();

        BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE, width, height);
        Bitmap ImageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < width; i++) {//width
            for (int j = 0; j < height; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
            }
        }

        if (ImageBitmap != null) {
            image.setImageBitmap(ImageBitmap);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Error with QR Code Generation",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
