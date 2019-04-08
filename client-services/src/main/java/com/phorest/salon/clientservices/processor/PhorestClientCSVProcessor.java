package com.phorest.salon.clientservices.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.jpa.client.Client;
import com.phorest.salon.clientservices.jpa.repository.ClientRepository;

@Component
public class PhorestClientCSVProcessor implements PhorestCSVProcessor {

	  @Autowired
	  private ClientRepository clientrepo;
	  
	@Override
	public Boolean processCSV(MultipartFile file) {
		// TODO Auto-generated method stub
 
		BufferedReader br; 
		List<Client> listOfClients = new ArrayList<>();
		try {
			 int count = 0;
		     String line;
		     InputStream is = file.getInputStream();
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
		     
		
	 } catch (IOException e) {
	    System.err.println(e.getMessage());       //TODO
	  }
		return Boolean.TRUE;
}
	
}
