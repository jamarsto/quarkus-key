package uk.co.jasonmarston.key.adaptor.output;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

class RSAPublicKeyUtility {
    private static final RSAPublicKeyUtility INSTANCE = new RSAPublicKeyUtility();

    public static RSAPublicKeyUtility getInstance() {
        return INSTANCE;
    }

    private RSAPublicKeyUtility() {
    }

    public RSAPublicKey getPublicKey(final String certificateLocation) throws CertificateException {
        final X509Certificate certificate = X509CertificateUtility
            .getInstance()
            .getCertificate(certificateLocation);

        return (RSAPublicKey) certificate
            .getPublicKey();
    }
}
