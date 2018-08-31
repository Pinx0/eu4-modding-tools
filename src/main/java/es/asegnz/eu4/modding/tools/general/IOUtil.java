package es.asegnz.eu4.modding.tools.general;

import static java.nio.file.StandardOpenOption.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class IOUtil {

	public String readAllFileFromRelativePath(String relativePath) {
		Path path = null;
		try {
			path = Paths.get(getClass().getClassLoader().getResource(relativePath).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		StringBuilder data = new StringBuilder();
		Stream<String> lines = null;
		try {
			lines = Files.lines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lines.forEach(line -> data.append(line).append("\n"));
		lines.close();
		return data.toString();
	}

	public List<String> readLinesFromRelativePath(String relativePath) {
		Path path = null;
		try {
			path = Paths.get(getClass().getClassLoader().getResource(relativePath).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Stream<String> lines = null;
		try {
			lines = Files.lines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> stringLines = new ArrayList<String>();
		lines.forEach(line -> stringLines.add(line + "\n"));
		lines.close();
		return stringLines;
	}

	// TODO: this method is not for this class
	public void fillListByLine(String line, Map<Integer, List<Integer>> map) {
		int provinceIdCol = 1;
		int pointTypeIdCol = 2;
		String[] splittedLineByCommas = line.split(",");

		if (splittedLineByCommas[provinceIdCol] != null && !splittedLineByCommas[provinceIdCol].isEmpty()
				&& splittedLineByCommas[pointTypeIdCol] != null && !splittedLineByCommas[pointTypeIdCol].isEmpty()) {
			Integer provinceId = Integer.valueOf(splittedLineByCommas[provinceIdCol]);
			Integer pointTypeId = Integer.valueOf(splittedLineByCommas[pointTypeIdCol]);
			if (!map.containsKey(pointTypeId)) {
				List<Integer> list = new ArrayList<Integer>();
				map.put(pointTypeId, list);
			}
			List<Integer> list = map.get(pointTypeId);
			list.add(provinceId);
		}
	}

	public void writeToFile(String output, String relativePath) {
		try {
			File file = new File(relativePath);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				FileOutputStream writer = new FileOutputStream(relativePath);
				writer.write(output.getBytes());
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
