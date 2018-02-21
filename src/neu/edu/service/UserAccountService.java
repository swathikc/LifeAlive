package neu.edu.service;

import java.security.MessageDigest;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.dao.DAO;
import neu.edu.entity.CardDetails;
import neu.edu.entity.Donation;
import neu.edu.entity.Projects;
import neu.edu.entity.Report;
import neu.edu.entity.UserAccount;
import neu.edu.model.CategoriesModel;
import neu.edu.model.DonationModel;
import neu.edu.model.ProjectsModel;
import neu.edu.model.UserAccountModel;
import neu.edu.model.CardDetailsModel;
import neu.edu.model.CardNDonation;

@Service
public class UserAccountService {
	
	@Autowired
	DAO dao;
	
	public UserAccountModel validateUser(String username, String password)
	{
		//password hash

        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(passwordToHash.getBytes());
	            //Get the hash's bytes 
	            byte[] bytes = md.digest();
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
        //end of password hash
		UserAccount userAccountTable = dao.validateUser(username, generatedPassword);
		UserAccountModel user = new UserAccountModel();
		
		if(userAccountTable != null)
		{
			user.setId(userAccountTable.getId());
			user.setFirstName(userAccountTable.getFirstName());
			user.setLastName(userAccountTable.getLastName());
			user.setUsername(userAccountTable.getUsername());
			user.setPassword(userAccountTable.getPassword());
			user.setDob(userAccountTable.getDob());
			user.setEmailId(userAccountTable.getEmailId());
			user.setPhoneNo(userAccountTable.getPhoneNo());
			user.setRole(userAccountTable.getRole());
			
			return user;
		}
		return null;
	}
	
	public Boolean registerUser(UserAccountModel userAccountModel)
	{
		String firstName = userAccountModel.getFirstName();
		String lastName = userAccountModel.getLastName();
		String username = userAccountModel.getUsername();
		String password = userAccountModel.getPassword();
		//password hash

        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(passwordToHash.getBytes());
	            //Get the hash's bytes 
	            byte[] bytes = md.digest();
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
        //end of password hash
        String country = userAccountModel.getCountry();
		String dob = userAccountModel.getDob();
		String emailId = userAccountModel.getEmailId();
		String phoneNo = userAccountModel.getPhoneNo();
		
		UserAccount userAccount = dao.userExistsCheck(username);
		if(userAccount == null)
		{
			UserAccount newUserAccount = new UserAccount();
			newUserAccount.setFirstName(firstName);
			newUserAccount.setLastName(lastName);
			newUserAccount.setUsername(username);
			newUserAccount.setPassword(generatedPassword);
			newUserAccount.setDob(dob);
			newUserAccount.setCountry(country);
			newUserAccount.setEmailId(emailId);
			newUserAccount.setPhoneNo(phoneNo);
			newUserAccount.setRole("user");
			dao.registerUser(newUserAccount);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void addCardDetailsDonation(CardNDonation cardNDonation)
	{
		CardDetails cardDetailsTable = new CardDetails();
		cardDetailsTable.setCardType(cardNDonation.getCardType());
		cardDetailsTable.setCardNo(cardNDonation.getCardNo());
		cardDetailsTable.setNameOnCard(cardNDonation.getNameOnCard());
		cardDetailsTable.setCvc(cardNDonation.getCvc());
		cardDetailsTable.setExpiryDate(cardNDonation.getExpDate());
		cardDetailsTable.setUserAccount(dao.getUserAccount(cardNDonation.getUserId()));
		CardDetails card = dao.getCardDetails(cardNDonation.getCardNo());
		System.out.println("Card :"+card);
		if(card == null)
		{
			dao.addCardDetails(cardDetailsTable);
		}
		
		Donation donationTable = new Donation();
		donationTable.setProjects(dao.getProject(cardNDonation.getProjectId()));
		UserAccount userAccount = dao.getUserAccount(cardNDonation.getUserId());
		donationTable.setUserAccount(userAccount);
		donationTable.setAmount(cardNDonation.getAmount());
		donationTable.setDateOfPayment(cardNDonation.getDateOfPayment());
		donationTable.setComment(cardNDonation.getComment());
		dao.addDonation(donationTable);
		
		Report report = dao.getCountryReport(userAccount.getCountry());
		if(report != null)
		{
			System.out.println("report :"+report);
			int count = report.getCount();
			System.out.println("count :"+count);
			int totalAmt = count+Integer.parseInt(cardNDonation.getAmount());
			System.out.println("totalAmt :"+totalAmt);
			System.out.println("userAccount.getCountry() :"+userAccount.getCountry());
			Report newReport = report;
			newReport.setCount(totalAmt);
			dao.addReport(newReport);
		}
		else
		{
			Report newReport = new Report();
			newReport.setCountry(userAccount.getCountry());
			newReport.setCount(Integer.parseInt(cardNDonation.getAmount()));
			dao.addReport(newReport);
		}
		
		Projects project = new Projects();
		project.setAmountRecieved(cardNDonation.getAmount());
		Projects oldProject = dao.getProject(cardNDonation.getProjectId());
		String oldAmt = oldProject.getAmountRecieved();
		int oldAmount = Integer.parseInt(oldAmt);
		int amount = Integer.parseInt(cardNDonation.getAmount());
		int newAmount = oldAmount+amount;
		String newAmt = Integer.toString(newAmount);
		Projects newProject = new Projects();
		newProject.setAmountRecieved(newAmt);
		newProject.setDonationCount(oldProject.getDonationCount()+1);
		dao.updateProject(newAmt,cardNDonation.getProjectId());
		dao.updateProjectDonationCount(oldProject.getDonationCount()+1,cardNDonation.getProjectId());
		
	}
	
	public boolean serviceLimit(int userId, int projectId)
	{
		long count = dao.serviceLimit(userId, projectId);
		System.out.println("ServiceLimit count :"+count);
		if(count>=2)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public void addCardDetails(CardDetailsModel cardDetails)
	{
		CardDetails cardDetailsTable = new CardDetails();
		cardDetailsTable.setCardType(cardDetails.getCardType());
		cardDetailsTable.setCardNo(cardDetails.getCardNo());
		cardDetailsTable.setNameOnCard(cardDetails.getNameOnCard());
		cardDetailsTable.setCvc(cardDetails.getCvc());
		cardDetailsTable.setExpiryDate(cardDetails.getExpDate());
		cardDetailsTable.setUserAccount(dao.getUserAccount(cardDetails.getUserId()));
		CardDetails card = dao.getCardDetails(cardDetails.getCardNo());
		System.out.println("Card :"+card);
		if(card == null)
		{
			dao.addCardDetails(cardDetailsTable);
		}
		
	}
	
	public void donateTime(DonationModel donationModel)
	{
		Donation donationTable = new Donation();
		donationTable.setProjects(dao.getProject(donationModel.getProjectId()));
		donationTable.setUserAccount(dao.getUserAccount(donationModel.getUserId()));
		donationTable.setAmount(donationModel.getAmount());
		donationTable.setDateOfPayment(donationModel.getDateOfPayment());
		donationTable.setComment(donationModel.getComment());
		dao.addDonation(donationTable);
		
		Projects project = new Projects();
		Projects oldProject = dao.getProject(donationModel.getProjectId());
		String oldAmt = oldProject.getAmountRecieved();
		int oldAmount = Integer.parseInt(oldAmt);
		int amount = Integer.parseInt(donationModel.getAmount());
		int newAmount = oldAmount+amount;
		String newAmt = Integer.toString(newAmount);
		
		project.setAmountRecieved(newAmt);
		project.setDonationCount(oldProject.getDonationCount()+1);
		dao.updateProject(newAmt,donationModel.getProjectId());
		dao.updateProjectDonationCount(oldProject.getDonationCount()+1,donationModel.getProjectId());
		
	}
	
	public ArrayList<DonationModel> getDonations(int id)
	{
		ArrayList<Donation> donationsTable = dao.getDonations(id);
		ArrayList<DonationModel> donations = new ArrayList<>();
		for(Donation donationTable : donationsTable)
		{
			DonationModel donation = new DonationModel();
			donation.setId(donationTable.getId());
			donation.setUserId(donationTable.getUserAccount().getId());
			donation.setProjectId(donationTable.getProjects().getId());
			donation.setAmount(donationTable.getAmount());
			donation.setDateOfPayment(donationTable.getDateOfPayment());
			donation.setComment(donationTable.getComment());
			ProjectsModel projectsModel = donation.getProjectsModel();
			System.out.println("donationTable.getProjects().getProjectName() :"+donationTable.getProjects().getProjectName());
			projectsModel.setProjectName(donationTable.getProjects().getProjectName());
			CategoriesModel category = projectsModel.getCategoriesModel();
			category.setCategoryName(donationTable.getProjects().getCategories().getCategoryName());
			donations.add(donation);
		}
		return donations;
	}
	
	public ArrayList<DonationModel> getDonationsforProjectId(int id)
	{
		ArrayList<Donation> donationsTable = dao.getDonationsforProjectId(id);
		ArrayList<DonationModel> donations = new ArrayList<>();
		for(Donation donationTable : donationsTable)
		{
			DonationModel donation = new DonationModel();
			donation.setId(donationTable.getId());
			donation.setUserId(donationTable.getUserAccount().getId());
			UserAccountModel userAccountModel = donation.getUserAccountModel();
			userAccountModel.setFirstName(donationTable.getUserAccount().getFirstName());
			userAccountModel.setLastName(donationTable.getUserAccount().getLastName());
			donation.setUserAccountModel(userAccountModel);
			donation.setProjectId(donationTable.getProjects().getId());
			donation.setAmount(donationTable.getAmount());
			donation.setDateOfPayment(donationTable.getDateOfPayment());
			donation.setComment(donationTable.getComment());
			ProjectsModel projectsModel = donation.getProjectsModel();
			System.out.println("donationTable.getProjects().getProjectName() :"+donationTable.getProjects().getProjectName());
			projectsModel.setProjectName(donationTable.getProjects().getProjectName());
			CategoriesModel category = projectsModel.getCategoriesModel();
			category.setCategoryName(donationTable.getProjects().getCategories().getCategoryName());
			donations.add(donation);
		}
		return donations;
	}
}
