package plugin.util;

public class OldCode {

    //            if(element1 instanceof PsiReferenceList){
//                 if(((PsiReferenceList) element1).getRole().name().equals("EXTENDS_LIST")){
//                     NavigationGutterIconBuilder<PsiElement> builder =
//                             NavigationGutterIconBuilder.create(IconUtil.getAddIcon()).
//                                     setTargets(emptyList()). /*getPublisherTargets(element, "System.out.println(\"website\");")*/
//                                     setTooltipText("Navigate to a website property");
//                     result.add(builder.createLineMarkerInfo(element));
//                 }
//            }

//    private List<PsiElement> getPublisherTargets(PsiElement element, String value){
////        if(!(element instanceof PsiIdentifier)) return emptyList();
////        PsiElement parent = element.getParent();
////        if(!(parent instanceof PsiLiteralExpression)) return  emptyList();
//        Module module = findModuleForPsiElement(element);
////        if (module == null) return emptyList();
//
//        return getPublishPoints(module, value);
//    }
//
//
//    private List<PsiElement> getPublishSqlTargets(PsiElement element, String value){
//        Module module = findModuleForPsiElement(element);
//
////        return getPublishSqlPoints(module, value);
//        return null;
//    }
//
//
//    private List<SqlColumnDefinition> getPublishSqlPoints(Module module, String value) {
//        GlobalSearchScope globalSearchScope = moduleWithDependenciesAndLibrariesScope(module);
//        List<PsiMethod> publicMethods = getPublicMethods(globalSearchScope);
//        List<PsiElement> res =  new ArrayList<>();
//        for (PsiMethod method : publicMethods) {
//            if(method.getBody()!=null) {
//                List<PsiElement> simple =
//                        Arrays.stream(Objects.requireNonNull(method.getBody()).getStatements())
//                                .filter(s -> s.getText().equals(value)).map(PsiElement::getParent).collect(toList());
//                Optional.of(simple).map(res::addAll);
//            }
//        }
////        return res;
//        return null;
//    }
//
//    private List<PsiElement> getPublishPoints(Module module, String value) {
//        GlobalSearchScope globalSearchScope = moduleWithDependenciesAndLibrariesScope(module);
//        List<PsiMethod> publicMethods = getPublicMethods(globalSearchScope);
//        List<PsiElement> res =  new ArrayList<>();
//        for (PsiMethod method : publicMethods) {
//            if(method.getBody() != null) {
//                List<PsiElement> simple =
//                        Arrays.stream(Objects.requireNonNull(method.getBody()).getStatements())
//                                .filter(s -> s.getText().equals(value)).map(PsiElement::getParent).collect(toList());
//                Optional.of(simple).map(res::addAll);
//            }
//        }
//        return res;
//    }
//
//    private List<SqlColumnDefinition> getPublicSqlParameters(GlobalSearchScope scope) {
////        SqlPsiFacade sqlPsiFacade = SqlPsiFacade.getInstance(scope.getProject());
//
////        PsiClass clazz = sqlPsiFacade.get(TEST, scope);
////        return Arrays.asList(Objects.requireNonNull(clazz).getAllMethods());
//        return null;
//    }
//
//    private List<PsiMethod> getPublicMethods(GlobalSearchScope scope) {
//        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(scope.getProject());
//
//        PsiClass clazz = javaPsiFacade.findClass("Test", scope);
//        return Arrays.asList(Objects.requireNonNull(clazz).getAllMethods());
//    }
}
