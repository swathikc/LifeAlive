package neu.edu.model;

import neu.edu.entity.Projects;

public class CartModel {
	private int id;
	private int userId;
	private int projectId;
	private ProjectsModel projectsModel;
	private UserAccountModel userAccountModel;
	private int amount;
	private String status;
	private String comment;
	private String totalAmount;
	
	public CartModel()
	{
		projectsModel = new ProjectsModel();
		userAccountModel = new UserAccountModel();
	}
	public int getId() {
		return id;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public UserAccountModel getUserAccountModel() {
		return userAccountModel;
	}
	public void setUserAccountModel(UserAccountModel userAccountModel) {
		this.userAccountModel = userAccountModel;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public ProjectsModel getProjectsModel() {
		return projectsModel;
	}
	public void setProjectsModel(ProjectsModel projectsModel) {
		this.projectsModel = projectsModel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
