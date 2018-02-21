package neu.edu.controller;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.model.CategoriesModel;
import neu.edu.service.CategoryService;

@Controller
@Path("/category")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class category {
	@Autowired
	CategoryService categoryService;
	
	@POST
	@Path("/addCategory")
	@PermitAll
	public boolean addCategory(CategoriesModel newCategory)
	{
		System.out.println("Image in rest :"+newCategory.getImage());
		return categoryService.addCategory(newCategory);
	}
	
	@GET
	@Path("/getCategoryList")
	@PermitAll
	public ArrayList<CategoriesModel> getCategoryList()
	{
		return categoryService.getCategoryList();
	}
	
	@POST
	@Path("/deleteCategory")
	@RolesAllowed({"admin"})
	//@PermitAll
	public void deleteCategory(int id)
	{
		categoryService.deleteCategory(id);
	}
	
	@POST
	@Path("/enableCategory")
	@RolesAllowed({"admin"})
	//@PermitAll
	public void enableCategory(int id)
	{
		categoryService.enableCategory(id);
	}

}
