package neu.edu.model;

public class ProjectNCardDetails {

	private int id;
	private String projectName;
	private int creatorId;
	private String createdOn;
	private CategoriesModel categoriesModel;
	private String creatorName;
	private int categoryId;
	private String description;
	private String amountRequired;
	private String amountRecieved;
	private String deadline;
	private String status;
	private String cardType;
	private String nameOnCard;
	private String cardNo;
	private String cvc;
	private String expDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public CategoriesModel getCategoriesModel() {
		return categoriesModel;
	}
	public void setCategoriesModel(CategoriesModel categoriesModel) {
		this.categoriesModel = categoriesModel;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAmountRequired() {
		return amountRequired;
	}
	public void setAmountRequired(String amountRequired) {
		this.amountRequired = amountRequired;
	}
	public String getAmountRecieved() {
		return amountRecieved;
	}
	public void setAmountRecieved(String amountRecieved) {
		this.amountRecieved = amountRecieved;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	
	
}
