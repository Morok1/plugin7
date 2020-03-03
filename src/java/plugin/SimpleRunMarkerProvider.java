package plugin;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IconUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.intellij.openapi.module.ModuleUtilCore.findModuleForPsiElement;
import static com.intellij.psi.search.GlobalSearchScope.moduleWithDependenciesAndLibrariesScope;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static plugin.util.StringUtil.*;

//class extends from cursor

// name cut cursor

//create table with this name

//regenerate at some activity

//disable inspection

//define celesta project if recoursces contains chelcesta dependencies

// all elements error find view просто view не будет

//конфигурация дефолтовая скор назодистся, схордит в мавен плагин
//отключаем инспекции
//находим фрагмент

// сходить от одного шксти ыск потом

// убрать иконку с диалектом


//Saturday
// 1. Get all sql file in project
// 2 Get Sql_create_table_statement
// 3. by element name




public class SimpleRunMarkerProvider extends RelatedItemLineMarkerProvider {

    Predicate<PsiElement> isPsiKeyword = element -> element instanceof PsiKeyword;
    Predicate<PsiElement> contextIsPreferenceList = element -> element.getContext() instanceof PsiReferenceList;
    Predicate<PsiElement> isPsiPreferenceList = element -> element instanceof PsiReferenceList;
    Predicate<PsiElement> containsExtends = element -> element.getText().contains(EXTENDS);
    Predicate<PsiElement> isPsiJavaFile = element ->
    {
        if (element.getContainingFile() instanceof PsiJavaFile) {
            PsiJavaFile file = (PsiJavaFile) element.getContainingFile();
            return file.getContainingDirectory().getText().contains("generated-sources");
        } else return false;
    };



    Predicate<PsiElement> isPsiLiteralExpression = element -> element instanceof PsiLiteralExpression;
    BiPredicate<String, String> isStartWith = (value, word) -> value != null && value.startsWith(word);

    Predicate<PsiElement> isExtendsOfCursor = (element) -> Pattern.compile(EXTENDS_CURSOR).matcher(element.getText()).matches();
    Predicate<PsiElement> isExtendsOfSequence = (element) -> Pattern.compile(EXTENDS_SEQUENCE).matcher(element.getText()).matches();

    Predicate<String> isExtendsOfSequenceString = (s) -> Pattern.compile(EXTENDS_SEQUENCE).matcher(s).matches();
    Predicate<String> isExtendsOfCursorString = (s) -> Pattern.compile(EXTENDS_CURSOR).matcher(s).matches();
    //todo change on Predicate on this BiPredicate
    BiPredicate<String, String> test = (pattern, matcher) -> Pattern.compile(pattern).matcher(matcher).matches();

    Predicate<String> isSqlFile = s -> Pattern.compile(SQL_FILE).matcher(s).matches();
    private BiFunction<PsiElement, String, Function<GlobalSearchScope, PsiFile[]>> getSqlFileByName
            = (element, fileName)-> scope -> FilenameIndex.getFilesByName(element.getProject(), fileName, scope);
    private Predicate<PsiElement> isSqlCreateTableStatement = elem -> elem.getNavigationElement()
            .toString()
            .equals(SQL_CREATE_TABLE_STATEMENT);

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (isPsiJavaFile.test(element)) {
            drawButtonForPsiLiteralExpression(element, result);
        }
        if (isPsiPreferenceList.and(isExtendsOfCursor.or(isExtendsOfSequence))
                .test(element)) {
            drawButton(element, result);
        }
    }

    private void drawButtonForPsiLiteralExpression(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        PsiElement element1 = element.getParent();
        if (isPsiLiteralExpression.test(element1)) {
            PsiLiteralExpression psiLiteralExpression = (PsiLiteralExpression) element1;
            String value = psiLiteralExpression.getValue() instanceof String ? (String) psiLiteralExpression.getValue() : null;
            if (isStartWith.test(value, WEBSITE)) {
                drawButton(element, result);
            }
            if (isStartWith.test(value, SIMPLE)) {
                drawButton(element, result);
            }
        }
    }


//file name index
//    FilenameIndex.getFilesByName(element.getProject(), "order.sql", moduleWithDependenciesAndLibrariesScope(findModuleForPsiElement(element)))[0].getChildren()[3].getText()
    private void drawButton(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        NavigationGutterIconBuilder<PsiElement> builder =
                NavigationGutterIconBuilder.create(IconUtil.getAddIcon()).
                        setTargets(getTargetPoints(element)). /*getPublisherTargets(element, "System.out.println(\"website\");")*/
                        setTooltipText("Navigate to a website property");
        result.add(builder.createLineMarkerInfo(element));
//
//        List<PsiElement> targetPoints = getTargetPoints(element);{
//}
//        targetPoints.toArray();
    }

    private List<PsiElement> getTargetPoints(@NotNull PsiElement element) {
        GlobalSearchScope scope = moduleWithDependenciesAndLibrariesScope(findModuleForPsiElement(element));

        List<String> sqlFileNames = getSqlFilesOfProject(element);
        List<PsiFile> sqlFilesInProject = getSqlFilesInProject(element, scope, sqlFileNames);

        return sqlFilesInProject.stream()
                .map(file -> getTargetElementByPsiFile(file, element))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private PsiElement getTargetElementByPsiFile(PsiFile file, PsiElement element) {
        List<PsiElement> tablesCursorOrSequence = stream(file.getChildren())
                .filter(isSqlCreateTableStatement)
                .collect(toList());

        String className = element.getText();

        if (className.contains("extends ")) {
            className = className.replaceAll("extends ", "");
        }

        if (className.contains("Cursor")) {
            className = className.replaceAll("Cursor", "");
        }

        String finalClassName = className;

        Function<PsiElement, Boolean> isEqualClassName = elem -> elem.getText().split("\\h")[2].replace("(\n", "").
                equals(finalClassName);
        return tablesCursorOrSequence.stream()
                .filter(isEqualClassName::apply)
                .findFirst().orElse(null);
    }

    @NotNull
    private List<PsiFile> getSqlFilesInProject(@NotNull PsiElement element, GlobalSearchScope scope, List<String> sqlFileNames) {
        return sqlFileNames.stream()
                .map(s -> getSqlFileByName.apply(element, s).apply(scope))
                .flatMap(Arrays::stream)
                .collect(toList());
    }

    private List<String> getSqlFilesOfProject(@NotNull PsiElement element) {
        String[] allFilenames = FilenameIndex.getAllFilenames(element.getProject());
        return stream(allFilenames).filter(isSqlFile).collect(toList());
    }
}
