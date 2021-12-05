"# ProductSynchronizer" 


This reads a CSV file including all the products coming from an ERP in the following format:

SKU, title, description, price, quantity
0001, “Sapiens: A Brief History of Humankind”, “A beautiful book about history of humans”, 100, 10
0002, “Learning Python”, “A beautiful book about Python”, 20, 100


Steps to run :
--------------------------


1. Build the Jar using "mvn clean install"

2. Create folder as input and output and pass the directory to the below command

3. Run the below cmd in cmd prompt.
java -jar productSynchronizer-1.0-SNAPSHOT.jar com.mercado.productSynchronizer.Application --input.path=c:/input/ --output.path=c:/output/

**Note: ** 
Put the files in **input directory**, it will pick and make post call to other service and load the data to h2 database.
