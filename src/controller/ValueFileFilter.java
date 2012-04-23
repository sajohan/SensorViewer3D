package controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;


/**
 * Filter that filters out any file not ending with .val
 * @author simoniv
 *
 */
public class ValueFileFilter extends FileFilter{

	/**
	 * Is file acceptable?
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
	        return true;
	    }

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals("val") ||
					extension.equals("obj") ||
					extension.equals("stl")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	/**
	 * Get description of the filter
	 */
	@Override
	public String getDescription() {
		return "Graphical files (*.val, *.stl, *.obj)";
	}
	
    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

}
