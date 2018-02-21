package neu.edu.controller;

import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.entity.Donation;
import neu.edu.entity.Report;
import neu.edu.model.DonationModel;
import neu.edu.model.ProjectsModel;
import neu.edu.model.ReportModel;
import neu.edu.service.ReportService;

@Controller
@Path("/report")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class report {

	@Autowired
	ReportService reportService;
	
	@GET
	@Path("/getCountryReport")
	@RolesAllowed({"admin"})
	//@PermitAll
	public ArrayList<Report> getCountryReport()
	{
		ArrayList<Report> reports = reportService.getCountryReport();
		return reports;
	}
	
	@GET
	@Path("/getAllDonations")
	@RolesAllowed({"admin"})
	//@PermitAll
	public ArrayList<DonationModel> getAllDonations()
	{
		ArrayList<DonationModel> donations = reportService.getAllDonations();
		return donations;
	}
	
}
