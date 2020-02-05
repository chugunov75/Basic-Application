# Basic-Application

a simple command line app that would read 3 CSV files with next entities:
	user:     id, name, date_of_birth, city_id
	city:    id, name, country_id
  country: id, name
And output shall be written in a MySQL table with next columns:
flat_users(table): id, name, date_of_birth, city_name, country_name
