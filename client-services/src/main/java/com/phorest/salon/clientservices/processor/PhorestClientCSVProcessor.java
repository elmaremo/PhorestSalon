package com.phorest.salon.clientservices.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.controller.PhorestClientController;
import com.phorest.salon.clientservices.exceptions.PhorestCSVParsingException;
import com.phorest.salon.clientservices.exceptions.PhorestCSVValidationException;
import com.phorest.salon.clientservices.jpa.client.Client;
import com.phorest.salon.clientservices.jpa.repository.ClientRepository;

@Component
public class PhorestClientCSVProcessor implements PhorestCSVProcessor {

	@Autowired
	private ClientRepository clientrepo;
	
	private static final Logger logger = LoggerFactory.getLogger(PhorestClientCSVProcessor.class);


	@Override
	public Boolean processCSV(MultipartFile file) {

		logger.debug("Start - Debugging processCSV of Client");
		BufferedReader br;
		List<Client> listOfClients = new ArrayList<>();
		try {
			int count = 0;
			String line;
			InputStream is = file.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				if (count == 0) {
					validateCSV(line);
				}
				if (count >= 1) {
					String[] arrayofClients = line.split(",");
					if (arrayofClients.length != 7) {
						throw new PhorestCSVValidationException("Expected number of column is 7");
					}

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
			logger.debug("End - Debugging processCSV of Client");
		} catch (IOException e) {
			logger.isDebugEnabled();
			throw new PhorestCSVParsingException("Fail to parse the Client CSV");
		}
		return Boolean.TRUE;
	}

	private void validateCSV(String line) {

		String[] headers = line.split(",");
		if (headers == null) {
			throw new PhorestCSVValidationException("Empty headers in Client csv");
		}
		if (headers.length != 7) {
			throw new PhorestCSVValidationException("Expected number of column is 7");
		}
		if (!"id".equalsIgnoreCase(headers[0])) {
			throw new PhorestCSVValidationException("1st column in Client.csv is Client Id ");
		}
		if (!"first_name".equalsIgnoreCase(headers[1])) {
			throw new PhorestCSVValidationException("2nd column in Client.csv is firstName ");
		}
		if (!"last_name".equalsIgnoreCase(headers[2])) {
			throw new PhorestCSVValidationException("3rd column in Client.csv is last_name ");
		}
		if (!"email".equalsIgnoreCase(headers[3])) {
			throw new PhorestCSVValidationException("4th column in Client.csv is email ");
		}
		if (!"phone".equalsIgnoreCase(headers[4])) {
			throw new PhorestCSVValidationException("5th column in Client.csv is phone ");
		}
		if (!"gender".equalsIgnoreCase(headers[5])) {
			throw new PhorestCSVValidationException("6th column in Client.csv is gender ");
		}
		if (!"banned".equalsIgnoreCase(headers[6])) {
			throw new PhorestCSVValidationException("7th column in Client.csv is banned ");
		}

	}

}
