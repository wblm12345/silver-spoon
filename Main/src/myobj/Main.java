package myobj;
import java.util.*;
/**
 * @author linxingde
 */
public class Main {
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int n = cin.nextInt();
		int m = cin.nextInt();
		int q = cin.nextInt();
		ArrayList<Role> roleList = new ArrayList<Role>();
		ArrayList<RoleRelevance> roleRelevanceList = new ArrayList<RoleRelevance>();
		User user = new User();
		for(int i = 0; i < n; ++i) {
			Role role = new Role();
			role.setName(cin.next());
			int nv = cin.nextInt();
			for(int j = 0; j < nv; ++j) {
				role.getOperationList().add(cin.next());
			}
			int no = cin.nextInt();
			for(int j = 0; j < no; ++j) {
				role.getResourceKindList().add(cin.next());
			}
			int nn = cin.nextInt();
			for(int j = 0; j < nn; ++j) {
				role.getResourceNameList().add(cin.next());
			}
			roleList.add(role);
		}
		for(int i = 0; i < m; ++i) {
			RoleRelevance roleRelevance = new RoleRelevance();
			roleRelevance.setRoleName(cin.next());
			int ns = cin.nextInt();
			for(int j = 0; j < ns; ++j) {
				String firstStr = cin.next();
				String nextStr = cin.next();
				if("u".equals(firstStr)) {
					roleRelevance.getAuthorizationObjectName().add(nextStr);
				}
				else if("g".equals(firstStr)) {
					roleRelevance.getAuthorizationObjectNameGroup().add(nextStr);
				}
			}
			roleRelevanceList.add(roleRelevance);
		}
		for(int i = 0; i < q; ++i) {
			user.setName(cin.next());
			int ng = cin.nextInt();
			for(int j = 0; j < ng; ++j) {
				user.getUserGroup().add(cin.next());
			}
			String operator = cin.next();
			String resourceKind = cin.next();
			String resourceName = cin.next();
			if(user.canDoOperation(roleList,
					user.getRelevanceRole(roleRelevanceList), operator, resourceKind, resourceName)) {
				System.out.println(1);
			}
			else {
				System.out.println(0);
			}
		}
		cin.close();
	}	
};

class User{
	private String name;
	private ArrayList<String> userGroup;
	User(){
		userGroup = new ArrayList<String>();
	}
	public String getName() {
		return name;
	}
	public ArrayList<String> getRelevanceRole(ArrayList<RoleRelevance> roleRelevanceList) {
		ArrayList<String> relevanceRoleList = new ArrayList<String>();
		for(RoleRelevance roleRelevance : roleRelevanceList) {
			if(roleRelevance.getAuthorizationObjectName().contains(name)) {
				relevanceRoleList.add(roleRelevance.getRoleName());
				continue;
			}
			for(String strUserGroup : userGroup) {
				if(roleRelevance.getAuthorizationObjectNameGroup().contains(strUserGroup)) {
					relevanceRoleList.add(roleRelevance.getRoleName());
					break;
				}
			}
		}
		return relevanceRoleList;
	}
	public boolean canDoOperation(ArrayList<Role> roleList, ArrayList<String> relevanceRoleList,
			String operation, String resourceKind, String resourceName) {
		for(Role role : roleList) {
			for(String relevance : relevanceRoleList) {
				if(role.getName().equals(relevance) && 
						role.canDoOperation(operation, resourceKind, resourceName)) {
					return true;
				}
			}
		}
		return false;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getUserGroup(){
		return userGroup;
	}
}
class Role{
	private String name;
	private ArrayList<String> operationList;
	private ArrayList<String> resourceKindList;
	private ArrayList<String> resourceNameList;
	Role(){
		operationList = new ArrayList<String>();
		resourceKindList = new ArrayList<String>();
		resourceNameList = new ArrayList<String>();
	}
	public boolean canDoOperation(String operation, String resourceKind, String resourceName) {
		if(!operationList.contains(operation) && !operationList.contains("*")) {
			return false;
		}
		if(!resourceKindList.contains(resourceKind) && !resourceKindList.contains("*")) {
			return false;
		}
		if(!resourceNameList.contains(resourceName) && !resourceNameList.isEmpty()) {
			return false;
		}
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getOperationList(){
		return operationList;
	}
	public ArrayList<String> getResourceKindList(){
		return resourceKindList;
	}
	public ArrayList<String> getResourceNameList(){
		return resourceNameList;
	}
}
class RoleRelevance{
	private String roleName;
	private ArrayList<String> authorizationObjectName;
	private ArrayList<String> authorizationObjectNameGroup;
	RoleRelevance(){
		authorizationObjectName = new ArrayList<String>();
		authorizationObjectNameGroup = new ArrayList<String>();
	}
	public ArrayList<String> getAuthorizationObjectName(){
		return authorizationObjectName;
	}
	public ArrayList<String> getAuthorizationObjectNameGroup(){
		return authorizationObjectNameGroup;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String name) {
		this.roleName = name;
	}
}