package es.asegnz.eu4.modding.tools.general;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.asegnz.eu4.modding.tools.general.common.Constants;

public class OrderTransform {

	// Declare tokens in order to be replace later
	public static final String TK_ID = "TK_ID";
	
	public static final String TK_FLAG_ID = "TK_FLAG_ID";

	public static final String TK_COUNTRY_FLAG = "TK_COUNTRY_FLAG_REPEAT_N";
	
	public static final String TK_200_PLUS_ID = "TK_200_PLUS_ID";
	
	// Declare pathfiles from the resources folder
	public static final String baseOrderTemplatePath = "es/asegnz/eu4/modding/tools/text/baseOrderTemplate.txt";

	public static final String countryFlagTemplatePath = "es/asegnz/eu4/modding/tools/text/countryFlagTemplate.txt";
	
	public static int INIT_VARIABLE = 200;
	

	public static Map<Integer, List<Integer>> idProvinceListByType = new HashMap<Integer, List<Integer>>();

	public static void main(String[] args) {
		IOUtil io = new IOUtil();
		String baseOrderTemplate = io.readAllFileFromRelativePath(baseOrderTemplatePath);
		String countryFlagTemplate = io.readAllFileFromRelativePath(countryFlagTemplatePath);
		StringBuilder baseOrderBuilder = new StringBuilder();
		for (int i = 1; i < Constants.N; i++) {
			String baseOrderClon = new String(baseOrderTemplate);
			StringBuilder countryFlagBuilder = new StringBuilder();
			for (int j = 1; j <= i; j++) {
				String countryFlagClon = new String(countryFlagTemplate);
				countryFlagBuilder.append(countryFlagClon.replaceAll(TK_FLAG_ID, String.valueOf(j)))
				.append("\n");
			}
			baseOrderBuilder.append(baseOrderClon
					.replaceAll(TK_ID, String.valueOf(i))
					.replaceAll(TK_200_PLUS_ID, String.valueOf(i+INIT_VARIABLE))
					.replaceAll(TK_COUNTRY_FLAG, countryFlagBuilder.toString()))
				.append("\n");
		}
		String output = baseOrderBuilder.toString();
		//io.writeToFile(output, countryEventOutputPath);
		System.out.println(output);

	}
}
