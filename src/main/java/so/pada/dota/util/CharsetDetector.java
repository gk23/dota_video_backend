package so.pada.dota.util;

import java.io.ByteArrayInputStream;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

public class CharsetDetector {
    private boolean found = false;
    // 2 => Chinese
    private nsDetector detector = new nsDetector(2);
    private String charset;

    public static String detector(String test) {
        CharsetDetector det = new CharsetDetector();
        try {
            return det.inner_detector(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String inner_detector(String test) throws Exception {
        detector.Init(new nsICharsetDetectionObserver() {
            public void Notify(String the_charset) {
                found = true;
                charset = the_charset;
                System.out.println("CHARSET = " + the_charset);
            }
        });
        ByteArrayInputStream reader = new ByteArrayInputStream(test.getBytes("iso-8859-1"));
        byte[] buf = new byte[1024];
        int len;
        boolean done = false;
        boolean isAscii = true;
        while ((len = reader.read(buf, 0, 1024)) != -1) {
            // Check if the stream is only ascii.
            if (isAscii)
                isAscii = detector.isAscii(buf, len);
            // DoIt if non-ascii and not done yet.
            if (!isAscii && !done)
                done = detector.DoIt(buf, len, false);
        }
        detector.DataEnd();
        if (isAscii) {
            System.out.println("CHARSET = ASCII");
            found = true;
            return "ASCII";
        }
        if (found)
            return charset;
        if (!found) {
            String prob[] = detector.getProbableCharsets();
            for (int i = 0; i < prob.length; i++) {
                System.out.println("Probable Charset = " + prob[i]);
            }
        }
        return null;
    }

}
