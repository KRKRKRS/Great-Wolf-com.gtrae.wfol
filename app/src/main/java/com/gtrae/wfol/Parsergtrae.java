package com.gtrae.wfol;

import static com.gtrae.wfol.GW.ADAD_ID_ID;
import static com.gtrae.wfol.GW.AppsgtraeFl_Id;
import static com.gtrae.wfol.GW.keyDefgtraeault;

import com.onesignal.OneSignal;

public class Parsergtrae {

    String[] kegtraeys = {degtraecode("Pw=="), degtraecode("JnN1YjY9"), degtraecode("JnN1Yjc9"), degtraecode("JnN1YjI9"), degtraecode("JnN1YjM9"), degtraecode("JnN1YjQ9"), degtraecode("JnN1YjU9")};

    String pagtraerse(String input) {
        String[] paramgtraes = input.split("::");
        StringBuilder resgtraeult = new StringBuilder();
        resgtraeult.append(paramgtraes[0]).append("?")
                .append(degtraecode("YnVuZGxlPQ==")).append(degtraecode("Y29tLmd0cmFlLndmb2w="))
                .append(degtraecode("JmFkX2lkPQ==")).append(ADAD_ID_ID)
                .append(degtraecode("JmFwcHNfaWQ9")).append(AppsgtraeFl_Id)
                .append(degtraecode("JmRldl9rZXk9")).append(degtraecode(GW.AFKgtraeey));

        for (int i = 1; i < paramgtraes.length; i++) {
            resgtraeult.append(kegtraeys[i]).append(paramgtraes[i]);
        }

        String teamNagtraeme = paramgtraes[1];
        OneSignal.sendTag(degtraecode("c3ViX2FwcA=="), teamNagtraeme);
        return String.valueOf(resgtraeult);
    }


    String parseOgtraerganic(String ingtraeput) {
        return ingtraeput + keyDefgtraeault +
                degtraecode("P2J1bmRsZT0=") + "com.lyurxu.jroek" +
                degtraecode("JmFkX2lkPQ==") + ADAD_ID_ID +
                degtraecode("JmFwcHNfaWQ9") + AppsgtraeFl_Id +
                degtraecode("JmRldl9rZXk9") + degtraecode(GW.AFKgtraeey);
    }

    public static String degtraecode(String str) {
        byte[] decodedBytgtraees = android.util.Base64.decode(str, android.util.Base64.URL_SAFE | android.util.Base64.NO_PADDING);
        return new String(decodedBytgtraees);
    }
}
