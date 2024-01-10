package uk.co.jasonmarston.key.adaptor.output;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

class PEMUtility {
    private static final PEMUtility INSTANCE = new PEMUtility();
    private static final int LINE_WIDTH = 64;
    private static final String NEW_LINE = "\n";
    private static final String BEGIN_PUBLIC_KEY =
            "-----BEGIN PUBLIC KEY-----";
    private static final String END_PUBLIC_KEY =
            "-----END PUBLIC KEY-----";

    public static PEMUtility getInstance() {
        return INSTANCE;
    }

    private PEMUtility() {
    }

    public String convertToPEM(final RSAPublicKey publicKey) {
        final byte[] publicKeyBytes = publicKey.getEncoded();
        final String publicKeyBase64 = Base64
                .getEncoder()
                .encodeToString(publicKeyBytes);
        final StringBuilder builder = new StringBuilder();

        builder.append(BEGIN_PUBLIC_KEY);
        builder.append(NEW_LINE);

        int index = 0;
        while (index < publicKeyBase64.length()) {
            final String line = publicKeyBase64.substring(
                    index,
                    Math.min(index + LINE_WIDTH, publicKeyBase64.length())
            );
            builder.append(line);
            builder.append(NEW_LINE);
            index += LINE_WIDTH;
        }

        builder.append(END_PUBLIC_KEY);

        return builder.toString();
    }
}
