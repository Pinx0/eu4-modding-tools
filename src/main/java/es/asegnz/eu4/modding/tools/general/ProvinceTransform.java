package es.asegnz.eu4.modding.tools.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvinceTransform {

	// Declare tokens in order to be replace later
	public static final String TK_EVERY_PROVINCE_TOTAL = "TKEVERYPROVINCETOTAL";

	public static final String TK_PROVINCES = "TKPROVINCES";

	public static final String TK_PROVINCE_TYPE_ID = "TKPROVINCETYPEID";

	public static final String TK_PROVINCE = "TKPROVINCE";

	// Declare pathfiles from the resources folder
	public static final String baseTextTemplatePath = "es/asegnz/eu4/modding/tools/text/baseProvinceTemplate.txt";

	public static final String everyProvinceTemplatePath = "es/asegnz/eu4/modding/tools/text/everyProvinceTemplate.txt";

	public static final String provinceTemplatePath = "es/asegnz/eu4/modding/tools/text/provinceTemplate.txt";

	public static final String lastIdListPath = "es/asegnz/eu4/modding/tools/text/listaIds30082018.csv";
	
	public static final String countryEventOutputPath = "es/asegnz/eu4/modding/tools/text/output/country_event.txt";

	public static Map<Integer, List<Integer>> idProvinceListByType = new HashMap<Integer, List<Integer>>();

	public static void main(String[] args) {
		IOUtil io = new IOUtil();
		String baseTextTemplate = io.readAllFileFromRelativePath(baseTextTemplatePath);
		String everyProvinceTemplate = io.readAllFileFromRelativePath(everyProvinceTemplatePath);
		String provinceTemplate = io.readAllFileFromRelativePath(provinceTemplatePath);
		List<String> provinceIdListAndType = io.readLinesFromRelativePath(lastIdListPath);
		boolean ignoredHeaderYet = false;
		for (String line: provinceIdListAndType) {
			if (!ignoredHeaderYet) {
				ignoredHeaderYet = true;
			}
			else {
				io.fillListByLine(line, idProvinceListByType);
			}
		}
		StringBuilder everyProvinceBuilder = new StringBuilder();
		for (Map.Entry<Integer, List<Integer>> typeGroupOfProvincesEntry: idProvinceListByType.entrySet()) {
			String everyProvinceTemplateClon = new String(everyProvinceTemplate);
			StringBuilder provincesBuilder = new StringBuilder();
			for (Integer provinceId: typeGroupOfProvincesEntry.getValue()) {
				String provinceTemplateClon = new String(provinceTemplate);
				provincesBuilder.append(provinceTemplateClon.replaceAll(TK_PROVINCE, provinceId.toString()))
					.append("\n");
			}
			everyProvinceBuilder.append(everyProvinceTemplateClon
					.replaceAll(TK_PROVINCES, provincesBuilder.toString())
					.replaceAll(TK_PROVINCE_TYPE_ID, typeGroupOfProvincesEntry.getKey().toString()))
				.append("\n");
		}
		String output = baseTextTemplate.replaceAll(TK_EVERY_PROVINCE_TOTAL, everyProvinceBuilder.toString());
		//io.writeToFile(output, countryEventOutputPath);
		System.out.println(output);

	}
}
