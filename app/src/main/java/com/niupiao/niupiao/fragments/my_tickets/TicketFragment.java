package com.niupiao.niupiao.fragments.my_tickets;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.niupiao.niupiao.R;
import com.niupiao.niupiao.activities.TicketActivity;
import com.niupiao.niupiao.models.Event;
import com.niupiao.niupiao.models.Ticket;
import com.niupiao.niupiao.models.User;

/**
 * Created by Inanity on 2/27/15.
 */
public class TicketFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ticket, container, false);

        ImageView qrCodeImageView = (ImageView) root.findViewById(R.id.iv_qrcode);
        TextView ticketStatusTextView = (TextView) root.findViewById(R.id.tv_ticket_type);
        TextView buyerNameTextView = (TextView) root.findViewById(R.id.tv_ticket_name);
        TextView maxTicketsAllowedTextView = (TextView) root.findViewById(R.id.tv_ticket_allow);

        Ticket ticket = getActivity().getIntent().getParcelableExtra(TicketActivity.INTENT_KEY_FOR_TICKET);
        Event event = ticket.getEvent();
        User user = getActivity().getIntent().getParcelableExtra(TicketActivity.INTENT_KEY_FOR_USER);

        ticketStatusTextView.setText(ticket.getTicketStatus().getName());
        buyerNameTextView.setText(user.getName());

        maxTicketsAllowedTextView.setText(
                String.format("%s %d %s",
                        getResources().getString(R.string.allows),
                        ticket.getTicketStatus().getMaxPurchasable(),
                        getResources().getString(R.string.for_entry)
                ));


        String embeddedInfo = String.format("%s %s %s %s %s %s %s %s %s %s %s",
                "event_name:",
                event.getName(),
                "\n",

                "buyer_name:",
                user.getName(),
                "\n",

                "admit_type:",
                ticket.getStatus(),
                "\n",

                "quantity:",
                ticket.getTicketStatus().getMaxPurchasable()
        );
        try {
            generateQRCode(embeddedInfo, qrCodeImageView);
        } catch (WriterException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Error with QR Code Generation",
                    Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    public static TicketFragment newInstance() {
        return new TicketFragment();
    }

    // Code taken from:
    // http://stackoverflow.com/questions/22371626/android-generate-qr-code-and-barcode-using-zxing
    private void generateQRCode(String data, ImageView image) throws WriterException {
        int width = 500;
        int height = 500;
        Writer writer = new QRCodeWriter();

        BitMatrix bm = writer.encode(data, BarcodeFormat.QR_CODE, width, height);
        Bitmap ImageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < width; i++) {//width
            for (int j = 0; j < height; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
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
