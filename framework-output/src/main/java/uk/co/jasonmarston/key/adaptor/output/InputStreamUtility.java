package uk.co.jasonmarston.key.adaptor.output;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class InputStreamUtility {
    private static final InputStreamUtility INSTANCE = new InputStreamUtility();

    public static InputStreamUtility getInstance() {
        return INSTANCE;
    }

    private InputStreamUtility() {
    }

    public InputStream getInputStream(
            final String certificateLocation
    ) {
        if(ProfileUtility.getInstance().isProduction()) {
            try {
                return new FileInputStream(certificateLocation);
            } catch (final FileNotFoundException e) {
                return null;
            }
        }
        else {
            return Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream(certificateLocation);
        }
    }
}
