package synfron.reshaper.burp.core.vars;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class VariableSourceEntry implements Serializable {
    private final VariableSource variableSource;
    private final String name;
    private final String tag;

    private VariableSourceEntry() {
        this(null, null, null);
    }

    public VariableSourceEntry(VariableSource variableSource, String name, String tag) {
        this.variableSource = variableSource;
        this.name = name;
        this.tag = tag;
    }

    public VariableSourceEntry(VariableSource variableSource, String name) {
        this.variableSource = variableSource;
        this.name = name;
        this.tag = getTag();
    }

    public String getTag() {
        return StringUtils.isNotEmpty(tag) ? tag : getTag(variableSource, name);
    }

    public static String getTag(VariableSource variableSource, String name) {
        return String.format("{{%s:%s}}", variableSource.toString().toLowerCase(), name);
    }

    public static String getTag(VariableSource variableSource, String... names) {
        return String.format("{{%s}}", Stream.concat(
                Stream.of(StringUtils.defaultString(variableSource.toString().toLowerCase())),
                Arrays.stream(names).map(name -> StringUtils.defaultIfEmpty(name, null))
        ).filter(Objects::nonNull).collect(Collectors.joining(":")));
    }

    public static String getShortTag(VariableSource variableSource, String name) {
        return String.format("{{%s:%s}}", variableSource.getShortName(), name);
    }

    public static String getShortTag(VariableSource variableSource, String... names) {
        return String.format("{{%s}}", Stream.concat(
                Stream.of(StringUtils.defaultString(variableSource.getShortName())),
                Arrays.stream(names).map(name -> StringUtils.defaultIfEmpty(name, null))
        ).filter(Objects::nonNull).collect(Collectors.joining(":")));
    }
}
