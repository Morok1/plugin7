package plugin.util;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.sql.psi.SqlCreateTableStatement;
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


//naming
//refactoring

//1.Сделать переход из классов в target'е, которые наследуются от курсора или sequence,
// в соответствуюущую таблицу, которая расположена в celesta/ru/kurs/demo/
//2. Для каждой таблицы сделать иконку с переходом в соответствующий по имени таблицы
// класс в target'e
//3.Убрать no data source
//4. Автоматические генерировать target(при нахождении в pom.xml ru.kurs)
//5.
//
//


public class SimpleRunMarkerProvider extends RelatedItemLineMarkerProvider {

    Predicate<PsiElement> isPsiKeyword = element -> element instanceof PsiKeyword;
    Predicate<PsiElement> contextIsPreferenceList = element -> element.getContext() instanceof PsiReferenceList;
    Predicate<PsiElement> isPsiReferenceList = element -> element instanceof PsiReferenceList;
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
            = (element, fileName) -> scope -> FilenameIndex.getFilesByName(element.getProject(), fileName, scope);
    private Predicate<PsiElement> isSqlCreateTableStatement = elem -> elem.getNavigationElement()
            .toString()
            .equals(SQL_CREATE_TABLE_STATEMENT);

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PsiClass) {
            PsiClass classElement = (PsiClass) element;
            for (PsiClassType superType : classElement.getExtendsListTypes()) {
                if ("Cursor".equals(superType.getClassName()))
                    drawButton(classElement, result);
            }
        }

    }


    //file name index
//    FilenameIndex.getFilesByName(element.getProject(), "order.sql", moduleWithDependenciesAndLibrariesScope(findModuleForPsiElement(element)))[0].getChildren()[3].getText()
    private void drawButton(@NotNull PsiClass element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {

        System.out.println("!!!!!");
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

    private List<PsiElement> getTargetPoints(@NotNull PsiClass element) {
        GlobalSearchScope scope = moduleWithDependenciesAndLibrariesScope(findModuleForPsiElement(element));

        Stream<String> sqlFileNames = getSqlFilesOfProject(element);
        System.out.println("--------------");
        getSqlFilesOfProject(element).forEach(
                System.out::println
        );
        System.out.println("---------------");

        Stream<PsiFile> sqlFilesInProject = getSqlFilesInProject(element, scope, sqlFileNames);

        return sqlFilesInProject
                .peek(e -> System.out.println(e.getName()))
                .map(file -> getTargetElementByPsiFile(file, element))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .map(Collections::singletonList).orElse(Collections.emptyList());

    }

    private Optional<PsiElement> getTargetElementByPsiFile(PsiFile sqlFile, PsiClass classElement) {
        String className = classElement.getName();
        String tableName = className.substring(0, className.length() - "Cursor".length());

        return stream(sqlFile.getChildren())
                .filter(SqlCreateTableStatement.class::isInstance)
                .map(SqlCreateTableStatement.class::cast)
                .filter(e -> e.getName().equals(tableName))
                .findAny()
                .map(PsiElement.class::cast);
    }

    @NotNull
    private Stream<PsiFile> getSqlFilesInProject(@NotNull PsiElement element, GlobalSearchScope scope, Stream<String> sqlFileNames) {
        return sqlFileNames
                .map(s -> getSqlFileByName.apply(element, s).apply(scope))
                .flatMap(Arrays::stream);
    }

    private Stream<String> getSqlFilesOfProject(@NotNull PsiElement element) {
        String[] allFilenames = FilenameIndex.getAllFilenames(element.getProject());
        return stream(allFilenames).filter(isSqlFile);
    }
}
