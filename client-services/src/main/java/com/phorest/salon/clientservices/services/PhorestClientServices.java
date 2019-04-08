package com.phorest.salon.clientservices.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.jpa.client.Appointments;
import com.phorest.salon.clientservices.jpa.client.Client;
import com.phorest.salon.clientservices.jpa.repository.AppointmentRepository;
import com.phorest.salon.clientservices.jpa.repository.ClientRepository;

@Service
public class PhorestClientServices {
	
	  
	  @Autowired
	  private ClientRepository clientrepo;
	  
	  @Autowired
	  private AppointmentRepository appointmentrepo;
	 
	
	public Boolean saveImportedClientInfo(MultipartFile client2, MultipartFile appointment, MultipartFile services, MultipartFile purchases) {
		
		BufferedReader br;
		List<Client> listOfClients = new ArrayList<>();
		try {
			 int count = 0;
		     String line;
		     InputStream is = client2.getInputStream();
		     br = new BufferedReader(new InputStreamReader(is));
		     while ((line = br.readLine()) != null) {
		    	 if(count>=1) {
		    	 String[] arrayofClients = line.split(",");
		    	 Client c = new Client();
			     c.setId(arrayofClients[0]);
			     c.setFirst_name(arrayofClients[1]);
			     c.setLast_name(arrayofClients[2]);
			     
			     c.setEmail(arrayofClients[3]);
			     c.setPhone(arrayofClients[4]);
			     c.setGender(arrayofClients[5]);
			     c.setBanned(Boolean.valueOf(arrayofClients[6]));
			     listOfClients.add(c);
			     
		    	 }
			     count++;
		     }
		     clientrepo.saveAll(listOfClients);
		  Optional<Client> client =   clientrepo.findById("a1fa2efb-1bf3-4d8b-a2c8-154ab7899f32");

		  
		  } catch (IOException e) {
		    System.err.println(e.getMessage());       
		  }
		BufferedReader br1;
		List<Appointments> listOfAppointments = new ArrayList<>();
		try {
			
			 DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");//TODO
			 int count = 0;
		     String line;
		     InputStream is = appointment.getInputStream();
		     br1 = new BufferedReader(new InputStreamReader(is));
		     while ((line = br1.readLine()) != null) {
		    	 if(count>=1) {
		    	 String[] arrayofAppointments = line.split(",");
		    	 Appointments appointments = new Appointments();
		    	 appointments.setId(arrayofAppointments[0]);
		    	 
		    	 appointments.setClient(clientrepo.findById(arrayofAppointments[1]).get());
		    
		    	 appointments.setStart_time(LocalDateTime.parse(arrayofAppointments[2],dateformatter));
			     
		    	 appointments.setEnd_time(LocalDateTime.parse(arrayofAppointments[3],dateformatter));
			    
		    	 listOfAppointments.add(appointments);
			     
		    	 }
			     count++;
		     }
		     appointmentrepo.saveAll(listOfAppointments);

		  
		  } catch (IOException e) {
		    System.err.println(e.getMessage());       
		  }
		
		return Boolean.FALSE;
		
	}

}
