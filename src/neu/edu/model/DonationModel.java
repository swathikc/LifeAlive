package neu.edu.model;


public class DonationModel {

	private int id;
	private int userId;
	private UserAccountModel userAccountModel;
	private ProjectsModel projectsModel;
	private int projectId;
	private String amount;
	private String dateOfPayment;
	private String comment;
	
	public DonationModel()
	{
		projectsModel = new ProjectsModel();
		userAccountModel = new UserAccountModel();
	}
	
	public ProjectsModel getProjectsModel() {
		return projectsModel;
	}
	public UserAccountModel getUserAccountModel() {
		return userAccountModel;
	}
	public void setUserAccountModel(UserAccountModel userAccountModel) {
		this.userAccountModel = userAccountModel;
	}
	public void setProjectsModel(ProjectsModel projectsModel) {
		this.projectsModel = projectsModel;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
