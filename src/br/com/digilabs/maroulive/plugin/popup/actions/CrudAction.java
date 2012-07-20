package br.com.digilabs.maroulive.plugin.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class CrudAction implements IObjectActionDelegate {

	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public CrudAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage(); // null check
															// omitted

		IEditorPart editorPart = activePage.getActiveEditor(); // null check
																// omitted

		IEditorInput input = editorPart.getEditorInput();
		
		IFile file = null;
		if ((input instanceof IFileEditorInput)) {
			file = ((IFileEditorInput) input).getFile();
		}

		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		
		try {
			IType[] allTypes = compilationUnit.getTypes();
			for (IType type : allTypes) {				
				IField[] fields = type.getFields();
				for (IField field : fields) {				
					System.out.println(( Flags.isStatic(field.getFlags()) ) + " :: "+ field.getElementName());					
				}
			}
		} catch (JavaModelException e1) {
			e1.printStackTrace();
		}		


		MessageDialog.openInformation(shell, "Plugin",
				"Create Crud was executed.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
