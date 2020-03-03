package plugin;

import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static plugin.util.StringUtil.*;
import static plugin.util.StringUtil.SQL_FILE;

public class PredicateUtil {
    public Predicate<PsiElement> isPsiKeyword = element -> element instanceof PsiKeyword;
    public Predicate<PsiElement> contextIsPreferenceList = element -> element.getContext() instanceof PsiReferenceList;
    public Predicate<PsiElement> isPsiPreferenceList = element -> element instanceof PsiReferenceList;
    public Predicate<PsiElement> containsExtends = element -> element.getText().contains(EXTENDS);
    public Predicate<PsiElement> isPsiJavaFile = element -> element.getContainingFile() instanceof PsiJavaFile;
    public Predicate<PsiElement> isPsiLiteralExpression = element -> element instanceof PsiLiteralExpression;
    public BiPredicate<String, String> isStartWith = (value, word) -> value != null && value.startsWith(word);

    public Predicate<PsiElement> isExtendsOfCursor = (element) -> Pattern.compile(EXTENDS_CURSOR).matcher(element.getText()).matches();
    public Predicate<PsiElement> isExtendsOfSequence = (element) -> Pattern.compile(EXTENDS_SEQUENCE).matcher(element.getText()).matches();

    public Predicate<String> isExtendsOfSequenceString = (s) -> Pattern.compile(EXTENDS_SEQUENCE).matcher(s).matches();
    public Predicate<String> isExtendsOfCursorString = (s) -> Pattern.compile(EXTENDS_CURSOR).matcher(s).matches();
    //todo change on Predicate on this BiPredicate
    public BiPredicate<String, String> test = (pattern, matcher) -> Pattern.compile(pattern).matcher(matcher).matches();

    public Predicate<String> isSqlFile = s -> Pattern.compile(SQL_FILE).matcher(s).matches();
    public BiFunction<PsiElement, String, Function<GlobalSearchScope, PsiFile[]>> getSqlFileByName
            = (element, fileName)-> scope -> FilenameIndex.getFilesByName(element.getProject(), fileName, scope);

}
