package com.phorest.salon.clientservices.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhorestClientControllerTest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void importCSVSuccess()
	  throws Exception {
	     
		StringBuilder clientStr=new StringBuilder();
		clientStr.append("id,first_name,last_name,email,phone,gender,banned \n")
		.append("e0b8ebfc-6e57-4661-9546-328c644a3764,Dori,Dietrich,patrica@keeling.net,(272) 301-6356,Male,false \n")
		.append("104fdf33-c8a2-4f1c-b371-3e9c2facdfa0,Gordon,Hammes,glen@cummerata.co,403-844-1643,Male,false");
		
		MockMultipartFile clientsFile = new MockMultipartFile("clients_csv", "clients.csv", "text/plain",
				clientStr.toString().getBytes());
		
		StringBuilder appointmentStr=new StringBuilder();
		appointmentStr.append("id,client_id,start_time,end_time \n")
		.append("67ce894a-9625-4ab7-8b91-17d83fb3fd10,e0b8ebfc-6e57-4661-9546-328c644a3764,2017-05-09 15:30:00 +0100,2017-05-09 18:30:00 +0100 \n")
		.append("a659bdd1-cd79-473a-aff4-a20c5760748d,e0b8ebfc-6e57-4661-9546-328c644a3764,2017-08-04 17:15:00 +0100,2017-08-04 18:15:00 +0100 \n")
		.append("f9fd9d10-8832-48a1-b1c9-058fea5e3232,e0b8ebfc-6e57-4661-9546-328c644a3764,2018-07-04 13:00:00 +0100,2018-07-04 13:20:00 +0100 \n")
		.append("0ad5c5da-08b2-4845-b612-3528396463f6,104fdf33-c8a2-4f1c-b371-3e9c2facdfa0,2018-09-25 11:15:00 +0100,2018-09-25 14:15:00 +0100 \n")
		.append("6f7c11ab-4705-4e2b-b32c-94df3ba97d65,104fdf33-c8a2-4f1c-b371-3e9c2facdfa0,2016-09-11 12:45:00 +0100,2016-09-11 12:55:00 +0100 \n")
		.append("58d87032-7516-4be7-9973-bc00dd5af863,104fdf33-c8a2-4f1c-b371-3e9c2facdfa0,2017-02-26 16:15:00 +0000,2017-02-26 17:15:00 +0000");

		MockMultipartFile appointmentsFile = new MockMultipartFile("appointment_csv", "appointments.csv", "text/plain",
				appointmentStr.toString().getBytes());

		StringBuilder serviceStr=new StringBuilder();
		serviceStr.append("id,appointment_id,name,price,loyalty_points \n")
		.append("a5534117-feee-4c13-a238-bb76a19bf22d,67ce894a-9625-4ab7-8b91-17d83fb3fd10,Pedicure,46.0,40 \n")
		.append("65039a46-17f0-479f-9898-a178a066499d,67ce894a-9625-4ab7-8b91-17d83fb3fd10,Spray Tan,18.0,20 \n")
		.append("25675db1-f24a-404a-8934-38cb2f70265a,67ce894a-9625-4ab7-8b91-17d83fb3fd10,Luxury Tri-Active Facial,35.0,30 \n")
		.append("fc8ad74c-10bf-4d5e-80e8-34224f9550a5,f9fd9d10-8832-48a1-b1c9-058fea5e3232,Blowdry,20.0,20 \n")
		.append("989e7c16-412f-4bcb-bc93-ee9a91f216f3,f9fd9d10-8832-48a1-b1c9-058fea5e3232,Cut & Style,28.0,30 \n")
		.append("e3afff74-2e1d-4796-ad0b-6c2a23d89633,f9fd9d10-8832-48a1-b1c9-058fea5e3232,Manicure,26.0,20 \n")
		.append("d6c1cf42-ec0c-4292-8f44-f189e98525dc,0ad5c5da-08b2-4845-b612-3528396463f6,Eyebrow Wax,15.0,15 \n")
		.append("2974524d-b5c1-4139-8507-84ea8056b9d2,0ad5c5da-08b2-4845-b612-3528396463f6,Cut & Style,28.0,30 \n")
		.append("812661f4-3d77-45de-b342-f53ad0864ca6,6f7c11ab-4705-4e2b-b32c-94df3ba97d65,Blowdry,20.0,20 \n")
		.append("216c1ed4-c23d-4a69-975e-cd3790121003,6f7c11ab-4705-4e2b-b32c-94df3ba97d65,Full Head Colour,85.0,80 \n")
		.append("8513a4cf-210b-4c6c-8595-9f1858d89c41,58d87032-7516-4be7-9973-bc00dd5af863,Eyebrow Wax,15.0,15");
		
		MockMultipartFile servicesFile = new MockMultipartFile("services_csv", "services.csv", "text/plain",
				serviceStr.toString().getBytes());

		
		StringBuilder purchasesStr=new StringBuilder();
		purchasesStr.append("id,appointment_id,name,price,loyalty_points \n")
		.append("187def47-f9cb-41a3-9041-e2c7d2130e6e,67ce894a-9625-4ab7-8b91-17d83fb3fd10,Body Wash,11.0,10 \n")
		.append("20669e7f-7308-4858-bdd1-a64dd338b186,a659bdd1-cd79-473a-aff4-a20c5760748d,Styling Gel,21.5,20 \n")
		.append("187def47-f9cb-41a3-9041-e2c7d2130e6e,67ce894a-9625-4ab7-8b91-17d83fb3fd10,Body Wash,11.0,10 \n")
		.append("7ee43e8d-52af-4229-8f28-22d5f15b52d3,6f7c11ab-4705-4e2b-b32c-94df3ba97d65,Mascara,20.0,10 \n")
		.append("30550930-d69f-40e4-af80-c934e1d8f2c6,58d87032-7516-4be7-9973-bc00dd5af863,Shampoo,19.5,20");
		
		
		MockMultipartFile purchasesFile = new MockMultipartFile("purchases_csv", "purchases.csv", "text/plain",
				purchasesStr.toString().getBytes());
	 
	    mvc.perform(MockMvcRequestBuilders.multipart("/phorest/importcsv")
                .file(clientsFile)
                .file(appointmentsFile)
                .file(servicesFile)
                .file(purchasesFile))
	    .andExpect(status().isOk())
	    .andExpect(content().json("{ \"message\": \"Success\", \"value\": true}"));
	      
	}
	
	@Test
	public void importCSV_FileNotUploaded()
	  throws Exception {
	     
		StringBuilder str=new StringBuilder();
		str.append("id,first_name,last_name,email,phone,gender,banned \n")
		.append("e0b8ebfc-6e57-4661-9546-328c644a3764,Dori,Dietrich,patrica@keeling.net,(272) 301-6356,Male,false \n")
		.append("104fdf33-c8a2-4f1c-b371-3e9c2facdfa0,Gordon,Hammes,glen@cummerata.co,403-844-1643,Male,false");
		

		MockMultipartFile appointmentsFile = new MockMultipartFile("appointment_csv", "appointments.csv", "text/plain",
				str.toString().getBytes());
		

		MockMultipartFile servicesFile = new MockMultipartFile("services_csv", "services.csv", "text/plain",
				str.toString().getBytes());
		

		MockMultipartFile purchasesFile = new MockMultipartFile("purchases_csv", "purchases.csv", "text/plain",
				str.toString().getBytes());
	 
	    mvc.perform(MockMvcRequestBuilders.multipart("/phorest/importcsv")
                .file(appointmentsFile)
                .file(servicesFile)
                .file(purchasesFile))
	    .andExpect(status().isBadRequest());
	      
	}
	

		@Test
	    public void shouldReturnTopXClientrHighestPoints() throws Exception {
		 mvc.perform(get("/phorest/topclient/loyalty/1?date=2016-02-06")).andExpect(status().isOk())
		 .andExpect(content().json("[\r\n" + 
		 		"    {\r\n" + 
		 		"        \"first_name\": \"Christen\",\r\n" + 
		 		"        \"last_name\": \"Hermann\",\r\n" + 
		 		"        \"email\": \"bobwunsch@wehner.info\",\r\n" + 
		 		"        \"phone\": \"(873) 257-2444\",\r\n" + 
		 		"        \"gender\": \"Male\",\r\n" + 
		 		"        \"loyalty_points\": 965\r\n" + 
		 		"    }\r\n" + 
		 		"]"));
	    }
		
		@Test
	    public void shouldThrowDateFormatException() throws Exception {
		 mvc.perform(get("/phorest/topclient/loyalty/1?date=2016/02/06"))
		 .andExpect(status().isBadRequest());
	    }
	
	
}