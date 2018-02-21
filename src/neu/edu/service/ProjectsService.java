package neu.edu.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.dao.DAO;
import neu.edu.entity.CardDetails;
import neu.edu.entity.Cart;
import neu.edu.entity.Donation;
import neu.edu.entity.Projects;
import neu.edu.entity.Report;
import neu.edu.model.CartModel;
import neu.edu.model.CategoriesModel;
import neu.edu.model.ProjectsModel;

@Service
public class ProjectsService {
	
	@Autowired
	DAO dao;
	
	public ArrayList<ProjectsModel> getAllProjects() throws ParseException
	{
		ArrayList<Projects> projectsTable = dao.getAllProjects();
		ArrayList<ProjectsModel> projects = new ArrayList<>();
//		Date date = new Date();
//		String stringDate = new SimpleDateFormat("MM-dd-yyyy").format(date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date today =  new Date();
	    System.out.println("Today :"+today);
		
		
		for(Projects projectTable : projectsTable)
		{
			//2017-05-31T04:00:00.000Z
		    Date parseDate =  df.parse(projectTable.getDeadline());
		    System.out.println("deadline date 1 :"+projectTable.getDeadline());
		    System.out.println("condition = "+(parseDate.after(today)));
//		    
		    float amountReceived = Float.parseFloat(projectTable.getAmountRecieved());
		    float amountRequired = Float.parseFloat(projectTable.getAmontRequired());
		    //parseDate.before(today) && 
		    System.out.println("condition = "+(parseDate.after(today) && (amountReceived < amountRequired)));

			if(parseDate.after(today) && (amountReceived < amountRequired))
			{
				ProjectsModel project = new ProjectsModel();
				project.setId(projectTable.getId());
				project.setProjectName(projectTable.getProjectName());
				project.setCreatedOn(projectTable.getCreatedOn());
				project.setCreatorId(projectTable.getUserAccount().getId());
				project.setCreatorName(projectTable.getUserAccount().getFirstName()+" "+projectTable.getUserAccount().getLastName());
				project.setCategoryId(projectTable.getCategories().getId());
				project.setDescription(projectTable.getDescription());
				project.setAmountRequired(projectTable.getAmontRequired());
				project.setAmountRecieved(projectTable.getAmountRecieved());
				project.setDeadline(projectTable.getDeadline());
				project.setStatus(projectTable.getStatus());
				project.setDonationCount(projectTable.getDonationCount());
				projects.add(project);
			}
			else
			{
				dao.updateProjectStatus("invalid", projectTable.getId());
			}
		}
		return projects;
	}
	
	public void addProject(ProjectsModel newProject)
	{
		Projects project = new Projects();
		project.setProjectName(newProject.getProjectName());
		project.setCreatedOn(newProject.getCreatedOn());
		project.setUserAccount(dao.getUserAccount(newProject.getCreatorId()));
		project.setCategories(dao.getCategories(newProject.getCategoryId()));
		project.setDescription(newProject.getDescription());
		project.setAmontRequired(newProject.getAmountRequired());
		project.setAmountRecieved("0");
		project.setDeadline(newProject.getDeadline());
		project.setStatus("pending");
		project.setDonationCount(0);
		dao.addProject(project);
	}
	
	public ArrayList<ProjectsModel> getProjects(int id)
	{
		ArrayList<Projects> projectsTable = dao.getProjects(id);
		ArrayList<ProjectsModel> projects = new ArrayList<>();
		for(Projects projectTable : projectsTable)
		{
			ProjectsModel project = new ProjectsModel();
			project.setId(projectTable.getId());
			project.setProjectName(projectTable.getProjectName());
			project.setCreatedOn(projectTable.getCreatedOn());
			project.setCreatorId(projectTable.getUserAccount().getId());
			project.setCreatorName(projectTable.getUserAccount().getFirstName()+" "+projectTable.getUserAccount().getLastName());
			project.setCategoryId(projectTable.getCategories().getId());
			project.setDescription(projectTable.getDescription());
			project.setAmountRequired(projectTable.getAmontRequired());
			project.setAmountRecieved(projectTable.getAmountRecieved());
			project.setDeadline(projectTable.getDeadline());
			project.setStatus(projectTable.getStatus());
			project.setDonationCount(projectTable.getDonationCount());
			projects.add(project);
		}
		return projects;
	}

	public ProjectsModel getProject(int id)
	{
		Projects projectTable = dao.getProject(id);
		ProjectsModel project = new ProjectsModel();
		project.setId(projectTable.getId());
		project.setProjectName(projectTable.getProjectName());
		project.setCreatedOn(projectTable.getCreatedOn());
		project.setCreatorId(projectTable.getUserAccount().getId());
		project.setCreatorName(projectTable.getUserAccount().getFirstName()+" "+projectTable.getUserAccount().getLastName());
		project.setCategoryId(projectTable.getCategories().getId());
		project.setDescription(projectTable.getDescription());
		project.setAmountRequired(projectTable.getAmontRequired());
		project.setAmountRecieved(projectTable.getAmountRecieved());
		project.setDeadline(projectTable.getDeadline());
		project.setStatus(projectTable.getStatus());
		project.setDonationCount(projectTable.getDonationCount());
		
		return project;
	}
	public ArrayList<ProjectsModel> getMyProjects(int id)
	{
		ArrayList<Projects> projectsTable = dao.getMyProjects(id);
		ArrayList<ProjectsModel> projects = new ArrayList<>();
		for(Projects projectTable : projectsTable)
		{
			ProjectsModel project = new ProjectsModel();
			project.setId(projectTable.getId());
			project.setProjectName(projectTable.getProjectName());
			project.setCreatedOn(projectTable.getCreatedOn());
			project.setCreatorId(projectTable.getUserAccount().getId());
			project.setCreatorName(projectTable.getUserAccount().getFirstName()+" "+projectTable.getUserAccount().getLastName());
			project.setCategoryId(projectTable.getCategories().getId());
			project.setDescription(projectTable.getDescription());
			project.setAmountRequired(projectTable.getAmontRequired());
			project.setAmountRecieved(projectTable.getAmountRecieved());
			project.setDeadline(projectTable.getDeadline());
			project.setStatus(projectTable.getStatus());
			project.setDonationCount(projectTable.getDonationCount());
			projects.add(project);
		}
		return projects;
	}
	
	public void approveProject(int id)
	{
		dao.updateProjectStatus("valid", id);
	}
	
	public void addToCart(CartModel cartDetails)
	{
		Cart cart = new Cart();
		cart.setProjects(dao.getProject(cartDetails.getProjectId()));
		cart.setUserAccount(dao.getUserAccount(cartDetails.getUserId()));
		cart.setAmount(cartDetails.getAmount());
		cart.setStatus(cartDetails.getStatus());
		cart.setComment(cartDetails.getComment());
		dao.addToCart(cart);
	}
	
	public ArrayList<CartModel> getCart(int userId)
	{
		ArrayList<CartModel> cartModelList = new ArrayList<>();
		ArrayList<Cart> cartList = dao.getCart(userId);
		int totalAmount = 0;
		for(Cart cart : cartList)
		{
			CartModel cartModel = new CartModel();
			ProjectsModel projectsModel = cartModel.getProjectsModel();
			projectsModel.setId(cart.getProjects().getId());
			projectsModel.setProjectName(cart.getProjects().getProjectName());
			CategoriesModel category = projectsModel.getCategoriesModel();
			category.setCategoryName(cart.getProjects().getCategories().getCategoryName());
			cartModel.setAmount(cart.getAmount());
			cartModel.setComment(cart.getComment());
			cartModelList.add(cartModel);	
			totalAmount += cart.getAmount();
			cartModel.setTotalAmount(String.valueOf(totalAmount));
		}
		return cartModelList;
	}
	
	public void deleteProject(int id)
	{
		dao.updateProjectStatus("invalid", id);
	}
	public void forcedDeleteProject(int id)
	{
		dao.deleteProject(id);
	}
	
	public void updateProjectNDonation(int id)
	{
		ArrayList<Cart> cartList = dao.getCart(id);
		for(Cart cart: cartList)
		{
			Donation donation = new Donation();
			donation.setProjects(cart.getProjects());
			donation.setUserAccount(cart.getUserAccount());
			donation.setAmount(String.valueOf(cart.getAmount()));
			donation.setComment(cart.getComment());
			donation.setDateOfPayment(String.valueOf(new Date()));
			dao.addDonation(donation);
			int oldAmt = Integer.parseInt(dao.getProject(cart.getProjects().getId()).getAmountRecieved());
			int newAmt = oldAmt+cart.getAmount();
			dao.updateProject(String.valueOf(newAmt), cart.getProjects().getId());
			dao.updateProjectDonationCount(cart.getProjects().getDonationCount()+1,cart.getProjects().getId());
			dao.deleteCart(cart.getId());
			Report report = dao.getCountryReport(cart.getUserAccount().getCountry());
			if(report != null)
			{
				System.out.println("report :"+report);
				int count = report.getCount();
				System.out.println("count :"+count);
				int totalAmt = count+cart.getAmount();
				System.out.println("totalAmt :"+totalAmt);
				System.out.println("userAccount.getCountry() :"+cart.getUserAccount().getCountry());
				Report newReport = report;
				newReport.setCount(totalAmt);
				dao.addReport(newReport);
			}
			else
			{
				Report newReport = new Report();
				newReport.setCountry(cart.getUserAccount().getCountry());
				newReport.setCount(cart.getAmount());
				dao.addReport(newReport);
			}
			
		}
		
	}
	public void clearCart(int userId)
	{
		dao.clearCart(userId);
	}
}
