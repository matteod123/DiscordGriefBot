package de.corruptedbytes.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class FileUtils {

	public static void exportFile(String file, File output) throws IOException {
		JarFile jarFile = new JarFile(System.getProperty("java.class.path"));
		JarEntry jarEntry = jarFile.getJarEntry(file);
		InputStream stream = jarFile.getInputStream(jarEntry);
		Files.copy(stream, output.toPath());
	}

	public static void extractZip(File zipFile, File outputDirectory) throws ZipException, IOException {
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<? extends ZipEntry> entries = zip.entries();

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			InputStream stream = zip.getInputStream(entry);

			if (entry.isDirectory())
				new File(outputDirectory + File.separator + entry.getName()).mkdir();
			else
				Files.copy(stream, new File(outputDirectory + File.separator + entry.getName()).toPath());
		}
		zip.close();
		zipFile.delete();
	}

	public static String readFile(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			return null;
		}
	}

}
