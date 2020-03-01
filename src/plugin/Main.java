package plugin;

import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(?:\\s+\\p{Alnum})(_)(?<name>[*Cursor]?[*Sequence])");

        System.out.println("NameCursor".replaceAll("Cursor", ""));
    }
}
