package neu.edu.controller;

import java.text.ParseException;
import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import neu.edu.model.CartModel;
import neu.edu.model.DonationModel;
import neu.edu.model.ProjectsModel;
import neu.edu.service.ProjectsService;

@Controller
@Path("/projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class projects {
	
	@Autowired
	ProjectsService projectsService;
	
	@GET
	@Path("/projectList")
	@PermitAll
	public ArrayList<ProjectsModel> getAllProjects() throws ParseException
	{
		ArrayList<ProjectsModel> allProjects = projectsService.getAllProjects();
		return allProjects;
	}
	
	@POST
	@Path("/addProject")
	@RolesAllowed({"user"})
	//@PermitAll
	public void addProject(ProjectsModel newProject)
	{
		projectsService.addProject(newProject);
	}
	
	@GET
	@Path("/getProjects")
	@PermitAll
	public ArrayList<ProjectsModel> getProjects(@QueryParam("id") Integer categoryId)
	{
		ArrayList<ProjectsModel> projects = projectsService.getProjects(categoryId);
		return projects;
	}
	
	@POST
	@Path("/addToCart")
	@RolesAllowed({"user"})
	//@PermitAll
	public void addToCart(CartModel cartDetails)
	{
		projectsService.addToCart(cartDetails);
	}
	@GET
	@Path("/getCart")
	@PermitAll
	public ArrayList<CartModel> getCart(@QueryParam("id") Integer userId)
	{
		ArrayList<CartModel> cartModel = projectsService.getCart(userId);
		return cartModel;
	}
	
	@GET
	@Path("/getProject")
	@PermitAll
	public ProjectsModel getProject(@QueryParam("id") Integer projectId)
	{
		ProjectsModel project = projectsService.getProject(projectId);
		return project;
	}
	
	@GET
	@Path("/getMyProject")
	@RolesAllowed({"user"})
	//@PermitAll
	public ArrayList<ProjectsModel> getMyProjects(@QueryParam("id") Integer userId)
	{
		ArrayList<ProjectsModel> projects = projectsService.getProjects(userId);
		return projects;
	}
	
	@POST
	@Path("/approveProject")
	@RolesAllowed({"admin"})
	//@PermitAll
	public void approveProject(int id)
	{
		projectsService.approveProject(id);
	}
	
	@POST
	@Path("/deleteProject")
	@RolesAllowed({"admin"})
	//@PermitAll
	public void deleteProject(int id)
	{
		projectsService.deleteProject(id);
	}
	
	@POST
	@Path("/forcedDeleteProject")
	@RolesAllowed({"admin"})
	//@PermitAll
	public void forcedDeleteProject(int id)
	{
		projectsService.forcedDeleteProject(id);
	}
	
	@POST
	@Path("/updateProjectNDonation")
	@RolesAllowed({"user"})
	//@PermitAll
	public void updateProjectNDonation(int id)
	{
		projectsService.updateProjectNDonation(id);
	}
	
	@POST
	@Path("/clearCart")
	@RolesAllowed({"user"})
	//@PermitAll
	public void clearCart(int userId)
	{
		projectsService.clearCart(userId);
	}
}
