package uk.co.jasonmarston.key.adaptor.output;

import io.smallrye.config.SmallRyeConfig;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.List;

class ProfileUtility {
    private static final String PRODUCTION = "prod";
    private static final ProfileUtility INSTANCE = new ProfileUtility();

    public static ProfileUtility getInstance() {
        return INSTANCE;
    }

    private ProfileUtility() {
    }

    public boolean isProduction() {
        return getProfiles().contains(PRODUCTION);
    }

    private List<String> getProfiles() {
        return ConfigProvider
            .getConfig()
            .unwrap(SmallRyeConfig.class)
            .getProfiles();
    }
}
