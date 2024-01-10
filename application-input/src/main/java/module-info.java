module application.input {
    requires application.output;
    requires static lombok;
    requires jakarta.cdi;
    requires transitive io.smallrye.mutiny;

    exports uk.co.jasonmarston.key.usecase;
}