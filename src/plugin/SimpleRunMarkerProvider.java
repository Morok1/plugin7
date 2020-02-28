package plugin;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.sql.dialects.SqlLanguageDialect;
import com.intellij.sql.psi.SqlColumnDefinition;
import com.intellij.sql.psi.SqlPsiFacade;
import com.intellij.util.IconUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.intellij.openapi.module.ModuleUtilCore.findModuleForPsiElement;
import static com.intellij.psi.search.GlobalSearchScope.moduleWithDependenciesAndLibrariesScope;
import static java.util.Collections.emptyList;
import static java.util.Collections.synchronizedCollection;
import static java.util.stream.Collectors.toList;

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
public class SimpleRunMarkerProvider extends RelatedItemLineMarkerProvider {
    public static final String WEBSITE = "website";
    public static final String SIMPLE = "simple";
    public static final String EXTENDS = "extends";

    public static final String EXTENDS_CURSOR = ".*(extends)*Cursor";
    public static final String EXTENDS_SEQUENCE = ".*(extends)*Sequence";

    Predicate<PsiElement> isPsiKeyword = element -> element instanceof PsiKeyword;
    Predicate<PsiElement> contextIsPreferenceList = element -> element.getContext() instanceof PsiReferenceList;
    Predicate<PsiElement> isPsiPreferenceList = element -> element instanceof PsiReferenceList;
    Predicate<PsiElement> containsExtends = element -> element.getText().contains(EXTENDS);
    Predicate<PsiElement> isPsiJavaFile = element -> element.getContainingFile() instanceof PsiJavaFile;
    Predicate<PsiElement> isPsiLiteralExpression = element -> element instanceof PsiLiteralExpression;
    BiPredicate<String, String> isStartWith = (value, word) -> value != null && value.startsWith(word);
    Predicate<PsiElement> isExtendsOfCursor = (element) -> Pattern.compile(EXTENDS_CURSOR).matcher(element.getText()).matches();
    Predicate<PsiElement> isExtendsOfSequence = (element) -> Pattern.compile(EXTENDS_SEQUENCE).matcher(element.getText()).matches();


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
                        setTargets(emptyList()). /*getPublisherTargets(element, "System.out.println(\"website\");")*/
                        setTooltipText("Navigate to a website property");
        result.add(builder.createLineMarkerInfo(element));

        Module module = findModuleForPsiElement(element);
        GlobalSearchScope scope = moduleWithDependenciesAndLibrariesScope(module);
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(element.getProject(), "order.sql", scope);
        for (PsiFile file : psiFiles) {
            file.getChildren();
        }

        Arrays.stream(psiFiles).filter(p -> p.getText());

        SqlPsiFacade sqlPsiFacade = SqlPsiFacade.getInstance(scope.getProject());
    }



    private List<PsiElement> getPublisherTargets(PsiElement element, String value) {
        if (!(element instanceof PsiIdentifier)) return emptyList();
        PsiElement parent = element.getParent();
        if (!(parent instanceof PsiLiteralExpression)) return emptyList();
        Module module = findModuleForPsiElement(element);
        if (module == null) return emptyList();

        return getPublishPoints(module, value);
    }


    private List<PsiElement> getPublishPoints(Module module, String value) {
        GlobalSearchScope globalSearchScope = moduleWithDependenciesAndLibrariesScope(module);
        List<PsiMethod> publicMethods = getPublicMethods(globalSearchScope);
        List<PsiElement> res = new ArrayList<>();
        for (PsiMethod method : publicMethods) {
            if (method.getBody() != null) {
                List<PsiElement> simple =
                        Arrays.stream(Objects.requireNonNull(method.getBody()).getStatements())
                                .filter(s -> s.getText().equals(value)).map(PsiElement::getParent).collect(toList());
                Optional.of(simple).map(res::addAll);
            }
        }
        return res;
    }
    double companyBotStrategy(int[][] trainingData) {
            double sum = 0.0;
            int count = 0;
        for (int j = 0; j < trainingData.length; j++) {
                if(trainingData[j][1]==1){
                    sum+=trainingData[j][1];
            }
                }
            return sum/count;

    }


    private List<PsiElement> getSqlTables(GlobalSearchScope scope) {
        SqlPsiFacade sqlPsiFacade = SqlPsiFacade.getInstance(scope.getProject());
        sqlPsiFacade.createROFile(sqlPsiFacade.getDefaultDialect(), "order.sql");
        return emptyList();
    }

    private List<PsiElement> getPublishSqlTargets(PsiElement element, String value) {
        Module module = findModuleForPsiElement(element);

//        return getPublishSqlPoints(module, value);
        return null;
    }

    private List<SqlColumnDefinition> getPublishSqlPoints(Module module, String value) {
        GlobalSearchScope globalSearchScope = moduleWithDependenciesAndLibrariesScope(module);
        List<PsiMethod> publicMethods = getPublicMethods(globalSearchScope);
        List<PsiElement> res = new ArrayList<>();
        for (PsiMethod method : publicMethods) {
            if (method.getBody() != null) {
                List<PsiElement> simple =
                        Arrays.stream(Objects.requireNonNull(method.getBody()).getStatements())
                                .filter(s -> s.getText().equals(value)).map(PsiElement::getParent).collect(toList());
                Optional.of(simple).map(res::addAll);
            }
        }
//        return res;
        return null;
    }


    private List<SqlColumnDefinition> getPublicSqlParameters(GlobalSearchScope scope) {
//        SqlPsiFacade sqlPsiFacade = SqlPsiFacade.getInstance(scope.getProject());

//        PsiClass clazz = sqlPsiFacade.get(TEST, scope);
//        return Arrays.asList(Objects.requireNonNull(clazz).getAllMethods());
        return null;
    }

    private List<PsiMethod> getPublicMethods(GlobalSearchScope scope) {
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(scope.getProject());

        PsiClass clazz = javaPsiFacade.findClass("Test", scope);
        return Arrays.asList(Objects.requireNonNull(clazz).getAllMethods());
    }
}
