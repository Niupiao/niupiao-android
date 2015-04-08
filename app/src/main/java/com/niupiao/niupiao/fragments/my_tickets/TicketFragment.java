package com.niupiao.niupiao.fragments.my_tickets;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.niupiao.niupiao.utils.StringUtils;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Created by Inanity on 2/27/15.
 */
public class TicketFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_ticket, container, false);

        Typeface light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        ImageView qrCodeImageView = (ImageView) root.findViewById(R.id.iv_qrcode);
        TextView ticketStatusTextView = (TextView) root.findViewById(R.id.tv_ticket_type);
        TextView buyerNameTextView = (TextView) root.findViewById(R.id.tv_ticket_name);
        TextView maxTicketsAllowedTextView = (TextView) root.findViewById(R.id.tv_ticket_allow);

        ticketStatusTextView.setTypeface(light);
        buyerNameTextView.setTypeface(light);
        maxTicketsAllowedTextView.setTypeface(light);

        Ticket ticket = getActivity().getIntent().getParcelableExtra(TicketActivity.INTENT_KEY_FOR_TICKET);
        Event event = ticket.getEvent();
        User user = getActivity().getIntent().getParcelableExtra(TicketActivity.INTENT_KEY_FOR_USER);
        String name = user.getFirstName() + " " + user.getLastName();

        ticketStatusTextView.setText(ticket.getTicketStatus().getName());
        buyerNameTextView.setText(name);

        maxTicketsAllowedTextView.setText(
                String.format("%s %d %s",
                        getResources().getString(R.string.allows),
                        ticket.getTicketStatus().getMaxPurchasable(),
                        getResources().getString(R.string.for_entry)
                ));

        //Get Local Time and Date
        LocalTime localTime = new LocalTime();
        LocalDate localDate = new LocalDate();

        //Set up format for embeddedInfo
        String format = "%s ";
        int numArgs = 17;
        format = StringUtils.repeatHelper(format, numArgs);
        format = format.substring(0, format.length()-1);

        String embeddedInfo = String.format(format,
                "Current Date:",
                localDate.toString("yyyy/MM/dd"), //International Format
                "\n",

                "Current Local Time:",
                localTime.getHourOfDay() + ":" + localTime.getMinuteOfHour(),
                "\n",

                "Event Name:",
                event.getName(),
                "\n",

                "Buyer Name:",
                name,
                "\n",

                "Ticket Type:",
                ticket.getStatus(),
                "\n",

                "Quantity:",
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
