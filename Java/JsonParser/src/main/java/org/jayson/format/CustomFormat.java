package org.jayson.format;

import org.jayson.dto.*;

import static java.util.stream.Collectors.joining;

public class CustomFormat implements JsonFormat {

    // Parameters
    private String indent = "    ";
    private String lf = "\n";
    private String sep = ", ";
    private String colon = ": ";

    // Cached concatenations
    private String sepLf = sep + lf;
    private String openObj = "{" + lf;
    private String openArr = "[" + lf;
    private String quoteColon = '"' + colon;

    public CustomFormat indent(String indent) {
        this.indent = indent;
        return this;
    }

    public CustomFormat newline(String newline) {
        this.lf = newline;
        sepLf = sep + lf;
        openObj = "{" + lf;
        openArr = "[" + lf;
        return this;
    }

    public CustomFormat separator(String separator) {
        this.sep = separator;
        sepLf = sep + lf;
        return this;
    }

    public CustomFormat colon(String colon) {
        this.colon = colon;
        quoteColon = '"' + colon;
        return this;
    }

    private int level = 0;

    public String format(JsonObject object) {
        level++;
        String formatted = object.entries().stream()
                .map(e -> margin(level) + '"' + e.getKey() + quoteColon + format(e.getValue()))
                .collect(joining(sepLf, openObj, lf + margin(level - 1) + "}"));
        level--;
        return formatted;
    }

    public String format(JsonArray array) {
        level++;
        String formatted = array.values().stream()
                .map(element -> margin(level) + format(element))
                .collect(joining(sepLf, openArr, lf + margin(level-1) + "]"));
        level--;
        return formatted;
    }

    public String format(JsonBoolean bool) {
        return bool.getValue() ? "true" : "false";
    }

    public String format(JsonNumber number) {
        return number.isDouble()
                ? String.valueOf(number.getDouble())
                : String.valueOf(number.getLong());
    }

    public String format(JsonString string) {
        return '"' + string.getValue() + '"';
    }

    private String[] marginCache = new String[8];

    private String margin(int n) {
        if (n >= marginCache.length) {
            String[] copy = new String[marginCache.length * 2];
            System.arraycopy(marginCache, 0, copy, 0, marginCache.length);
            marginCache = copy;
        }
        if (marginCache[n] == null) {
            marginCache[n] = new String(new char[n]).replace("\0", indent);
        }
        return marginCache[n];
    }
}
