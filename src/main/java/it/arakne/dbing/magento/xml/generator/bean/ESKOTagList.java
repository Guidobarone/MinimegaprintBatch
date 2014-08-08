package it.arakne.dbing.magento.xml.generator.bean;

public class ESKOTagList {
	
	private static String[] _rootTagList = {"Workflow","Product","OrderId","ItemId","Quantity","Title","Priority"};//,"Component1","Component2","Component3"};
	private static String[] _componentTagList = {"Type","Press","Width","Height","Material","Grammage","Foil","CutPath","Duplex"
		,"NumberOfPages","FrontFile","BackFile","BlackOnly","NumberOfColors","Sleeve","Reinforcements","Eyelet"};

	public static String[] getRootTagList() {
		return _rootTagList;
	}

	public static String[] getComponentTagList() {
		return _componentTagList;
	}

}
