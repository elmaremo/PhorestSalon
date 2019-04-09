package com.phorest.salon.clientservices.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.phorest.salon.clientservices.constants.PhorestConstants;
import com.phorest.salon.clientservices.exceptions.PhorestCSVValidationException;
import com.phorest.salon.clientservices.jpa.client.Appointments;
import com.phorest.salon.clientservices.jpa.repository.AppointmentRepository;
import com.phorest.salon.clientservices.jpa.repository.ClientRepository;

@Component
public class PhorestAppointmentCSVProcessor implements PhorestCSVProcessor {

	@Autowired
	private ClientRepository clientrepo;

	@Autowired
	private AppointmentRepository appointmentrepo;

	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern(PhorestConstants.DATEFORMAT);
	
	private static final Logger logger = LoggerFactory.getLogger(PhorestAppointmentCSVProcessor.class);


	@Override
	public Boolean processCSV(MultipartFile file) {
		
		logger.info("START - Logging processCSV of Apoointments");
		BufferedReader br1;
		List<Appointments> listOfAppointments = new ArrayList<>();
		try {

			int count = 0;
			String line;
			InputStream is = file.getInputStream();
			br1 = new BufferedReader(new InputStreamReader(is));
			while ((line = br1.readLine()) != null) {
				if (count == 0) {
					validateCSV(line);
				}
				if (count >= 1) {
					String[] arrayofAppointments = line.split(",");
					Appointments appointments = new Appointments();
					appointments.setId(arrayofAppointments[0].trim());

					appointments.setClient(clientrepo.findById(arrayofAppointments[1].trim()).get());

					appointments.setStart_time(LocalDateTime.parse(arrayofAppointments[2].trim(), dateformatter));

					appointments.setEnd_time(LocalDateTime.parse(arrayofAppointments[3].trim(), dateformatter));

					listOfAppointments.add(appointments);

				}
				count++;
			}
			List<Appointments> savedAppoitments = appointmentrepo.saveAll(listOfAppointments);

			if (savedAppoitments != null && savedAppoitments.size() > 0)
				return true;
			
			logger.info("END - Logging processCSV of Apoointments");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	private void validateCSV(String line) {

		String[] headers = line.split(",");
		if (headers == null) {
			throw new PhorestCSVValidationException("Empty headers in Appointment csv");
		}
		if (headers.length != 4) {
			throw new PhorestCSVValidationException("Expected number of column is 4");
		}
		if (!"id".equalsIgnoreCase(headers[0].trim())) {
			throw new PhorestCSVValidationException("1st column in Appointment.csv is appointemnt Id ");
		}
		if (!"client_id".equalsIgnoreCase(headers[1].trim())) {
			throw new PhorestCSVValidationException("2nd column in Appointment.csv is client id ");
		}
		if (!"start_time".equalsIgnoreCase(headers[2].trim())) {
			throw new PhorestCSVValidationException("3rd column in Appointment.csv is start_time ");
		}
		if (!"end_time".equalsIgnoreCase(headers[3].trim())) {
			throw new PhorestCSVValidationException("4th column in Appointment.csv is end_time ");
		}

	}

}
