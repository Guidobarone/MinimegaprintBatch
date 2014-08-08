package it.arakne.dbing.magento.xml.generator.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class ESKORoot {

	private static Map<String,ESKOTagBean> tagRootList = new LinkedHashMap<String,ESKOTagBean>();
	private static Map<String,ESKOTagBean> tagComponent1List = new LinkedHashMap<String,ESKOTagBean>();
	private static Map<String,ESKOTagBean> tagComponent2List = new LinkedHashMap<String,ESKOTagBean>();
	private static Map<String,ESKOTagBean> tagComponent3List = new LinkedHashMap<String,ESKOTagBean>();

	public static void init(){
		for (String tagName : ESKOTagList.getRootTagList()){
			ESKOTagBean tagBean = new ESKOTagBean(tagName, false);
			tagRootList.put(tagName, tagBean);
		}
		for (String innerTagName : ESKOTagList.getComponentTagList()){
			ESKOTagBean innerTagBean = new ESKOTagBean(innerTagName, false);
			tagComponent1List.put(innerTagName, innerTagBean);
			tagComponent2List.put(innerTagName, innerTagBean);
			tagComponent3List.put(innerTagName, innerTagBean);
		}
	}

	public static Map<String, ESKOTagBean> getTagRootList() {
		return tagRootList;
	}

	public static void setTagRootList(Map<String, ESKOTagBean> tagRootList) {
		ESKORoot.tagRootList = tagRootList;
	}

	public static Map<String, ESKOTagBean> getTagComponent1List() {
		return tagComponent1List;
	}

	public static void setTagComponent1List(
			Map<String, ESKOTagBean> tagComponent1List) {
		ESKORoot.tagComponent1List = tagComponent1List;
	}

	public static Map<String, ESKOTagBean> getTagComponent2List() {
		return tagComponent2List;
	}

	public static void setTagComponent2List(
			Map<String, ESKOTagBean> tagComponent2List) {
		ESKORoot.tagComponent2List = tagComponent2List;
	}

	public static Map<String, ESKOTagBean> getTagComponent3List() {
		return tagComponent3List;
	}

	public static void setTagComponent3List(
			Map<String, ESKOTagBean> tagComponent3List) {
		ESKORoot.tagComponent3List = tagComponent3List;
	}

}
