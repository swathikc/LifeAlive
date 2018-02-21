 package neu.edu.controller;

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
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.model.DonationModel;
import neu.edu.model.UserAccountModel;	
import neu.edu.model.CardDetailsModel;
import neu.edu.model.CardNDonation;
import neu.edu.service.UserAccountService;
import neu.edu.util.JWTUtil;

@Controller
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class user 
{	
	@Autowired
	UserAccountService userAccountService;
	
	@POST
	@Path("/validateUser")
	@PermitAll
	public Response validateUser(UserAccountModel userAccountModel)
	{		
		UserAccountModel user = userAccountService.validateUser(userAccountModel.getUsername(), userAccountModel.getPassword());
		if(user!=null)
		return Response.ok().status(200).entity(user)
				.header(JWTUtil.AUTHORIZATION_PROPERTY, JWTUtil.generateToken(String.valueOf(user.getId()), new String[]{user.getRole()}))
				.build();
		else
			return null;
	}
	
	@POST
	@Path("/registerUser")
	@PermitAll
	public boolean registerUser(UserAccountModel  newUser)
	{
		Boolean valid = userAccountService.registerUser(newUser);
		System.out.println("registerValid :"+valid);
		
		if(valid)
		{
			return true;
		}
		
		return false;
	}
	
	@POST
	@Path("/cardNDonation")
	@RolesAllowed({"user"})
	//@PermitAll
	public void cardNDonation(CardNDonation cardNDonation)
	{
		userAccountService.addCardDetailsDonation(cardNDonation);
	}
	@POST
	@Path("/addCardDetails")
	@RolesAllowed({"user"})
	//@PermitAll
	public void addCardDetails(CardDetailsModel cardDetails)
	{
		userAccountService.addCardDetails(cardDetails);
	}
	
	@POST
	@Path("/donateTime")
	@RolesAllowed({"user"})
	//@PermitAll
	public void donateTime(DonationModel timeDonation)
	{
		userAccountService.donateTime(timeDonation);
	}
	
	@GET
	@Path("/getDonations")
	@RolesAllowed({"user"})
	//@PermitAll
	public ArrayList<DonationModel> getDonations(@QueryParam("userId") Integer id)
	{
		ArrayList<DonationModel> donations = userAccountService.getDonations(id);
		return donations;
	}
	
	@GET
	@Path("/serviceLimit")
	@RolesAllowed({"user"})
	//@PermitAll
	public boolean serviceLimit(@QueryParam("userId") Integer userId, @QueryParam("projectId") Integer projectId)
	{
		return userAccountService.serviceLimit(userId, projectId);
	}
	
	@GET
	@Path("/getComments")
	@PermitAll
	public ArrayList<DonationModel> getComments(@QueryParam("projectId") Integer id)
	{
		ArrayList<DonationModel> donations = userAccountService.getDonationsforProjectId(id);
		return donations;
	}
	
}
