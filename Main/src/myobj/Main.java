package myobj;
import java.util.*;
/**
 * @author linxingde
 */
public class Main {
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int t = cin.nextInt();
		cin.nextLine();
		FileRoot system = new FileRoot();
		while(t-- != 0) {
			String s = cin.nextLine();
			String[] operator = s.split(" ");
			if("C".equals(operator[0])) {
				if(system.createFile(operator[1].split("/"), Long.parseLong(operator[2]))) {
					System.out.println("Y");
				}
				else {
					System.out.println("N");
				}
			}
			else if("R".equals(operator[0])) {
				system.removeFile(operator[1].split("/"));
				System.out.println("Y");
			}
			else if("Q".equals(operator[0])) {
				if(system.setQuota(operator[1].split("/"), Long.parseLong(operator[2]),
						Long.parseLong(operator[3]))) {
					System.out.println("Y");
				}
				else {
					System.out.println("N");
				}
			}
		}
		cin.close();
	}	
};

class FileNode{
	boolean type;
	long size;
	long catalogQuota, usedCatalogQuota;
	long unbornQuota, usedUnbornQuota;
	HashMap<String, FileNode> unborn;
	FileNode() {
		this.usedCatalogQuota = 0;
		this.usedUnbornQuota = 0;
		this.catalogQuota = 0;
		this.unbornQuota = 0;
		this.size = 0;
		this.type = false;
		this.unborn = new HashMap<>();
	}
	FileNode(long size) {
		this.usedCatalogQuota = 0;
		this.usedUnbornQuota = 0;
		this.catalogQuota = 0;
		this.unbornQuota = 0;
		this.type = true;
		this.size = size;
		this.unborn = new HashMap<>();
	}
 }
class FileRoot{
	FileNode root;
	FileRoot(){
		root = new FileNode();
	}
	public boolean createFile(String[] path, long size) {
		if(path.length == 0) {
			return false;
		}
		FileNode nowNode = root;
		int i;
		long beReplaceSize = size - isReplace(path);
		for(i = 1; i < path.length - 1; ++i) {
			if(nowNode.unborn.containsKey(path[i])) {
				if(nowNode.unborn.get(path[i]).type == true) {
					return false;
				}
				else {
					if(checkUnbornQuota(nowNode, beReplaceSize)){
						nowNode = nowNode.unborn.get(path[i]);
					}
					else {
						return false;
					}
				}
			}
			else {
				if(checkUnbornQuota(nowNode, size)) {
					nowNode.unborn.put(path[i], new FileNode());
					nowNode = nowNode.unborn.get(path[i]);
				}
				else {
					return false;
				}
			}
		}
		if(!nowNode.unborn.containsKey(path[i])) {
			if(checkQuota(nowNode, size) && checkUnbornQuota(nowNode, size)) {
				nowNode.unborn.put(path[i], new FileNode(size));
				nowNode.usedCatalogQuota += size;
			}
			else {
				return false;
			}
		}
		else {
			if(nowNode.unborn.get(path[i]).type == false) {
				return false;
			}
			else {
				if(canReplace(nowNode, nowNode.unborn.get(path[i]), size)) {
					nowNode.usedCatalogQuota += beReplaceSize;
					nowNode.unborn.get(path[i]).size = size;
				}
				else {
					return false;
				}
			}
		}
		nowNode = root;
		for(i = 1; i < path.length; ++i) {
			nowNode.usedUnbornQuota += beReplaceSize;
			nowNode = nowNode.unborn.get(path[i]);
		}
		return true;
	}
	public boolean removeFile(String[] path) {
		if(path.length == 0) {
			return true;
		}
		FileNode nowNode = root;
		int i;
		long toBeDeleteSize = 0;
		for(i = 1; i < path.length - 1; ++i) {
			if(nowNode.unborn.containsKey(path[i])) {
				nowNode = nowNode.unborn.get(path[i]);
			}
			else {
				return true;
			}
		}
		if(nowNode.unborn == null) {
			return true;
		}
		if(nowNode.unborn.containsKey(path[i])) {
			if(nowNode.unborn.get(path[i]).type) {
				toBeDeleteSize = nowNode.unborn.get(path[i]).size;
				nowNode.usedCatalogQuota -= toBeDeleteSize;
			}
			else {
				toBeDeleteSize = nowNode.unborn.get(path[i]).usedUnbornQuota;
			}
			nowNode.unborn.remove(path[i]);
		}
		else {
			return true;
		}
		nowNode = root;
		if(path.length == 1) {
			nowNode.usedUnbornQuota -= toBeDeleteSize;
			return true;
		}
		for(i = 1; i < path.length; ++i) {
			nowNode.usedUnbornQuota -= toBeDeleteSize;
			nowNode = nowNode.unborn.get(path[i]);
		}
		return true;
	}
	public boolean setQuota(String[] path, long cata, long unborn) {
		FileNode nowNode = root;
		int i;
		for(i = 1; i < path.length; ++i) {
			if(nowNode.unborn.containsKey(path[i]) 
					&& nowNode.unborn.get(path[i]).type == false) {
				nowNode = nowNode.unborn.get(path[i]);
			}
			else {
				return false;
			}
		}
		if(unborn != 0 && unborn < nowNode.usedUnbornQuota) {
			return false;
		}
		if(cata != 0 && cata < nowNode.usedCatalogQuota) {
			return false;
		}
		nowNode.unbornQuota = unborn;
		nowNode.catalogQuota = cata;
		return true;
	}
	boolean checkUnbornQuota(FileNode toCheck, long size) {
		if(toCheck.unbornQuota == 0) {
			return true;
		}
		else if(size <= toCheck.unbornQuota - toCheck.usedUnbornQuota) {
			return true;
		}
		return false;
	}
	boolean checkQuota(FileNode toCheck, long size) {
		if(toCheck.catalogQuota == 0) {
			return true;
		}
		else if(size <= toCheck.catalogQuota - toCheck.usedCatalogQuota) {
			return true;
		}
		return false;
	}
	boolean canReplace(FileNode toCheck, FileNode toBeReplace, long size) {
		if(toCheck.catalogQuota == 0) {
			return true;
		}
		else if(toCheck.catalogQuota - toCheck.usedCatalogQuota
				+ toBeReplace.size >= size) {
			return true;
		}
		return false;
	}
	long isReplace(String[] path) {
		FileNode nowNode = root;
		int i;
		for(i = 1; i < path.length - 1; ++i) {
			if(!nowNode.unborn.containsKey(path[i])) {
				return 0;
			}
			nowNode = nowNode.unborn.get(path[i]);
		}
		if(nowNode.unborn.containsKey(path[i]) && nowNode.unborn.get(path[i]).type) {
			return nowNode.unborn.get(path[i]).size;
		}
		return 0;
	}
}
