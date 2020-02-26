package plugin.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test extends NameCursor {
    public static void main(String[] args) {
        Matcher matcher = isMatchOfCondition();
        System.out.println("truel;" + matcher.matches());
        System.out.println("website");
    }

    @NotNull
    private static Matcher isMatchOfCondition() {
        String regex = ".*(extends)*Cursor";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("extends NameCursor");
        boolean result = matcher.matches();
        return matcher;
    }

    public void testa() {
        System.out.println("simple");
    }
}