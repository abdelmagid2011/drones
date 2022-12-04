Drones demo project
There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

build steps using java version 17
run scropt 
	windows: .\mvnw.cmd spring-boot:run
	linux: ./mvnw spring-boot:run
	

functionalities:	
	/api/drone post request to register a drone
	/api/drone get request to list all drones
	/api/drone/load/{serial} post request to add items to drone
	/api/drone/finish-loading/{serial} put request to finish loading and change state to loaded
	/api/drone/loaded-items/{serial} get request to get loaded items for specific drone
	/api/drone/loaded get request to get loaded drones 
	/api/drone/available get request to get available drones for loading
	
	

Assumptions:
	- there is external service will update the battary of the drones
	- Battary check periodic interval can be changed through application.properities
	- loaded items only have describtion and weight
	- we can increament drone loaded items (add items on multiple times) then call finsish loaded to be ready for delivering
	- Drones Data generation can be called through the URL /api/drones-generator/{count}/{base-serial}