package neu.edu.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import neu.edu.entity.CardDetails;
import neu.edu.entity.Cart;
import neu.edu.entity.Categories;
import neu.edu.entity.Donation;
import neu.edu.entity.Projects;
import neu.edu.entity.Report;
import neu.edu.entity.UserAccount;
@Repository
public class DAO 
{
	@Autowired
	SessionFactory sessionFactory;
	
	public UserAccount validateUser(String username, String password)
	{
		Session session = getSession();
		
		Query query = session.createQuery("from UserAccount where username=:username and password=:password");
		query.setString("username", username);
		query.setString("password", password);
		
		UserAccount user = (UserAccount) query.uniqueResult();
		return user;
	}
	
	public UserAccount userExistsCheck(String username)
	{
		Session session = getSession();
		
		Query query = session.createQuery("from UserAccount where username=:username");
		query.setString("username", username);
		
		UserAccount userAccount = (UserAccount) query.uniqueResult();		
		return userAccount;
	}
	
	@Transactional
	public void registerUser(UserAccount userAccount)
	{
		Session session = getSession();
		session.save(userAccount);
		session.flush();
	}
	
	@Transactional
	public UserAccount getUserAccount(int id)
	{
		Session session = getSession();
		return session.get(UserAccount.class, id);
	}
	
	@Transactional
	public void addCategory(Categories newCategory)
	{
		Session session = getSession();
		session.save(newCategory);
		session.flush();
	}
	
	public ArrayList<Categories> getCategoryList()
	{
		Session session = getSession();
		return (ArrayList<Categories>) session.createCriteria(Categories.class).list();
	}
	
	@Transactional
	public Categories getCategories(int id)
	{
		Session session = getSession();
		return session.get(Categories.class, id);
	}
	
	@Transactional
	public Categories getCategoryByName(String categoryName)
	{
		Session session = getSession();
		Query query = session.createQuery("from Categories where categoryName=:categoryName");
		query.setString("categoryName", categoryName);
		
		Categories categories = (Categories) query.uniqueResult();
		return categories;
	}
	
	@Transactional
	public void deleteCategories(int id)
	{
		Session session = getSession();
		session.delete(session.get(Categories.class, id));
	}
	@Transactional
	public void updateCategory(String status, int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Categories set status = :status where id = :id");
		query.setString("status", status);
		query.setInteger("id", id);	
		query.executeUpdate();
		session.flush();
	}
	
	@Transactional
	public void addProject(Projects newProject)
	{
		Session session = getSession();
		session.saveOrUpdate(newProject);
		session.flush();
	}
	@Transactional
	public void deleteProject(int id)
	{
		Session session = getSession();
		session.delete(session.get(Projects.class, id));
	}
	
	@Transactional
	public void updateProject(String amount, int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects set amountRecieved = :amount where id = :id");
		query.setString("amount", amount);
		query.setInteger("id", id);	
		query.executeUpdate();
		session.flush();
	}
	@Transactional
	public void updateProjectDonationCount(int donationCount, int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects set donationCount = :donationCount where id = :id");
		query.setInteger("donationCount", donationCount);
		query.setInteger("id", id);	
		query.executeUpdate();
		session.flush();
	}
	
	@Transactional
	public void updateProjectStatus(String status, int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects set status = :status where id = :id");
		query.setString("status", status);
		query.setInteger("id", id);	
		query.executeUpdate();
		session.flush();
	}
	@Transactional
	public void updateProjectStatusCategoryId(String status, int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Projects set status = :status where categoryId = :id");
		query.setString("status", status);
		query.setInteger("id", id);	
		query.executeUpdate();
		session.flush();
	}
	
	public ArrayList<Projects> getAllProjects()
	{
		Session session = getSession();
		Query query = session.createQuery("From Projects");
		ArrayList<Projects> projects = (ArrayList<Projects>) query.list();
		return projects;
	}
	
	public ArrayList<Projects> getProjects(int categoryId)
	{
		Session session = getSession();
		Query query = session.createQuery("From Projects where categoryId=:categoryId");
		query.setInteger("categoryId", categoryId);
		
		ArrayList<Projects> projects = (ArrayList<Projects>) query.list();
		return projects;
	}
	
	public Projects getProject(int projectId)
	{
		Session session = getSession();
		Query query = session.createQuery("From Projects where id=:projectId");
		query.setInteger("projectId", projectId);
		
		Projects project = (Projects) query.uniqueResult();
		return project;
	}
	public ArrayList<Projects> getMyProjects(int userId)
	{
		Session session = getSession();
		Query query = session.createQuery("From Projects where creatorId=:userId");
		query.setInteger("userId", userId);
		ArrayList<Projects> projects = (ArrayList<Projects>) query.list();
		return projects;
	}
	
	@Transactional
	public void addCardDetails(CardDetails cardDetails)
	{
		Session session = getSession();
		session.save(cardDetails);
		session.flush();
	}
	public CardDetails getCardDetails(String cardNo)
	{
		Session session = getSession();
		Query query = session.createQuery("From CardDetails where cardNo=:cardNo");
		query.setString("cardNo", cardNo);
		
		CardDetails cardDetails = (CardDetails) query.uniqueResult();
		return cardDetails;
	}
	
	@Transactional
	public void addDonation(Donation donation)
	{
		Session session = getSession();
		session.save(donation);
		session.flush();
	}
	public ArrayList<Donation> getDonations(int userId)
	{
		Session session = getSession();
		Query query = session.createQuery("From Donation where userId=:userId");
		query.setInteger("userId", userId);
		
		ArrayList<Donation> donation = (ArrayList<Donation>) query.list();
		return donation;
	}
	public long serviceLimit(int userId, int projectId)
	{
		Session session = getSession();
		Query query = session.createQuery("select count(*) from Donation where userId=:userId and projectId=:projectId");
		query.setInteger("userId", userId);
		query.setInteger("projectId", projectId);
		
		long donationCount = (long) query.uniqueResult();
		return donationCount;
	}
	public ArrayList<Donation> getDonationsforProjectId(int id)
	{
		Session session = getSession();
		Query query = session.createQuery("From Donation where projectId=:id");
		query.setInteger("id", id);
		
		ArrayList<Donation> donation = (ArrayList<Donation>) query.list();
		return donation;
	}
	public ArrayList<Donation> getAllDonations()
	{
		Session session = getSession();
		Query query = session.createQuery("From Donation");
		ArrayList<Donation> donations = (ArrayList<Donation>) query.list();
		return donations;
	}
	
	public ArrayList<Report> getReports()
	{
		Session session = getSession();
		Query query = session.createQuery("From Report");
		ArrayList<Report> report = (ArrayList<Report>) query.list();
		return report;
	}
	public Report getCountryReport(String country)
	{
		Session session = getSession();
		Query query = session.createQuery("From Report where country=:country");
		query.setString("country", country);
		Report report = (Report) query.uniqueResult();
		return report;
	}
	@Transactional
	public void addReport(Report report)
	{
		Session session = getSession();
		session.saveOrUpdate(report);
		session.flush();
	}
	@Transactional
	public void updateCountryReport(int count, int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Report set count=:count where id=:id");
		query.setInteger("count", count);	
		query.setInteger("id", id);
		query.executeUpdate();
		session.flush();
	}
	@Transactional
	public void addToCart(Cart cart)
	{
		Session session = getSession();
		session.save(cart);
		session.flush();
	}
	public ArrayList<Cart> getCart(int userId)
	{
		Session session = getSession();
		Query query = session.createQuery("From Cart where userId=:userId");
		query.setInteger("userId", userId);
		
		ArrayList<Cart> cartList = (ArrayList<Cart>) query.list();
		return cartList;
	}
	public void deleteCart(int id)
	{
		Session session = getSession();
		session.delete(session.get(Cart.class, id));
		session.flush();
	}
	public void clearCart(int userId)
	{
		Session session = getSession();
		Query query = session.createQuery("Delete from Cart where userId=:userId");
		query.setInteger("userId", userId);
		query.executeUpdate();
		session.flush();
	}
	
	private Session getSession()
	{
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
		}
		catch(Exception ex)
		{
			session = sessionFactory.openSession();
		}
		
		return session;
	}
	
}
