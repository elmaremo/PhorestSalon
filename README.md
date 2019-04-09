# PhorestSalon
Phorest Salon Software

This is the github for Phorest Salon Software

What it does?
- Let's the customer import their client information stored in csv to DB
- Let's the customer choose and see the top 'X' clients with highest loyality points from a requested date

REST API's are as follows

API 
http://localhost:9000/phorest/importcsv  POST
upload the respective csv's and place the request

clients_csv
appointment_csv
services_csv
purchases_csv

API
http://localhost:9000/phorest/topclient/loyalty/{size}?date={yyyy-MM-DD}  GET
sample 
size:50
date:2016-02-06
