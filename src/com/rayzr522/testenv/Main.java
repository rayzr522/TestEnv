
package com.rayzr522.testenv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Strings;

public class Main {

	private File					file;
	private HashMap<String, String>	props	= new HashMap<String, String>();

	public static final File		FOLDER	= new File("/Users/PeterBlood/Desktop/Temp/");

	public Main() {

		log("Loading file...");
		file = new File(getClass().getResource("/test.txt").getPath());
		log("File loaded @ " + file.getAbsolutePath());

		log(Strings.repeat("-", 200));

		PlayerData data = new PlayerData();

		data.setName("Rayzr");
		data.setLevel(15);
		data.setMoney(150.32);

		SkillData skillData = new SkillData();
		skillData.setSkillMining(3);

		data.setSkillData(skillData);

		Map<String, Object> map = serialize(data);

		File configFile = new File(FOLDER, "playerData.yml");

		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				System.err.println("Failed to load file");
				e.printStackTrace();
			}
		}

		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

		System.out.println(deserialize(PlayerData.class, convertToMap(config.getConfigurationSection("data"))));

		config.set("data", map);

		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Map<String, Object> convertToMap(ConfigurationSection section) {

		Map<String, Object> map = new HashMap<String, Object>();

		for (String key : section.getKeys(false)) {

			if (section.isConfigurationSection(key)) {
				map.put(key, convertToMap(section.getConfigurationSection(key)));
			} else {
				map.put(key, section.get(key));
			}

		}

		return map;

	}

	public void run() throws Exception {

		if (!file.exists()) {
			err("File doesn't exist!");
		}

		BufferedReader reader = new BufferedReader(new FileReader(file));

		log("Reading lines:");

		reader.lines().forEach(line -> {

			if (line != null && !(line.startsWith("#") || line.startsWith("//"))) {

				if (!line.contains(":") || line.split(":").length <= 1) {

					err("Invalid config line: '" + line + "'");

				}

				props.put(line.split(":")[0].trim(), line.split(":")[1].trim());

			}

		});

		props.forEach((key, value) -> {
			log(key + ": " + value);
		});

		reader.close();

		return;
	}

	@SuppressWarnings("unchecked")
	public static Object deserialize(Class<? extends Object> clazz, Map<String, Object> data) {

		if (!Serializable.class.isAssignableFrom(clazz)) {
			System.err.println("Attempted to deserialize to a non-serializable class!!");
			return null;
		}

		Object o;
		try {
			o = clazz.newInstance();
		} catch (Exception e) {
			System.err.println("Could not instantiate an object of type '" + clazz.getCanonicalName() + "'");
			System.err.println("Classes implementing Serializable should not have a constructor, instead they should use onDeserialized.");
			e.printStackTrace();
			return null;
		}

		List<Field> fields = getFields(o.getClass(), Serialized.class);;

		for (Field field : fields) {
			if (!data.containsKey(field.getName())) {
				continue;
			}
			if (Serializable.class.isAssignableFrom(field.getType())) {
				if (!(data.get(field.getName()) instanceof Map<?, ?>)) {
					System.err.println("Expected a Map for field '" + field.getName() + "' in '" + clazz.getCanonicalName() + "', however an instance of '" + data.get(field.getName()).getClass().getCanonicalName() + "' was found!");
					return null;
				}
				setValue(field, o, deserialize(field.getType(), (Map<String, Object>) data.get(field.getName())));
			} else {
				setValue(field, o, data.get(field.getName()));
			}

		}

		return o;

	}

	public static Map<String, Object> serialize(Object o) {

		if (!Serializable.class.isAssignableFrom(o.getClass())) {
			System.err.println("Attempted to serialize a class that does not implement Serializable!");
			return null;
		}

		List<Field> fields = getFields(o.getClass(), Serialized.class);

		Map<String, Object> map = new HashMap<String, Object>();

		for (Field field : fields) {

			try {

				// Save the state of the field
				boolean accessible = field.isAccessible();
				field.setAccessible(true);

				// Check if it's another Serializable
				if (Serializable.class.isAssignableFrom(field.getType())) {
					map.put(field.getName(), serialize(field.get(o)));
				} else {
					map.put(field.getName(), field.get(o));
				}

				field.setAccessible(accessible);

			} catch (IllegalArgumentException e) {

				e.printStackTrace();

			} catch (IllegalAccessException e) {

				e.printStackTrace();

			}

		}

		return map;

	}

	public static List<Field> getFields(Class<? extends Object> clazz, Class<? extends Annotation> annotation) {

		List<Field> fields = new ArrayList<>();

		Class<? extends Object> clazz2 = clazz;

		while (clazz2 != null && Object.class.isAssignableFrom(clazz2)) {

			for (Field field : clazz2.getDeclaredFields()) {

				if (field.isAnnotationPresent(annotation)) {

					fields.add(field);

				}

			}

			clazz2 = clazz2.getSuperclass();

		}

		return fields;

	}

	public static void setValue(Field f, Object o, Object v) {

		try {
			boolean accessible = f.isAccessible();
			f.setAccessible(true);
			f.set(o, v);
			f.setAccessible(accessible);
		} catch (Exception e) {
			System.err.println("Failed to set value of field '" + f.getName() + "' in class '" + o.getClass().getCanonicalName() + "'");
			e.printStackTrace();
		}

	}

	public static void err(Object obj) {
		System.err.println(obj);
		System.exit(1);
	}

	private static void log(Object obj) {
		System.out.println(obj);
	}

	public static void main(String[] args) {

		log("Creating application");
		Main app = new Main();
		log(Strings.repeat("-", 200));
		log("Creating and starting thread");

		new Thread(() -> {
			try {
				app.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		log("Thread started");

	}

}
