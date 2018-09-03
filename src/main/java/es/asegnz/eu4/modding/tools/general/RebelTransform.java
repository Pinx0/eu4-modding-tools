package es.asegnz.eu4.modding.tools.general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.asegnz.eu4.modding.tools.general.common.Constants;

public class RebelTransform {

	// Declare tokens in order to be replace later
	public static final String TK_ID = "TK_ID";
	
	// Declare pathfiles from the resources folder
	public static final String baseRebelTemplatePath = "es/asegnz/eu4/modding/tools/text/baseRebelTemplate.txt";

	public static Map<Integer, List<Integer>> idProvinceListByType = new HashMap<Integer, List<Integer>>();

	public static void main(String[] args) {
		IOUtil io = new IOUtil();
		String baseRebelTemplate = io.readAllFileFromRelativePath(baseRebelTemplatePath);
		
		StringBuilder rebelBuilder = new StringBuilder();
		for (int i = 1; i < Constants.N; i++) {
			String clon = new String(baseRebelTemplate);
			rebelBuilder.append(clon.replaceAll(TK_ID, String.valueOf(i))).append("\n");
		}
		
		String output = rebelBuilder.toString();
		//io.writeToFile(output, countryEventOutputPath);
		System.out.println(output);

	}
}
