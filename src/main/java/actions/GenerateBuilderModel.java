package actions;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import utils.CovertUtil;

/**
 * @Description
 * This class is a class that captures user actions in the IDEA plug-in SDK.
 * The menu directory where the user action is triggered is defined in the plugin.xml.
 * The trigger mode: select the class name -> right click -> generator -> builder model generator
 * @author teslazy
 * @date 2019-08-16
 */
public class GenerateBuilderModel extends BaseGenerateAction {

    public GenerateBuilderModel() {
        super(null);
    }

    public GenerateBuilderModel(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        //Detect if the user is in the editor
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (editor == null || project == null) {
            return;
        }
        //Get the file the user is working on
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        PsiClass targetClass = getTargetClass(editor, psiFile); //当前的文件类
        new CovertUtil(psiFile,targetClass).run();
    }

}
