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