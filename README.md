# fgen

## Description

**fgen** is an application whose goal is to allow users to generate 
invoices emitted between registered customers on the system.

The customer can be a particular person or a company. It is 
identified on the system by a unique code. Then, it will be defined 
by some required data like the name and the TIN (Tax Identification 
Number). Besides, it will have optional data such as the address, 
the city, the province, the ZIP code and the IBAN.

Once the customer is registered on the system, the application will 
allow us to modify all its data (except the code). Furthermore, it 
also offers the possibility of removing the user, and even 
restoring it.

The invoice is composed by the sender and the recipient customers, 
the list of goods purchased or services provided, which will be 
called as “items”, and a set of subtotals which can alter the 
invoice total.

Each “item” includes the quantity, the description and the price. 
The subtotal can be a discount or a tax, which will be defined by a 
name and a percentage. Besides, the invoice will also contain 
additional metadata like generation datetime.

Respecting its characteristics, an invoice can have the same 
customer as recipient and sender at the same time. Then, its 
generation is based on an invoice template which can be configured. 
Finally, once the invoice has been created, it cannot be edited or 
removed.

The invoice template indicates how each invoice attribute must be 
filled on the generated invoice. It is composed by the mapping 
between each attribute and its filled position, apart from the 
identifier and the type name. Besides, it stores the Excel file to 
use to fill the invoice.

Finally, the generated invoice can be saved in your local computer 
in Excel (XLSX) or PDF format.

## Architecture & Technologies

In the first phase of the project, a monolithic architecture which
will be composed by the next three layers:

- **Presentation**: It will responsible of showing the system
information to the user. In other words, it will contain all the UI.
- **Application**: It will contain all the business logic with all
the use cases.
- **Persistence**: This layer will be responsible of accessing to the
data. It will interact with the database.

Then, each entity will be distinguished in a separate folder, so it is
independent from the others. This will facilitate a future transition
to isolated microservices. Note that common resources for all entities
will be set in a shared folder.

This architecture has been chosen because it will facilitate the
development and the product testing during this first project phase,
as it is preferred to set more focus on the logic at this moment. Then,
improvements could be made based on this first version.

Respect to the technology stack, Java will be used as the programming
language because with the help of the Netbeans IDE, it will accelerate
the UI development, so we can set a better focus to the logic and the
system functionality, as mentioned before. The chosen programming
language also has an easy learning curve and its generated applications
can be executed easily on any operating system after installing Java.
Besides, the Mongo database has been chosen due to its installation
facility on any place (locally and cloud), scability and flexibility
on the data structure. Keep in mind that there are some entities like
the customer which will have optional data. Finally, the conversion
from Excel to PDF will be done thanks to the [Gembox](https://www.gemboxsoftware.com/)
library, which offers a free license to process 150 rows per sheet and
a maximum of 5 sheets per file.

## License

A GNU General Public License v3.0 license (GNU GPLv3) has been chosen because it lets people to do whatever they want with the project, except distributing closed source versions.

Source: [https://choosealicense.com/](https://choosealicense.com/)