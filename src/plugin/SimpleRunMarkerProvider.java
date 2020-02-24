package plugin;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.*;
import com.intellij.util.IconUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.ULiteralExpression;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;

//class extends from cursor
// name cut cursor
//create table with this name
//regenerate at some activity
//disable inspection
//defeine celesta project if recoursces contains chelcesta dependencies
// all elements error find view просто view не будет

//конфигурация дефолтовая скор назодистся, схордит в мавен плагин
//отключаем инспекции
//находим фрагмент

// сходить от одного шксти ыск потом

// убрать иконку с диалектом
public class SimpleRunMarkerProvider extends RelatedItemLineMarkerProvider {

    public static final String WEBSITE = "website";
    public static final String SIMPLE = "simple";
    Predicate<PsiElement> isPsiKeyword = element -> element instanceof PsiKeyword;
    Predicate<PsiElement> contextIsPreferenceList = element -> element.getContext() instanceof PsiReferenceList;
    Predicate<PsiElement> containsExtends = element -> element.getText().contains("extends");
    Predicate<PsiElement> isPsiJavaFile = element -> element.getContainingFile() instanceof PsiJavaFile;
    Predicate<PsiElement> isPsiLiteralExpression = element -> element instanceof PsiLiteralExpression;
    BiPredicate<String, String> isStartWith = (value, word) -> value != null && value.startsWith(word);

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (isPsiJavaFile.test(element)) {
            drawButtonForPsiLiteralExpression(element, result);
        }
        if (isPsiKeyword.and(contextIsPreferenceList).and(containsExtends).test(element)) {
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

    private void drawButton(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        NavigationGutterIconBuilder<PsiElement> builder =
                NavigationGutterIconBuilder.create(IconUtil.getAddIcon()).
                        setTargets(emptyList()). /*getPublisherTargets(element, "System.out.println(\"website\");")*/
                        setTooltipText("Navigate to a website property");
        result.add(builder.createLineMarkerInfo(element));
    }
}
