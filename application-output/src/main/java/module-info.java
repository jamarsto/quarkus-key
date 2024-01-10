module application.output {
    requires static lombok;
    requires transitive io.smallrye.mutiny;

    exports uk.co.jasonmarston.key.output.port;
}