package synfron.reshaper.burp.core.vars;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum VariableSource {
    Event("e", false),
    Global("g", false),
    Message("m", true),
    File("f", true),
    Special("s", true),
    CookieJar("Cookie Jar", "cj", true);

    private final String displayName;
    private final String shortName;
    private final boolean accessor;

    VariableSource(String displayName, String shortName, boolean accessor) {
        this.displayName = displayName;
        this.shortName = shortName;
        this.accessor = accessor;
    }

    VariableSource(String shortName, boolean accessor) {
        this.displayName = this.name();
        this.shortName = shortName;
        this.accessor = accessor;
    }

    public static List<String> getSupportedNames() {
        return Stream.concat(
                Arrays.stream(VariableSource.values()).map(source -> source.name().toLowerCase()),
                Arrays.stream(VariableSource.values()).map(VariableSource::getShortName)
        ).collect(Collectors.toList());
    }

    public static VariableSource get(String name) {
        return Arrays.stream(VariableSource.values())
                .filter(source -> source.name().equalsIgnoreCase(name) || source.shortName.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }


    @Override
    public String toString() {
        return displayName;
    }
}
