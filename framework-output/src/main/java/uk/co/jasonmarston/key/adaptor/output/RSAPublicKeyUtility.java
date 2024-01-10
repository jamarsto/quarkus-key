package uk.co.jasonmarston.key.adaptor.output;

import lombok.extern.slf4j.Slf4j;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

@Slf4j
class RSAPublicKeyUtility {
    private static final RSAPublicKeyUtility INSTANCE = new RSAPublicKeyUtility();
    private static final String ERROR_MESSAGE =
        "Certificate Not Loaded From: {}";

    public static RSAPublicKeyUtility getInstance() {
        return INSTANCE;
    }

    private RSAPublicKeyUtility() {
    }

    public RSAPublicKey getPublicKey(final String certificateLocation) {
        try {
            final X509Certificate certificate = X509CertificateUtility
                .getInstance()
                .getCertificate(certificateLocation);

            return (RSAPublicKey) certificate
                .getPublicKey();
        }
        catch(final CertificateException e) {
            log.error(ERROR_MESSAGE, certificateLocation);
            return null;
        }
    }
}
