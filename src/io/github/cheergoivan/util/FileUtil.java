package io.github.cheergoivan.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {
	public static void main(String[] args) {
		FileUtil.copyChildrenFiles(new File("C:\\Users\\i321035\\Desktop\\template"), (dir, f) -> true,
				new File("C:\\Users\\i321035\\Desktop\\a"));
	}

	public static String getFileContent(String filePath) {
		String content = "";
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			content = new String(bytes, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * search if exists target files in the given directory
	 * 
	 * @param directory
	 * @param targetFiles
	 * @return
	 */
	public static boolean exists(File directory, String... targetFiles) {
		Set<String> targetFileNames = new HashSet<>();
		for (String t : targetFiles) {
			targetFileNames.add(new File(t).getName());
		}
		File[] files = directory.listFiles();
		for (File f : files) {
			if (targetFileNames.contains(f.getName())) {
				targetFileNames.remove(f.getName());
			}
			if (targetFileNames.size() == 0)
				return true;
		}
		return false;
	}

	/**
	 * copy all children of a given directory to target directory
	 * 
	 * @param directory
	 * @param targetDirectory
	 */
	public static void copyChildrenFiles(File directory, FilenameFilter filter, File targetDirectory) {
		for (File f : directory.listFiles(filter)) {
			copyFile(f, targetDirectory);
		}
	}

	/**
	 * copy file to target directory,if file is a directory,it will copy
	 * recursively
	 * 
	 * @param file
	 *            file to be copied
	 * @param target
	 *            target directory
	 */
	public static void copyFile(File file, File target) {
		System.out.println("copy " + file.getAbsolutePath() + " to " + target.getAbsolutePath());
		if (file.isDirectory()) {
			File newFile = new File(target, file.getName());
			if (!newFile.exists())
				newFile.mkdir();
			for (File son : file.listFiles()) {
				copyFile(son, newFile);
			}
		} else {
			try {
				String targetFile = target.getAbsolutePath() + "/" + file.getName();
				Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(targetFile),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteFilesInDirectory(File directory, FileFilter filter) {
		for (File f : directory.listFiles(filter)) {
			boolean result = deleteDir(f);
			if (result)
				System.out.println("delete " + f.getAbsolutePath());
			else
				StringUtil.printError("failed to delete " + f.getAbsolutePath());
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// dir is a file or dir is empty
		return dir.delete();
	}

	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	public static String getFilename(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}
	
	public static InputStream getResourceFileContent(String resource){
		return FileUtil.class.getResourceAsStream(resource);
	}
}
