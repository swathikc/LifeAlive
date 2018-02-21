package neu.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.dao.DAO;
import neu.edu.entity.Categories;
import neu.edu.entity.Projects;
import neu.edu.model.CategoriesModel;
import neu.edu.model.ProjectsModel;

@Service
public class CategoryService {

	@Autowired
	DAO dao;
	
	public boolean addCategory(CategoriesModel newCategory)
	{
		Categories tableCategory = dao.getCategoryByName(newCategory.getCategoryName());
		if(tableCategory == null)
		{
			Categories category = new Categories();
			category.setCategoryName(newCategory.getCategoryName());
			category.setDescription(newCategory.getDescription());
			category.setStatus("valid");
			category.setImage(newCategory.getImage());
			dao.addCategory(category);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public ArrayList<CategoriesModel> getCategoryList()
	{
		ArrayList<Categories> categoryList = dao.getCategoryList();
		ArrayList<CategoriesModel> categoryListModel = new ArrayList<>();
		for(Categories category : categoryList)
		{
			CategoriesModel categoryModel = new CategoriesModel();
			categoryModel.setId(category.getId());
			categoryModel.setCategoryName(category.getCategoryName());
			categoryModel.setDescription(category.getDescription());
			categoryModel.setStatus(category.getStatus());
			categoryModel.setImage(category.getImage());
			categoryListModel.add(categoryModel);
		}
		return categoryListModel;
	}
	
	public void deleteCategory(int id)
	{
		ArrayList<Projects> projects = dao.getProjects(id);
		System.out.println("Projects :"+projects);
		if(projects.size() == 0)
		{
			System.out.println("Delete Category was called");
			dao.deleteCategories(id);
			
		}
		else
		{
			System.out.println("update Category was called");
			dao.updateCategory("invalid", id);
			dao.updateProjectStatusCategoryId("invalid", id);
		}
	}
	
	public void enableCategory(int id)
	{
		dao.updateCategory("valid", id);
		dao.updateProjectStatusCategoryId("valid", id);
	}
}
