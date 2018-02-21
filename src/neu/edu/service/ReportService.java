package neu.edu.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import neu.edu.dao.DAO;
import neu.edu.entity.Donation;
import neu.edu.entity.Report;
import neu.edu.model.DonationModel;
import neu.edu.model.ProjectsModel;
import neu.edu.model.ReportModel;

@Service
public class ReportService {
	@Autowired
	DAO dao;	

	public ArrayList<Report> getCountryReport()
	{
		return dao.getReports();
	}
	
	public ArrayList<DonationModel> getAllDonations()
	{
		ArrayList<Donation> donationTable = dao.getAllDonations();
		ArrayList<DonationModel> donationModels = new ArrayList<>();
		for(Donation donation : donationTable)
		{
			DonationModel donationModel = new DonationModel();
			donationModel.setId(donation.getId());
			ProjectsModel projectsModel = new ProjectsModel();
			projectsModel.setAmountRecieved(donation.getProjects().getAmountRecieved());
			projectsModel.setProjectName(donation.getProjects().getProjectName());
			donationModel.setProjectsModel(projectsModel);
			donationModels.add(donationModel);
		}
		return donationModels;
	}
}
