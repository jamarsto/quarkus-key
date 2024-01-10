package uk.co.jasonmarston.key.adaptor.output;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

class X509CertificateUtility {
    private static final X509CertificateUtility INSTANCE = new X509CertificateUtility();
    private final static String CERTIFICATE_TYPE = "X.509";

    public static X509CertificateUtility getInstance() {
        return INSTANCE;
    }

    private X509CertificateUtility() {
    }

    public X509Certificate getCertificate(final String certificateLocation) throws CertificateException {
        final InputStream inputStream = InputStreamUtility
            .getInstance()
            .getInputStream(certificateLocation);

        return (X509Certificate) CertificateFactory
            .getInstance(CERTIFICATE_TYPE)
            .generateCertificate(inputStream);
    }
}
