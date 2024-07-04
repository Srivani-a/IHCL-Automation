package com.ihclweb.automation.generator;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster._shade.org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Generator {
	
	private static final String BASEPACKAGE_DIRS = "src/main/java/com/ihclweb/automation";
	private static final String BASEPACKAGE_DOT = "com.ihclweb.automation";
	private static final String WEBELEMENT_QUALIFIED_NAME = WebElement.class.getName();
	private static final String FINDBY_QUALIFIED_NAME = FindBy.class.getName();
	private static final char DOT = '.';

	private static final String MODULE = "module";
	private static final String MODEL = "model";
	private static final String JAVA = "java";
	private static final String NAME = "name";
	
	private static final String KEY = "key";
	private static final String VALUE = "value";
	private static final String TYPE = "type";
	private static final String ELEMENTS = "elements";
	private static final String SEMI_COLON = ";";
	
	public static void main(String[] args) throws Exception {
		
		File modelsFolder = new File("models");
		File[] files = modelsFolder.listFiles();
		for (File file : files) {
			JSONObject obj = null;
			obj = (JSONObject) new JSONParser().parse(new FileReader(file));
			if (obj == null) { continue; }
			String pkg = ((String)obj.get(MODULE)).toLowerCase();
			String modulePackage = BASEPACKAGE_DOT + DOT + pkg + DOT + MODEL;
			String modulePackageFolder = BASEPACKAGE_DIRS + File.separator + pkg + File.separator + MODEL;
			JSONArray clses = (JSONArray) obj.get("models");
			final JavaClassSource moduleClass = Roaster.create(JavaClassSource.class);
			final String moduleClassName = (pkg.charAt(0) + StringUtils.EMPTY).toUpperCase() + ((String)obj.get(MODULE)).substring(1);
			moduleClass.setPackage(modulePackage).setName(moduleClassName);

			String methodCode = "public void init(WebDriver driver) {";
			for (int i = 0; i < clses.size(); i++) {
				{
					File folder = new File(modulePackageFolder);
					folder.mkdirs();
				}
				JSONObject clssObj = (JSONObject) clses.get(i);
				String cls = (String) clssObj.get(NAME);
				String classFile = modulePackageFolder + File.separator + cls + DOT + JAVA;
				final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
				javaClass.setPackage(modulePackage).setName(cls);

				moduleClass.addImport(modulePackage + DOT + cls);
				javaClass.addImport(WEBELEMENT_QUALIFIED_NAME);
				javaClass.addImport(FINDBY_QUALIFIED_NAME);

				JSONArray elements = (JSONArray) clssObj.get(ELEMENTS);
				for (int j = 0; j < elements.size(); j++) {
					JSONObject element = (JSONObject) elements.get(j);
					String key = (String) element.get(KEY);
					String value = (String) element.get(VALUE);
					String type = (String) element.get(TYPE);
					javaClass.addField().setType(WebElement.class).setName(key).setPrivate().setStatic(true).addAnnotation(FindBy.class).setStringValue(type.toLowerCase(), value);
					genSettersGetters(javaClass, javaClass.getField(key));
				}
				//javaClass.addMethod().setConstructor(true).setPrivate().setBody(StringUtils.EMPTY);
				String variableName = (cls.charAt(0) + StringUtils.EMPTY).toLowerCase() + cls.substring(1);
				moduleClass.addProperty(cls, variableName).setAccessible(true).getField().setPublic();
				methodCode = methodCode + StringUtils.EMPTY + variableName + " = PageFactory.initElements(driver, " + cls + ".class);";
				Files.write(Paths.get(classFile), javaClass.toString().getBytes());
			}
			methodCode = methodCode + "}";
			moduleClass.addMethod(methodCode);
			moduleClass.addImport(PageFactory.class);
			moduleClass.addImport(WebDriver.class);
			Files.write(Paths.get(modulePackageFolder + File.separator + moduleClassName + DOT + JAVA), moduleClass.toString().getBytes());
		}
	}
	
	private static void genSettersGetters(JavaClassSource clazz, FieldSource<JavaClassSource> field){
		 if (!clazz.hasField(field))
		        throw new IllegalArgumentException("Entity did not contain the given field [" + field + "]");
	      clazz.getMethods();
	      String fieldName = field.getName();
	      String methodNameSuffix = StringUtils.capitalize(fieldName);
	      clazz.addMethod().setPublic().setStatic(true).setReturnType(field.getType().toString()).setName("get" + methodNameSuffix)
	      .setBody("return " + fieldName + SEMI_COLON);
	      if (!field.isFinal())
	      {
	        clazz.addMethod().setPublic().setStatic(true).setReturnTypeVoid().setStatic(true).setName("set" + methodNameSuffix)
	          .setParameters("final " + field.getType().toString() + " " + fieldName)
	          .setBody(clazz.getName()+"." + fieldName + " = " + fieldName + ";");
	      }
	}
}
