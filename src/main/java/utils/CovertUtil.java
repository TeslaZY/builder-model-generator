package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;

/**
 * @Description This class contains a series of operations on the file to be modified.
 * @Author teslazy
 * @Date 2019-08-16 11:30
 **/

public class CovertUtil {

    private final PsiFile psiFile;

    private final PsiClass targetClass;

    public CovertUtil(PsiFile psiFile, PsiClass targetClass){
        this.psiFile = psiFile;
        this.targetClass = targetClass;
    }

    /**
     * @descrition  Intellij Platform does not allow real-time file writes in the main thread,
     * but requires an asynchronous task. At this time, you need to call WriteCommandAction to
     * write related content.
     * @param
     * @return
     */
    public void run() {
        try {
            WriteCommandAction.runWriteCommandAction(psiFile.getProject(), () -> {
                //get the PsiElementFactory to produce the java element
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiFile.getProject());
                PsiField[] fields = targetClass.getFields();
                String targetClassName = targetClass.getQualifiedName();
                System.out.println(targetClassName);
                //Create the inner builder class
                addInnerBuilderClass(elementFactory,targetClass,fields);
                ////Create the constructor method
                addConstructorMethodtoClass(elementFactory,targetClass,fields);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addInnerBuilderClass(PsiElementFactory elementFactory, PsiClass targetClass, PsiField[] fields) {
        //inner class name
        String className = "Builder";
        PsiClass reqClass = elementFactory.createClass(className);
        reqClass.getModifierList().getFirstChild().delete();
        reqClass.getModifierList().add(elementFactory.createKeyword("public"));
        reqClass.getModifierList().add(elementFactory.createKeyword("static"));
        PsiElement innerClass = targetClass.add(reqClass);
        addFieldsToInnerClass(elementFactory,fields,(PsiClass) innerClass);
        addMethodsToInnerClass(elementFactory,fields,(PsiClass) innerClass);

    }

    private void addFieldsToInnerClass(PsiElementFactory elementFactory, PsiField[] fields, PsiClass innerClass) {
        for(PsiField field : fields){
            if(field==null){
                continue;
            }
            StringBuffer innerClassFieldsStr = new StringBuffer();
            String fieldType = field.getType().toString().split(":")[1];
            innerClassFieldsStr.append("private").append(" ").append(fieldType).append(" ").append(field.getName()).append(";");
            innerClass.add(elementFactory.createFieldFromText(innerClassFieldsStr.toString(), innerClass));
        }
    }

    private void addMethodsToInnerClass(PsiElementFactory elementFactory, PsiField[] fields, PsiClass innerClass) {
        for(PsiField field: fields){
            StringBuffer methodStr = new StringBuffer();
            methodStr.append("public Builder ").append(field.getName()).append("() { \n")
                    .append(field.getName()).append(" = var;")
                    .append("return this;\n")
                    .append("}");
            PsiMethod psiMethod = elementFactory.createMethodFromText(methodStr.toString(), targetClass);
            psiMethod.getParameterList().add(elementFactory.createParameter("var", field.getType()));
            innerClass.add(psiMethod);
        }

        StringBuffer constructorMethodStr = new StringBuffer();
        constructorMethodStr = constructorMethodStr.append("public ").append(targetClass.getName()).append(" build() { \n")
                .append("return new ").append(targetClass.getName()).append("(this);")
                .append("}");
        PsiMethod psiMethod = elementFactory.createMethodFromText(constructorMethodStr.toString(), targetClass);
        innerClass.add(psiMethod);
    }

    private void addConstructorMethodtoClass(PsiElementFactory elementFactory, PsiClass targetClass, PsiField[] fields) {
        StringBuffer methodStr = new StringBuffer();
        methodStr.append("private ").append(targetClass.getName()).append("() { \n");
        for(PsiField field: fields){
            methodStr.append(field.getName()).append(" = builder.").append(field.getName()).append(";\n");
        }
        methodStr.append("\n}");
        PsiMethod psiMethod = elementFactory.createMethodFromText(methodStr.toString(), targetClass);
        psiMethod.getParameterList().add(elementFactory.createParameterFromText("Builder builder",psiMethod));
        targetClass.add(psiMethod);
    }

}
